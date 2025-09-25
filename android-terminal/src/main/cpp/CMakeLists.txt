cmake_minimum_required(VERSION 3.22.1)
project("termnative" LANGUAGES C CXX)

add_library(termnative SHARED
        "lib/bus/I2CChannel_test.cpp")

target_include_directories(termnative PUBLIC "./header/")

target_compile_options(termnative
        PRIVATE
        -Wall
        -Wextra
        -Werror)

target_link_libraries(termnative
    android
    log
)
