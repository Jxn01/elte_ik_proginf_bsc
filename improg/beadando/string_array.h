#ifndef STRINGARRAY_H
#define STRINGARRAY_H

typedef struct{
    int index;
    int size;
    char** strings;
} string_array;

void push_array(char* string, string_array* array);
void init_array(string_array* array);
void free_array(string_array* array);

#endif