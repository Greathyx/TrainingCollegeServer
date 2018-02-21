package com.training_college_server.utils;


/**
 * 课程类型
 */
public class CourseType {

    private static final String[] TYPE_LIST = new String[]{
            "外语", "考研", "奥数", "文学", "物化", "编程", "前端交互", "摄影", "健身", "棋类", "烹饪"
    };

    public static String[] getTypeList() {
        return TYPE_LIST;
    }

}
