package com.SE_Project.mapper;

import com.SE_Project.pojo.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserInfoMapper {
    // 根据openid获取用户额外信息
    @Select("SELECT * FROM userInfo WHERE openid = #{openid}")
    UserInfo getUserInfoByOpenid(String openid);

    // 更新用户电话号码
    @Update("UPDATE userInfo SET tel = #{tel} WHERE openid = #{openid}")
    void updateTel(String openid, String tel);

    // 更新用户微信号
    @Update("UPDATE userInfo SET wechat = #{weixinID} WHERE openid = #{openid}")
    void updateWeixinID(String openid, String weixinID);

    // 更新用户专业信息
    @Update("UPDATE userInfo SET major = #{major} WHERE openid = #{openid}")
    void updateMajor(String openid, String major);

    // 更新用户简介
    @Update("UPDATE userInfo SET biography = #{intro} WHERE openid = #{openid}")
    void updateIntro(String openid, String intro);

    // 更新用户姓名
    @Update("UPDATE userInfo SET name = #{name} WHERE openid = #{openid}")
    void updateName(String openid, String name);

    // 更新用户性别
    @Update("UPDATE userInfo SET gender = #{gender} WHERE openid = #{openid}")
    void updateGender(String openid, String gender);

    // 用户注册
    @Insert("INSERT INTO userinfo (name,openid) " +
            "VALUES ('无名氏', #{openid}) ")
    void registerUser(String openid);
}

