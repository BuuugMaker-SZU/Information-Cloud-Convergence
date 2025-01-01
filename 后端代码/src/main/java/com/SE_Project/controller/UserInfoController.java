package com.SE_Project.controller;

import com.SE_Project.pojo.Result;
import com.SE_Project.pojo.UserInfo;
import com.SE_Project.service.UserInfoService;
import com.SE_Project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserService userService;

    /**
     * @Author 刘泽铭
     * @Date 2023-12-14
     * @Description 更新用户专业信息
     * @Param [requestBody - 包含openid和major的请求体]
     * @return Result对象，包含操作结果信息
     **/
    @PostMapping("/update/myMajor")
    public Result updateMajor(@RequestBody Map<String, Object> requestBody) {
        String openid = (String) requestBody.get("openid");
        String major = (String) requestBody.get("major");
        try {
            if (openid == null || major == null) {
                return Result.error("Missing required parameters");
            }
            // 调用服务层方法更新用户专业信息
            userInfoService.updateMajor(openid, major);
            return Result.success("User updated successfully");
        } catch (Exception e) {
            return Result.error("Failed to update user: " + e.getMessage());
        }
    }

    /**
     * @Author 刘泽铭
     * @Date 2023-12-14
     * @Description 更新用户微信号
     * @Param [requestBody - 包含openid和weixinID的请求体]
     * @return Result对象，包含操作结果信息
     **/
    @PostMapping("/update/weixinID")
    public Result updateWeixinID(@RequestBody Map<String, Object> requestBody) {
        String openid = (String) requestBody.get("openid");
        String weixinID = (String) requestBody.get("weixinID");
        try {
            if (openid == null || weixinID == null) {
                return Result.error("Missing required parameters");
            }

            // 调用服务层方法更新用户的微信号信息
            userInfoService.updateWeixinID(openid, weixinID);
            return Result.success("User updated successfully");
        } catch (Exception e) {
            return Result.error("Failed to update user: " + e.getMessage());
        }
    }

    /**
     * @Author 刘泽铭
     * @Date 2023-12-14
     * @Description 更新用户电话信息
     * @Param [requestBody - 包含openid和tel的请求体]
     * @return Result对象，包含操作结果信息
     **/
    @PostMapping("/update/tel")
    public Result updateTel(@RequestBody Map<String, Object> requestBody) {
        System.out.println("\n\n\n");
        String openid = (String) requestBody.get("openid");
        String tel = (String) requestBody.get("tel");
        try {
            if (openid == null || tel == null) {
                return Result.error("Missing required parameters");
            }

            // 调用服务层方法更新用户的电话信息
            userInfoService.updateTel(openid, tel);
            return Result.success("User updated successfully");
        } catch (Exception e) {
            return Result.error("Failed to update user: " + e.getMessage());
        }
    }

    /**
     * @Author 刘泽铭
     * @Date 2023-12-14
     * @Description 更新用户个人简介信息
     * @Param [requestBody - 包含openid和intro的请求体]
     * @return Result对象，包含操作结果信息
     **/
    @PostMapping("/update/intro")
    public Result updateIntro(@RequestBody Map<String, Object> requestBody) {
        String openid = (String) requestBody.get("openid");
        String intro = (String) requestBody.get("intro");
        try {
            if (openid == null || intro == null) {
                return Result.error("Missing required parameters");
            }

            // 调用服务层方法更新用户的个人简介信息
            userInfoService.updateIntro(openid, intro);
            return Result.success("User updated successfully");
        } catch (Exception e) {
            return Result.error("Failed to update user: " + e.getMessage());
        }
    }

    /**
     * @Author 刘泽铭
     * @Date 2023-12-14
     * @Description 更新用户姓名信息
     * @Param [requestBody - 包含openid和name的请求体]
     * @return Result对象，包含操作结果信息
     **/
    @PostMapping("/update/name")
    public Result updateName(@RequestBody Map<String, Object> requestBody) {
        String openid = (String) requestBody.get("openid");
        String name = (String) requestBody.get("name");
        try {
            if (openid == null || name == null) {
                return Result.error("Missing required parameters");
            }
            // 调用服务层方法更新用户的姓名信息
            userInfoService.updateName(openid, name);
            userService.updateName(openid, name);
            return Result.success("User updated successfully");
        } catch (Exception e) {
            return Result.error("Failed to update user: " + e.getMessage());
        }
    }

    /**
     * @Author 刘泽铭
     * @Date 2023-12-14
     * @Description 更新用户性别信息
     * @Param [requestBody - 包含openid和gender的请求体]
     * @return Result对象，包含操作结果信息
     **/
    @PostMapping("/update/gender")
    public Result updateGender(@RequestBody Map<String, Object> requestBody) {
        String openid = (String) requestBody.get("openid");
        String gender = (String) requestBody.get("gender");
        try {
            if (openid == null || gender == null) {
                return Result.error("Missing required parameters");
            }
            // 调用服务层方法更新用户的性别信息
            userInfoService.updateGender(openid, gender);
            return Result.success("User updated successfully");
        } catch (Exception e) {
            return Result.error("Failed to update user: " + e.getMessage());
        }
    }

    /**
     * @Author 刘泽铭
     * @Date 2023-12-14
     * @Description 获取用户额外信息
     * @Param [openid - 用户ID]
     * @return Result对象，包含用户额外信息
     **/
    @GetMapping("/get/extra_user_Info")
    public Result get_extra_user_Info(@RequestParam("openid") String openid) {
        try {
            // 调用服务层方法获取用户额外信息
            UserInfo userInfo = userInfoService.getUserInfoByOpenid(openid);
            return Result.success(userInfo);
        } catch (Exception e) {
            return Result.error("Failed to get extra_user_Info: " + e.getMessage());
        }
    }

}