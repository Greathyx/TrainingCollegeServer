package com.training_college_server.service.impl;

import com.training_college_server.dao.CourseDao;
import com.training_college_server.dao.InstitutionApplyDao;
import com.training_college_server.dao.InstitutionDao;
import com.training_college_server.entity.Course;
import com.training_college_server.entity.Institution;
import com.training_college_server.entity.InstitutionApply;
import com.training_college_server.service.InstitutionService;
import org.springframework.stereotype.Component;
import utils.ResultBundle;
import javax.annotation.Resource;
import java.util.List;


@Component
public class InstitutionServiceImpl implements InstitutionService {

    @Resource
    private InstitutionDao institutionDao;

    @Resource
    private InstitutionApplyDao institutionApplyDao;

    @Resource
    private CourseDao courseDao;



    @Override
    public ResultBundle institutionApply(Institution institution, InstitutionApply institutionApply) {
        Institution institution1 = institutionDao.findByEmail(institution.getEmail());
        if (institution1 != null) {
            return new ResultBundle<Institution>(false, "该邮箱已被注册！", null);
        } else {
            institutionApplyDao.save(institutionApply);
            String message = "正在等待管理员验证，验证通过后，我们会第一时间发邮件通知您，请注意查收。";
            return new ResultBundle<>(true, message, institution);
        }
    }

    @Override
    public ResultBundle institutionLogin(String code, String password) {
        Institution institution = institutionDao.findByCode(code);
        if (institution != null && institution.getPassword().equals(password)) {
            return new ResultBundle<>(true, "登陆成功！", institution);
        } else {
            return new ResultBundle<Institution>(false, "登陆码或密码错误！", null);

        }
    }

    @Override
    public ResultBundle institutionEditInfo(Institution institution, String password_previous) {
        if (institution.getCode() == null) {
            return new ResultBundle<Institution>(false, "修改机构信息出错！", null);
        } else {
            // modify表示机构申请修改信息
            String modifyTag = "modify";

            Institution institution1 = institutionDao.findByCode(institution.getCode());
            // 如果无法根据机构注册码查询到该机构
            if (institution1 == null) {
                return new ResultBundle<Institution>(false, "该机构未注册，暂无信息！", null);
            }
            // 不修改密码的情况，即原密码和新密码输入框的值均为""
            else if (institution.getPassword().equals("") && password_previous.equals("")) {
                InstitutionApply institutionApply = new InstitutionApply(
                        institution.getEmail(),
                        institution.getName(),
                        institution1.getPassword(), // 写入原密码
                        institution.getLocation(),
                        institution.getFaculty(),
                        institution.getIntroduction(),
                        modifyTag
                );
                institutionApplyDao.save(institutionApply);
                return new ResultBundle<>(true, "已向管理员申请修改信息，我们会通过邮件告诉您申请结果。", institutionApply);
            }
            // 修改密码的情况
            // 如果输入的原密码与数据库中的原密码不一致
            else if (!institution1.getPassword().equals(password_previous)) {
                return new ResultBundle<Institution>(false, "原密码错误！", null);
            }
            // 修改机构信息
            else {
                InstitutionApply institutionApply = new InstitutionApply(
                        institution.getEmail(),
                        institution.getName(),
                        institution.getPassword(), // 写入新密码
                        institution.getLocation(),
                        institution.getFaculty(),
                        institution.getIntroduction(),
                        modifyTag
                );
                institutionApplyDao.save(institutionApply);
                return new ResultBundle<>(true, "已向管理员申请修改信息，我们会通过邮件告诉您申请结果。", institutionApply);
            }
        }
    }

    @Override
    public ResultBundle releaseCourse(Course course) {
        Institution institution = institutionDao.findOne(course.getPublisher());
        if (institution == null) {
            return new ResultBundle<Course>(false, "该机构未注册！", null);
        } else {
            Course course1 = courseDao.save(course);
            return new ResultBundle<>(true, "课程已成功发布！", course1);
        }
    }

    @Override
    public ResultBundle getCourseInfo(int publisher) {
        List<Course> courseList = courseDao.findAllByPublisher(publisher);
        return new ResultBundle<List>(true, "已获取机构课程信息！", courseList);
    }

}
