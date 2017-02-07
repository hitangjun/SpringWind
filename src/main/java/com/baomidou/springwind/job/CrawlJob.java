package com.baomidou.springwind.job;

import com.baomidou.framework.quartz.QuartzJobSupport;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author JohnTang
 * @date 2017/1/12
 */
public class CrawlJob extends QuartzJobSupport {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void innerIter(JobExecutionContext context) {
        try{
            JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        String logPath = dataMap.getString("logPath");
        String crawlUrl = dataMap.getString("crawlUrl");
        String triggerName = context.getTrigger().getKey().getName();
        logger.debug("CrawlJob is run..."+triggerName+" ,url : "+crawlUrl);
        if(StringUtils.isNotEmpty(logPath) && StringUtils.isNotEmpty(crawlUrl)){
            String result= HttpClientUtil.requestGet(crawlUrl,30000 );
            BusinessUtil.writeLog(logPath,triggerName,result);
        }
//        logger.debug("CrawlJob is run..."+context.getJobDetail().getKey().getName());
        }catch (Exception e){
            logger.error("CrawlJob error",e);
        }
    }

    public static void main(String[] args) throws ParseException {

        System.out.println(randomString(-229985452) + " " + randomString(-147909649));

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str3 = "1927-12-31 23:54:07";
        String str4 = "1927-12-31 23:54:08";
        Date sDt3 = sf.parse(str3);
        Date sDt4 = sf.parse(str4);
        long ld3 = sDt3.getTime() /1000;
        long ld4 = sDt4.getTime() /1000;
        System.out.println(ld4-ld3);
    }

    public static String randomString(int i)
    {
        Random ran = new Random(i);
        StringBuilder sb = new StringBuilder();
        while (true)
        {
            int k = ran.nextInt(27);
            if (k == 0)
                break;

            sb.append((char)('`' + k));
        }

        return sb.toString();
    }


}
