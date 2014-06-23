<?php
	include_once("../conn.php");
	$lifeTime = 0.5 * 3600; 
	session_set_cookie_params($lifeTime); 
	session_start();

	if(isset($_SESSION['phonenum']) && !isset($_GET["action"]))
	{
		header("Location:board.php");
		exit();
	}
?>

<!DOCTYPE html>
<html class="login-bg">
<head>
	<title>唐医生 - Web 医师端</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
    <!-- bootstrap -->
    <link href="css/bootstrap/bootstrap.css" rel="stylesheet">
    <link href="css/bootstrap/bootstrap-overrides.css" type="text/css" rel="stylesheet">

    <!-- global styles -->
    <link rel="stylesheet" type="text/css" href="css/compiled/layout.css">
    <link rel="stylesheet" type="text/css" href="css/compiled/elements.css">
    <link rel="stylesheet" type="text/css" href="css/compiled/icons.css">

    <!-- libraries -->
    <link rel="stylesheet" type="text/css" href="css/lib/font-awesome.css">
    
    <!-- this page specific styles -->
    <link rel="stylesheet" href="css/compiled/signin.css" type="text/css" media="screen" />

    <!-- open sans font -->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,700italic,800italic,400,300,600,700,800' rel='stylesheet' type='text/css'>

    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
</head>
<body>
    <!-- background switcher -->
    <div class="bg-switch visible-desktop">
        <div class="bgs">
            <a href="#" data-img="landscape.jpg" class="bg active">
                <img src="img/bgs/landscape.jpg" />
            </a>
            <a href="#" data-img="blueish.jpg" class="bg">
                <img src="img/bgs/blueish.jpg" />
            </a>            
            <a href="#" data-img="7.jpg" class="bg">
                <img src="img/bgs/7.jpg" />
            </a>
            <a href="#" data-img="8.jpg" class="bg">
                <img src="img/bgs/8.jpg" />
            </a>
            <a href="#" data-img="9.jpg" class="bg">
                <img src="img/bgs/9.jpg" />
            </a>
            <a href="#" data-img="10.jpg" class="bg">
                <img src="img/bgs/10.jpg" />
            </a>
            <a href="#" data-img="11.jpg" class="bg">
                <img src="img/bgs/11.jpg" />
            </a>
        </div>
    </div>


    <div class="login-wrapper">
        <a href="index.html">
            <img class="logo" src="img/logo-white.png">
        </a>

        <div class="box">
            <div class="content-wrap">
                <h6>医师登录</h6>
				<?php
					if ($_GET["action"] == "logout")
					{
						unset($_SESSION['phonenum']);
						echo "	<div class=\"alert alert-info\">\n
									<strong>提示！</strong>您已成功登出！请重新登录。
								</div>\n";

					}
					if ($_POST["phonenum"] != "")
					{
						$result = mysql_query("SELECT password FROM account WHERE phonenum = ".$_POST["phonenum"]);
						$row = mysql_fetch_array($result);
						if ($_POST["password"] != $row["password"])
						{
							echo "	<div class=\"alert alert-info\">\n
										<strong>提示！</strong>用户名或密码错误。请检查你的输入。\n
									</div>\n";
						}
						else
						{
							$_SESSION['phonenum'] = $_POST["phonenum"];
							echo "<script>window.location.href=\"board.php\";</script>";
						}
					}
				?>
				<form action="index.php" method="post">
					<input name="phonenum" class="form-control" type="text" placeholder="患者电话">
					<input name="password" class="form-control" type="password" placeholder="密码">
					<a href="#" class="forgot">忘记密码?</a>
					<div class="remember">
						<input id="remember-me" type="checkbox">
						<label for="remember-me">记住密码</label>
					</div>
					<button type="submit" class="btn-glow primary login">登录</button>
				</form>
            </div>
        </div>

        <div class="no-account">
            <p>请与您的病患联系，以获取登陆账号。</p>
        </div>
    </div>

	<!-- scripts -->
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/theme.js"></script>

    <!-- pre load bg imgs -->
    <script type="text/javascript">
        $(function () {
            // bg switcher
            var $btns = $(".bg-switch .bg");
            $btns.click(function (e) {
                e.preventDefault();
                $btns.removeClass("active");
                $(this).addClass("active");
                var bg = $(this).data("img");

                $("html").css("background-image", "url('img/bgs/" + bg + "')");
            });

        });
    </script>
</body>
</html>
