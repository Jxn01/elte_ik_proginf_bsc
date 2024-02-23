#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include "reverse.h"

void reverse(string_array* input_array, int files, int* files_lengths){
    for(int i = 0; i <= files; i++){
        int prev = 0;

        if(i != 0){
            files_lengths[i] += files_lengths[i - 1];
            prev = files_lengths[i - 1];
        }

        for(int j = files_lengths[i] - 1; j >= prev; j--){
            int l = 0;
            int difference;
            char* new_str = (char*) malloc(strlen(input_array->strings[j]) + 1);
            difference = j - prev;
            if(new_str == NULL){
                fprintf(stderr, "%s", "Memory allocation failed!");
                exit(1);
            }
            
            for(int k = strlen(input_array->strings[j]) - 1; k >= 0; k--){
                new_str[l] = input_array->strings[j][k];
                l++;
            }
            new_str[l] = '\0';

            printf("%d " "%s\n", difference + 1, new_str);
            
            strcpy(new_str, "");
            free(new_str);
        }
    }
}