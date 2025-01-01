package com.SE_Project.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private Integer code;//响应码，1 代表成功；0 代表失败
    private String msg;//响应信息 描述字符串
    private Object results;//返回的数据

    //增删改 成功响应
    public static Result success() {
        return new Result(1, "success", null);
    }
    public static Result error(int code,String msg){ return new Result(code,msg,null); }

    //查询 成功响应
    public static Result success(Object results) {
        return new Result(200, "success", results);
    }

    //失败响应
    public static Result error(String msg) {
        return new Result(0, msg, null);
    }
}