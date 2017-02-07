package com.baomidou.springwind.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.framework.common.Pinyin4jHelper;
import com.baomidou.framework.quartz.QuartzJobManager;
import com.baomidou.kisso.annotation.Permission;
import com.baomidou.springwind.entity.QrtzTriggers;
import com.baomidou.springwind.job.CrawlJob;
import com.baomidou.springwind.service.ISchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户管理相关操作
 * </p>
 *
 *
 * @Author Jack
 * @Date 2016/4/15 15:03
 */
@Controller
@RequestMapping("/monitor/qrtz")
public class QrtzTriggersController extends BaseController {

	@Autowired
	private ISchedulerService schedulerService;

	@Autowired
	protected QuartzJobManager quartzJobManager;




	@Permission("3001")
	@RequestMapping("/list")
	public String list(Model model) {
		return "/quartz/list";
	}

    @Permission("3001")
    @RequestMapping("/edit")
    public String edit(Model model, Long id ) {
    	if ( id != null ) {
			model.addAttribute("qrtz", schedulerService.selectById(id));
		}
//    	model.addAttribute("roleList", roleService.selectList(null));
        return "/quartz/edit";
    }
    
	@ResponseBody
	@Permission("3001")
	@RequestMapping("/addQrtz")
	public String addQrtz() {
		boolean rlt = true;
		String triggerName = Pinyin4jHelper.converterToAllFirstSpell(request.getParameter("triggerName"));
		String cronExpression = request.getParameter("cronExpression");
		String crawlUrl = request.getParameter("crawlUrl");
		String logPath = request.getParameter("logPath");
//		schedulerService.schedule(triggerName,cronExpression);

		try {
//			public void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName,
//					Class<? extends Job > jobClass, String cronExpression, Map<String, Object> dataMap)
			Map<String,Object> dataMap = new HashMap<String,Object>();
//			if(triggerName.startsWith("crawl")){
				dataMap.put("crawlUrl",crawlUrl);
				dataMap.put("logPath",logPath);
//			}
			quartzJobManager.addJob(triggerName,QuartzJobManager.JOB_GROUP_NAME ,triggerName,QuartzJobManager.TRIGGERGROUP,
					CrawlJob.class,cronExpression,dataMap);
		} catch (Exception e) {
			logger.error("quartzJobManager.addJob SchedulerException ",e);
		}
		return callbackSuccess(rlt);
	}

	@ResponseBody
	@Permission("3001")
	@RequestMapping("/getQrtzList")
	public String getQrtzList() {
//		Page<QrtzTriggers> page = getPage();
//		page = schedulerService.selectPage(page, null);
		JSONObject jo = new JSONObject();
		jo.put("total", 1);
//		jo.put("rows", page.getRecords());
		jo.put("rows",quartzJobManager.list());
//		jo.put("newRows",quartzJobManager.listTrigger());
		return toJson(jo);
	}

	@ResponseBody
	@Permission("3001")
	@RequestMapping("/delQrtz")
	public String delQrtz() {
		String triggerName = null;
		try {
			triggerName = URLDecoder.decode(request.getParameter("triggerName"), "utf-8");
			String group = URLDecoder.decode(request.getParameter("triggerGroup"), "utf-8");
			boolean rs = schedulerService.removeTrigdger(triggerName, group);
		} catch (Exception e) {
			return "false";
		}
		return "true";
	}

	@ResponseBody
	@Permission("3001")
	@RequestMapping("/pauseQrtz")
	public String pauseQrtz() {
		String triggerName = null;
		try {
			triggerName = URLDecoder.decode(request.getParameter("triggerName"), "utf-8");
			String group = URLDecoder.decode(request.getParameter("triggerGroup"), "utf-8");
			logger.debug("pause triggerName"+triggerName);
			schedulerService.pauseTrigger(triggerName, group);
		} catch (Exception e) {
			return "false";
		}
		return "true";
	}

	@ResponseBody
	@Permission("3001")
	@RequestMapping("/resumeQrtz")
	public String resumeQrtz() {
		String triggerName = null;
		try {
			triggerName = URLDecoder.decode(request.getParameter("triggerName"), "utf-8");
			String group = URLDecoder.decode(request.getParameter("triggerGroup"), "utf-8");
			schedulerService.resumeTrigger(triggerName, group);
		} catch (Exception e) {
			return "false";
		}
		return "true";
	}

	@ResponseBody
	@Permission("3001")
	@RequestMapping("/{qrtzId}")
	public QrtzTriggers getQrtz(@PathVariable Long userId) {
		return schedulerService.selectById(userId);
	}

}
