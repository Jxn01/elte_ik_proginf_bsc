#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include "string_array.h"

void push_array(char* string, string_array* array){
    if(array->size == array->index){
        array->size *= 2;
        array->strings = (char**) realloc(array->strings, array->size * sizeof(char*));
        if(array->strings == NULL){
            fprintf(stderr, "%s", "Memory allocation failed!");
            exit(1);
        }
    }

    array->strings[array->index] = (char*) malloc(sizeof(char) * strlen(string) + 1);
    if(array->strings[array->index] == NULL){
        fprintf(stderr, "%s", "Memory allocation failed!");
        exit(1);
    }

    strcpy(array->strings[array->index], string);
    array->index++;
}

void init_array(string_array* array){
    array->strings = (char**) malloc(sizeof(char*));
    if(array->strings == NULL){
        fprintf(stderr, "%s", "Memory allocation failed!");
        exit(1);
    }

    array->index = 0;
    array->size = 1;
}

void free_array(string_array* array){
    for(int i = 0; i < array->index; i++){
        free(array->strings[i]);
    }
    free(array->strings);
}
