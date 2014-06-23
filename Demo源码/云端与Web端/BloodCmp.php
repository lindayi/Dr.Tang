<?php
include_once("fetion.php");
class BloodCmp{
	// 饭前判断标准数据区（单位：mmol/l）
	private $BStdHigh = 6.1;				// 测量血糖值高压阀值标准
	private $BStdLow = 3.9;					// 测量血糖值低压阀值标准
	private $BStdHighLevel = 0.9;				// 测量血糖值轻重度高压标准
	private $BStdLowLevel = 1.09;				// 测量血糖值轻重度低压标准		
	// 饭后
	private $StdHigh = 7.8;					// 测量血糖值高压阀值标准
	private $StdLow = 3.9;					// 测量血糖值低压阀值标准
	private $StdHighLevel = 0.8;				// 测量血糖值轻重度高压标准
	private $StdLowLevel = 0.8;				// 测量血糖值轻重度低压标准		
	
	// 获取数据
	private $Telephone;
	private $BloodData;

	// 构造函数
	function __construct($Tphone, $BData)
	{
		$this->BloodData = $BData;
		$this->Telephone = $Tphone;
	}
	
	// 创建目录
	function Mkdirs($dir)  
	{  
		if(!is_dir($dir))  
		{  
			if(!mkdir($dir, 0777))
			{  
				return false;  
			}  
		}  
		return true;  
	}

	// 云端日志记录
	function RecordLog($result)
	{
		date_default_timezone_set('Etc/GMT-8');	   	//这里设置了时区
		$nowtime = date("Y-m-d H:i:s");
		
		if (!$this->Mkdirs("./log"))
		{
			return false;
		}
		$filename = "log/" . $this->Telephone . ".txt";
		$tmp = "电话:" . $this->Telephone . " \n时间:" . $nowtime . "\n血糖值:" . $this->BloodData . "\n结果:" . $result . "\n\n";
		$fp = fopen($filename, "a");
		fwrite($fp, $tmp);
		fclose($fp);
	}
	
	// 进行单次饭前阀值比较
	public function BBlood_cmp()
	{
		$temph = $this->BloodData - $this->BStdHigh;	// 本次测量值和高阀值的比较
		$templ = $this->BloodData - $this->BStdLow;	// 本次测量值和低阀值的比较
		if ($this->BloodData > $this->BStdHigh)		// 超出高阀值
		{
			if ($temph < $this->BStdHighLevel)
				$level = 1;			// 轻度高, level 进行等级设定
			else
				$level = 2;			// 重度高
			$result = "血糖高";
		}
		else if($this->BloodData < $this->BStdLow)	// 超出低阀值
		{
			if ($templ < $this->BStdHighLevel)
				$level = -1;			// 轻度低
			else 
				$level = -2;			// 重度低
			$result = "血糖低";
		}
		else
		{
			$level = 0;
			$result = "正常";
		}
		$this->RecordLog($result);			// 进行云端日志记录
		$SendFetion = new Fetion($level, $this->Telephone, "");
		return $SendFetion->fetion();
	}

	// 进行单次饭后阀值比较
	public function Blood_cmp()
	{
		$temph = $this->BloodData - $this->StdHigh;	// 本次测量值和高阀值的比较
		$templ = $this->BloodData - $this->StdLow;	// 本次测量值和低阀值的比较
		if ($this->BloodData > $this->StdHigh)		// 超出高阀值
		{
			if ($temph < $this->StdHighLevel)
				$level = 1;			// 轻度高, level 进行等级设定
			else
				$level = 2;			// 重度高
			$result = "血糖高";
		}
		else if($this->BloodData < $this->StdLow)	// 超出低阀值
		{
			if ($templ < $this->StdHighLevel)
				$level = -1;			// 轻度低
			else 
				$level = -2;			// 重度低
			$result = "血糖低";
		}
		else
		{
			$level = 0;
			$result = "正常";
		}
		$this->RecordLog($result);			// 进行云端日志记录
		$SendFetion = new Fetion($level, $this->Telephone, "");
		return $SendFetion->fetion();
	}
}
?>
