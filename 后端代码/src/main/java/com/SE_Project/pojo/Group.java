package com.SE_Project.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Group {
    private String groupid; //队伍id
    private String actid; // 活动id
    private String groupname; // 组名
    private String tel; // 联系电话
    private String chargename; // 负责人姓名
    private String introduction; // 简介
    private String date; // 日期
    private int required; // 必需人数
    private int current; // 当前人数
    private String registered; // 已注册人员
    private String openid; // 用户唯一标识
    private String actname; // 活动名称
}
