package com.SE_Project.controller;

import com.SE_Project.pojo.Group;
import com.SE_Project.pojo.Result;
import com.SE_Project.pojo.UserInfo;
import com.SE_Project.service.GroupService;
import com.SE_Project.service.UserInfoService;
import com.SE_Project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import static com.SE_Project.utils.util.parseArrayString;
import static com.SE_Project.utils.util.vectorToString;

@RestController
public class GroupController {
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserService userService;

    /**
     * @Author 余泽锋
     * @Date 2023-11-27
     * @Description 查询指定openid下的所有小组信息
     * @Param [openid - 用户openid]
     * @return Result对象，包含用户所在的所有小组信息列表
     **/
    @GetMapping("/get/teaminfobyopenid")
    public Result getAllGroupsByOpenid(@RequestParam String openid) {
        try {
            // 调用服务层方法获取指定openid下的所有小组信息
            List<Group> groupList = groupService.getGroupsDetailByOpenid1(openid);

            // 返回成功结果及小组信息列表
            return Result.success(groupList);
        } catch (Exception e) {
            // 返回错误信息，说明获取小组信息失败
            return Result.error("Failed to fetch group data: " + e.getMessage());
        }
    }

    /**
     * @Author 余泽锋
     * @Date 2023-12-04
     * @Description 查询指定actid下的所有小组信息
     * @Param [actid - 活动id]
     * @return Result对象，包含活动下的所有小组信息列表
     **/
    @GetMapping("/get/teaminfobyactid")
    public Result getAllGroupsByActid(@RequestParam String actid) {
        try {
            // 调用服务层方法获取指定actid下的所有小组信息
            List<Group> groupList = groupService.getGroupsDetailByActid(actid);

            // 返回成功结果及小组信息列表
            return Result.success(groupList);
        } catch (Exception e) {
            // 返回错误信息，说明获取小组信息失败
            return Result.error("Failed to fetch group data: " + e.getMessage());
        }
    }

    /**
     * @Author 余泽锋
     * @Date 2023-12-11
     * @Description 创建一个新的小组
     * @Param [group - 包含小组信息的Group对象]
     * @return Result对象，包含操作结果信息
     **/
    @PostMapping("/post/create_group")
    public Result insertGroup(@RequestBody Group group) {
        try {
            // 检查是否需要更新小组的人数限制，保证小组所需人数不小于当前人数
            if (group.getRequired() < group.getCurrent()) {
                return Result.error("Failed to insert group,required number of members cannot be less than current number of members");
            }

            // 调用服务层方法插入新的小组
            groupService.insertGroup(group);

            // 返回成功消息
            return Result.success("Group inserted successfully");
        } catch (Exception e) {
            // 返回其他异常信息
            return Result.error("Failed to insert group: " + e.getMessage());
        }
    }

    /**
     * @Author 余泽锋
     * @Date 2023-12-11
     * @Description 删除一个具体的小组
     * @Param [params - 包含小组id的Map对象]
     * @return Result对象，包含操作结果信息
     **/
    @DeleteMapping("/delete/teaminfo")
    public Result deleteGroup(@RequestBody Map<String, String> params) {
        String groupid = params.get("groupid");
        try {
            // 获取小组下的所有成员openid，更新用户参与的小组信息
            Vector<String> users = parseArrayString(groupService.getRegisteredByGroupid(groupid));
            for (String openid : users) {
                String participated = userService.getParticipated(openid);
                Vector<String> participated_array = parseArrayString(participated);
                participated_array.remove(groupid);
                participated = vectorToString(participated_array);
                userService.updateParticipated(participated, openid);
            }

            // 调用服务层方法删除小组
            groupService.deleteGroup(groupid);

            // 返回成功消息
            return Result.success("Record deleted successfully");
        } catch (Exception e) {
            // 返回删除失败的错误信息
            return Result.error("Failed to delete record: " + e.getMessage());
        }
    }

    /**
     * @Author 余泽锋
     * @Date 2023-12-11
     * @Description 修改小组信息
     * @Param [group - 包含小组信息的Group对象]
     * @return Result对象，包含操作结果信息
     **/
    @PutMapping("/put/update_group")
    public Result updateGroup(@RequestBody Group group) {
        try {
            // 调用服务层方法更新小组信息
            groupService.updateGroup(group);

            // 返回成功消息
            return Result.success("Group updated successfully");
        } catch (Exception e) {
            // 返回更新失败的错误信息
            return Result.error("Failed to update group: " + e.getMessage());
        }
    }

    /**
     * @Author 刘泽铭
     * @Date 2023-12-11
     * @Description 查询指定openid下的所有未满小组信息
     * @Param [openid - 用户openid]
     * @return Result对象，包含用户所在的所有未满小组信息列表
     **/
    @GetMapping("/getResGroup")
    public Result getResponseGroupByOpenID(@RequestParam("openid") String openid) {
        try {
            // 调用服务层方法获取指定openid下的所有未满小组信息
            List<Group> groupList = groupService.getGroupsDetailByOpenid(openid);

            // 返回成功结果及未满小组信息列表
            return Result.success(groupList);
        } catch (Exception e) {
            // 返回错误信息，说明获取未满小组信息失败
            return Result.error("Failed to fetch group data: " + e.getMessage());
        }
    }

    /**
     * @Author 刘泽铭
     * @Date 2023-12-2
     * @Description 通过groupid查询小组下的所有成员
     * @Param [groupid - 小组id]
     * @return Result对象，包含小组成员信息列表
     **/
    @GetMapping("/getUserByGroupid")
    public Result getUserByGroupid(@RequestParam("groupid") String groupid) {
        try {
            // 获取小组下的所有成员openid
            String registeredByGroupid = groupService.getRegisteredByGroupid(groupid);
            Vector<String> openidList = parseArrayString(registeredByGroupid);

            // 获取每个成员的用户信息
            List<UserInfo> userList = new ArrayList<>();
            for (String openid : openidList) {
                userList.add(userInfoService.getUserInfoByOpenid(openid));
            }

            // 返回成功结果及小组成员信息列表
            return Result.success(userList);
        } catch (Exception e) {
            // 返回错误信息，说明获取小组成员信息失败
            return Result.error("Failed to fetch group data: " + e.getMessage());
        }
    }
}
