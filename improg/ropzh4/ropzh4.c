#include <stdio.h>
int FindInt(int* array, int num);
int main(){

    int array[5] = {2,3,7,0,10};
    int num = 5;

    printf("%d", FindInt(array, num));

    return 0;
}

int FindInt(int* array, int num)
{
  int index = 0;
  while(*array != num) {
    array += 1;
    index++;
  }
  return index;
}