package com.SE_Project.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class util {
    public static Vector<String> parseArrayString(String input) {
        if (input == null || input.length() <= 2) {
            // 如果输入为 null 或者 "[]"（长度小于等于2），返回空的 Vector
            return new Vector<>();
        }
        // 去掉方括号，并去除首尾空格
        String content = input.substring(1, input.length() - 1).trim();

        // 使用逗号分隔字符串
        String[] elementArray = content.split(",");
        List<String> elementList = Arrays.asList(elementArray);
        // 将数组转换为Vector
        Vector<String> elements = new Vector<>(elementList);
        return elements;
    }
     public static String vectorToString(Vector<String> vector) {
        // 遍历Vector元素，使用逗号连接
        StringBuilder result = new StringBuilder();
        result.append("[");
        for (int i = 0; i < vector.size(); i++) {
            result.append(vector.get(i));
            if (i < vector.size()-1) {
                result.append(",");
            }
        }
        result.append("]");

        return result.toString();
    }
}
