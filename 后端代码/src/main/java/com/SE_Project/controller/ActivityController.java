package com.SE_Project.controller;

import com.SE_Project.pojo.Activity;
import com.SE_Project.pojo.Result;
import com.SE_Project.pojo.ScreenItems;
import com.SE_Project.service.ActivityService;
import com.SE_Project.service.GroupService;
import com.SE_Project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Vector;

import static com.SE_Project.utils.util.parseArrayString;
import static com.SE_Project.utils.util.vectorToString;

@RestController
public class ActivityController {
    @Autowired
    private ActivityService activityService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;

    /**
     * @Author 余泽锋
     * @Date 2023-11-27
     * @Description 获取所有活动的列表
     * @Param 无
     * @return Result对象，包含活动列表的信息
     **/
    @GetMapping("/get/activity")
    public Result getAllActivity() {
        try {
            List<Activity> activityList = activityService.activityList();
            for (Activity act : activityList) {
                // 读取头像文件并转换为 Base64 编码
                String imgsrc = act.getImgsrc();

                Path imagePath = Paths.get(imgsrc);

                byte[] imageBytes = null;

                // 判断文件是否存在
                if (Files.exists(imagePath)) {
                    // 读取文件内容并转换为 Base64 编码
                    imageBytes = Files.readAllBytes(imagePath);
                }

                // 将 Base64 编码的头像放入响应对象中
                act.setImgsrc(Base64.getEncoder().encodeToString(imageBytes));
            }

            return Result.success(activityList);
        } catch (Exception e) {
            return Result.error("Failed to fetch activity data: " + e.getMessage());
        }
    }

    /**
     * @Author 余泽锋
     * @Date 2023-12-04
     * @Description 得到某活动的详细信息
     * @Param [actid - 活动id]
     * @return Result对象，包含活动详细信息
     **/
    @GetMapping("/get/detail")
    public Result getActivityDetail(@RequestParam String actid) {
        try {
            Activity activity = activityService.getActivityDetail(actid);
            String imgsrc = activity.getImgsrc();

            Path imagePath = Paths.get(imgsrc);

            byte[] imageBytes = null;

            // 判断文件是否存在
            if (Files.exists(imagePath)) {
                // 读取文件内容并转换为 Base64 编码
                imageBytes = Files.readAllBytes(imagePath);
            }

            // 将 Base64 编码的头像放入响应对象中
            activity.setImgsrc(Base64.getEncoder().encodeToString(imageBytes));

            return Result.success(activity);
        } catch (Exception e) {
            return Result.error("Failed to fetch a activity: " + e.getMessage());
        }
    }

    /**
     * @Author 余泽锋
     * @Date 2023-11-27
     * @Description 创建活动
     * @Param [file - 活动图片文件, actname - 活动名称, actid - 活动id, date - 活动日期,
     *        location - 活动地点, type - 活动类型, organizer - 组织者, reward - 奖励,
     *        status - 活动状态, permission - 活动权限, introduction - 活动简介]
     * @return Result对象，包含操作结果信息
     **/
    @PostMapping("/post/create_act")
    public Result createAct(@RequestParam("file") MultipartFile file,
                            @RequestParam("actname") String actname,
                            @RequestParam("date") String date,
                            @RequestParam("location") String location,
                            @RequestParam("type") String type,
                            @RequestParam("organizer") String organizer,
                            @RequestParam("reward") String reward,
                            @RequestParam("status") char status,
                            @RequestParam("permission") char permission,
                            @RequestParam("introduction") String introduction
    ) {
        try {
            // 设置文件保存路径
            String uploadDir = "../images/activity/";

            // 构建文件路径
            Path path = Paths.get(uploadDir + actname + ".png");

            // 从MultipartFile获取文件内容
            byte[] bytes = file.getBytes();

            // 将文件写入指定路径
            Files.write(path, bytes);

            // 将活动信息插入到数据库
            String imgsrc = path.toString().replace('\\', '/');
            activityService.insertActivity(actname, type, organizer, date, location, reward, permission, imgsrc, status, introduction, 0);

            return Result.success("活动创建成功");
        } catch (DuplicateKeyException e) {
            // 处理主键（actname）冲突
            return Result.error("无法创建活动。活动名称 '" + actname + "' 已存在。请使用不同的名称。");
        } catch (IOException e) {
            // 处理文件写入异常
            return Result.error("创建活动失败：" + e.getMessage());
        }
    }

