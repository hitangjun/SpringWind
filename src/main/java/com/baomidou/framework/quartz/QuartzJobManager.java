/**
 * Copyright (c) 2011-2014, yyn_0210@sina.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.baomidou.framework.quartz;

import com.baomidou.springwind.entity.QrtzTriggers;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.text.ParseException;
import java.util.*;

import static org.quartz.impl.matchers.GroupMatcher.groupEquals;

/**
 * <p>
 * QuartzJob 管理器
 * </p>
 *
 * @author yyn_0210@sina.com
 * @Date 2016-04-13
 */
public class QuartzJobManager {
    public final static String JOB_GROUP_NAME = "DEFAULT_JOB_GROUP";
    public static final String 	TRIGGERNAME = "triggerName";
    public static final String 	TRIGGERGROUP = "DEFAULT_TRIGGER_GROUP";
    public static final String STARTTIME = "startTime";
    public static final String ENDTIME = "endTime";
    public static final String REPEATCOUNT = "repeatCount";
    public static final String REPEATINTERVEL = "repeatInterval";

    public static final Map<String,String> status = new HashMap<String,String>();
    static{
        status.put("ACQUIRED", "运行中");
        status.put("PAUSED", "暂停中");
        status.put("WAITING", "等待中");
    }

    private Logger logger = LoggerFactory.getLogger(QuartzJobManager.class);

    /**
     * 注入调度工厂
     */
    @Autowired
    private SchedulerFactoryBean schedulerFactory;

