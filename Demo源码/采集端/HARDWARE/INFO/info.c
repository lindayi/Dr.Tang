#include "info.h"
#include "swap.h"
#include "mmc_sd.h"
#include "stdio.h"
#include "sys.h"
#include "delay.h"
#include "rtc.h"
#include "string.h"
#include "usart2.h"
#include "stdarg.h"	
#include "lcd.h" 	 
#include "led.h"

u8 datacount=0;  //�ڼ���д����
const u8 LEN =19;	//List����
u8 data[512]={0x0};   //���ݻ�����
List link[10];      //List���ݻ�����
extern tm timer;
//��SD����д����
//tp1:�����¶�
//tp2:�����¶�
//pre:Ѫѹ
//data:д���ݻ���
//List:һ��ʱ����Ϣ�Ľṹ��
void Write_data(List list)
{
	u8 i;
	IE myie;
	float tp1,tp2,pre;
	tp1=list.temp1;
	tp2=list.temp2;
	pre=list.press;
	myie=float_hex(tp1);
	for(i=0;i<4;i++)
		data[i]=myie.bytes[i];
	myie=float_hex(tp2);
	for(;i<8;i++)
		data[i]=myie.bytes[i-4];
	myie=float_hex(pre);
	for(;i<12;i++)
		data[i]=myie.bytes[i-8];
	data[12]=list.hour;
	data[13]=list.min;
	data[14]=list.sec;
	data[15]=list.year_h;
	data[16]=list.year_l;
	data[17]=list.month;
	data[18]=list.date;
	SD_Write(data,datacount);
	datacount+=1;
}

//��SD���ж�ȡ����
//count:�ӵڼ������ݿ�ʼ��ȡ
//cnt:��ȡ��������
void  Read_data(u32 start,u8 cnt)
{
	u8 i,j=0;
	u8 buf[]={0x0,0x0,0x0,0x0};		    //���ݻ�����
	SD_Read(data,start,cnt);
	while(cnt)
	{
		for(i=0;i<4;i++)
			buf[i]=data[j++];
		link[start].temp1=*(float*)buf;	    //��4���ֽڵ�16����ת����һ��float��
		for(i=0;i<4;i++)
			buf[i]=data[j++];
		link[start].temp2=*(float*)buf;
		for(i=0;i<4;i++)
			buf[i]=data[j++];
		link[start].press=*(float*)buf;
	
		link[start].hour=data[j++];
		link[start].min=data[j++];
		link[start].sec=data[j++];
		link[start].year_h=data[j++];
		link[start].year_l=data[j++];
		link[start].month=data[j++];
		link[start].date=data[j++];

		cnt--;
		start++;
	}
}

//�����л����¶�
void Read_temp1(void)
{
	u8 i;
	u2_printf("\nall temp1:\n");
	delay_ms(100);
	for(i=0;i<datacount;i++)
	{
		u2_printf("\nt1:%4.1f %d:%d:%d %d%d-%d-%d\n",link[i].temp1,link[i].hour,link[i].min,link[i].sec,link[i].year_h,
				link[i].year_l,link[i].month,link[i].date);		//���͵�����ģ��
		delay_ms(100);
	}
}

//�����������¶�
void Read_temp2(void)
{
	u8 i;
	u2_printf("\nall temp2:\n");
	delay_ms(100);
	for(i=0;i<datacount;i++)
	{
		u2_printf("\nt2:%4.1f %d:%d:%d %d%d-%d-%d\n",link[i].temp2,link[i].hour,link[i].min,link[i].sec,link[i].year_h,
				link[i].year_l,link[i].month,link[i].date);		//���͵�����ģ��
		delay_ms(100);
	}
}

