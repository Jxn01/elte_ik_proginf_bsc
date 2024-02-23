#ifndef APPLICANT_READER_H
#define APPLICANT_READER_H

#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include "applicant_type.h"
#include "linked_list.h"

#define MAX_NAME_LENGTH 100

applicant_t *read_applicant(FILE *file);

List *read_file_to_list(char *);

List *read_file_to_list(char *file_name) {
    List *result = l_create();
    FILE *file = fopen(file_name, "r");
    if (file == NULL) { return NULL; }
    while (true) {
        applicant_t *ap = read_applicant(file);
        if (ap == NULL) {
            break;
        }
        l_push_front(result, ap);
    }
    fclose(file);
    return result;
}

applicant_t *read_applicant(FILE *file) {
    int *working_days = (int *) malloc(sizeof(int) * 7);
    applicant_t *result = create_applicant("", working_days);
    char line[400];
    char word[64];
    if (fgets(line, 400, file) != NULL) {
        const char *p = line;
        while (sscanf(p, "%63s", word) == 1) {
            if (strcmp(word, "Hetfo") == 0) { result->working_days[0] = true; }
            else if (strcmp(word, "Kedd") == 0) { result->working_days[1] = true; }
            else if (strcmp(word, "Szerda") == 0) { result->working_days[2] = true; }
            else if (strcmp(word, "Csutortok") == 0) { result->working_days[3] = true; }
            else if (strcmp(word, "Pentek") == 0) { result->working_days[4] = true; }
            else if (strcmp(word, "Szombat") == 0) { result->working_days[5] = true; }
            else if (strcmp(word, "Vasarnap") == 0) { result->working_days[6] = true; }
            else {
                strncat(result->name, word, MAX_NAME_LENGTH - strlen(result->name) - 1);
                strncat(result->name, " ", MAX_NAME_LENGTH - strlen(result->name) - 1);
            }
            p += strlen(word) + 1;
        }
    } else {
        return NULL;
    }
    result->name[strlen(result->name) - 1] = '\0';
    return result;
}

#endif //APPLICANT_READER_H
