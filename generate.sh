#!/bin/ksh
WO=$1
echo "Generate SQL $WO"
java -classpath  axispck-0.1.jar  com.openet.axis.MainParser $WO
