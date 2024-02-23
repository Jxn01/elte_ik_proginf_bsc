#ifndef APPLICANT_TYPE_H
#define APPLICANT_TYPE_H

#include <stdio.h>
#include "error_handling_malloc.h"

typedef struct {
    char name[64];
    int working_days[7]; //bool array
} applicant_t;

applicant_t *create_applicant(char *, const int *);

int compare_applicant(applicant_t *, applicant_t *);

int filter_applicant_by_wd(applicant_t *, int *);

void free_applicant(applicant_t *);

void print_applicant_w_days(applicant_t *);

void print_applicant(applicant_t *);

applicant_t *create_applicant(char *name, const int *working_days) {
    applicant_t *a = (applicant_t *) eh_malloc(sizeof(applicant_t));
    strcpy(a->name, name);
    for (int i = 0; i < 7; i++) {
        a->working_days[i] = working_days[i];
    }
    return a;
}

int compare_applicant(applicant_t *a1, applicant_t *a2) {
    return strcmp(a1->name, a2->name);
}

int filter_applicant_by_wd(applicant_t *a, int *wd) {
    return a->working_days[*wd];
}

void free_applicant(applicant_t *a) {
    free(a);
}

void print_applicant_w_days(applicant_t *a) {
    printf("%s", a->name);
    for (int i = 0; i < 7; i++) {
        if (a->working_days[i]) {
            switch (i) {
                case 0:
                    printf(" Hétfő");
                    break;
                case 1:
                    printf(" Kedd");
                    break;
                case 2:
                    printf(" Szerda");
                    break;
                case 3:
                    printf(" Csütörtök");
                    break;
                case 4:
                    printf(" Péntek");
                    break;
                case 5:
                    printf(" Szombat");
                    break;
                case 6:
                    printf(" Vasárnap");
                    break;
                default:
                    break;
            }
        }
    }
    printf("\n");
}

void print_applicant(applicant_t *a) {
    printf("%s\n", a->name);
}

#endif //APPLICANT_TYPE_H
