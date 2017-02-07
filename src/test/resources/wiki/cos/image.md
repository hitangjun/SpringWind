

# 上传 Flash 插件
http://www.fullavatareditor.com/
http://fex.baidu.com/webuploader/



# 图片处理

graphicsmagick 官方网站：http://www.graphicsmagick.org/
im4java 	         官方网站：http://im4java.sourceforge.net/

GraphicsMagick+Im4Java在windows和linux下的配置

http://blog.csdn.net/asd987123456/article/details/44059305



# windows 安装

< graphicsmagick 下载地址：

http://jaist.dl.sourceforge.net/project/graphicsmagick/graphicsmagick-binaries/1.3.23/GraphicsMagick-1.3.23-Q8-win64-dll.exe

1、安装 graphicsmagick 在 windows 选择 GraphicsMagick-1.3.23-Q8-win64-dll.exe 默认安装即可！
2、cmd 中输入 gm 有输出提示，安装成功！否则将 graphicsmagick 安装目录，加入环境变量 PATH 中。


# linux 安装

http://sourceforge.net/projects/graphicsmagick/files/graphicsmagick/1.3.23/

1、安装编译环境
yum groupinstall "Development tools"


2、安装依赖
yum install freetype gd-devel libgomp libjpeg libjpeg-devel libpng libpng-devel


3、wget 下载 GraphicsMagick 安装
http://jaist.dl.sourceforge.net/project/graphicsmagick/graphicsmagick/1.3.23/GraphicsMagick-1.3.23.tar.gz


4、安装  GraphicsMagick
	tar -zxvf GraphicsMagick-1.3.23.tar.gz -C /usr/local
	cd /usr/local/GraphicsMagick-1.3.23
	./configure
	make
	make install
	

5、验证安装
gm display


6、测试
gm convert input.jpg -thumbnail '100x100' output_1.jpg


7、创建软链接 【ln -s 源文件  链接文件名】 
ln -s GraphicsMagick-1.3.23 GraphicsMagick


8、常见错误 gm 命令
java.io.IOException: Cannot run program "gm": java.io.IOException: error=2, No such file or directory 
【解决：convert.setSearchPath("XXXXX");//设置你的gm路径，如：/usr/local/bin/】 


9、常见错误、设置授权（给使用的用户授权）
给xxx用户赋graphicsMagick安装目录的访问权限
上述安装步骤都是在root用户下进行的，所以在安装完成之后需要给 xxx用户赋访问权限，否则在xxx用户下截取图片，将会报错org.im4java.core.CommandException: java.io.IOException: Cannot run program "gm": java.io.IOException: error=13, Permission denied。
使用root用户登录，然后执行如下命令：
chown -R xxx:users /usr/local/bin/gm



# GraphicsMagick介绍
-----------------------------------------------------------------

GraphicsMagick是从 ImageMagick 5.5.2 分支出来的，但是现在他变得更稳定和优秀，GM更小更容易安装、GM更有效率、GM的手册非常丰富GraphicsMagick的命令与ImageMagick基本是一样的。
GraphicsMagick的命令概览

----------------------------------------------------------------------------

[ convert | identify | mogrify | composite | montage | compare | display | animate | import | conjure ]

convert：转换图像格式和大小，模糊，裁剪，驱除污点，抖动，临近，图片上画图片，加入新图片，生成缩略图等。
identify：描述一个或较多图像文件的格式和特性。
mogrify：按规定尺寸***一个图像，模糊，裁剪，抖动等。Mogrify改写最初的图像文件然后写到一个不同的图像文件。
composite：根据一个图片或多个图片组合生成图片。
montage：创建一些分开的要素图像。在含有要素图像任意的装饰图片，如边框、结构、图片名称等。
compare：在算术上和视觉上评估不同的图片***其它的改造图片。
display：如果你拥有一个X server的系统，它可以按次序的显示图片
animate：利用X server显示动画图片
import：在X server或任何可见的窗口上输出图片文件。 你可以捕获单一窗口，整个的荧屏或任何荧屏的矩形部分。
conjure：解释执行 MSL (Magick Scripting Language) 写的脚本。

