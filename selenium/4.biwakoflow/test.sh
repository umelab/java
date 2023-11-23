#!/bin/bash

export PATH=$PATH:/usr/local/gradle-8.0.2/bin
RET=`java -version`
echo "jdk:$RET"
RET=`gradle -version`
echo "gradle:$RET"
