package com.SE_Project.service.impl;

import com.SE_Project.mapper.UserInfoMapper;
import com.SE_Project.pojo.UserInfo;
import com.SE_Project.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfo getUserInfoByOpenid(String openid) { return userInfoMapper.getUserInfoByOpenid(openid); }

    @Override
    public void updateTel(String openid, String tel) {
        userInfoMapper.updateTel(openid, tel);
    }

    @Override
    public void updateWeixinID(String openid, String weixinID) {
        userInfoMapper.updateWeixinID(openid, weixinID);
    }

    @Override
    public void updateMajor(String openid, String major) {
        userInfoMapper.updateMajor(openid, major);
    }

    @Override
    public void updateIntro(String openid, String intro) {
        userInfoMapper.updateIntro(openid, intro);
    }

    @Override
    public void updateName(String openid, String name) {
        userInfoMapper.updateName(openid, name);
    }

    @Override
    public void updateGender(String openid, String gender) {
        userInfoMapper.updateGender(openid, gender);
    }
    @Override
    public void registerUser(String openid){ userInfoMapper.registerUser(openid);}
}