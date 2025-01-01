package com.SE_Project.service;

import com.SE_Project.pojo.Group;

import java.util.List;

public interface GroupService {
    List<Group> groupList();

    //插入组
    void insertGroup(Group group);

    //更新组
    void updateGroup(Group group);

    //通过groupid删除组
    void deleteGroup(String groupid);

    //获取一个用户的所有未满组详情
    List<Group> getGroupsDetailByOpenid(String openid);

    //获取一个用户的所有组详情
    List<Group> getGroupsDetailByOpenid1(String openid);

    //获取一个活动的所有组详情
    List<Group> getGroupsDetailByActid(String actid);

    // 更新小组成员名单
    void updateGroup_userlist(String register,String groupid);

    // 增加当前人数
    void increaseCurrent(String groupid);

    // 减少当前人数
    void decreaseCurrent(String groupid);

    // 获取参与小组的openid串
    String getRegisteredByGroupid(String groupid);

    // 通过groupid获取组信息
    Group getGroupsByGroupid(String groupid);

    // 通过actid获取活动下的组列表
    List<String> getGroupidByActid(String actid);
}
