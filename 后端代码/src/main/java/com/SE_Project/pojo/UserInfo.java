package com.SE_Project.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private String name; // 姓名
    private String tel; // 联系电话
    private String wechat; // 微信号
    private String major; // 专业
    private String biography; // 个人简历
    private String gender;//性别
    private String openid; // 用户id
}

