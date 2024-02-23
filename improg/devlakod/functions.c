#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "functions.h"

void freeArray(char** arr, int size){ // jó is lenne polimorfizmus c-ben
	for(int j=0; j<size; j++){
		free(arr[j]);
	}
	free(arr);
}

void reverse(char* txt){
	int ln = strlen(txt)-1;
	for(int i=0; i<ln/2; i++){
		char tmp=txt[i];
		txt[i]=txt[ln-i-1];
		txt[ln-i-1]=tmp;
	}
}

// világvége
void readCore(FILE* from){
	int size=8;
	char** arr = (char**)malloc(size * sizeof(char*));
	
	char buf[1024];
	int i=0;
	while(fgets(buf, 1024, from)){
		if(size <= i){
			char** new;
			size*=2;
			new = (char**)realloc(arr, size * sizeof(char*));
			if(new == NULL){
				printf("Memory allocation failed!");
				exit(0);
			}
			arr = new;
		}
		arr[i]=(char*)malloc((strlen(buf)+1)*sizeof(char));
		
		reverse(buf);
		strcpy(arr[i], buf);
		
		i++;
	}
	
	for(int j=i-1; j>=0; j--){
		printf("%d %s",(j+1),arr[j]);
	}
	
	freeArray(arr, i);
}

void readConsole(){
	readCore(stdin);
}

void readFile(char* filename){
	FILE* fp=NULL;
	fp = fopen(filename, "r");
	if(fp==NULL){
		fprintf(stderr, "File opening unsuccessful!\n");
		return;
	}
	
	readCore(fp);
	
	fclose(fp);
}