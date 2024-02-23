#ifndef REVERSE_H
#define REVERSE_H
#include <string.h>
#include <stdio.h>
#include "stringArray.h"

void reverse(stringArray *inputArray, int fajlok, int *fajlokHossza);

void reverse(stringArray *inputArray, int fajlok, int *fajlokHossza){
    int i;
    for(i = 0; i <= fajlok; i++){
        int elozo = 0;
        int j;

        if(i!=0){
            fajlokHossza[i]+=fajlokHossza[i-1];
            elozo = fajlokHossza[i-1];
        }

        for(j=fajlokHossza[i]-1; j >= elozo; j--){
            int k;
            int l = 0;
            int kulonbseg;
            char* newStr = (char*)malloc(strlen(inputArray->strings[j])+1);
            kulonbseg = j-elozo;
            if(newStr==NULL){
                fprintf(stderr, "%s", "Memory allocation failed!");
                exit(1);
            }
            
            for(k=strlen(inputArray->strings[j])-1; k >= 0; k--){
                newStr[l] = inputArray->strings[j][k];
                l++;
            }
            newStr[l] = '\0';

            printf("%d " "%s\n", kulonbseg+1, newStr);
            
            strcpy(newStr, "");
            free(newStr);
        }
        
    }
}

#endif