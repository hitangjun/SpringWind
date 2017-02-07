package com.baomidou.framework.sigar;

/**
 *<p>
 * Sigar 系统信息收集API
 * <br>
 * 收集的信息包括：<br>
 * 1， CPU信息，包括基本信息（vendor、model、mhz、cacheSize）和统计信息（user、sys、idle、nice、wait）<br>
 * 2， 文件系统信息，包括Filesystem、Size、Used、Avail、Use%、Type<br>
 * 3， 事件信息，类似Service Control Manager<br>
 * 4， 内存信息，物理内存和交换内存的总数、使用数、剩余数；RAM的大小<br>
 * 5， 网络信息，包括网络接口信息和网络路由信息<br>
 * 6， 进程信息，包括每个进程的内存、CPU占用数、状态、参数、句柄<br>
 * 7， IO信息，包括IO的状态，读写大小等<br>
 * 8， 服务状态信息<br>
 * 9， 系统信息，包括操作系统版本，系统资源限制情况，系统运行时间以及负载，JAVA的版本信息等.<br>
 *</p>
 * 
 * {@docRoot https://support.hyperic.com/display/SIGAR/Home}
 * 
 *<p>
 * 帮助文档：/spring-wind/src/test/resources/wiki/sigar/sigar.md
 *</p>
 * 
 * @author hubin
 * @Date 2016-04-15
 */
