package com.SE_Project.mapper;

import com.SE_Project.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    // 获取所有用户列表
    @Select("SELECT * FROM user")
    List<User> userList();

    // 根据openid获取用户信息
    @Select("SELECT * FROM user WHERE openid = #{openid}")
    User getUserByOpenid(String openid);

    // 获取用户的管理员权限
    @Select("SELECT administrator FROM user WHERE openid = #{openid}")
    Character getAdministrator(String openid);

    // 用户注册
    @Insert("INSERT INTO user (name, administrator, imgsrc, openid, participated) " +
            "VALUES ('无名氏', 0,'images/avatar/defaultAvatar.png', #{openid}, '[]')")
    void registerUser(String openid);

    // 更新用户姓名
    @Update("UPDATE user SET name = #{name} WHERE openid = #{openid}")
    void updateName(String openid, String name);

    // 删除用户
    @Delete("DELETE FROM user WHERE openid = #{openid}")
    void deleteUser(String openid);

    // 更新用户头像
    @Update("UPDATE user SET imgsrc = #{imgSrc} WHERE openid = #{openid}")
    void updateAvatar(String openid, String imgSrc);

    // 更新用户搜索历史记录
    @Update("UPDATE user SET history = #{history} WHERE openid = #{openid}")
    void updateHistory(String openid, String history);

    // 得到用户搜索记录
    @Select("SELECT history FROM user WHERE openid = #{openid}")
    String getHistory(String openid);

    // 得到用户参与的小组的groupid串
    @Select("SELECT participated FROM user WHERE openid = #{openid}")
    String getParticipated(String openid);

    // 更新用户参与的小组的groupid串
    @Update("UPDATE user SET participated = #{participated} WHERE openid = #{openid}")
    void updateParticipated(String participated,String openid);

    // 得到用户头像的地址
    @Select("SELECT imgsrc FROM user WHERE openid = #{openid}")
    String getImgSrc(String openid);
}