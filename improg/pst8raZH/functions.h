#ifndef FUNCTIONS_H
#define FUNCTIONS_H
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

typedef struct {
    char* line;
    int times;
} lineFreq;

typedef struct {
    lineFreq** lines;
    int size;
    int index;
} dynamicArray;

dynamicArray* inputFromFile(int argc, char* argv[]);
void addToArray(dynamicArray* da, char* line);
void initArray(dynamicArray* da);
void freeArray(dynamicArray* da);
dynamicArray input(dynamicArray *da, FILE *fp);
#endif