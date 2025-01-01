package com.SE_Project.service;

import com.SE_Project.pojo.UserInfo;

public interface UserInfoService {
    // 通过openid获取用户额外信息
    UserInfo getUserInfoByOpenid(String openid);

    // 更新用户联系方式
    void updateTel(String openid, String tel);

    // 更新用户微信号
    void updateWeixinID(String openid, String weixinID);

    // 更新用户专业
    void updateMajor(String openid, String major);

    // 更新用户个人简介
    void updateIntro(String openid, String intro);

    // 更新用户昵称
    void updateName(String openid, String name);

    // 更新用户性别
    void updateGender(String openid, String name);

    // 用户注册
    void registerUser(String openid);
}

