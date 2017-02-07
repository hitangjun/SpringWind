
# Sigar 系统依赖文件配置

```
Hyperic-hq官方网站：http://www.hyperic.com
Sigar.jar下载地址：http://sourceforge.net/projects/sigar/files/
Sigar.jar文档地址：https://support.hyperic.com/display/SIGAR/Home
```


> Windows（注意服务器多少位）

```
1.在代码中执行String str=System.getProperty("java.library.path");
2.查看str中第一个；前的位置
3.将sigar-x86-winnt.dll拷到这个目录下
4.重新执行代码（如果是服务器的话，记得重新启动在执行）
```


> Linux（注意服务器多少位）

```
将 libsigar-x86-linux.so 防止 /usr/lib 目录下
```