//������Ѫѹ
void Read_press(void)
{
	u8 i;
	u2_printf("\nall press:\n");
	delay_ms(100);
	for(i=0;i<datacount;i++)
	{
		u2_printf("\nP:%4.1f %d:%d:%d %d%d-%d-%d\n",link[i].press,link[i].hour,link[i].min,link[i].sec,link[i].year_h,
				link[i].year_l,link[i].month,link[i].date);		//���͵�����ģ��
		delay_ms(100);
	}
}
//����������
void Read_all(void)
{
	u8 i;
	u2_printf("\nall data:\n");
	delay_ms(100);
	for(i=0;i<datacount;i++)
	{
		u2_printf("\nt1:%4.1f t2:%4.1f P:%4.1f %d:%d:%d %d%d-%d-%d\n",link[i].temp1,link[i].temp2,link[i].press,link[i].hour,link[i].min,link[i].sec,link[i].year_h,
				link[i].year_l,link[i].month,link[i].date);		//���͵�����ģ��
		delay_ms(100);
	}
}
//�����һ�λ����¶�
void Read_lastt1(void)
{
	u8 i=datacount-1;
	u2_printf("\nlast temp1:\n");
	delay_ms(100);
	u2_printf("\nt1:%4.1f %d:%d:%d %d%d-%d-%d\n",link[i].temp1,link[i].hour,link[i].min,link[i].sec,link[i].year_h,
			link[i].year_l,link[i].month,link[i].date);		//���͵�����ģ��
}

//�����һ�������¶�
void Read_lastt2(void)
{
	u8 i=datacount-1;
	u2_printf("\nlast temp2:\n");
	delay_ms(100);
	u2_printf("\nt2:%4.1f %d:%d:%d %d%d-%d-%d\n",link[i].temp2,link[i].hour,link[i].min,link[i].sec,link[i].year_h,
			link[i].year_l,link[i].month,link[i].date);		//���͵�����ģ��
}

//�����һ��Ѫѹ
void Read_lastpr(void)
{
	u8 i=datacount-1;
	u2_printf("\nlast press:\n");
	delay_ms(100);
	u2_printf("P:%4.1f %d:%d:%d %d%d-%d-%d\n",link[i].press,link[i].hour,link[i].min,link[i].sec,link[i].year_h,
			link[i].year_l,link[i].month,link[i].date);		//���͵�����ģ��
}
//д��Ϣ����
void Write_count(void)
{
	SD_Write(&datacount,100);			  //д��1000fans
}
//����Ϣ����
void Read_count(void)
{
	u8 byte=0x0;
	Read_data(100,1);
	byte=data[0];
	datacount=byte;
}
//���Գ���
//ÿ��2S���һ�Σ�дһ������
void TEST()
{  
	u8 cnt,start;
	List list;
	RTC_Get();
	list.temp1=-12.1;
	list.temp2=-1.0;
	list.press=29.0;
	list.hour=timer.hour;
	list.min=timer.min;
	list.sec=timer.sec;
	list.year_h=timer.w_year_h;
	list.year_l=timer.w_year_l;
	list.month=timer.w_month;
	list.date=timer.w_date;
    Write_data(list);
	u2_printf("\nt1:%4.1f t2:%4.1f P:%4.1f %d:%d:%d %d%d-%d-%d\n",list.temp1,list.temp2,list.press,list.hour,list.min,list.sec,list.year_h,
				list.year_l,list.month,list.date);		//���͵�����ģ��

	delay_ms(1000);
	delay_ms(1000);
//	Write_data(list1);
//	u2_printf("\nt1:%4.1f t2:%4.1f P:%4.1f %d:%d:%d %d%d-%d-%d\n",list1.temp1,list1.temp2,list1.press,list1.hour,list1.min,list1.sec,list1.year_h,
//				list1.year_l,list1.month,list1.date);		//���͵�����ģ��

	delay_ms(1000);
	RTC_Get();
	list.temp1=12.5;
	list.temp2=-11.0;
	list.press=29.0;
	delay_ms(1000);
	list.hour=timer.hour;
	list.min=timer.min;
	list.sec=timer.sec;
	list.year_h=timer.w_year_h;
	list.year_l=timer.w_year_l;
	list.month=timer.w_month;
	list.date=timer.w_date;
	Write_data(list);
	u2_printf("\nt1:%4.1f t2:%4.1f P:%4.1f %d:%d:%d %d%d-%d-%d\n",list.temp1,list.temp2,list.press,list.hour,list.min,list.sec,list.year_h,
				list.year_l,list.month,list.date);		//���͵�����ģ��
	

	start=0;
	cnt=2;
	Read_data(start,cnt);
//	printf("datacount=%d\n",datacount);
//	Write_count();
//	Read_count();
//	printf("count=%d\n",datacount);
}	

