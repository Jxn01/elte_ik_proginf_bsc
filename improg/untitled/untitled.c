#include <stdio.h>

int main() {
    int szamok [25] = {0,1,2,3,4,5,6,7,89,1,2,3,4,0,1,2,0,1,2,0,2,1,78,69,420};
    int nullaDb = 0;
    int i;
    for(i=0;i<25;i++){
        if(szamok[i]==0){
            nullaDb++;
        }
    }

    printf("%d", nullaDb);
    return 0;
}
