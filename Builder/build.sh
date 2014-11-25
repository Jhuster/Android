#!/bin/bash

#NOTE: 用法，在本工程根目录下执行 ./build.sh xxx
#其中，xxx为参数，可以是clean debug release，也可以不带参数，不带参数默认为debug

#如果参数是 clean，  则会删除所有编译生成的文件
#如果参数是 debug ， 则会生成 debug 版本的apk;
#如果参数是 release，则生成 release版本的apk，位于 bin 目录下

#NOTE: 如果更改了工程配置，请先删除目录下的build.xml

#获取本目录的名称，作为build.xml的项目名称
PROJECT_NAME=${PWD##*/}

#路径配置，请根据编译环境修改
ANDROID_SDK_PATH=/opt/android/sdk
ANDROID_NDK_PATH=/opt/android/ndk
ANT_PATH=/opt/apache-ant-1.9.0/bin

#决定是否生成release版
BUILD_RELEASE=0

if [ $1 = "clean" ]
then
	rm -rf gen
	rm -rf obj
	rm -rf libs/armeabi
	echo "clean success"
	exit
fi

if [ $1 = "debug" ]
then
	BUILD_RELEASE=0
fi

if [ $1 = "release" ]
then
	BUILD_RELEASE=1
fi

echo "begin to build..."

#判断 build.xml 是否存在，若不存在，则创建一个
#请根据你的目标平台，修改update project命令的最后一个参数，该值为 android list 命令输出结果列表的id值
if [ ! -e build.xml ]
then 
	echo "No build.xml exist, generate build.xml"
	$SDK_PATH/tools/android update project -n $PROJECT_NAME -p . -s -t 1
fi

#判断jni目录是否存在，如果存在，则首先编译jni目录
if [ -d jni ]
then
	echo "build jni, generate libs"
	$ANDROID_NDK_PATH/ndk-build
fi

#使用ant来编译
if [ $BUILD_RELEASE ]
then
	$ANT_PATH/ant release	
else
	$ANT_PATH/ant debug
fi

echo "build success"

	
