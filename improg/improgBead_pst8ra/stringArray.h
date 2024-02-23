#ifndef STRINGARRAY_H
#define STRINGARRAY_H

#include <string.h>
#include <stdio.h>
#include <stdlib.h>

typedef struct{
    int index;
    int size;
    char** strings;
} stringArray;

void arrayPush(char* string, stringArray* array);
void arrayInit(stringArray* array);
void arrayFree(stringArray* array);

void arrayPush(char* string, stringArray* array){
    if(array->size==array->index){
        array->size*=2;
        array->strings = (char**)realloc(array->strings, array->size*sizeof(char*));
        if(array->strings == NULL){
            fprintf(stderr, "%s", "Memory allocation failed!");
            exit(1);
        }
    }
    
    array->strings[array->index] = (char*)malloc(sizeof(char)*strlen(string)+1);
    if(array->strings[array->index] == NULL){
        fprintf(stderr, "%s", "Memory allocation failed!");
        exit(1);
    }

    strcpy(array->strings[array->index], string);
    array->index++;
}

void arrayInit(stringArray* array){
    array->strings = (char**)malloc(sizeof(char*));
    if(array->strings == NULL){
        fprintf(stderr, "%s", "Memory allocation failed!");
        exit(1);
    }

    array->index=0;
    array->size=1;
}

void arrayFree(stringArray* array){
    int i;
    for(i=0; i < array->index; i++){
        free(array->strings[i]);
    }
    free(array->strings);
}

#endif