#!/bin/bash

declare -a arrayCorrectResponse

let count=0

while read LINE; 
do
	arrayCorrectResponse[$count]=$LINE
	((count++))	
done <TrueValues

let count=0

command rm -f  "Results"

while [ $count -lt 2 ]
do
	echo "#" >> Results
	((count++))
done

echo "#	The following document was created by a script written by Travis King to easily" >> Results
echo "#test the second compiler project of Compilers taught by Professor Egen." >> Results
echo "#" >> Results
echo "#	This script is made for my personal use and is free to use for anyone else" >> Results
echo "#However no assurance is given that this test is accurate. If one excecutes this script" >> Results
echo "#and passess all tests as defined by the user of this script this does not assure that" >> Results
echo "#a passing grade on the project itself." >> Results

let count=0

while [ $count -lt 2 ]
do 
	echo "#" >> Results
	((count++))
done

let count=0

while [ $count -lt ${#arrayCorrectResponse[@]} ]
do	
	actualResponse=$(./p2 ${arrayCorrectResponse[$count]})
	
	((count++))
	
	correctResponse=${arrayCorrectResponse[$count]}
	
	bold=$(tput bold)
	normal=$(tput sgr0)

	if [ "$actualResponse" = "$correctResponse" ]
	then
		echo "#" >> Results
		echo "#The test file" ${arrayCorrectResponse[$count - 1]} "successfully passed" >> Results
		echo "#" >> Results
		echo "#" >> Results
	else
		echo "#" >> Results
		echo "#The test file" ${arrayCorrectResponse[$count - 1]} "did not pass the test" >> Results
		echo "#The Response given by the program was" $actualResponse >> Results
		echo "#" >> Results
		
		let errorCount=0

		cat ${arrayCorrectResponse[$count - 1]} >> Results
		#while read LINE	
		#do		
		#	echo "# " $LINE >> Results	
		#done <${arrayCorrectResponse[$count - 1]}
		
		echo "#" >> Results
		echo "#" >> Results

	fi
	
	((count++))
done
