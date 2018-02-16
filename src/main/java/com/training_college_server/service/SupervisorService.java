package com.training_college_server.service;

import com.training_college_server.entity.Supervisor;
import com.training_college_server.utils.ResultBundle;

public interface SupervisorService {

    /**
     * 判断管理员用户名密码是否正确
     *
     * @param supervisor 管理员对象
     * @return 是否成功登陆的布尔值
     */
    boolean supervisorLogin(Supervisor supervisor);

    /**
     * 获取所有机构申请信息
     *
     * @param tag 机构申请处理项的标志，有register, modify, done三种，
     *            分别对应申请注册，申请修改机构信息，申请已被处理三种情况
     * @return ResultBundle
     */
    ResultBundle getAllApplies(String tag);

    /**
     * 批准机构申请或修改信息
     *
     * @param institution_apply_id 机构申请处理项的ID
     * @return ResultBundle
     */
    ResultBundle approveApply(int institution_apply_id);

    /**
     * 驳回机构申请或修改信息
     *
     * @param institution_apply_id 机构申请处理项的ID
     * @return ResultBundle
     */
    ResultBundle rejectApply(int institution_apply_id);

}
