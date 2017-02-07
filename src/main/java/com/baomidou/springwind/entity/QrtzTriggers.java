package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import org.quartz.Trigger;

import java.io.Serializable;

/**
 *
 * 
 *
 */
@TableName("qrtz_triggers")
public class QrtzTriggers implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId(value = "SCHED_NAME")
	private String schedName;

	/**  */
	@TableField(value = "TRIGGER_NAME")
	private String triggerName;

	/**  */
	@TableField(value = "TRIGGER_GROUP")
	private String triggerGroup;

	/**  */
	@TableField(value = "JOB_NAME")
	private String jobName;

	/**  */
	@TableField(value = "JOB_GROUP")
	private String jobGroup;

	/**  */
	private String description;

	/**  */
	@TableField(value = "NEXT_FIRE_TIME")
	private Long nextFireTime;

	/**  */
	@TableField(value = "PREV_FIRE_TIME")
	private Long prevFireTime;

	/**  */
	private Integer priority;

	/**  */
	@TableField(value = "TRIGGER_STATE")
	private String triggerState;

	/**  */
	@TableField(value = "TRIGGER_TYPE")
	private String triggerType;

	/**  */
	@TableField(value = "START_TIME")
	private Long startTime;

	/**  */
	@TableField(value = "END_TIME")
	private Long endTime;

	/**  */
	@TableField(value = "CALENDAR_NAME")
	private String calendarName;

	/**  */
	@TableField(value = "MISFIRE_INSTR")
	private Integer misfireInstr;

	/**  */
	@TableField(value = "JOB_DATA")
	private byte[] jobData;

	private String jobDataMap;

	public QrtzTriggers(){

	}

	public QrtzTriggers(Trigger trigger ,String state){
		this.startTime = trigger.getStartTime() != null ?trigger.getStartTime().getTime() : 0;
//		this.endTime = trigger.getEndTime().getTime();
		this.description = trigger.getDescription();
		this.jobGroup = trigger.getJobKey().getGroup();
		this.jobName = trigger.getJobKey().getName();
		this.misfireInstr = trigger.getMisfireInstruction();
		this.prevFireTime = trigger.getPreviousFireTime() != null ? trigger.getPreviousFireTime().getTime() : 0;
		this.nextFireTime = trigger.getNextFireTime() != null ?trigger.getNextFireTime().getTime() : 0;
		this.priority = trigger.getPriority();
		this.triggerGroup=trigger.getKey().getGroup();
		this.triggerName = trigger.getKey().getName();
		this.triggerState=state;
	}

	public String getJobDataMap() {
		return jobDataMap;
	}

	public void setJobDataMap(String jobDataMap) {
		this.jobDataMap = jobDataMap;
	}
	public String getSchedName() {
		return this.schedName;
	}

	public void setSchedName(String schedName) {
		this.schedName = schedName;
	}

	public String getTriggerName() {
		return this.triggerName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	public String getTriggerGroup() {
		return this.triggerGroup;
	}

	public void setTriggerGroup(String triggerGroup) {
		this.triggerGroup = triggerGroup;
	}

	public String getJobName() {
		return this.jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return this.jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getNextFireTime() {
		return this.nextFireTime;
	}

	public void setNextFireTime(Long nextFireTime) {
		this.nextFireTime = nextFireTime;
	}

	public Long getPrevFireTime() {
		return this.prevFireTime;
	}

	public void setPrevFireTime(Long prevFireTime) {
		this.prevFireTime = prevFireTime;
	}

	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getTriggerState() {
		return this.triggerState;
	}

	public void setTriggerState(String triggerState) {
		this.triggerState = triggerState;
	}

	public String getTriggerType() {
		return this.triggerType;
	}

	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}

	public Long getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public String getCalendarName() {
		return this.calendarName;
	}

	public void setCalendarName(String calendarName) {
		this.calendarName = calendarName;
	}

	public Integer getMisfireInstr() {
		return this.misfireInstr;
	}

	public void setMisfireInstr(Integer misfireInstr) {
		this.misfireInstr = misfireInstr;
	}

	public byte[] getJobData() {
		return this.jobData;
	}

	public void setJobData(byte[] jobData) {
		this.jobData = jobData;
	}

}
