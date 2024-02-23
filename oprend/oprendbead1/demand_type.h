#ifndef DEMAND_TYPE_H
#define DEMAND_TYPE_H

#include "linked_list.h"
#include "error_handling_malloc.h"

typedef struct {
    int week[7];
    List *days[7];
} demand_t;

demand_t *init_demand();

void free_demand(demand_t *);

demand_t *init_demand() {
    demand_t *d = (demand_t *) eh_malloc(sizeof(demand_t));
    memset(d->week, 0, sizeof(d->week));
    for (int i = 0; i < 7; i++) {
        d->days[i] = l_create();
    }
    return d;
}

void free_demand(demand_t *d) {
    for (int i = 0; i < 7; i++) {
        l_free(d->days[i]);
    }
    free(d);
}

#endif //DEMAND_TYPE_H