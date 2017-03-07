import java.util.Random;

public class p3 {
	
	static int fault = 0;

	public static void main(String[] args) {
		String maxSize = "";
		String biggestNumber = "";
		
		int arraySize = 1000;
		
		if (args.length < 1){
			System.out.println("No input provided!");
			System.exit(0);
		}
		else{
			maxSize = args[0];
			biggestNumber = args[1];
		}
		
		int size= Integer.parseInt(maxSize);
		int randSize = Integer.parseInt(biggestNumber);
		
		
		int[] array = generateArray(arraySize, randSize);
				
		System.out.print("\t\t\t\ta\t\t\tb\t\tPF\n" + "FCFS\t\t\t\t" + size + "\t\t\t" + randSize + "\t\t" + fifo(array, size));
		System.out.print("\n\nClock\t\t\t\t" + size + "\t\t\t" + randSize + "\t\t" + clk(array, size));
		System.out.print("\n\nLRU\t\t\t\t" + size + "\t\t\t" + randSize + "\t\t" + lru(array, size));
		System.out.print("\n\noptimal\t\t\t\t" + size + "\t\t\t" + randSize + "\t\t" + opt(array, size) + "\n\n");


		run10000(size, arraySize,  randSize);
		
	}
	
	static int[] generateArray(int arraySize, int randSize){
		Random rand = new Random(System.currentTimeMillis());  
		int array[] = new int [arraySize];	
		
		for (int i = 0; i < arraySize; i++){
			array[i] = rand.nextInt(randSize) + 1;
		}
		
		return array;
	}
	
	static void run10000(int size, int arraySize, int randSize){
		int testTimes = 10000;
		
		double num1 = 0, num1Total = 0;
		double num2 = 0, num2Total = 0;
		double num3 = 0, num3Total = 0;
		double num4 = 0, num4Total = 0;
		
		System.out.print("Fault average after running 10,000 times with random arrays");
		
		int calcPeriod = testTimes / 20;
		int printPeriod = 0;
		
		for (int i = 0; i < testTimes; i++){
		
			num1 = fifo(generateArray(arraySize, randSize), size);
			num1Total +=num1;
			
			num2 = clk(generateArray(arraySize, randSize), size);
			num2Total +=num2;
			
			num3 = lru(generateArray(arraySize, randSize), size);
			num3Total +=num3;
			
			num4 = opt(generateArray(arraySize, randSize), size);
			num4Total +=num4;
			if (i == printPeriod ){
				System.out.print(".");
				printPeriod += calcPeriod;
			}
		}
		num1Total/=testTimes;
		num2Total/=testTimes;
		num3Total/=testTimes;
		num4Total/=testTimes;
		
		System.out.println("\n\n" + "FCFS: " + num1Total + " Clock: " + num2Total + " LRU: " 
		+ num3Total + " optimal: " +num4Total + "\n");
	}
	
	static int opt(int array[], int size){
		int pointer = 0;
		int optArray[] = new int [size];
		int fault = 0;
		int minusPointer = 0;
		
		for (int i = 0; i < array.length; i++){		
			pointer = (i - minusPointer) % size;
	
			if (optArray[pointer] == 0 && !duplicateNumber(optArray, array[i])){
				optArray[pointer] = array[i];
			}
			else if (duplicateNumber(optArray, array[i] )){		//do nothing
				minusPointer++;
			}
			else {
				optArray[findHighestIndex( array, optArray, size , i)] = array[i];
				fault++;
			}	
		}
		return fault;
	}
	
