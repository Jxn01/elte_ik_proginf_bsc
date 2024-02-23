#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "functions.h"

/*
azt hinné az ember milyen rövid kis kód ez
aztán meglátja a functions.c tartalmát
*/

int main(int argc, char *argv[]){
	
	if(argc >= 2){
		for(int i=1; i<argc; i++){
			readFile(argv[i]);
		}
	}else{
		readConsole();
	}
	
	return 0;
}