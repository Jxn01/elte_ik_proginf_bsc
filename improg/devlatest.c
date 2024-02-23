#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(){

    char* input[20];
    char text[20] = "abcde";
    int* ptr=malloc(2);
    if(ptr!=NULL){
        input[0]=ptr;
    }
    printf("%d", sizeof(ptr));
    strcpy(input[0],text);
    printf("%s\n%d\n%c", input[0], strlen(input[0]), *input[0]);

    return 0;
}
