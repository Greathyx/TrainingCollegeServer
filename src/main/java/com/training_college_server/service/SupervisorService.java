package com.training_college_server.service;

import com.training_college_server.entity.Supervisor;
import utils.ResultBundle;

public interface SupervisorService {

    /**
     *
     * 判断管理员用户名密码是否正确
     *
     * @param supervisor
     * @return
     */
    public boolean supervisorLogin(Supervisor supervisor);

    /**
     *
     * 获取所有机构申请信息
     *
     * @param tag
     * @return
     */
    public ResultBundle getAllApplies(String tag);

    /**
     *
     * 批准机构申请或修改信息
     *
     * @param institution_apply_id
     * @return
     */
    public ResultBundle approveApply(int institution_apply_id);

    /**
     *
     * 驳回机构申请或修改信息
     *
     * @param institution_apply_id
     * @return
     */
    public ResultBundle rejectApply(int institution_apply_id);

}
