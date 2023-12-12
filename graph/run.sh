#!/bin/bash

echo -- start gradlew script --
export LANG=ja_jp.UTF-8
export PATH=$PATH:/usr/local/gradle-8.0.2/bin:/usr/bin:/bin
echo $PATH >> /home/umeda/graph_log.txt
RET=`java -version`
echo "jdk:$RET" >> /home/umeda/graph_log.txt
cd /home/umeda/biwako/java/graph 
RET=`gradle run`
echo $RET >> /home/umeda/graph_log.txt
D=`date +'%Y/%m/%d %T'`
echo --- start gradlew script ${D} >> /home/umeda/graph_log.txt
