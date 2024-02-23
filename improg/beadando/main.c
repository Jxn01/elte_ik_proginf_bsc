#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include "string_array.h"
#include "input.h"
#include "reverse.h"

int files_lengths[100];
int files = 0;

int main(int argc, char** argv){
    /*
	char** == char[][] == string[]

	int* szám;

	fgv(szám);

	fprintf(stdout, "%d\n", szám);
	*/
    string_array input_array;

    init_array(&input_array);

    input(&input_array, argc, argv, &files, files_lengths);

    reverse(&input_array, files, files_lengths);

    free_array(&input_array);
    return 0;
}

