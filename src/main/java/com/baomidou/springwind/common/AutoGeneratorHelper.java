package com.baomidou.springwind.common;

import com.baomidou.kisso.common.util.EnvUtil;
import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.ConfigGenerator;

/**
 * 
 * 自动生成映射工具类
 * 
 * @author hubin
 *
 */
public class AutoGeneratorHelper {

	/**
	 * 
	 * 测试 run 执行
	 * 
	 * <p>
	 * 配置方法查看 {@link ConfigGenerator}
	 * </p>
	 * 
	 */
	public static void main( String[] args ) {
		ConfigGenerator cg = new ConfigGenerator();
		cg.setTableNames(new String[]{"qrtz_triggers"});
		cg.setEntityPackage("com.baomidou.springwind.entity");
		cg.setMapperPackage("com.baomidou.springwind.mapper");
		cg.setServicePackage("com.baomidou.springwind.service");
		cg.setSuperServiceImpl("com.baomidou.springwind.service.support.BaseServiceImpl");
		cg.setIdType(IdType.ID_WORKER);
		if (EnvUtil.isLinux()) {
			cg.setSaveDir("/Users/hubin/springwind/");
		} else {
			cg.setSaveDir("D:/aaaaaaa/");
		}
		cg.setDbDriverName("com.mysql.jdbc.Driver");
		cg.setDbUser("opay");
		cg.setDbPassword("QpT%Vx*3TY");
		cg.setDbUrl("jdbc:mysql://opayatlas:3306/springwind?characterEncoding=utf8");
		cg.setDbPrefix(false);
		AutoGenerator.run(cg);
	}
	
}