    /**
     * 添加JOB
     *
     * @param jobName        JOB名称
     * @param jobClass       JOB类
     * @param cronExpression
     * @throws ParseException
     * @throws SchedulerException
     */
    public void addJob(String jobName, Class<? extends Job> jobClass, String cronExpression)
            throws ParseException, SchedulerException {

        JobBuilder jobBuilder = JobBuilder.newJob(jobClass);
        jobBuilder.withIdentity(jobName, JOB_GROUP_NAME);
        JobDetail jobDetail = jobBuilder.build();

        TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
        triggerBuilder.withIdentity(jobName, TRIGGERGROUP);
        triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression));
        Trigger trigger = triggerBuilder.build();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.scheduleJob(jobDetail, trigger);
        if (!scheduler.isShutdown())
            scheduler.start();
    }

    /**
     * 添加JOB
     *
     * @param jobName          JOB名称
     * @param jobGroupName     JOB组名称
     * @param triggerName      触发器名称
     * @param triggerGroupName 触发器组名称
     * @param jobClass         JOB类
     * @param cronExpression   时间规则表达式
     * @throws SchedulerException
     * @throws ParseException
     */
    public void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName,
                       Class<? extends Job> jobClass, String cronExpression)
            throws SchedulerException, ParseException {
        addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass,  cronExpression, null);
    }

    /**
     * 添加JOB
     *
     * @param jobName          JOB名称
     * @param jobGroupName     JOB组名称
     * @param triggerName      触发器名称
     * @param triggerGroupName 触发器组名称
     * @param jobClass         JOB类
     * @param cronExpression   时间规则表达式
     * @param dataMap          数据Map
     * @throws SchedulerException
     * @throws ParseException
     */
    public void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName,
                       Class<? extends Job> jobClass, String cronExpression, Map<String, Object> dataMap)
            throws SchedulerException, ParseException {
        Scheduler scheduler = schedulerFactory.getScheduler();

            JobBuilder jobBuilder = JobBuilder.newJob(jobClass);
            jobBuilder.withIdentity(jobName, jobGroupName);
            JobDetail jobDetail = jobBuilder.build();
            if (dataMap != null) {
                for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
                    jobDetail.getJobDataMap().put(entry.getKey(),  entry.getValue());
                }
            }

            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            triggerBuilder.withIdentity(triggerName, triggerGroupName);
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression));
            triggerBuilder.withDescription(cronExpression);
            Trigger trigger = triggerBuilder.build();
            scheduler.scheduleJob(jobDetail, trigger);
            if (!scheduler.isShutdown()){
                scheduler.start();
            }
    }

    /**
     * 修改JOB触发时间
     *
     * @param jobName        JOB名称
     * @param cronExpression 时间表达式
     * @throws SchedulerException
     * @throws ParseException
     */
    public void modifyJobTime(String jobName, String cronExpression)
            throws SchedulerException, ParseException {
        Scheduler scheduler = schedulerFactory.getScheduler();
        Trigger trigger = scheduler.getTrigger(TriggerKey.triggerKey(jobName, TRIGGERGROUP));
        if (trigger != null) {
            CronTriggerImpl ct = (CronTriggerImpl) trigger;
            ct.setCronExpression(cronExpression);
            scheduler.resumeTrigger(TriggerKey.triggerKey(jobName, TRIGGERGROUP));
        }
    }

    /**
     * 修改JOB触发时间
     *
     * @param triggerName      触发器名称
     * @param triggerGroupName 触发器粗面
     * @param cronExpression   时间表达式
     * @throws SchedulerException
     * @throws ParseException
     */
    public void modifyJobTime(String triggerName, String triggerGroupName,
                              String cronExpression) throws SchedulerException, ParseException {
        Scheduler scheduler = schedulerFactory.getScheduler();

        Trigger trigger = scheduler.getTrigger(TriggerKey.triggerKey(triggerName, triggerGroupName));
        if (trigger != null) {
            CronTriggerImpl ct = (CronTriggerImpl) trigger;
            // 修改时间
            ct.setCronExpression(cronExpression);
            // 重启触发器
            scheduler.rescheduleJob(TriggerKey.triggerKey(triggerName, triggerGroupName), ct);
        }
    }

    /**
     * 修改JOB触发时间
     *
     * @param triggerName      触发器名称
     * @param triggerGroupName 触发器组名
     * @param startTime        开始时间
     * @param endTime          结束时间
     * @throws SchedulerException
     */
    public void modifyJobTime(String triggerName, String triggerGroupName, Date startTime, Date endTime)
            throws SchedulerException {
        Trigger trigger = null;
        Scheduler scheduler = schedulerFactory.getScheduler();
        try {

            trigger = scheduler.getTrigger(TriggerKey.triggerKey(triggerName, triggerGroupName));
            // 停止触发器
            scheduler.pauseTrigger(TriggerKey.triggerKey(triggerName, triggerGroupName));

        } catch (SchedulerException e) {
        	logger.error("scheduler.getTrigger(triggerName, triggerGroupName) Exception: ", e);
        }

        if (trigger != null) {
            CronTriggerImpl ct = (CronTriggerImpl) trigger;
            ct.setStartTime(startTime);
            ct.setEndTime(endTime);
            // 重启触发器
            try {
                scheduler.resumeTrigger(TriggerKey.triggerKey(triggerName, triggerGroupName));
                scheduler.rescheduleJob(TriggerKey.triggerKey(triggerName, triggerGroupName), ct);
            } catch (SchedulerException e) {
            	logger.error("scheduler.resumeTrigger(triggerName, triggerGroupName) Exception: ", e);
                throw new SchedulerException();
            }
        }
    }

    /**
     * 移除JOB
     *
     * @param jobName JOB名称
     * @throws SchedulerException
     */
    public void removeJob(String jobName) throws SchedulerException {
        Scheduler scheduler = schedulerFactory.getScheduler();
        // 停止触发器
        scheduler.pauseTrigger(TriggerKey.triggerKey(jobName, TRIGGERGROUP));
        // 移除触发器
        scheduler.unscheduleJob(TriggerKey.triggerKey(jobName, TRIGGERGROUP));
        // 删除任务
        scheduler.deleteJob(JobKey.jobKey(jobName, JOB_GROUP_NAME));
    }

    /**
     * 移除JOB
     *
     * @param jobName          JOB名称
     * @param jobGroupName     JOB组名
     * @param triggerName      触发器名称
     * @param triggerGroupName 触发器组名
     * @throws SchedulerException
     */
    public void removeJob(String jobName, String jobGroupName,
                          String triggerName, String triggerGroupName)
            throws SchedulerException {
        Scheduler scheduler = schedulerFactory.getScheduler();
        // 停止触发器
        scheduler.pauseTrigger(TriggerKey.triggerKey(triggerName, triggerGroupName));
        // 移除触发器
        scheduler.unscheduleJob(TriggerKey.triggerKey(triggerName, triggerGroupName));
        // 删除任务
        scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));
    }

    public List<QrtzTriggers> list(){
        List<QrtzTriggers> list= new ArrayList<QrtzTriggers>();
        try{
            Scheduler scheduler = schedulerFactory.getScheduler();
            // enumerate each trigger group
            for(String group: scheduler.getTriggerGroupNames()) {

                GroupMatcher<TriggerKey> matcher = groupEquals(group);
                Set<TriggerKey> triggerKeysSet = scheduler.getTriggerKeys(matcher);
                // enumerate each trigger in group
                for(TriggerKey triggerKey : triggerKeysSet) {

                    Trigger trigger = scheduler.getTrigger(triggerKey);



                    QrtzTriggers qrtzTriggers = new QrtzTriggers(trigger,scheduler.getTriggerState(triggerKey).toString());

                    String jobdata = "";
                    JobKey jobKey = trigger.getJobKey();
                    JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                    JobDataMap dataMap = jobDetail.getJobDataMap();
                    for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
                        jobdata+=(entry.getKey() + " : "
                                + entry.getValue() + " <br/> ");
                    }
                    qrtzTriggers.setJobDataMap(jobdata);
                    list.add(qrtzTriggers);
                }
            }
        }catch(Exception e){
            logger.error("list cron job error",e);
        }
        return list;
    }

    public List<Trigger> listTrigger(){
        List<Trigger> list= new ArrayList<Trigger>();
        try{
            Scheduler scheduler = schedulerFactory.getScheduler();
            // enumerate each trigger group
            for(String group: scheduler.getTriggerGroupNames()) {

                GroupMatcher<TriggerKey> matcher = groupEquals(group);
                Set<TriggerKey> triggerKeysSet = scheduler.getTriggerKeys(matcher);
                // enumerate each trigger in group
                for(TriggerKey triggerKey : triggerKeysSet) {
                    Trigger trigger = scheduler.getTrigger(triggerKey);

                    JobDataMap tdataMap =  trigger.getJobDataMap();
                    for (Map.Entry<String, Object> entry : tdataMap.entrySet()) {
                        System.out.println("triggermap key= " + entry.getKey() + " and value= "
                                + entry.getValue());
                    }

                    JobKey jobKey = trigger.getJobKey();
                    JobDetail jobDetail = scheduler.getJobDetail(jobKey);

                    JobDataMap dataMap = jobDetail.getJobDataMap();
                    System.out.println("dataMap a : "+dataMap.getString("crawlUrl"));

                    for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
                        System.out.println("jobmap key= " + entry.getKey() + " and value= "
                                + entry.getValue());
                    }

                    list.add(trigger);
                }
            }
        }catch(Exception e){
            logger.error("list cron job error",e);
        }
        return list;
    }

    /**
     * 判断是否已添加过该job
     *
     * @param jobName      任务名称
     * @param jobGroupName 任务组名称
     * @return true/false
     * @throws SchedulerException
     * @throws ParseException
     */
    public boolean isJobAdded(String jobName, String jobGroupName)
            throws SchedulerException, ParseException {
        Scheduler scheduler = schedulerFactory.getScheduler();
        JobDetail jobDetail = scheduler.getJobDetail(JobKey.jobKey(jobName, jobGroupName));
        if (jobDetail != null) {
            return true;
        } else {
            return false;
        }
    }

}