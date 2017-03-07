#include "my.h"

void swap (int array[], int num){   // swaps higher and lower value
	int temp;
	temp = array[num];
	array[num] = array[num+1];
	array[num+1] = temp;
}
