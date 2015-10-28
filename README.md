自做OA管理项目是一个由MyEclipse创建，Struts2.3.x+Spring3.2.x+Hibernate3.x+EasyUI1.4.1架构的示例程序；后面如果有空闲时间会继续添加一些常见的功能，工作流什么的，有空也会修改。

后台访问路径：http://localhost:8080/JokerOne

系统截图：
 ![image](https://github.com/haojiahong/JokerOne/blob/master/readme_image/1.png)
用户管理：
  1、实现用户的增删改查
  2、下载EXCEL模板文件，批量添加用户
  3、通过echarts显示用户图表信息
  4、使用jasperReport和Ireports使用用户报表打印功能
  5、上传文件至FTP服务器，实现附件管理功能
 ![image](https://github.com/haojiahong/JokerOne/blob/master/readme_image/userlist.png)
 ![image](https://github.com/haojiahong/JokerOne/blob/master/readme_image/useradd.png)
 ![image](https://github.com/haojiahong/JokerOne/blob/master/readme_image/userchart.png)
 ![image](https://github.com/haojiahong/JokerOne/blob/master/readme_image/userprint.png)
角色管理：
 实现角色的增删改查，为权限管理做准备。
 ![image](https://github.com/haojiahong/JokerOne/blob/master/readme_image/rolelist.png)
菜单管理：
 easyui树表的形式实现菜单的增删改查
 ![image](https://github.com/haojiahong/JokerOne/blob/master/readme_image/menu.png)
部门管理：
 ![image](https://github.com/haojiahong/JokerOne/blob/master/readme_image/orglist.png)
其他功能：
 Quartz定时任务的实现。
配置项目注意：
配置数据库修改\config目录下的jdbc.properties即可。
配置FTP服务器需修改，applicationContext.xml中的ftpStore这个Bean的serverIP属性，同时创建相应的FTP文件夹，接收上传文件。

 
