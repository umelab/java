#!/bin/bash

echo -- start gradlew script --
export LANG=ja_jp.UTF-8
export PATH=$PATH:/usr/local/gradle-8.0.2/bin:/usr/bin:/bin
echo $PATH >> /home/umeda/log_flow.txt
RET=`java -version`
cd /home/umeda/biwako/java/selenium/4.biwakoflow
RET=`gradle run`
echo $RET >> /home/umeda/log_flow.txt
D=`date +'%Y/%m/%d %T'`
echo --- start gradlew script ${D} >> /home/umeda/log_flow.txt
