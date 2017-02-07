package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * 定时任务配置信息
 *
 */
@TableName("cron_task")
public class CronTask implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId
	private Long id;

	/** 任务名称 */
	@TableField(value = "task_name")
	private String taskName;

	/** 任务执行类名 */
	@TableField(value = "task_clazz_name")
	private String taskClazzName;

	/** 定时任务时间表达式 */
	@TableField(value = "task_cron_time")
	private String taskCronTime;

	/** 以空格分隔的任务参数 */
	@TableField(value = "task_params")
	private String taskParams;

	/** 创建时间 */
	@TableField(value = "create_time")
	private Date createTime;

	/** 修改时间 */
	@TableField(value = "update_time")
	private Date updateTime;

	/** 描述 */
	private String remark;

	/** 状态:0无效，1可用 */
	private Integer status;


	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskClazzName() {
		return this.taskClazzName;
	}

	public void setTaskClazzName(String taskClazzName) {
		this.taskClazzName = taskClazzName;
	}

	public String getTaskCronTime() {
		return this.taskCronTime;
	}

	public void setTaskCronTime(String taskCronTime) {
		this.taskCronTime = taskCronTime;
	}

	public String getTaskParams() {
		return this.taskParams;
	}

	public void setTaskParams(String taskParams) {
		this.taskParams = taskParams;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
