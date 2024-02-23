#include <stdio.h>

int main() {
    int a [5];
    for(int i=0; i<5;i++){
        a[i]=0;
    }

    int b [10] = {1,2,3,4,5,6,7,8,9,10};
    int max = 0;
    int sum = 0;
    int min=100;
    int min2=90;
    for(int i = 0; i < 10; i++){
        sum+=b[i];
        if(b[i]>max){
            max=b[i];
        }
        if(b[i]<min){
            min2=min;
            min=b[i];
        }
    }



    printf("%d" " %d" " %d",max, sum, min2);


    return 0;
}
