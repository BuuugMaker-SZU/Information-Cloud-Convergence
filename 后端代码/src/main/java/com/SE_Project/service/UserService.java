package com.SE_Project.service;

import com.SE_Project.pojo.User;

import java.util.List;

public interface UserService {
    List<User> userList();

    // 通过openid得到用户信息
    User getUserByOpenid(String openid);

    // 通过openid得到用户权限值
    Character getAdministrator(String openid);

    // 用户注册
    void registerUser(String openid);

    // 更新昵称
    void updateName(String openid, String name);

    // 更新头像
    void updateAvatar(String openid, String imgSrc);

    // 更新搜索历史
    void updateHistory(String openid, String history);

    // 得到搜索历史
    String getHistory(String openid);

    // 通过openid得到用户参与的小组的groupid串
    String getParticipated(String openid);

    // 更新用户参与的小组
    void updateParticipated(String participated,String openid);

    // 获取用户头像
    String getImgSrc(String openid);
}
