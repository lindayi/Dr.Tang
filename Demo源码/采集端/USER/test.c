#include "sys.h"		
#include "delay.h"	
#include "led.h" 
#include "usart.h"
#include "usart2.h"
#include "lcd.h"
//Mini STM32开发板范例代码1
//跑马灯实验		   
//正点原子@ALIENTEK
//技术论坛:www.openedv.com	 
extern u8 USART2_START[12];
extern u8 USART2_WAIT[12];
extern u8 USART2_TIME[12];
extern u8 USART2_CHECK[12];
u8 flag=0;
extern u8 USART2_RX_BUF[18];
int main(void)
{ 	
	u16 tp;
	float temp;
	u8 bytes[2]={0x01,0x7c};			  
	Stm32_Clock_Init(9); //系统时钟设置
	delay_init(72);	     //延时初始化
	uart_init(72,9600);
	LED_Init();		  	 //初始化与LED连接的硬件接口
	LCD_Init();
	delay_ms(1000);
	uart2_init(36,57600);
	USART2_send(USART2_START);
	while(flag==0);	
	USART2_send(USART2_CHECK);
	while(1)
	{ 
		LED0=0;
		if(flag==1)
		{
			func1();
			delay_ms(1000);
		}
		else if(flag==2)
		{
			func2();
			delay_ms(1000);
		}
		else if(flag==3)
		{
			func3();
			delay_ms(1000);
		}
		else if(flag==4)
		{
			func4();
			delay_ms(1000);
		}
		else if(flag==5)
		{
			func5();
			delay_ms(1000);
		}
		else if(flag==6)
		{
			func6();
			delay_ms(1000);
		}
		else if(flag==7)break;
	}
	LCD_Clear(WHITE);
	LCD_ShowString(50,150,"blood:");
	LCD_ShowString(120,150,".  mmol/L");
	bytes[0]=USART2_RX_BUF[9];
	bytes[1]=USART2_RX_BUF[10];
	tp=(bytes[0]<<8)|bytes[1];
	temp=(float)tp/10.0;
	LCD_ShowNum(100,150,tp/10,2,16);
	LCD_ShowNum(125,150,tp%10,2,16);
	USART3_Init(36,9600);
	u2_printf("%.2f",temp);
	while(1);
}

























