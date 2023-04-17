## JNI Example

Reference: https://www.developer.com/open-source/getting-started-with-jni/

1. [Write the Java Code](src/main/java/dev/appkr/jniExample/JNIExample.java)

2. Compile the Java Code
```shell
./gradlew clean build
```

3. [Create the C/C++ Header File](src/main/cpp/jniexample.h)
```shell
javah -cp $(pwd)/build/classes/java/main -o $(pwd)/src/main/cpp/jniexample.h dev.appkr.jniExample.JNIExample
```

4. [Write the C/C++ Program](src/main/cpp/jniexample.cpp)

5. Create a Shared Library
```shell
JAVAHOME=$(/usr/libexec/java_home -v 1.8)
PLATFORM=darwin
ARCH=$(uname -m)
CXXFLAGS=""
if [ "${ARCH}" = "arm64" ] ; then
  CXXFLAGS="-mcpu=apple-m1"
fi

g++ -fPIC -shared ${CXXFLAGS} \
  -I${JAVAHOME}/include \
  -I${JAVAHOME}/include/${PLATFORM} \
  -I$(pwd)/src/main/cpp/ \
  -o $(pwd)/src/main/resources/mynativelib.so \
  $(pwd)/src/main/cpp/jniexample.cpp
```

> For 3 and 5, a Makefile is ready!
> 
> ```shell
> make
> ```

6. Run the JNI Empowered Java Code
```shell
./gradlew assemble
java -jar build/libs/jni-example.jar
# Hello JNI                                                                                  
```