	static int findHighestIndex(int array[], int optArray[], int size, int i){
		int highestIndex = 0, temp = 0;
		int findHighestIndex[] = new int [size];
		
		int k = i+1;
		for (int j = 0; j < optArray.length; j++){
			k = i+1;
			for (; k < array.length; k++){
				if (optArray[j] == array[k]){		//find matching index and store in array
					findHighestIndex[j] = k;
					break;
				}
			}
		}
		for (int m = 0; m < findHighestIndex.length; m++){
			if (findHighestIndex[m] == 0){
				highestIndex = findHighestIndex[m];
				return m;
			}
			else{									//find index to be removed
				temp = highestIndex;
				if(temp > findHighestIndex[m]){
					highestIndex = temp;
				}
				else{
					highestIndex = findHighestIndex[m];
				}
				
			}
		}
		
		for (int n = 0; n < optArray.length; n++){					//get index to delete from optArray
			if (array[highestIndex] == optArray[n]){
				
				return n;
			}
		}
		int num = 0;
		
		return num;   //error
	}
	
	static int clk(int array[], int size){
		int pointer = 0;
		int fault = 0;
		int minusPointer = 0;
		
		int clkArray[] = new int [size];
		int clkArray2[] = new int [size]; 
		
		for (int i = 0; i < clkArray2.length; i++){		
			clkArray2[i] = -1;					
		}
		
		for (int i = 0; i < array.length; i++){			
			pointer = (i - minusPointer) % size;
			if (clkArray2[pointer] == -1 && !duplicateNumber(clkArray, array[i])){
				clkArray[pointer] = array[i];
				clkArray2[pointer] = 1;
			}
			else if (duplicateNumber(clkArray, array[i] )){
				 int num = addOne(clkArray, array[i]);
				 clkArray2[num] = 1;
				 minusPointer++;
			}
			else if (clkArray2[pointer] == 1 && !duplicateNumber(clkArray, array[i])){
				int findZero = pointer;	
				
				while (clkArray2[findZero] == 1){
					clkArray2[findZero] = 0;
					findZero++;
					findZero = findZero % size;
				}
				
				clkArray[findZero] = array[i];
				clkArray2[findZero] = 1;
				fault++;
			}
			else if (clkArray2[pointer] == 0 && !duplicateNumber(clkArray, array[i])){
				clkArray[pointer] = array[i];
				clkArray2[pointer] = 1;
				fault++;
			}
		}
		return fault;
	}
	
	static int fifo(int array[], int size){
		int pointer = 0;
		int fault = 0;
		int minusPointer = 0;
		
		int clkArray[] = new int [size];
		
		
		for (int i = 0; i < array.length; i++){			
			pointer = (i - minusPointer) % size;
			if (clkArray[pointer] == 0 && !duplicateNumber(clkArray, array[i])){
				clkArray[pointer] = array[i];
			}
			else if (duplicateNumber(clkArray, array[i] )){		//do nothing
				minusPointer++;
			}
			else {
				clkArray[pointer] = array[i];
				fault++;
 			}
		}

		return fault;
	}
	
	
	static int lru(int array[], int size){
		fault = 0;
		int counter = 0;
		int lruArray[] = new int [size];
		
		 for (int i = 0; i < array.length; i++){
			 if (isEmpty(size, counter) == true && duplicateNumber( lruArray, array[i]) == false){
				lruArray[counter] = array[i];
				counter++;
			 }
			 else if (isEmpty(size, counter) == true && duplicateNumber( lruArray, array[i]) == true){
			 }				//nothing happens
			 else if (isEmpty(size, counter) == false && duplicateNumber( lruArray, array[i]) == false){
				 pageFault(lruArray, array[i-3], array[i]);
			 }
		 }

		 return fault;
	}
	
		static boolean isEmpty(int size, int counter){
			if (counter < size)
				return true;
			else
				return false;
		}
		
		static boolean duplicateNumber(int array[], int number){
			for (int i = 0; i < array.length; i++){
				if ( array[i] == number){
					return true;
				}
			 }
			return false;
		}
		
		static void pageFault (int x[], int y, int newNumber ){
			for(int i = 0; i < x.length; i++){
				if(x[i] == y)
					x[i] = newNumber;
			}
			
			fault++;
		}
		
		static int addOne(int array[], int number){
			for (int i = 0; i < array.length; i++){
				if ( array[i] == number){
					return i;
				}
			 }
			return -1;
		}

}
