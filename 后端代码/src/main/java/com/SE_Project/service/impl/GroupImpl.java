package com.SE_Project.service.impl;

import com.SE_Project.mapper.GroupMapper;
import com.SE_Project.pojo.Group;
import com.SE_Project.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupImpl implements GroupService {
    @Autowired
    private GroupMapper groupMapper;

    @Override
    public List<Group> groupList() {
        // 获取并返回所有小组的简要信息列表。
        return groupMapper.groupList();
    }

    @Override
    public void insertGroup(Group group) {
        // 插入新的小组信息。
        groupMapper.insertGroup(group);
    }

    @Override
    public void updateGroup(Group group) {
        // 更新小组的信息。
        groupMapper.updateGroup(group);
    }

    @Override
    public void deleteGroup(String groupid) {
        // 通过小组ID删除小组。
        groupMapper.deleteGroup(groupid);
    }

    @Override
    public List<Group> getGroupsDetailByOpenid(String openid) {
        // 获取一个用户的所有未满组的详细信息。
        return groupMapper.getGroupsByOpenid(openid);
    }

    @Override
    public List<Group> getGroupsDetailByOpenid1(String openid) {
        // 获取一个用户的所有组的详细信息。
        return groupMapper.getGroupsByOpenid1(openid);
    }

    @Override
    public List<Group> getGroupsDetailByActid(String actid) {
        // 获取一个活动下的所有小组的详细信息。
        return groupMapper.getGroupsByActid(actid);
    }

    @Override
    public void updateGroup_userlist(String register,String groupid){
        // 更新小组成员名单，将用户ID添加到小组的注册名单中。
        groupMapper.updateGroup_userlist(register,groupid);
    }

    @Override
    public void increaseCurrent(String groupid){
        // 小组当前人数增加。
        groupMapper.increaseCurrent(groupid);
    }

    @Override
    public void decreaseCurrent(String groupid){
        // 小组当前人数减少。
        groupMapper.decreaseCurrent(groupid);
    }

    @Override
    public String getRegisteredByGroupid(String groupid){
        // 获取指定小组的所有已注册用户的openid串。
        return groupMapper.getRegisteredByGroupid(groupid);
    }

    @Override
    public Group getGroupsByGroupid(String groupid){
        // 通过小组ID获取小组信息。
        return groupMapper.getGroupsByGroupid(groupid);
    }

    @Override
    public List<String> getGroupidByActid(String actid){
        // 通过活动ID获取活动下的所有小组ID列表。
        return groupMapper.getGroupidByActid(actid);
    }
}
