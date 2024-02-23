#include <stdio.h>
#include <time.h>
#include <stdlib.h>

int randNumber();
int main() {
    printf("%d", randNumber());
    return 0;
}

int randNumber(){
    srand(time(NULL));
    int random = rand()*100;
    return random;
}