    /**
     * @Author 余泽锋
     * @Date 2023-12-04
     * @Description 删除某个活动
     * @Param [actid - 活动id]
     * @return Result对象，包含操作结果信息
     **/
    @DeleteMapping("/delete/activity")
    public Result deleteActivity(@RequestParam("actid") String actid) {
        try {
            // 获取与活动关联的所有小组ID
            List<String> groupIds = groupService.getGroupidByActid(actid);

            // 循环处理每个小组
            for (String groupId : groupIds) {
                try {
                    // 获取小组中所有已注册的用户
                    Vector<String> users = parseArrayString(groupService.getRegisteredByGroupid(groupId));

                    // 逐个更新用户参与的活动信息
                    for (String openid : users) {
                        // 获取用户已参与的活动列表
                        String participated = userService.getParticipated(openid);
                        Vector<String> participatedArray = parseArrayString(participated);

                        // 移除当前活动ID
                        participatedArray.remove(actid);

                        // 更新用户参与的活动信息
                        participated = vectorToString(participatedArray);
                        userService.updateParticipated(participated, openid);
                    }

                    // 删除小组
                    groupService.deleteGroup(groupId);

                    // 返回删除成功的结果
                    return Result.success("记录删除成功");
                } catch (Exception e) {
                    // 处理小组删除异常
                    return Result.error("删除记录失败：" + e.getMessage());
                }
            }

            // 获取活动详情中的图片路径
            String imgsrc = activityService.getActivityDetail(actid).getImgsrc();

            // 构建路径对象
            Path imagePath = Paths.get(imgsrc);

            try {
                // 删除活动对应的图片文件
                Files.deleteIfExists(imagePath);
            } catch (Exception e) {
                // 处理文件删除异常
            }

            // 删除活动
            activityService.deleteActivity(actid);

            // 返回删除成功的结果
            return Result.success("记录删除成功");
        } catch (Exception e) {
            // 处理删除活动记录异常
            return Result.error("删除记录失败：" + e.getMessage());
        }
    }


    /**
     * @Author 刘泽铭
     * @Date 2023-11-27
     * @Description 修改活动信息（不修改图片）
     * @Param [actname - 活动名称, actid - 活动id, date - 活动日期, location - 活动地点,
     *        type - 活动类型, organizer - 组织者, reward - 奖励, status - 活动状态,
     *        permission - 活动权限, introduction - 活动简介]
     * @return Result对象，包含操作结果信息
     **/
    @PostMapping("/update/act1")
    public Result updateGroup1(
            @RequestParam("actname") String actname,
            @RequestParam("actid") String actid,
            @RequestParam("date") String date,
            @RequestParam("location") String location,
            @RequestParam("type") String type,
            @RequestParam("organizer") String organizer,
            @RequestParam("reward") String reward,
            @RequestParam("status") char status,
            @RequestParam("permission") char permission,
            @RequestParam("introduction") String introduction
    ) {
        try {
            String imgsrc = "";
            imgsrc = activityService.getActivityDetail(actid).getImgsrc();  // 获取活动详情中的图片路径

            // 调用服务层方法更新活动信息
            activityService.updateActivity(actid, actname, type, organizer, date, location, reward, permission, imgsrc, status, introduction);

            return Result.success("Activity updated successfully");  // 返回成功消息
        } catch (Exception e) {
            return Result.error("Failed to update activity: " + e.getMessage());  // 返回更新失败的错误消息
        }
    }

