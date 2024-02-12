#!/bin/bash

echo -- start gradlew script --
export LANG=ja_jp.UTF-8
export PATH=$PATH:/usr/local/gradle-8.0.2/bin:/usr/bin:/bin
cd /home/umeda/debug/java/selenium/3.biwako
RET=`gradle run --args="debug"`
echo -- end gradlew script --