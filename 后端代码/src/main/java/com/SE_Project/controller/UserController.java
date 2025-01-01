package com.SE_Project.controller;

import com.SE_Project.pojo.Group;
import com.SE_Project.pojo.Result;
import com.SE_Project.pojo.User;
import com.SE_Project.service.GroupService;
import com.SE_Project.service.UserInfoService;
import com.SE_Project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static com.SE_Project.utils.util.parseArrayString;
import static com.SE_Project.utils.util.vectorToString;


@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserInfoService userInfoService;

    /**
     * @Author 余泽锋
     * @Date 2023.11.27
     * @Description 更新用户头像
     * @Param [file - 上传的头像文件, openid - 用户ID]
     * @return Result对象，包含操作结果信息
     **/
    @PostMapping("/update/avatar")
    public Result updateAvatar(@RequestParam("file") MultipartFile file,
                               @RequestParam("openid") String openid) {
        try {
            // 获取文件字节数组
            byte[] bytes = file.getBytes();

            // 设置文件保存路径
            String uploadDir = "../images//avatar/";
            Path path = Paths.get(uploadDir + openid + ".png");

            // 将文件保存到指定路径
            Files.write(path, bytes);
            String imgsrc = path.toString().replace('\\', '/');

            // 更新 User 表中的 imgsrc 字段
            userService.updateAvatar(openid, imgsrc);

            return Result.success("File uploaded successfully.");
        } catch (IOException e) {
            return Result.error("Failed to upload file: " + e.getMessage());
        }
    }

    /**
     * @Author 余泽锋
     * @Date 2023.11.27
     * @Description 获取用户信息
     * @Param [openid - 用户ID]
     * @return Result对象，包含用户信息
     **/
    @GetMapping("/get/userinfo")
    public Result getUserInfo(@RequestParam String openid) {
        System.out.println("openid=" + openid);
        try {
            // 获取用户信息
            User user = userService.getUserByOpenid(openid);

            // 用户不存在则进行注册
            if (user == null) {
                userService.registerUser(openid);
                userInfoService.registerUser(openid);
            }

            // 读取头像文件并转换为 Base64 编码
            String imgsrc = user.getImgSrc();
            if (imgsrc != null) {
                Path imagePath = Paths.get(imgsrc);
                byte[] imageBytes = null;

                // 判断文件是否存在
                if (Files.exists(imagePath)) {
                    // 读取文件内容并转换为 Base64 编码
                    imageBytes = Files.readAllBytes(imagePath);
                    // 将 Base64 编码的头像放入响应对象中
                    user.setImgSrc(Base64.getEncoder().encodeToString(imageBytes));
                } else {
                    user.setImgSrc(null);
                }
            }

            return Result.success(user);
        } catch (Exception e) {
            return Result.error("Failed to fetch user info: " + e.getMessage());
        }
    }

    /**
     * @Author 刘泽铭
     * @Date 12.04
     * @Description 更新搜索历史
     * @Param [requestBody - 包含openid和history的请求体]
     * @return Result对象，包含操作结果信息
     **/
    @PostMapping("/update/history")
    public Result updateHistory(@RequestBody Map<String, Object> requestBody) {
        try {
            String openid = (String) requestBody.get("openid");
            String history = (String) requestBody.get("history");

            // 更新 User 表中的 history 字段
            userService.updateHistory(openid, history);
            return Result.success("History uploaded successfully.");
        } catch (Exception e) {
            return Result.error("Failed to upload history: " + e.getMessage());
        }
    }

    /**
     * @Author 刘泽铭
     * @Date 12.04
     * @Description 获取搜索历史
     * @Param [openid - 用户ID]
     * @return Result对象，包含用户的搜索历史
     **/
    @GetMapping("/get/history")
    public Result getHistory(@RequestParam("openid") String openid) {
        try {
            String history = userService.getHistory(openid);
                        return Result.success(history);
        } catch (Exception e) {
            return Result.error("Failed to upload history: " + e.getMessage());
        }
    }

    /**
     * @Author 刘泽铭
     * @Date 12.14
     * @Description 用户报名参加队伍
     * @Param [requestBody - 包含openid和groupid的请求体]
     * @return Result对象，包含操作结果信息
     **/
    @PostMapping("/signup")
    public Result signup(@RequestBody Map<String, Object> requestBody) {
        try {
            String openid = (String) requestBody.get("openid");
            String groupid = (String) requestBody.get("groupid");
            String participated = userService.getParticipated(openid);
            Vector<String> participated_array = parseArrayString(participated);
            Vector<String> group_userList = parseArrayString(groupService.getRegisteredByGroupid(groupid));
            // 用户已经加入队伍
            if (group_userList.contains(openid)) {
                return Result.error(100, "Existing members of the team include this member!");
            } else {
                // 将队伍加入用户
                participated_array.add(groupid);
                participated = vectorToString(participated_array);
                // 将用户加入队伍人员名单
                group_userList.add(openid);
                groupService.updateGroup_userlist(vectorToString(group_userList), groupid);
                groupService.increaseCurrent(groupid);
                userService.updateParticipated(participated, openid);
                return Result.success("Sign up successfully!");
            }
        } catch (Exception e) {
            return Result.error(404,"Failed to sign up: Team/User not found.");
        }
    }

    /**
     * @Author 刘泽铭
     * @Date 12.14
     * @Description 用户取消报名或小组删除用户
     * @Param [requestBody - 包含openid和groupid的请求体]
     * @return Result对象，包含操作结果信息
     **/
    @PostMapping("/cancelsign")
    public Result cancelsign(@RequestBody Map<String, Object> requestBody) {
        try {
            String openid = (String) requestBody.get("openid");
            String groupid = (String) requestBody.get("groupid");
            String participated = (String) userService.getParticipated(openid);
            Vector<String> participated_array = parseArrayString(participated);

            if (participated_array.contains(groupid)) {
                participated_array.remove(groupid);
                participated = vectorToString(participated_array);
                Vector<String> group_userList = parseArrayString((String) groupService.getRegisteredByGroupid(groupid));
                group_userList.remove(openid);
                groupService.updateGroup_userlist(vectorToString(group_userList), groupid);
                groupService.decreaseCurrent(groupid);
                userService.updateParticipated(participated, openid);
                return Result.success("Successfully deleted");
            } else {
                return Result.error("Failed to delete: not in the group");
            }
        } catch (Exception e) {
            return Result.error("Failed to cancel sign: " + e.getMessage());
        }
    }

    /**
     * @Author 刘泽铭
     * @Date 12.14
     * @Description 获取用户已参加的队伍名单
     * @Param [openid - 用户ID]
     * @return Result对象，包含用户已参加的队伍信息
     **/
    @GetMapping("/getRegGroup")
    public Result getRegGroup(@RequestParam("openid") String openid) {
        try {
            String participated = userService.getParticipated(openid);
            Vector<String> groupidlist = parseArrayString(participated);
            List<Group> groupList = new ArrayList<>();
            for (String groupid : groupidlist) {
                groupList.add(groupService.getGroupsByGroupid(groupid));
            }
            return Result.success(groupList);
        } catch (Exception e) {
            return Result.error("A list of participating teams is not available.: " + e.getMessage());
        }
    }

    /**
     * @Author 刘泽铭
     * @Date 12.14
     * @Description 获取用户头像
     * @Param [openid - 用户ID]
     * @return ResponseEntity<byte[]>，包含用户头像数据
     **/
    @GetMapping("/get_avatar")
    public ResponseEntity<byte[]> getAvatar(@RequestParam("openid") String openid) {
        try {
            // 通过 userService 获取图像路径
            String imgSrc = userService.getImgSrc(openid);

            // 构建图像路径
            Path imagePath = Paths.get(imgSrc);

            // 使用 UrlResource 读取图像资源
            Resource resource = new UrlResource(imagePath.toUri());

            // 读取图像数据
            byte[] imageBytes = resource.getInputStream().readAllBytes();

            // 设置响应头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG); // 假设图像是PNG格式

            // 返回图像数据
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(imageBytes);
        } catch (Exception e) {
            // 处理异常
            return ResponseEntity.status(500).body(null); // 500 Internal Server Error
        }
    }

}


