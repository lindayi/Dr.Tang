#ifndef __INFO_H
#define __INFO_H
#include "sys.h"
typedef struct node
{
	float temp1;    //环境温度
	float temp2;    //人体温度
	float press;    //血压
	u8 hour;
	u8 min;
	u8 sec;
	u8 year_h;
	u8 year_l;
	u8 month;
	u8 date;
}List;

void Write_data(List list);			 //写数据到SD卡中
void  Read_data(u32 count,u8 cnt);	 //读数据到缓冲区
void Read_all(void);				 //读所有数据
void Read_lastt1(void);				 //读最后一次环境温度
void Read_lastt2(void);				 //读最后一次人体温度
void Read_lastpr(void);				 //读最后一次血压
void Read_temp1(void);				 //读所有环境温度
void Read_temp2(void);				 //读所有人体温度
void Read_press(void);               //读所有血压
void TEST(void);
void Write_count(void);
void Read_count(void);
#endif
