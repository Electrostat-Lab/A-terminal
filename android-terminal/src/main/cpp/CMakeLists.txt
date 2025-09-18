cmake_minimum_required(VERSION 3.22.1)
project("termnative" LANGUAGES C CXX)

add_library(termnative SHARED
        hello-termnative.c)

target_compile_options(termnative
        PRIVATE
        -Wall
        -Wextra
        -Werror)

target_link_libraries(termnative
    android
    log
)
