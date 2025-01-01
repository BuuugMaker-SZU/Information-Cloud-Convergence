package com.SE_Project.pojo;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String name; // 姓名
    private String participated; // 参与的活动
    private int administrator; // 是否管理员
    private String openid; // 用户唯一标识
    @Getter
    @Setter
    private String imgSrc; // 用户头像路径
    private String history; //搜索历史
}