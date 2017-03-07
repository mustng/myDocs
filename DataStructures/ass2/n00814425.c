
#include "my.h"

main( int argc, char** argv)
{
    int* numberArray;
    int i,j;			// For loop counters
    int arraySize;		// First data in and is size of the array		
    int call = 0;		// Gives array loctaion when user asks for it
    int counter = 0; 	// counts total number of loops it took to sort array
    bool check = 1; 	// Runs while loop until counter stops.
    
    FILE *myFile;
    myFile = fopen(argv[1], "r+");

	fscanf(myFile, "%d", &arraySize);
	
	numberArray = 	(int*)calloc (arraySize,sizeof(int));
	
    for (i = 0; i < arraySize; i++)
    {
        fscanf(myFile, "%d", &numberArray[i]);
    }
    
	fclose(myFile);
    /////////////////////////////////Files scanned in
    
    
    while (check != 0){
    	check = 0;
        for (i = 1; i < arraySize; i+=2){
        	if (i+1 >= arraySize){			//if end of array break
				break;
				}
			if ( numberArray[i] > numberArray [i+1] ){
        		swap(numberArray , i);
        		check = 1;
        		counter++;
			}
    	}

    	for (j = 0; j < arraySize; j+=2){
    		if (j+1 >= arraySize){
					break;
				}
        	if ( numberArray[j] > numberArray [j+1] ){
        		swap(numberArray , j);
        		check = 1;
        		counter++;
			}
    	}	
    }
    
  	//printf("There were %d swaps made\n\n", counter);  

    /////////////////////////////////////////////// Poke at array
    while (call != -1){
    	printf("\nWhich index would you like to look or -1 to exit: ");
    	scanf("%d", &call);
    	if (call > arraySize){
    		printf("\nTry again!\n");			//error check
    	}
    	if ((call != -1) && (call <= arraySize)){
    	printf(" %d\n", numberArray[call]);		//print
    	} 
    }
    	
	return 0;
}
