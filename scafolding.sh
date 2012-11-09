#!/bin/ksh


echo "Hello, "$USER".  This script will generate sql for WO."

echo  "A [Generate new Package Allowance] "
echo  "B [Generate new Package Accumulator] "

echo -n "Select type of WO [A or B or 3  ENTER]: "
read typeWO

case "$typeWO" in


A)
 echo "Generate new Package Allowance :";;
B)
 echo "Generate new Package Accumulator:";;
*) 
echo "Invalid option"
exit;;
esac

echo -n "Enter your WO Code [eg. WO367] [ENTER]: "
read WO


echo "Generate SQL $WO"
java -classpath  axispck-0.1.jar  com.openet.axis.MainParser $WO


