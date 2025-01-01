package com.SE_Project.mapper;

import com.SE_Project.pojo.Activity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ActivityMapper {
    // 获取所有活动列表
    @Select("select * from activity order by date desc ")
    List<Activity> activityList();

    // 根据活动id获取活动信息
    @Select("SELECT * FROM activity WHERE actid = #{actid} order by date desc ")
    Activity getActivityByActId(String actid);

    // 插入新活动
    @Insert("INSERT INTO activity (actname, type, organizer, date, location, reward, permission, imgsrc, status, introduction, carousel) " +
            "VALUES (#{actname}, #{type}, #{organizer}, #{date}, #{location}, #{reward}, #{permission}, #{imgsrc}, #{status}, #{introduction},#{carousel})")
    void insertActivity(String actname, String type, String organizer, String date, String location, String reward, char permission, String imgsrc, char status, String introduction,int carousel);

    // 更新现有活动
    @Update("UPDATE activity SET actname = #{actname}, type = #{type}, organizer = #{organizer}, date = #{date}, " +
            "location = #{location}, reward = #{reward}, permission = #{permission}, " +
            "imgsrc = #{imgsrc}, status = #{status}, introduction = #{introduction} " +
            "WHERE actid = #{actid}")
    void updateActivity(String actid, String actname, String type, String organizer, String date, String location, String reward, char permission, String imgsrc, char status, String introduction);

    // 更新轮播图状态
    @Update("UPDATE activity SET carousel = CASE WHEN carousel = 0 THEN 1 ELSE 0 END WHERE actid = #{actid}")
    void updateCarousel(String actid);

    // 删除现有活动
    @Delete("DELETE FROM activity WHERE actid = #{actid}")
    void deleteActivity(String actid);

    // 搜索活动（根据关键字）
     @Select("SELECT * FROM activity " +
            "WHERE actname LIKE CONCAT('%', #{keyword}, '%') " +
            "OR location LIKE CONCAT('%', #{keyword}, '%') " +
            "OR type LIKE CONCAT('%', #{keyword}, '%') " +
            "OR organizer LIKE CONCAT('%', #{keyword}, '%') " +
            "OR reward LIKE CONCAT('%', #{keyword}, '%') " +
            "OR introduction LIKE CONCAT('%', #{keyword}, '%') order by date desc ")
    List<Activity> searchActivities(String keyword);

     // 筛选活动
     @Select("<script>" +
        "SELECT * FROM activity " +
        "<where>" +
        "<if test='activityTypes != null and activityTypes.size() > 0 and activityTypes[0] != \"不限\"'>" +
        "   AND type IN " +
        "   <foreach item='type' collection='activityTypes' open='(' separator=',' close=')'>" +
        "       #{type}" +
        "   </foreach>" +
        "</if>" +
        "<if test='organizers != null and organizers.size() > 0 and organizers[0] != \"不限\"'>" +
        "   AND organizer IN " +
        "   <foreach item='organizer' collection='organizers' open='(' separator=',' close=')'>" +
        "       #{organizer}" +
        "   </foreach>" +
        "</if>" +
        "</where> order by date desc " +
        "</script>" )
    List<Activity> searchActivitiesByFilter(@Param("activityTypes") List<String> activityTypes,
                                            @Param("organizers") List<String> organizers);

}