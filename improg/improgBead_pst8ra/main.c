#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include "stringArray.h"
#include "input.h"
#include "reverse.h"

int main(int argc, char *argv[]){
    int fajlokHossza[100];
    int fajlok=0;

    stringArray inputArray;
    arrayInit(&inputArray);

    input(&inputArray, argc, argv, &fajlok, fajlokHossza);

    reverse(&inputArray, fajlok, fajlokHossza);

    arrayFree(&inputArray);
    return 0;
}


