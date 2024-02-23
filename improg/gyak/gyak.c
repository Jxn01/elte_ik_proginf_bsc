#include <stdio.h>
#include <stdlib.h>
void myPrintf();
void myScanf();
void playground();

int main(){
    myPrintf();
    myScanf();
    playground();

    return 0;
}

void myPrintf(){
    printf("|----------------------------------------------------PRINTF----------------------------------------------------|\n\n");
    int a = 1;
    float b = 1.1;
    char c = 'a';
    double d = 4.0;
    char text [] = "Asd";

    /*%d = int | %f = float, double | %c = char | %s = string*/
    printf("Int: %d \nFloat: %f \nChar: %c \nDouble: %f \nString: %s\n\n", a, b, c, d, text);

    /*int = 4Byte | float = 4Byte | double = 8Byte | char = 1Byte | string = hossz*1Byte*/
    printf("Int size: %d \nFloat size: %d \nChar size: %d\nDouble size: %d\nString length: %d \nString size: %d\n\n", sizeof(int), sizeof(float), sizeof(char), sizeof(double), sizeof(text)/sizeof(char), sizeof(text));

    /* %[-][width].[precision]conversion character 
    * a "-" karakter balra igazítja az adatot a stringben
    * a [width] meghatározza az adat minimum hosszát (ha az adat kissebb mint a minimum hossz, akkor vezető szóközöket ad hozzá)
    * a "." karakter elválasztja egymástól a [width]-et és a [precision]-t
    * a [precision] szám esetén a tizedesjegy pontosságot adja meg, szöveg esetén a maximum hosszt */

    int a2 = 10000000;
    float b2 = 1.0000001;
    char text2 [] = "abcdefghijklmnop";

    printf("Pelda vezeto szokozokre es a balra igazitasra:\n");
    printf("Vezeto szokozokkel: \t\t\t Int: %12d szoveg vege\n", a2);
    printf("Vezeto szokozokkel es balra igazitva:    Int: %-12d szoveg vege\n\n", a2);

    printf("Pelda a tizedesjegy pontossagra:\n");
    printf("Eredeti float: %f\n", b2);
    printf("2 tizedesjegyre pontositott float: %.2f\n\n", b2);

    printf("Pelda a precisionre string eseten:\n");
    printf("Eredeti string: %s\n", text2);
    printf("Pontositott string (3 karakter): %.3s\n\n", text2);

    printf("|--------------------------------------------------------------------------------------------------------------------|\n\n");
}

void myScanf(){
    printf("|----------------------------------------------------SCANF----------------------------------------------------|\n\n");
    int a;
    float b;
    char c;
    double d;
    char s [100]; 

    /*%[*][max_field]conversion character
    * a "*" karakter skippeli az input azon részét
    * a [max_field] a maximum karakter számot adja meg (a maximum hosszon felül splitel! | a split 2. részét is kezelni kell)*/

    /*printf("Kerem adjon meg egy egesz szamot, egy valos szamot, egy karaktert, meg egy valos szamot, es egy szoveget, mindet szokozzel elvalasztva!\n");
    //scanf("%2d %*d %f %c %f %s", &a, &b, &c, &d, s);
    printf("\nInt: %d\nFloat: %f\nChar: %c\nDouble: %f\nString: %s\n\n", a, b, c, d, s);*/

    printf("|--------------------------------------------------------------------------------------------------------------------|\n\n");
}

void playground(){

    int tomb [] = {1,2,3,4,5,6,7,8};
    int* ptr = tomb;
    int i;
    for(i=0; i<8;i++){
        printf("Current elem: %d\n", *ptr+i);
    }

    int hossz;
    printf("%d\n\n", sizeof(ptr));

    scanf("%d", &hossz);

    int *ptr2 = malloc(hossz);
    if(ptr2!=NULL){
        for(i=0;i<hossz;i++){
            *(ptr2+i)=i+1;
            printf("Current elem: %d\n", *(ptr2+i));
        }
    }
    free(ptr2);
}