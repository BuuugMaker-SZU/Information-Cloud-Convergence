package com.SE_Project.service;

import com.SE_Project.pojo.Activity;

import java.util.List;

public interface ActivityService {
    // 查询所有活动简介数据（用来初始化首页）
    List<Activity> activityList();

    // 根据活动id获取活动详细信息和小组信息
    Activity getActivityDetail(String actid);

    // 插入新活动
    void insertActivity(String actname, String type, String organizer, String date, String location, String reward, char permission, String imgsrc, char status, String introduction,int carousel);

    // 更新活动信息
    void updateActivity(String actid, String actname, String type, String organizer, String date, String location, String reward, char permission, String imgsrc, char status, String introduction);

    //更新轮播图状态
    void updateCarousel(String actid);

    // 删除活动
    void deleteActivity(String actid);

    // 搜索活动
    List<Activity> searchActivities(String keyword);

    // 根据活动类型和主办方筛选活动
    List<Activity> searchActivitiesByFilter(List<String> activityTypes, List<String> organizers);

}
