JAVAHOME = $(shell /usr/libexec/java_home -v 1.8)
ARCH = $(shell uname -m)
PLATFORM = darwin

C = gcc
CXX = g++
CXXFLAGS := -fPIC -shared -std=c++11
ifeq ($(ARCH), "arm64")
	CXXFLAGS := $(CXXFLAGS) -mcpu=apple-m1
endif

INCLUDES := -I$(JAVAHOME)/include
INCLUDES := $(INCLUDES) -I$(JAVAHOME)/include/$(PLATFORM)
INCLUDES := $(INCLUDES) -Isrc/main/cpp/

compile: src/main/cpp/jniexample.cpp src/main/cpp/*.h
	@echo $(JAVAHOME)
	@echo  $(ARCH) $(PLATFORM)
	./gradlew clean build
	$(JAVAHOME)/bin/javah -cp build/classes/java/main -o src/main/cpp/jniexample.h dev.appkr.jniExample.JNIExample
	$(CXX) $(CXXFLAGS) $(INCLUDES) -o src/main/resources/mynativelib.so src/main/cpp/jniexample.cpp

clean:
	rm src/main/resources/mynativelib.so
