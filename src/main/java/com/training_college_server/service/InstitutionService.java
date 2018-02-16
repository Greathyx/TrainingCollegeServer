package com.training_college_server.service;

import com.training_college_server.entity.Course;
import com.training_college_server.entity.Institution;
import com.training_college_server.entity.InstitutionApply;
import com.training_college_server.utils.ResultBundle;

public interface InstitutionService {

    /**
     * 教育机构申请注册方法
     *
     * @param institution 机构对象
     * @return ResultBundle
     */
    ResultBundle institutionApply(Institution institution, InstitutionApply institutionApply);

    /**
     * 机构登陆方法
     *
     * @param code     7位机构登陆码
     * @param password 密码
     * @return ResultBundle
     */
    ResultBundle institutionLogin(String code, String password);

    /**
     * 机构申请修改信息方法
     *
     * @param institution       机构对象
     * @param password_previous 原密码
     * @return ResultBundle
     */
    ResultBundle institutionEditInfo(Institution institution, String password_previous);

    /**
     * 机构发布课程方法
     *
     * @param course 课程对象
     * @return ResultBundle
     */
    ResultBundle releaseCourse(Course course);

    /**
     * 获取机构所有课程信息
     *
     * @param publisher 机构ID
     * @return ResultBundle
     */
    ResultBundle getCourseInfo(int publisher);

}
