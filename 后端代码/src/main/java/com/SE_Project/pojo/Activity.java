package com.SE_Project.pojo;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Activity {
    @Getter
    private String actid;//活动id
    @Getter
    private String actname; // 活动名称
    private String type; // 活动类型
    private String organizer; // 组织者
    private String date; // 日期
    private String location; // 地点
    private String reward; // 奖励
    private char permission; // 是否可创建队伍的权限
    @Getter
    @Setter
    private String imgsrc; // 图片路径
    private char status; // 招募中或者已结束状态
    private String introduction; // 活动简介
    @Setter
    private int carousel;//轮播图状态
}
