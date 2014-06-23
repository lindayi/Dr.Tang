#ifndef __INFO_H
#define __INFO_H
#include "sys.h"
typedef struct node
{
	float temp1;    //�����¶�
	float temp2;    //�����¶�
	float press;    //Ѫѹ
	u8 hour;
	u8 min;
	u8 sec;
	u8 year_h;
	u8 year_l;
	u8 month;
	u8 date;
}List;

void Write_data(List list);			 //д���ݵ�SD����
void  Read_data(u32 count,u8 cnt);	 //�����ݵ�������
void Read_all(void);				 //����������
void Read_lastt1(void);				 //�����һ�λ����¶�
void Read_lastt2(void);				 //�����һ�������¶�
void Read_lastpr(void);				 //�����һ��Ѫѹ
void Read_temp1(void);				 //�����л����¶�
void Read_temp2(void);				 //�����������¶�
void Read_press(void);               //������Ѫѹ
void TEST(void);
void Write_count(void);
void Read_count(void);
#endif
