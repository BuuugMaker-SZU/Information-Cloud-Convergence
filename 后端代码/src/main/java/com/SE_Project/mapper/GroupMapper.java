package com.SE_Project.mapper;

import com.SE_Project.pojo.Group;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GroupMapper {
    // 查询所有组
    @Select("SELECT * FROM act_group order by date desc ")
    List<Group> groupList();

    // 根据openid查询所有未满组
    @Select("SELECT * FROM act_group WHERE openid = #{openid} AND current < required order by date desc ")
    List<Group> getGroupsByOpenid(String openid);

    // 根据openid查询所有组
    @Select("SELECT * FROM act_group WHERE openid = #{openid} order by date desc ")
    List<Group> getGroupsByOpenid1(String openid);

    // 根据actid查询所有组
    @Select("SELECT * FROM act_group WHERE actid = #{actid} order by date desc ")
    List<Group> getGroupsByActid(String actid);

    // 根据groupid查询组
    @Select("SELECT * FROM act_group WHERE groupid = #{groupid} order by date desc ")
    Group getGroupsByGroupid(String groupid);

    // 插入组
    @Insert("INSERT INTO act_group (actid, groupname, tel, chargename, introduction, date, required, current, openid, actname,registered) " +
            "VALUES (#{actid}, #{groupname}, #{tel}, #{chargename}, #{introduction}, #{date}, #{required}, #{current}, #{openid}, #{actname}, '[]')")
    void insertGroup(Group group);

    // 更新组
    @Update("UPDATE act_group SET groupname = #{groupname}, tel = #{tel}, chargename = #{chargename}, introduction = #{introduction}" +
            ", date = #{date}, required = #{required}, current = #{current}, registered = #{registered}, actname = #{actname} " +
            "WHERE openid = #{openid} AND groupid = #{groupid} AND actid = #{actid}")
    void updateGroup(Group group);

    // 通过groupid删除组
    @Delete("DELETE FROM act_group WHERE groupid = #{groupid} ")
    void deleteGroup(String  groupid);

    // 修改名单
    @Update("UPDATE act_group SET registered = #{register} WHERE groupid = #{groupid}")
    void updateGroup_userlist(String register,String groupid);

    // 增加当前人数
    @Update("UPDATE act_group SET current = current+1 WHERE groupid = #{groupid}")
    void increaseCurrent(String groupid);

    // 减少当前人数
    @Update("UPDATE act_group SET current = current-1 WHERE groupid = #{groupid}")
    void decreaseCurrent(String groupid);

    // 通过groupid查询组里的成员的openid串
    @Select("SELECT registered FROM act_group WHERE groupid = #{groupid} order by date desc ")
    String getRegisteredByGroupid(String groupid);

    // 通过actid查询活动下的小组的groupid串
    @Select("SELECT groupid FROM act_group WHERE actid = #{actid}")
    List<String> getGroupidByActid(String actid);
}