#include <stdio.h>
#include <stdlib.h>

int main(){

    int** matrix = (int**)malloc(sizeof(int*)*10);

    int i;
    for(i=0; i < 10;i++){
        *(matrix+i) = (int*)malloc(sizeof(int)*10); 
    }

    for(i=0;i<10;i++){
        int j;
        for(j=0; j< 10; j++){
            matrix[i][j] = 0;
            printf("%d", matrix[i][j]);
        }
    }

    for(i=0; i< 10; i++){
        free(*(matrix+i));
    }

    free(matrix);

    return 0;
}