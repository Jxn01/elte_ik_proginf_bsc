#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include "input.h"

void input(string_array* input_array, int argc, char* argv[], int* files, int* files_lengths){
    int argc_const = argc;
    bool argc_minus = false;
    bool fp_null = false;

    FILE* file_pointer;

    if(argc == 1){
        file_pointer = stdin;
    } else {
		file_pointer = fopen(argv[1], "r");
		if(file_pointer == NULL){
        	fprintf(stderr, "%s", "File opening unsuccessful!\n");
			exit(1);
        }
		char buffer[1024];
		int i = 0;
		név;kor;magasság;hőmérséklet
		while(fgets(buffer, 1024, file_pointer)){
            strcpy(array[i], buffer);
            strcpy(buffer, "");
			i++;
        }

		fclose(file_pointer);
	}

    for(int i = 1; i <= argc; i++){
        char buffer[1024];
        int j = 0;

        if(argc != 1){
            do {
                fp_null = false;
                file_pointer = fopen(argv[i], "r");

                if(file_pointer == NULL){
                    fprintf(stderr, "%s", "File opening unsuccessful!\n");
                    fp_null = true;
                    i++;
                }

                if(!argc_minus){
                    argc--;
                    argc_minus = true;
                }
            } while(i <= argc && fp_null);
        }

        if(i > argc){
            break;
        }

        while(fgets(buffer, 1024, file_pointer)){
            j++;
            if(strlen(buffer) != 2){
                buffer[strcspn(buffer, "\r\n")] = 0;
            } else {
                buffer[strcspn(buffer, "\r")] = 0;
            }
            push_array(buffer, input_array);
            strcpy(buffer, "");
        }

        *files_lengths = j;
        files_lengths++;
        (*files)++;
        if(argc_const != 1){
            fclose(file_pointer);
        }
    }

    if(argc_const == 1){
        fclose(file_pointer);
    }

    (*files)--;
}