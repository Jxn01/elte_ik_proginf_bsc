#include "functions.h"

void initArray(dynamicArray *da)
{
    da->index = 0;
    da->size = 4;
    da->lines = (lineFreq **)malloc(sizeof(lineFreq *) * 4);
}

void addToArray(dynamicArray *da, char *line)
{
    if (da->index == da->size)
    {
        da->size *= 2;
        da->lines = (lineFreq **)realloc(da->lines, da->size * sizeof(lineFreq *));
        if (da->lines == NULL)
        {
            fprintf(stderr, "%s", "Memoria lefoglalasa sikertelen!");
            exit(1);
        }
    }

    bool bennevan = false;

    int i;
    for(i=0; i < da->index; i++){
        if(strcmp(da->lines[i]->line, line) != -1 && strcmp(da->lines[i]->line, line) != 1){
            da->lines[i]->times++;
            bennevan = true;
        }
    }

    if (!bennevan)
    {
        da->lines[da->index] = (lineFreq *)malloc(sizeof(lineFreq));
        if (da->lines[da->index] == NULL)
        {
            fprintf(stderr, "%s", "Memoria lefoglalasa sikertelen!");
            exit(1);
        }

        da->lines[da->index]->line = (char *)malloc(sizeof(char) * 1024);
        if (da->lines[da->index]->line == NULL)
        {
            fprintf(stderr, "%s", "Memoria lefoglalasa sikertelen!");
            exit(1);
        }
        da->lines[da->index]->times=1;
        strcpy(da->lines[da->index]->line, line);
        da->index++;
    }
}

void freeArray(dynamicArray *da)
{
    int i;
    for(i = 0; i< da->index; i++){
        int j;
        free(da->lines[i]->line);
        free(da->lines[i]);
    }
    free(da->lines);
}
