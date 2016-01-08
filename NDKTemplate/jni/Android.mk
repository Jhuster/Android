LOCAL_PATH := $(call my-dir)
3RD_INC_DIR = $(LOCAL_PATH)/3rd/inc
3RD_LIB_DIR = $(LOCAL_PATH)/3rd/libs

# Prebuild the 3rd libraries 

include $(CLEAR_VARS)
LOCAL_MODULE := math
LOCAL_SRC_FILES := $(3RD_LIB_DIR)/libmath.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := json
LOCAL_SRC_FILES := $(3RD_LIB_DIR)/libjson.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := ffmpeg
LOCAL_SRC_FILES := $(3RD_LIB_DIR)/libffmpeg.so
include $(PREBUILT_SHARED_LIBRARY)

# Build native sdk 

include $(CLEAR_VARS)    

LOCAL_MODULE := native_sdk

LOCAL_SRC_FILES := \
	$(subst $(LOCAL_PATH)/,,$(wildcard $(LOCAL_PATH)/src/algorithm/*.c))  \
    $(subst $(LOCAL_PATH)/,,$(wildcard $(LOCAL_PATH)/src/core/*.c))  \
    $(subst $(LOCAL_PATH)/,,$(wildcard $(LOCAL_PATH)/src/network/*.c)) \
    $(subst $(LOCAL_PATH)/,,$(wildcard $(LOCAL_PATH)/src/utils/*.c)) \
    $(subst $(LOCAL_PATH)/,,$(wildcard $(LOCAL_PATH)/src/*.c))  
    
LOCAL_C_INCLUDES := $(3RD_INC_DIR)
LOCAL_C_INCLUDES := $(LOCAL_PATH)/src 
LOCAL_C_INCLUDES := $(LOCAL_PATH)/src/algorithm
LOCAL_C_INCLUDES += $(LOCAL_PATH)/src/core
LOCAL_C_INCLUDES += $(LOCAL_PATH)/src/network
LOCAL_C_INCLUDES += $(LOCAL_PATH)/src/utils

LOCAL_CFLAGS := -DANDROID	    								
LOCAL_LDLIBS := -llog 

LOCAL_STATIC_LIBRARIES := math json
LOCAL_SHARED_LIBRARIES := ffmpeg

include $(BUILD_SHARED_LIBRARY)


# Build tests

include $(CLEAR_VARS)    
LOCAL_MODULE := test.out
LOCAL_SRC_FILES := $(subst $(LOCAL_PATH)/,,$(wildcard $(LOCAL_PATH)/tests/*.c)) 
LOCAL_CFLAGS := -DANDROID 
LOCAL_C_INCLUDES := $(LOCAL_PATH)/src 
LOCAL_LDLIBS := -llog -fPIE -pie
LOCAL_SHARED_LIBRARIES:= native_sdk
include $(BUILD_EXECUTABLE)
