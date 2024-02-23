#include "functions.h"

dynamicArray *inputFromFile(int argc, char *argv[])
{
    FILE *fp;
    static dynamicArray da;
    initArray(&da);
    if (argc == 1)
    {
        fp = stdin;
        da = input(&da, fp);
    }
    else
    {
        int i;
        for (i = 1; i < argc; i++)
        {
            bool failedOpen = false;
            fp = fopen(argv[1], "r");
            if (fp == NULL)
            {
                fprintf(stderr, "Fajl megnyitasa sikertelen!");
                /*exit(1);*/
                failedOpen = true;
            }

            if (!failedOpen)
            {
                da = input(&da, fp);
                fclose(fp);
            }
        }
    }

    return &da;
}

dynamicArray input(dynamicArray *da, FILE *fp)
{
    char buffer[1024];

    while (fgets(buffer, 1024, fp))
    {
        if (strlen(buffer) != 2)
        {
            buffer[strcspn(buffer, "\r\n")] = 0;
        }
        else
        {
            buffer[strcspn(buffer, "\r")] = 0;
        }
        addToArray(da, buffer);
        strcpy(buffer, "");
    }

    return *da;
}