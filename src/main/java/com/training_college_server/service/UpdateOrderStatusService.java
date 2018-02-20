package com.training_college_server.service;

import com.training_college_server.dao.CourseDao;
import com.training_college_server.dao.CourseOrderDao;
import com.training_college_server.entity.Course;
import com.training_college_server.entity.CourseOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.concurrent.ScheduledFuture;

/**
 * 设置订单超过15min未付款为无效订单
 */
@Component
public class UpdateOrderStatusService {

    @Resource
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Resource
    private CourseOrderDao courseOrderDao;

    @Resource
    private CourseDao courseDao;

    private ScheduledFuture<?> future;

    private int course_order_id;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    public void invalidateOrderStatus(int course_order_id) {
        this.course_order_id = course_order_id;
        CourseOrder courseOrder = courseOrderDao.findOne(course_order_id);
        if (courseOrder != null) {
            Calendar cal = Calendar.getInstance(); // 获取当前时间
            cal.add(Calendar.MINUTE, 15); // 设置延迟15分钟
            int month = cal.get(Calendar.MONTH) + 1; // 获取月份
            int day = cal.get(Calendar.DATE); //获取日
            int hour = cal.get(Calendar.HOUR_OF_DAY); // 小时
            int minute = cal.get(Calendar.MINUTE); // 分
            int second = cal.get(Calendar.SECOND); // 秒
            String corn = second + " " + minute + " " + hour + " " + day + " " + month + " ?";
            future = threadPoolTaskScheduler.schedule(new MyRunnable(), new CronTrigger(corn));
        }
    }

    private class MyRunnable implements Runnable {
        @Override
        public void run() {
            CourseOrder courseOrder = courseOrderDao.findOne(course_order_id);

            // 如果触发定时器时，该订单状态不为invalid且不为unsubscribe且不为paid时，则关闭定时器
            // 当触发定时器时，该订单状态已为unsubscribe，则说明用户在15min内取消了订单
            if (!(courseOrder.getStatus().equals("unsubscribe"))
                    && !courseOrder.getStatus().equals("paid")
                    && !courseOrder.getStatus().equals("invalid")) {
                // 设置订单状态为invalid
                courseOrder.setStatus("invalid");
                CourseOrder courseOrder1 = courseOrderDao.save(courseOrder);

                // 该课程已订购人数减一
                Course course = courseDao.findOne(courseOrder1.getCourseID());
                course.setBooked_amount(course.getBooked_amount() - 1);
                courseDao.save(course);
            }
            // 关闭定时器
            if (future != null) {
                future.cancel(true);
            }
        }
    }

}
