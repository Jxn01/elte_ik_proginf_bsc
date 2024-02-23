#include <stdio.h>
#include <string.h>

int main() {
    FILE *fp;
    char* valami;

    fp = fopen("valami.txt", "w+");
    scanf("%s", valami);
    fprintf(fp, ""+strlen(valami), "%d");

   
    fclose(fp);
    return 0;
}