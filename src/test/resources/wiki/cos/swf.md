

# SwfTools 转换工具

< 下载地址：

http://www.swftools.org/download.html
http://www.swftools.org/swftools-2013-04-09-1007.tar.gz
http://www.swftools.org/swftools-2013-04-09-1007.exe


# windows 安装

> 下载 swftools-2013-04-09-1007.exe 默认安装即可！


# linux 安装

> 1、安装所需的库和组件。机器之前安装过了，主要安装的是下面几个组件。如果不安装会提示machine `x86_64-unknown-linux' not recognized
yum install gcc* automake zlib-devel libjpeg-devel giflib-devel freetype-devel

> 2、下载编译安装swftools。
wget http://www.swftools.org/swftools-0.9.2.tar.gz
tar vxzf swftools-0.9.2.tar.gz
cd swftools-0.9.2
./configure --prefix=/usr/swftools
make
make install

> 3、设置swftools环境变量，使 pdf2swf 成为一个可执行命令【注意！！如果环境变量自动设置好了不再需要设置】
vim /etc/profile
export PATH=$PATH:/usr/swftools/bin/

> 执行：source /etc/profile

> 4、安装xpdf语言包。下载  ftp://ftp.foolabs.com/pub/xpdf/xpdf-chinese-simplified.tar.gz 文件，解压到/usr/share/xpdf下，编辑add-to-xpdfrc文件，如下：【注意！！如果安装目录下存在 xpdf-chinese-simplified 不再需要下载】
  tar zxvf xpdf-chinese-simplified.tar.gz
  unzip font.zip
  mv Gbsn00lp.ttf gkai00mp.ttf xpdf-chinese-simplified/CMap/
  cd /usr/share/xpdf/xpdf-chinese-simplified
  vi add-to-xpdfrc
  
> 内容如下：
cidToUnicode   Adobe-GB1       /usr/share/xpdf/xpdf-chinese-simplified/Adobe-GB1.cidToUnicode
unicodeMap     ISO-2022-CN     /usr/share/xpdf/xpdf-chinese-simplified/ISO-2022-CN.unicodeMap
unicodeMap     EUC-CN          /usr/share/xpdf/xpdf-chinese-simplified/EUC-CN.unicodeMap
unicodeMap     GBK             /usr/share/xpdf/xpdf-chinese-simplified/GBK.unicodeMap
cMapDir        Adobe-GB1       /usr/share/xpdf/xpdf-chinese-simplified/CMap
toUnicodeDir                   /usr/share/xpdf/xpdf-chinese-simplified/CMap
displayCIDFontTT Adobe-GB1 	   /usr/share/xpdf/xpdf-chinese-simplified/CMap/gkai00mp.ttf
保存后退出
字体文件自己下载…

> 5、最后使用如下转换命令测试：
pdf2swf -s languagedir=/usr/local/xpdf-chinese-simplified -T 9 -s poly2bitmap -s zoom=150 -s flashversion=9 "/opt/123.pdf" -o "/opt/test/%.swf"
转换成功！搞定！

 
 
# 命令行参数

< pdf2swf -s languagedir=/usr/local/xpdf-chinese-simplified -T 9 -s poly2bitmap -s zoom=150 -s flashversion=9 "/opt/123.pdf" -o "/opt/test/%.swf"


-h , –help                      Print short help message and exit              打印帮助信息 
-V , –version                Print version info and exit                        打印版本号 
-o , –output file.swf         Direct output to file.swf. If file.swf contains ‘13568621′ (file13568630.swf), then each page指定输出的swf文件名 
-p , –pages range             Convert only pages in range with range e.g. 1-20 
or 1,4,6,9-11 or 

指定转换的页面范围，使用的页码描述方法与打印机打印文件时候的选页一样 

-P , –password password       Use password for deciphering the pdf.指定打开pdf的密码 
-v , –verbose                 Be verbose. Use more than one -v for greater effect.转换时输出详细的内容 
-z , –zlib                    Use Flash 6 (MX) zlib compression.使用Flash 6的zlib压缩机制 
-i , –ignore                  Allows pdf2swf to change the draw order of the pdf. This may make the generated允许程序修改pdf的绘制顺序，可能会导致结果与原来有差异 
-j , –jpegquality quality     Set quality of embedded jpeg pictures to quality. 0 is worst (small), 100 is best (big). (default:85)设置转换其中的jpeg图片的质量，从0到100，默认值是85。 
-s , –set param=value         Set a SWF encoder specific parameter.  See pdf2swf -s help for more information.  设置SWF转码时候的参数，具体参数可以用pdf2swf -s help获取 
-w , –samewindow              When converting pdf hyperlinks, don’t make the links open a new window.        设置转换后的swf打开原pdf中的连接时使用相同的窗口 
-t , –stop                    Insert a stop() command in each page.            在每页结尾添加一个stop()命令 
-T , –flashversion num        Set Flash Version in the SWF header to num.         设置SWF所使用的flash版本号 
-F , –fontdir directory       Add directory to the font search path.                    指定字体文件所在路径 
-b , –defaultviewer           Link a standard viewer to the swf file.             指定默认的swf导航文件，用来翻页、放大缩小等等 
-l , –defaultloader           Link a standard preloader to the swf file which will be displayed while the main swf is loading.     指定默认的swf加载文件，用来显示加载进程效果 
-B , –viewer filename         Link viewer filename to the swf file.   指定swf导航文件，作用同-b 
-L , –preloader filename      Link preloader filename to the swf file.      指定swf加载文件，作用同-l 
-q , –quiet                   Suppress normal messages.  Use -qq to suppress warnings, also.  不打印普通信息，用-qq就不打印警告信息。 
-S , –shapes                  Don’t use SWF Fonts, but store everything as shape. 不使用字体，所有都转为形状。 
-f , –fonts                   Store full fonts in SWF. (Don’t reduce to used characters). 在swf中保存全部字体。 
-G , –flatten                 Remove as many clip layers from file as possible. 在文件中尽量去除影片层，合并它们 
-I , –info                    Don’t do actual conversion, just display a list of all pages in the PDF. 不做实际转换，仅显示PDF的信息。 
-Q , –maxtime n               Abort conversion after n seconds. Only available on Unix. 如果运行时间超时则退出。 

-------------------------------------------------------------------------------- 
然后看看-s都可以设置些什么： 
PDF Parameters: 
PDF device global parameters: 
fontdir= a directory with additional fonts 指定字体目录, 与1级参数的-F相若 
font= an additional font filename 增加额外的字体文件 
pages= the range of pages to convert (example: pages=1-100,210-) 指定页面范围，与1级参数的-p相若 
zoom= the resolution (default: 72) 指定分辨率，默认为72dpi 
languagedir= Add an xpdf language directory 增加一个xpdf的语言目录，对非西欧字符有用 
multiply= Render everything at the resolution 在几倍分辨率下渲染 
poly2bitmap Convert graphics to bitmaps 把其中的图形转成点阵 
bitmap Convert everything to bitmaps 把所有内容转成点阵（包括字体） 

SWF Parameters: 

SWF layer options: 
jpegsubpixels=<pixels> resolution adjustment for jpeg images (same as jpegdpi, but in pixels) jpeg图片的分辨率 
ppmsubpixels=<pixels> resolution adjustment for  lossless images (same asppmdpi, but in pixels) 无损图片的分辨率 
subpixels=<pixels>     shortcut for setting both jpegsubpixels and ppmsubpixels 快速设置上两个参数 
drawonlyshapes           convert everything to shapes (currently broken) 所有都转成图形 
ignoredraworder         allow to perform a few optimizations for creating smaller SWFs 允许执行一些小优化 
linksopennewwindow   make links open a new browser window 链接打开新窗口 
linktarget                  target window name of new links       新链接窗口的名称 
linkcolor=<color)        color of links (format: RRGGBBAA)    链接的颜色 
linknameurl         Link buttons will be named like the URL they refer to (handy for iterating through links with actionscript)   链接名称与链接URL一致 
storeallcharacters      don’t reduce the fonts to used characters in the output file 保存所有的字符字体 
enablezlib                switch on zlib compression (also done if flashversion>=7) 使用zlib压缩 
bboxvars                 store the bounding box of the SWF file in actionscript variables 在as中保存swf的区域大小 
dots                        Take care to handle dots correctly 保存单点显示 
reordertags=0/1     (default: 1) perform some tag optimizations 执行某些tag优化 
internallinkfunction=<name> when the user clicks a internal link (to a different page) in the converted file, this actionscript function is called 内部链接函数，如果点击一个内部链接，将调用该actionscript函数 
externallinkfunction=<name> when the user clicks an external link (e.g. http://www.foo.bar/) on the converted file, this actionscript function is called 外部链接函数，如果点击一个外部链接，将调用该actionscript函数 
disable_polygon_conversion  never convert strokes to polygons (will remove capstyles and joint styles) 不要将笔画转成多边形 
caplinewidth=<width>        the minimum thichness a line needs to have so that capstyles become visible (and are converted)           线条最低转换宽度，比这个细的线条将不转换 
insertstop                  put an ActionScript “STOP” tag in every frame 在swf的每个桢中添加stop()函数 
protect                     add a “protect” tag to the file, to prevent loadingin the Flash editor 增加protect标签，禁止在flash中加载该swf 
flashversion=<version>  the SWF fileversion (6) 设置最低swf版本 
framerate=<fps>         SWF framerate  设置桢率 
minlinewidth=<width>  convert horizontal/vertical boxes smaller than thiswidth to lines (0.05)将宽度少于某值的矩形转成线条 
simpleviewer     Add next/previous buttons to the SWF 使用简单的导航 
animate           insert a showframe tag after each placeobject (animate draw order of PDF files) ？？？ 
jpegquality=<quality>  set compression quality of jpeg images 设置jpeg的压缩质量 
splinequality=<value>  Set the quality of spline convertion to value (0-100, default: 100). 设置样条曲线的转换质量 
disablelinks                Disable links.  禁止链接 


