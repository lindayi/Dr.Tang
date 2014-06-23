
唐医生血糖监控系统
======

初次使用：
------

1、fetion.php 中填写发送端的飞信手机号和密码

2、conn.php 中填写你的 mysql 数据库的密码


接口说明：
------

####接口入口：
>http://drtang.lindayi.tk/drtang.php

####接口调用方式：
>HTTP协议 POST包

####接口参数与返回值：
#####注册

>参数：

>>action: register

>>phonenum

>>password

>返回值：

>>1 注册成功

>>0 注册失败

>使用样例：

>>http://drtang.lindayi.tk/drtang.php?action=register&phonenum=12345&password=abcde

#####登录

>参数：

>>action: register

>>phonenum

>>password

>返回值：

>>1 认证成功

>>0 认证失败

>使用样例：

>>http://drtang.lindayi.tk/drtang.php?action=login&phonenum=12345&password=abcde

#####添加监护人

>参数：

>>action: add_guardian

>>phonenum

>>guardiantel

>返回值：

>>1 添加成功

>>0 添加失败

>使用样例：

>>http://drtang.lindayi.tk/drtang.php?action=add_guardian&phonenum=12345&guardiantel=67890

#####删除监护人

>参数：

>>action: del_guardian

>>phonenum

>>guardiantel

>返回值：

>>1 删除成功

>>0 删除失败

>使用样例：

>>http://drtang.lindayi.tk/drtang.php?action=del_guardian&phonenum=12345&guardiantel=67890

#####获取监护人

>参数：

>>action: get_guardian

>>phonenum

>返回值：

>>{"guardian" : [电话1, 电话2, ……]}

>使用样例：

>>http://drtang.lindayi.tk/drtang.php?action=get_guardian

#####添加记录

>参数：

>>action: add_record

>>phonenum

>>value

>>food

>>sport

>>medicine

>>round

>返回值：

>>字符串，健康评价与建议。

>使用样例：

>>http://drtang.lindayi.tk/drtang.php?action=add_record&phonenum=12345&value=3.1&food=食品&sport=运动&medicine=用药&round=1

#####获取记录

>参数：

>>action: get_record

>>phonenum

>>starttime

>>endtime

>返回值：

>>{"record":[{"time":"2014-04-28 17:55:36", "value":13.2, "food":"食品1", "sport":"运动1", "medicine":"药品1", "round":1},{"time":"2014-04-28 17:59:45", "value":32, "food":"食品2", "sport":"运动2", "medicine":"药品2", "round":2}]}

>使用样例：

>>http://drtang.lindayi.tk/drtang.php?action=get_record&phonenum=18706829087&starttime=2014-01-01 00:00:00&endtime=2015-01-01 23:59:59
