#ifndef JACKSONS_ERROR_HANDLING_MALLOC_LIBRARY_H
#define JACKSONS_ERROR_HANDLING_MALLOC_LIBRARY_H

#include "stdio.h"
#include "stdlib.h"

void *eh_malloc(size_t size);

void *eh_calloc(size_t num, size_t size);

void *eh_realloc(void *ptr, size_t size);

void *eh_malloc(size_t size) {
    void *p = malloc(size);
    if (p == NULL) {
        printf("Error while allocating memory!\n");
        exit(1);
    }
    return p;
}

void *eh_calloc(size_t num, size_t size) {
    void *p = calloc(num, size);
    if (p == NULL) {
        printf("Error while allocating memory!\n");
        exit(1);
    }
    return p;
}

void *eh_realloc(void *ptr, size_t size) {
    void *p = realloc(ptr, size);
    if (p == NULL) {
        printf("Error while allocating memory!\n");
        exit(1);
    }
    return p;
}

#endif //JACKSONS_ERROR_HANDLING_MALLOC_LIBRARY_H
