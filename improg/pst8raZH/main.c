#include "functions.h"
int main(int argc, char *argv[]){
    dynamicArray* da = inputFromFile(argc, argv);

    int i;
    for(i = 0; i< da->index; i++){
        printf("%d %s\n",da->lines[i]->times, da->lines[i]->line);
    }

    freeArray(da);
    return 0;
}