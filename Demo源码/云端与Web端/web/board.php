<?php
	session_start();
	if(!isset($_SESSION['phonenum'])){
		header("Location:index.php");
		exit();
	}
	
	include_once("../conn.php");

?>
<!DOCTYPE html>
<html>
<head>
	<title>唐医生 - Web 医师端</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
    <!-- bootstrap -->
    <link href="css/bootstrap/bootstrap.css" rel="stylesheet" />
    <link href="css/bootstrap/bootstrap-overrides.css" type="text/css" rel="stylesheet" />

    <!-- libraries -->
    <link href="css/lib/jquery-ui-1.10.2.custom.css" rel="stylesheet" type="text/css" />
    <link href="css/lib/font-awesome.css" type="text/css" rel="stylesheet" />

    <!-- global styles -->
    <link rel="stylesheet" type="text/css" href="css/compiled/layout.css">
    <link rel="stylesheet" type="text/css" href="css/compiled/elements.css">
    <link rel="stylesheet" type="text/css" href="css/compiled/icons.css">

    <!-- this page specific styles -->
    <link rel="stylesheet" href="css/compiled/index.css" type="text/css" media="screen" />

    <!-- open sans font -->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800' rel='stylesheet' type='text/css'>

    <!-- lato font -->
    <link href='http://fonts.googleapis.com/css?family=Lato:300,400,700,900,300italic,400italic,700italic,900italic' rel='stylesheet' type='text/css'>

    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
</head>
<body>
    <!-- navbar -->
    <header class="navbar navbar-inverse" role="banner">
        <div class="navbar-header">
            <a class="navbar-brand" href="#"><img src="img/logo-white.png"></a>
        </div>
        <ul class="nav navbar-nav pull-right hidden-xs">
            <li class="settings hidden-xs hidden-sm">
                <a href="index.php?action=logout" role="button">
                    <i class="icon-share-alt"></i>
                </a>
            </li>
        </ul>
    </header>
    <!-- end navbar -->

    <!-- sidebar -->
    <div id="sidebar-nav">
        <ul id="dashboard-menu">
            <li class="active">
                <div class="pointer">
                    <div class="arrow"></div>
                    <div class="arrow_border"></div>
                </div>
                <a href="board.php">
                    <i class="icon-home"></i>
                    <span>首页</span>
                </a>
            </li>            
            <li>
                <a href="history_data.php">
                    <i class="icon-signal"></i>
                    <span>历史数据查询</span>
                </a>
            </li>
            <li>
                <a href="history_medical.php">
                    <i class="icon-book"></i>
                    <span>历史病历查询</span>
                </a>
            </li>
            <li>
                <a href="new_medical.php">
                    <i class="icon-pencil"></i>
                    <span>病历记录填写</span>
                </a>
            </li>
            <li>
                <a href="push_message.php">
                    <i class="icon-envelope"></i>
                    <span>诊断意见推送</span>
                </a>
            </li>
        </ul>
    </div>
    <!-- end sidebar -->


	<!-- main container -->
    <div class="content">

        <!-- upper main stats -->
        <div id="main-stats">
            <div class="row stats-row">
                <div class="col-md-3 col-sm-3 stat">
                    <div class="data">
                        <span class="number">
						<?php
							$result = mysql_query("SELECT COUNT(*) FROM record WHERE phonenum = ".$_SESSION["phonenum"]);
							$row = mysql_fetch_array($result);
							echo $row[0];
						?>
						</span>
                        测量记录
                    </div>
                </div>
                <div class="col-md-3 col-sm-3 stat">
                    <div class="data">
                        <span class="number">
						<?php
							$result = mysql_query("SELECT COUNT(*) FROM guardian WHERE phonenum = ".$_SESSION["phonenum"]);
							$row = mysql_fetch_array($result);
							echo $row[0];
						?>
						</span>
                        监护人
                    </div>
                </div>
                <div class="col-md-3 col-sm-3 stat">
                    <div class="data">
                        <span class="number">
						<?php
							$result = mysql_query("SELECT COUNT(*) FROM medicalhistory WHERE phonenum = ".$_SESSION["phonenum"]);
							$row = mysql_fetch_array($result);
							echo $row[0];
						?>
						</span>
                        病历记录
                    </div>
                </div>
                <div class="col-md-3 col-sm-3 stat last">
                    <div class="data">
                        <span class="number">
						<?php
							$result = mysql_query("SELECT AVG(value) FROM record WHERE phonenum = ".$_SESSION["phonenum"]);
							$row = mysql_fetch_array($result);
							echo round($row[0],2);
						?>
						</span>
                        血糖均值
                    </div>
                    <span class="date">mmol / L</span>
                </div>
            </div>
        </div>
        <!-- end upper main stats -->

        <div id="pad-wrapper">
			<h3>欢迎您，患者<?php echo $_SESSION["phonenum"];?>的医师！</h3>
        </div>
    </div>


	<!-- scripts -->
    <script src="http://code.jquery.com/jquery-latest.js"></script>
	<script src="js/bootstrap.min.js"></script>
    <script src="js/jquery-ui-1.10.2.custom.min.js"></script>
    <!-- knob -->
    <script src="js/jquery.knob.js"></script>
    <!-- flot charts -->
    <script src="js/jquery.flot.js"></script>
    <script src="js/jquery.flot.stack.js"></script>
    <script src="js/jquery.flot.resize.js"></script>
    <script src="js/theme.js"></script>

</body>
</html>
