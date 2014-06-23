#ifndef __SWAP_H
#define __SWAP_H
#include "sys.h"
typedef union
{
	u8 bytes[4];
	float df;
	int di;
}IE;
IE hex_float(u8 data[]);
//float hex_float(u8 data[4]);
IE float_hex(float data);
IE int_hex(int data);
IE hex_int(u8 byte[]);
#endif
