#include "sys.h"
#include "usart2.h"
#include "stdio.h"
#include "lcd.h"
#include "led.h"
#include "delay.h"
//�������´���,֧��printf����,������Ҫѡ��use MicroLIB	  

//ע��,��ȡUSARTx->SR�ܱ���Ī������Ĵ���  
/**********************************/
u8 COUNT=0;
extern u8 flag,f=12;				   //f:��¼���ݳ���   ǰ����Ϊ12�ֽڣ����һ��Ϊ18�ֽ�
u8 USART2_RX_BUF[18]={0x00};    	  //�������ݻ�����
u8 USART2_START[12]= {0X4F,0X16,0XFF,0XFF,0X06,0XFF,0XFF,0X00,0X06,0X01,0X01,0X59};		  //��ʼָ��
u8 USART2_BACK[12]=  {0X5F,0X16,0XFF,0XFF,0X06,0XFF,0XFF,0X00,0X06,0X01,0X01,0X49};		  //���ͨѶ��ذ�
u8 USART2_CHECK[12]= {0X4F,0X16,0XFF,0XFF,0X06,0XFF,0XFF,0X00,0X06,0X02,0X01,0X5A};		  //�������Ͱ�
u8 USART2_TIME[12]=  {0X5F,0X16,0XFF,0XFF,0X06,0XFF,0XFF,0X00,0X06,0X05,0X01,0X4D};		  //��Ѫ����ʾ����ʱģ�鷢�Ͱ�
void USART2_IRQHandler(void)				                                              //�жϽ�������
{
	u8 res;	  
	if(USART2->SR&(1<<5))                                                                 //���յ�����
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

void USART2_send(u8 USART2_cmd[])										  //����һ�����ݵ�ģ��
{
	u8 i;
	for(i=0;i<12;i++)
	{
		while((USART2->SR & 0x40)==0);   //�ȴ�һ���ֽڷ������
		USART2->DR=USART2_cmd[i];
	}
}

//��ʼ��IO ����2
//pclk2:PCLK2ʱ��Ƶ��(Mhz)
//bound:������
//CHECK OK
//091209
//��������
//һ����ʼλ��8������λ��һ��У��λ
void uart2_init(u32 pclk2,u32 bound)
{  	 
	float temp;
	u16 mantissa;
	u16 fraction;	   
	temp=(float)(pclk2*1000000)/(bound*16);//�õ�USARTDIV
	mantissa=temp;				 //�õ���������
	fraction=(temp-mantissa)*16; //�õ�С������	 
    mantissa<<=4;
	mantissa+=fraction; 
	RCC->APB2ENR|=1<<2;   //ʹ��PORTA��ʱ�� PA2(TX) PA3(RX) 
	RCC->APB1ENR|=1<<17;  //ʹ�ܴ���2ʱ�� 
	
	GPIOA->CRL&=0xFFFF00FF; 
	GPIOA->CRL|=0X00008B00;//IO״̬���� 	  
	RCC->APB1RSTR|=1<<17;   //��λ����2
	RCC->APB1RSTR&=~(1<<17);//ֹͣ��λ	   	   
	//����������
 	USART2->BRR=mantissa; // ����������	 
	USART2->CR1|=0X200C;  //1λ��ʼλ,8������λ����У��λ.
//#ifdef EN_USART2_RX		  //���ʹ���˽���
	USART2->CR1|=1<<8;    //PE�ж�ʹ��
//	USART2->CR1|=1<<7;    //PE�ж�ʹ��
//	USART2->CR1|=1<<6;    //PE�ж�ʹ��
	USART2->CR1|=1<<5;    //���ջ������ǿ��ж�ʹ��		
	MY_NVIC_Init(0,1,USART2_IRQChannel,2);//��2��������ȼ� 
//#endif
}

//�����յ�һ������ʱ����ú�������
void check()
{
	if(USART2_RX_BUF[11]==0x49) flag=1;
	else if(USART2_RX_BUF[11]==0x4A && USART2_RX_BUF[9]==0x02) flag=2;	  //����
	else if(USART2_RX_BUF[11]==0x4A && USART2_RX_BUF[9]==0x03) flag=3;	  //������ֽ
	else if(USART2_RX_BUF[11]==0x4B) flag=4;							  //��ʾ��Ѫ
	else if(USART2_RX_BUF[11]==0x4C) flag=5;							  //��Ѫ�ɹ�
	else if(USART2_RX_BUF[11]==0x4D) {flag=6,f=18;}	
	else if(f==18) flag=7;			      //�ȴ����
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
