<?php
	include_once("conn.php");
	include_once("function.php");

	if ($_GET["action"] == "") exit();
	if ($_GET["action"] == "register")
		echo Register($_GET["phonenum"], $_GET["password"]);
	if ($_GET["action"] == "login")
		echo Login($_GET["phonenum"], $_GET["password"]);
	if ($_GET["action"] == "edit_password")
		echo EditPassword($_GET["phonenum"], $_GET["password"]);
	if ($_GET["action"] == "add_guardian")
		echo AddGuardian($_GET["phonenum"], $_GET["guardiantel"]);
	if ($_GET["action"] == "del_guardian")
		echo DelGuardian($_GET["phonenum"], $_GET["guardiantel"]);
	if ($_GET["action"] == "get_guardian")
		echo GetGuardian($_GET["phonenum"]);
	if ($_GET["action"] == "add_record")
		echo AddRecord($_GET["phonenum"], $_GET["value"], $_GET["food"], $_GET["sport"], $_GET["medicine"], $_GET["round"]);
	if ($_GET["action"] == "get_record")
		echo GetRecord($_GET["phonenum"], $_GET["starttime"], $_GET["endtime"]);
?>
