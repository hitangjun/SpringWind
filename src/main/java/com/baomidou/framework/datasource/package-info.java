/**
 * Created by L.cm
 * Date: 2016-04-14 18:34
 */
package com.baomidou.framework.datasource;

/**
 * 使用示例：
 * <pre>
 *
 * <!--数据源切面注入spring-->
 * <bean id="dsDataSourceAop" class="com.baomidou.framework.aop.DataSourceAop"/>
 *
 * <!--多数据源-->
 * <bean id="dynamicDataSource" class="com.baomidou.framework.datasource.DynamicDataSource">
 *      <property name="defaultTargetDataSource" ref="testXXReadDataSource"/>
 *      <property name="targetDataSources">
 *          <map key-type="java.lang.String">
 *              <entry key="testRead" value-ref="testReadDataSource"/>
 *              <entry key="shopReadWrite" value-ref="shopReadWriteDataSource"/>
 *              <entry key="test_slaves0" value-ref="testSlaves0DataSource"/>
 *              <entry key="test_slaves1" value-ref="testSlaves1DataSource"/>
 *              <entry key="test_slaves2" value-ref="testSlaves2DataSource"/>
 *              <entry key="test_slaves3" value-ref="testSlaves3DataSource"/>
 *          </map>
 *      </property>
 * </bean>
 *
 * <!-- 服务层注解 -->
 * @DataSourceManage(name = "testRead")
 *
 * @DataSourceManage(name = "shopReadWrite")
 *
 * @DataSourceManage(name = "test_slaves", dbSize = 4)
 *
 * </pre>
 *
 */