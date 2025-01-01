package com.SE_Project.service.impl;

import com.SE_Project.mapper.ActivityMapper;
import com.SE_Project.pojo.Activity;
import com.SE_Project.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityImpl implements ActivityService {
    @Autowired
    private ActivityMapper activityMapper;

    @Override
    public List<Activity> activityList() {
        // 获取并返回所有活动简介数据，用于初始化首页。
        return activityMapper.activityList();
    }

    @Override
    public Activity getActivityDetail(String actid) {
        // 根据活动ID获取活动详细信息和小组信息。
        return activityMapper.getActivityByActId(actid);
    }

    @Override
    public void insertActivity(String actname, String type, String organizer, String date, String location, String reward, char permission, String imgsrc, char status, String introduction, int carousel) {
        // 插入新活动的详细信息，包括活动名称、类型、组织者、日期、地点、奖励、权限、图片路径、状态、简介和轮播图状态。
        activityMapper.insertActivity(actname, type, organizer, date, location, reward, permission, imgsrc, status, introduction, carousel);
    }

    @Override
    public void updateActivity(String actid, String actname, String type, String organizer, String date, String location, String reward, char permission, String imgsrc, char status, String introduction) {
        // 更新指定活动的信息，包括活动名称、类型、组织者、日期、地点、奖励、权限、图片路径、状态和简介。
        activityMapper.updateActivity(actid, actname, type, organizer, date, location, reward, permission, imgsrc, status, introduction);
    }

    @Override
    public void updateCarousel(String actid) {
        // 更新指定活动的轮播图状态。
        activityMapper.updateCarousel(actid);
    }

    @Override
    public void deleteActivity(String actid) {
        // 删除指定actid的活动。
        activityMapper.deleteActivity(actid);
    }

    @Override
    public List<Activity> searchActivities(String keyword) {
        // 根据关键字搜索活动并返回结果列表。
        return activityMapper.searchActivities(keyword);
    }

    @Override
    public List<Activity> searchActivitiesByFilter(List<String> activityTypes, List<String> organizers) {
        // 根据活动类型和主办方筛选活动并返回结果列表。
        return activityMapper.searchActivitiesByFilter(activityTypes, organizers);
    }

}
