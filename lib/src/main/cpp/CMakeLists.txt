cmake_minimum_required(VERSION 3.6.0)
set(TARGET midisynthesizer)

add_library(${TARGET} SHARED midisynthesizer.cpp)

find_library(android-lib android)
find_library(log-lib log)
target_link_libraries(${TARGET} PRIVATE ${CMAKE_CURRENT_LIST_DIR}/../jniLibs/${ANDROID_ABI}/libfluidsynth.so ${android-lib} ${log-lib})