    /**
     * @Author 余泽锋
     * @Date 2023-12-11
     * @Description 修改活动信息（修改图片）
     * @Param [file - 活动图片文件, actname - 活动名称, actid - 活动id, date - 活动日期,
     *        location - 活动地点, type - 活动类型, organizer - 组织者, reward - 奖励,
     *        status - 活动状态, permission - 活动权限, introduction - 活动简介]
     * @return Result对象，包含操作结果信息
     **/
    @PostMapping("/update/act")
    public Result updateActivity(@RequestParam("file") MultipartFile file,
                                 @RequestParam("actname") String actname,
                                 @RequestParam("actid") String actid,
                                 @RequestParam("date") String date,
                                 @RequestParam("location") String location,
                                 @RequestParam("type") String type,
                                 @RequestParam("organizer") String organizer,
                                 @RequestParam("reward") String reward,
                                 @RequestParam("status") char status,
                                 @RequestParam("permission") char permission,
                                 @RequestParam("introduction") String introduction) {
        try {
            // 获取旧的图片路径
            String imgsrc = "";
            String oldActname = activityService.getActivityDetail(actid).getActname();
            String oldUploadDir = "../images/activity/";
            Path oldPath = Paths.get(oldUploadDir + oldActname + ".png");

            try {
                // 删除旧的图片文件
                Files.delete(oldPath);
                System.out.println("旧的图片删除成功");
            } catch (IOException e) {
                System.err.println("旧的图片删除失败：" + e.getMessage());
            }

            // 设置新的图片保存路径
            String uploadDir = "../images/activity/";
            Path path = Paths.get(uploadDir + actname + ".png");

            // 从MultipartFile获取文件内容
            byte[] bytes = file.getBytes();

            // 将新的图片写入指定路径
            Files.write(path, bytes);
            imgsrc = path.toString().replace('\\', '/');

            // 更新活动信息（包括图片路径）
            activityService.updateActivity(actid, actname, type, organizer, date, location, reward, permission, imgsrc, status, introduction);

            // 返回成功的结果
            return Result.success("活动信息更新成功");

        } catch (Exception e) {
            // 返回更新失败的错误消息
            return Result.error("更新活动信息失败: " + e.getMessage());
        }
    }

    /**
     * @Author 刘泽铭
     * @Date 2023-12-04
     * @Description 活动搜索
     * @Param [keyword - 搜索关键词]
     * @return Result对象，包含搜索到的活动列表
     **/
    @GetMapping("/homepage/search")
    public Result searchActivity(String keyword) {
        try {
            // 通过关键词进行搜索活动列表
            List<Activity> activityList = activityService.searchActivities(keyword);
            for (Activity act : activityList) {
                // 读取头像文件并转换为 Base64 编码
                String imgsrc = act.getImgsrc();

                Path imagePath = Paths.get(imgsrc);

                byte[] imageBytes = null;

                // 判断文件是否存在
                if (Files.exists(imagePath)) {
                    // 读取文件内容并转换为 Base64 编码
                    imageBytes = Files.readAllBytes(imagePath);
                }

                // 将 Base64 编码的头像放入响应对象中
                act.setImgsrc(Base64.getEncoder().encodeToString(imageBytes));
            }

            return Result.success(activityList);
        } catch (Exception e) {
            return Result.error("Failed to fetch activity data: " + e.getMessage());
        }
    }

    /**
     * @Author 刘泽铭
     * @Date 2023-12-04
     * @Description 活动筛选
     * @Param [data - 包含筛选条件的ScreenItems对象]
     * @return Result对象，包含筛选结果的活动列表
     **/
    @PostMapping("/homepage/select")
    public Result filterActivities(@RequestBody ScreenItems data) {
        try {
            // 从请求体中获取筛选条件数据
            List<String> selectedItems = data.getSelectedItems();
            List<String> hostSelectedItems = data.getHostSelectedItems();

            // 调用服务层方法进行活动筛选
            List<Activity> activityList = activityService.searchActivitiesByFilter(selectedItems, hostSelectedItems);

            // 遍历筛选结果，读取头像文件并转换为 Base64 编码
            for (Activity act : activityList) {
                String imgsrc = act.getImgsrc();
                Path imagePath = Paths.get(imgsrc);
                byte[] imageBytes = null;

                // 判断文件是否存在
                if (Files.exists(imagePath)) {
                    // 读取文件内容并转换为 Base64 编码
                    imageBytes = Files.readAllBytes(imagePath);
                }

                // 将 Base64 编码的头像放入响应对象中
                act.setImgsrc(Base64.getEncoder().encodeToString(imageBytes));
            }

            // 返回筛选结果
            return Result.success(activityList);

        } catch (Exception e) {
            // 返回错误信息，说明筛选失败
            return Result.error("Failed to fetch activity data: " + e.getMessage());
        }
    }


    /**
     * @Author 余泽锋
     * @Date 2023-12-11
     * @Description 更新轮播图
     * @Param [actid - 活动id]
     * @return Result对象，包含操作结果信息
     **/
    @PostMapping("/update/carousel")
    public Result updateCarousel(@RequestParam("actid") String actid) {
        try {
            // 调用服务层方法更新轮播图
            activityService.updateCarousel(actid);
            // 返回成功的结果
            return Result.success("Carousel updated successfully");
        } catch (Exception e) {
            // 如果发生异常，返回错误信息
            return Result.error("Failed to update carousel: " + e.getMessage());
        }
    }

}
