#include "swap.h"
#include "stdio.h"
#include "sys.h"
#define FLOAT_HEX(float,which) (((IE*)&float)->bytes[which])
#define INT_HEX(int,which) (((IE*)&int)->bytes[which])
IE float_hex(float data)
{
	int i;
	IE myie;
	for(i=0;i<4;i++)
	{
		myie.bytes[i]=FLOAT_HEX(data,i);
	}
	return myie;
}
IE hex_float(u8 byte[])
{
	IE myie;
	myie.df=*(float*)byte;
	return myie;
}
IE int_hex(int data)
{
	int i;
	IE myie;
	for(i=0;i<4;i++)
	{
		myie.bytes[i]=INT_HEX(data,i);
	}
	return myie;
}
IE hex_int(u8 byte[])
{
	IE myie;
	myie.di=*(int*)byte;
	return myie;
}


