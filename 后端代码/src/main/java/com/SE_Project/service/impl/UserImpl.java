package com.SE_Project.service.impl;

import com.SE_Project.mapper.UserMapper;
import com.SE_Project.pojo.User;
import com.SE_Project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public List<User> userList() {
        return userMapper.userList();
    }
    @Override
    public User getUserByOpenid(String openid) {
        return userMapper.getUserByOpenid(openid);
    }
    @Override
    public Character getAdministrator(String openid) {
        return userMapper.getAdministrator(openid);
    }
    @Override
    public void registerUser(String openid) {
        userMapper.registerUser(openid);
    }
    @Override
    public void updateName(String openid, String name) {
        userMapper.updateName(openid, name);
    }
    @Override
    public void updateAvatar(String openid, String imgSrc) { userMapper.updateAvatar(openid,imgSrc); }
    @Override
    public String getHistory(String openid){ return userMapper.getHistory(openid);}
    @Override
    public void updateHistory(String openid, String history){ userMapper.updateHistory(openid,history); }
    @Override
    public String getParticipated(String openid) { return userMapper.getParticipated(openid); }
    @Override
    public void updateParticipated(String participated,String openid){ userMapper.updateParticipated(participated,openid); }
    @Override
    public String getImgSrc(String openid){ return userMapper.getImgSrc(openid); }
}
