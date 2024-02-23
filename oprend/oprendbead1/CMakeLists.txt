cmake_minimum_required(VERSION 3.25)
project(oprendbead1 C)

set(CMAKE_C_STANDARD 23)

add_executable(oprendbead1 main.c menu.h applicant_type.h applicant_reader.h demand_type.h linked_list.h error_handling_malloc.h)
