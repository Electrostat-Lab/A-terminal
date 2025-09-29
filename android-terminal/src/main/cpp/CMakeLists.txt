cmake_minimum_required(VERSION 3.22.1)
project("termnative" LANGUAGES C CXX)

message(STATUS "Building for ABI: ${ANDROID_ABI}")

add_library(termnative SHARED
        "lib/bus/I2CChannel_test.cpp"
        "lib/bus/test.c")

target_include_directories(termnative PUBLIC
    "${CMAKE_CURRENT_SOURCE_DIR}/header/"
    "${CMAKE_CURRENT_SOURCE_DIR}/dependencies/include/"
)

target_compile_options(termnative
        PRIVATE
        -Wall
        -Wextra
        -Werror)

set(${ABI_LEVEL} "21")

if(${ANDROID_ABI} STREQUAL "armeabi-v7a")
    set(${VARIANT} "armv7a-linux-androideabi${ABI_LEVEL}")
elseif(${ANDROID_ABI} STREQUAL "arm64-v8a")
    set(${VARIANT} "aarch64-linux-android${ABI_LEVEL}")
elseif(${ANDROID_ABI} STREQUAL "x86")
    set(${VARIANT} "i686-linux-android${ABI_LEVEL}")
elseif(${ANDROID_ABI} STREQUAL "x86_64")
    set(${VARIANT} "x86_64-linux-android${ABI_LEVEL}")
endif()

# Declare the static lib as IMPORTED
set(${electrostatic} "${CMAKE_CURRENT_SOURCE_DIR}/dependencies/libs/android/${VARIANT}/libelectrostatic-a.a"
)

target_link_libraries(termnative
    android
    log
    ${electrostatic}
)
