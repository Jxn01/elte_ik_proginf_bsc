#ifndef INPUT_H
#define INPUT_H
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include "stringArray.h"

void input(stringArray *inputArray, int argc, char* argv[], int *fajlok, int *fajlokHossza);

void input(stringArray *inputArray, int argc, char* argv[], int *fajlok, int *fajlokHossza){
    int argcConst = argc;
    int i;
    bool argcMinus = false;
    bool fpNull = false;

    FILE *fp;

    if(argc==1){
        fp = stdin;
    }
    
    for(i=1;i<=argc;i++){
        char tmp[1024];
        int j=0;

        if(argc!=1){
            do{
                fpNull = false;
                fp = fopen(argv[i], "r");

                if(fp==NULL){
                    fprintf(stderr, "%s", "File opening unsuccessful!\n");
                    fpNull = true;
                    i++;
                }

                if(!argcMinus){
                    argc--;
                    argcMinus = true;
                }
            }while(i<=argc && fpNull);
        }

        if(i>argc){
            break;
        }
        
        while(fgets(tmp, 1024, fp)){
            j++;
            if(strlen(tmp)!=2){
                tmp[strcspn(tmp, "\r\n")] = 0;
            }else{
                tmp[strcspn(tmp, "\r")] = 0;
            }
            arrayPush(tmp, inputArray);
            strcpy(tmp, "");
        }
        *fajlokHossza=j;
        fajlokHossza++;
        (*fajlok)++;
        if(argcConst!=1){
            fclose(fp);
        }
        
    }

    if(argcConst==1){
        fclose(fp);
    }
    
    (*fajlok)--;
}
#endif