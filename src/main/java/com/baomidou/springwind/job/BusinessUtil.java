package com.baomidou.springwind.job;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class BusinessUtil {
	// 本地异常日志记录对象
	private static final Logger logger = Logger.getLogger(BusinessUtil.class);
	
	private static String localIp="";
	
	//单实例中 日志ID生成器
	private static AtomicLong LOG_ID = new AtomicLong(System.currentTimeMillis());
	
	static {
	   try {
			 localIp= Inet4Address.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
	 }

	public static void writeLog(String path,String fileName,String content) {
		File f = new File(path);
		if (!f.exists()) {
			f.mkdirs();
		}
		String nowDate= getNowYYYYMMDD();
		path = path + fileName+"_"+nowDate + ".log";
		writeFile(path, content);
	}

	private final static String getNowYYYYMMDD() {
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyy-MM-dd");
		return sdf.format(new Date());
	}

	private static void writeFile(String path, String content) {
		FileWriter fw=null;
		try {
			fw = new FileWriter(path,true);
			fw.write(content+"\n");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(fw!=null){
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


	public static String getIpAddress() {
		return localIp;
	}
	
	
	public static String getLogId(){
		 return LOG_ID.incrementAndGet()+"";
	}
	

}
