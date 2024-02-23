#include "func.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>


void Reverse(char *string)
{
    int vege = 0;
    if (string[0] != '\n')
    {
        while (string[vege] != '\0' && string[vege] != '\n')
        {
            vege++;
        }
        char temp[vege + 1];
        int i;
        for (i = 1; i <= vege; i++)
        {
            temp[i - 1] = string[vege - i];
        }
        temp[vege + 1] = '\0';
        strcpy(string, temp);
    }else{
        string[0] = '\0';
    }
}

void Write(int bentiSz, int count, char **sorok)
{
    int i;
    int end = count - bentiSz;
    for (i = (count - 1); i >= end; i--)
    {
        printf("%d %s\n",bentiSz, sorok[i]);
        bentiSz--;
    }
}

void FreeUp(int count, char **sorok)
{
    for (int i = 0; i < count; i++)
    {
        free(sorok[i]);
    }
    free(sorok);
}

void newLine(char **sorok, char *buff, int sorszam)
{
    

    sorok[sorszam] = (char *)malloc(1024);

    if (sorok[sorszam] == NULL)
    {
        printf("Memory allocation failed!");
        exit(-1);
    }
    Reverse(buff);
    strcpy(sorok[sorszam], buff);
}

void ArgumentRead(int argc, char **argv)
{
    int size = 8;
    char **sorok = (char **)malloc(sizeof(char *) * size);
    if (sorok == NULL)
    {
        printf("Memory allocation failed!");
        exit(-1);
    }

     
    int sorszam = 0;
    int i;
    for (i = 1; i < argc; i++)
    {
        FILE *fp = NULL;
        fp = fopen(argv[i], "r");
        int bentiSorSz = 0;
        if (fp == NULL)
        {
            printf("File opening unsuccessful!");
        }
        else
        {
            char buff[1024];
            while (fgets(buff, 1024, fp) != NULL)
            {
                bentiSorSz++;
                while (size <= sorszam)
                {
                    size = size * 2;
                    sorok = (char **)realloc(sorok, sizeof(char *) * size);
                    if (sorok == NULL)
                    {
                        printf("Memory allocation failed!");
                        exit(-1);
                    }
                    
                }
                newLine(sorok, buff, sorszam);
                sorszam++;
            }
        }
        fclose(fp);
        Write(bentiSorSz, sorszam, sorok);
    }
    FreeUp(sorszam, sorok);
}

void ConsoleRead()
{
    int size = 8;

    char **sorok = (char **)malloc(sizeof(char *) * size);

    if (sorok == NULL)
    {
        printf("Memory allocation failed!");
        exit(-1);
    }
    int sorszam = 0;
    int bentiSorSz = 0;
    char buff[1024];
    while (fgets(buff, 1024, stdin) != NULL)
    {
        bentiSorSz++;
        while (size <= sorszam)
        {
            size = size * 2;
            sorok = (char **) realloc(sorok, sizeof(char *) * size);
            if (sorok == NULL)
            {
                printf("Memory allocation failed!");
                exit(-1);
            }
        }
        newLine(sorok, buff, sorszam);
        sorszam++;
    }
    Write(bentiSorSz, sorszam, sorok);
    FreeUp(sorszam, sorok);
}