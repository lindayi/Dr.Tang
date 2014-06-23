#include "sys.h"
#include "usart2.h"
#include "stdio.h"
#include "lcd.h"
#include "led.h"
#include "delay.h"
//加入以下代码,支持printf函数,而不需要选择use MicroLIB	  

//注意,读取USARTx->SR能避免莫名其妙的错误  
/**********************************/
u8 COUNT=0;
extern u8 flag,f=12;				   //f:记录数据长度   前几包为12字节，最后一包为18字节
u8 USART2_RX_BUF[18]={0x00};    	  //接收数据缓冲区
u8 USART2_START[12]= {0X4F,0X16,0XFF,0XFF,0X06,0XFF,0XFF,0X00,0X06,0X01,0X01,0X59};		  //开始指令
u8 USART2_BACK[12]=  {0X5F,0X16,0XFF,0XFF,0X06,0XFF,0XFF,0X00,0X06,0X01,0X01,0X49};		  //如果通讯则回包
u8 USART2_CHECK[12]= {0X4F,0X16,0XFF,0XFF,0X06,0XFF,0XFF,0X00,0X06,0X02,0X01,0X5A};		  //矫正发送包
u8 USART2_TIME[12]=  {0X5F,0X16,0XFF,0XFF,0X06,0XFF,0XFF,0X00,0X06,0X05,0X01,0X4D};		  //加血后提示倒计时模块发送包
void USART2_IRQHandler(void)				                                              //中断接收数据
{
	u8 res;	  
	if(USART2->SR&(1<<5))                                                                 //接收到数据
	{
		res=USART2->DR; 
		USART2_RX_BUF[COUNT]=res;
		COUNT++; 
		if(COUNT==f)
		{  
			COUNT=0;
			check();	
		}							     
	}  											 
} 

void USART2_send(u8 USART2_cmd[])										  //发送一包数据到模块
{
	u8 i;
	for(i=0;i<12;i++)
	{
		while((USART2->SR & 0x40)==0);   //等待一个字节发送完成
		USART2->DR=USART2_cmd[i];
	}
}

//初始化IO 串口2
//pclk2:PCLK2时钟频率(Mhz)
//bound:波特率
//CHECK OK
//091209
//接收数据
//一个起始位，8个数据位，一个校验位
void uart2_init(u32 pclk2,u32 bound)
{  	 
	float temp;
	u16 mantissa;
	u16 fraction;	   
	temp=(float)(pclk2*1000000)/(bound*16);//得到USARTDIV
	mantissa=temp;				 //得到整数部分
	fraction=(temp-mantissa)*16; //得到小数部分	 
    mantissa<<=4;
	mantissa+=fraction; 
	RCC->APB2ENR|=1<<2;   //使能PORTA口时钟 PA2(TX) PA3(RX) 
	RCC->APB1ENR|=1<<17;  //使能串口2时钟 
	
	GPIOA->CRL&=0xFFFF00FF; 
	GPIOA->CRL|=0X00008B00;//IO状态设置 	  
	RCC->APB1RSTR|=1<<17;   //复位串口2
	RCC->APB1RSTR&=~(1<<17);//停止复位	   	   
	//波特率设置
 	USART2->BRR=mantissa; // 波特率设置	 
	USART2->CR1|=0X200C;  //1位起始位,8个数据位，无校验位.
//#ifdef EN_USART2_RX		  //如果使能了接收
	USART2->CR1|=1<<8;    //PE中断使能
//	USART2->CR1|=1<<7;    //PE中断使能
//	USART2->CR1|=1<<6;    //PE中断使能
	USART2->CR1|=1<<5;    //接收缓冲区非空中断使能		
	MY_NVIC_Init(0,1,USART2_IRQChannel,2);//组2，最低优先级 
//#endif
}

//当接收到一包数据时进入该函数处理
void check()
{
	if(USART2_RX_BUF[11]==0x49) flag=1;
	else if(USART2_RX_BUF[11]==0x4A && USART2_RX_BUF[9]==0x02) flag=2;	  //矫正
	else if(USART2_RX_BUF[11]==0x4A && USART2_RX_BUF[9]==0x03) flag=3;	  //插入试纸
	else if(USART2_RX_BUF[11]==0x4B) flag=4;							  //提示加血
	else if(USART2_RX_BUF[11]==0x4C) flag=5;							  //加血成功
	else if(USART2_RX_BUF[11]==0x4D) {flag=6,f=18;}	
	else if(f==18) flag=7;			      //等待结果
}

//void func1(void)
//{
//	printf("start");
//}
//void func2(void)
//{
//	printf("correct");
//}
//void func3(void)
//{
//	printf("pls input");
//}
//void func4(void)
//{
//	printf("pls blood");
//}
//void func5(void)
//{
//	printf("pls wait");
//}
//void func6(void)
//{
//	int temp;
//	temp=(int)((USART2_RX_BUF[9]<<8)|(USART2_RX_BUF[10]));
//	temp/=10;
//	printf("%d ",temp);
//}


void func1(void)
{
	LCD_ShowString(100,150,"start  check!");
}
void func2(void)
{
	LCD_ShowString(100,150,"that correct!");
}
void func3(void)
{	
	LCD_ShowString(100,150,"please input!");
}
void func4(void)
{
	LCD_ShowString(100,150,"zhi  inputed!");
}
void func5(void)
{  
	LCD_ShowString(100,150,"please blood!");
}
void func6(void)
{
	 LCD_ShowString(100,150,"time    time!");
}
//void solution(void)
//{
//	int temp;
//	u8 bytes[2]={0x00};
//	LCD_Clear(WHITE);
//	LCD_ShowString(50,150,"blood:");
//	LCD_ShowString(120,150,".0 mmol/L");
//	bytes[0]=USART2_RX_BUF[9];
//	bytes[1]=USART2_RX_BUF[10];
//	temp=*(int*)bytes;
//	temp/=10;
//	LCD_ShowNum(102,150,temp,2,16);
//}
