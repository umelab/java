#!/bin/bash

echo -- start gradlew script --
export LANG=ja_jp.UTF-8
export PATH=$PATH:/usr/local/gradle-8.0.2/bin:/usr/bin:/bin
echo $PATH >> /home/umeda/log.txt
cd /home/umeda/biwako/java/selenium/3.biwako
RET=`gradle run`
echo $RET >> /home/umeda/log.txt