GraphicsMagick图像处理系统使用方法
-----------------------------------------------------
0. 显示图像文件详细信息
gm identify a.jpg

1.更改当前目录下*.jpg的尺寸大小，并保存于目录.thumb里面
gm mogrify -output-directory .thumbs -resize 320x200 *.jpg

2. 将三幅图像和并为一副图像
gm montage -mode concatenate -tile 3x1 image1.ppm image2.ppm image3.ppm concatenated.miff

3. 显示图像
gm display 'vid:*.jpg'

4. 格式转换
gm convert a.bmp a.jpg
gm convert a.bmp a.pdf（转换为pdf)

5. 调整图像dpi和大小
gm convert -density 288 -geometry 25% image.gif image.gif
（缩小为原先的1／4，并且dpi为288）

gm convert -resize 640x480 image.gif image.gif
（转换为640x480的图像)

6. 在图像上添加文字
gm convert -font Arial -fill blue -pointsize 18 -draw "text 10,10 'your text here'" test.tif test.png

7. 从gif文件中抽取第一帧
gm convert "Image.gif[0]" first.gif

8. 建立gif图像
gm convert -delay 20 frame*.gif animation.gif
gm convert -loop 50 frame*.gif animation.gif
（让动画循环50次）

gm convert -delay 20 frame1.gif -delay 10 frame2.gif -delay 5 frame3.gif animation.gif
（对每一帧手动指定延时）

9. 截屏
gm import a.jpg
用鼠标点击所要截取的窗口，或者选择截屏区域，保存为a.jpg

gm import -frame a.jpg
保留窗口的边框

GraphicsMagick常用管理命令
-----------------------------------------------------
查看版本后安装情况：gm identify -version
结果：打印出信息

识别图片：gm identify  /Users/zhaorai/Pictures/照片/100CANON-1/IMG_4108.JPG
结果：/Users/zhaorai/Pictures/照片/100CANON-1/IMG_4108.JPG JPEG 3648x2736+0+0 DirectClass 8-bit 2.5M 0.000u 0:01

识别图片(高级)：gm identify  -verbose /Users/zhaorai/Desktop/4.png
结果：打印出很多信息。

GraphicsMagick缩放比例的精准控制
-----------------------------------------------------
原始图片是input.jpg，尺寸：160x120

只缩小不放大
gm convert input.jpg -resize "500x500>" output_1.jpg
加了>,表示只有当图片的宽与高，大于给定的宽与高时，才进行“缩小”操作。
生成的图片大小是：160x120，未进行操作
如果不加>,会导致图片被比等放大。

等比缩图  （缺点：产生白边）
gm convert input.jpg -thumbnail "100x100" output_1.jpg
生成的图片大小是：100x75

非等比缩图，按给定的参数缩图（缺点：长宽比会变化）
gm convert input.jpg -thumbnail "100x100!" output_2.jpg
生成的图片大小是：100x100

裁剪后保证等比缩图 （缺点：裁剪了图片的一部分）
gm convert input.jpg -thumbnail "100x100^" -gravity center -extent 100x100 output_3.jpg
生成的图片大小是：100x100，还保证了比例。不过图片经过了裁剪，剪了图片左右两边才达到1:1

填充后保证等比缩图 （缺点：要填充颜色，和第一种方法基本一样）
gm convert input.jpg -thumbnail "100x100" -background gray -gravity center -extent 100x100 output_4.jpg
生成的图片大小是：100x100，还保证了比例，同时没有对图片进行任何裁剪，缺失的部分按指定颜色进行填充。

裁剪、填充相结合 （缺点：最差的方法）
gm convert input.jpg -thumbnail "10000@ -background gray -gravity center -extent 100x100 output_5.jpg
生成的图片大小是：100x100，这次保证了大小和比例，其中的10000就是100x100的乘积，同时在填充和裁剪之间做了一个平衡。



