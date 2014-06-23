最近在网上找了一些飞信发送的实现，这个就是参考网上代码写的

使用方法：
首先你得安装 PHP 环境和 Apache 服务器.我的电脑是 fedora 18系统，64 位操作系统。
使用 yum 安装的环境

网页默认执行路径是 /var/www/html。
eg: http://http://127.0.0.1/feixin.php?user=你的手机号&passwd=你的飞信密码&to=发送给某人的手机&msg=发送的内容
所以你可以将这个飞信代码( feixin.php 和 Fetion.php )放到该目录下，然后使用 url就可以直接调用进行发送了。

当然我自己也实现了不用 url 发送的部分，代码在 test.php 文件。
