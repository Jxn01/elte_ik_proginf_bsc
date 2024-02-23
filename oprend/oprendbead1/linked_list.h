#ifndef JACKSONS_LINKED_LIST_LIBRARY_H
#define JACKSONS_LINKED_LIST_LIBRARY_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "error_handling_malloc.h"

typedef struct node {
    void *data;
    struct node *next;
} Node;

typedef struct list {
    int size;
    Node *head;
} List;

List *l_create();

void l_push_back(List *list, void *data);

void l_push_front(List *list, void *data);

void *l_get(List *list, int index);

void *l_pop_back(List *list);

void *l_pop_front(List *list);

void *l_remove_at(List *list, int index);

void l_add_all(List *list1, List *list2);

int l_contains(List *list, void *data, int (*compare)(void *, void *));

int l_index_of(List *list, void *data, int (*compare)(void *, void *));

int l_last_index_of(List *list, void *data, int (*compare)(void *, void *));

int l_is_equal(List *list1, List *list2, int (*compare)(void *, void *));

int l_is_empty(List *list);

int l_size(List *list);

int l_any(List *list, int (*predicate)(void *));

int l_all(List *list, int (*predicate)(void *));

void l_clear(List *list);

void l_quick_sort(List *list, int (*compare)(void *, void *));

void *l_binary_search(List *list, void *data, int (*compare)(void *, void *));

void l_map(List *list, void *(*function)(void *));

List *l_filter(List *list, int (*predicate)(void *));

List *l_filter2(List *list, int (*predicate)(void *, void *), void *arg);

void *l_reduce(List *list, void *initial_value, void *(*function)(void *, void *));

void l_for_each(List *list, void (*function)(void *));

List *l_take(List *list, int n);

List *l_drop(List *list, int n);

List *l_take_while(List *list, int (*predicate)(void *));

List *l_drop_while(List *list, int (*predicate)(void *));

List *l_intersection(List *list1, List *list2, int (*compare)(void *, void *));

void l_reverse(List *list);

void *l_max(List *list, int (*compare)(void *, void *));

void *l_min(List *list, int (*compare)(void *, void *));

void *l_find(List *list, int (*predicate)(void *));

void l_print(List *list, void (*print)(void *));

void l_free(List *list);

List *l_create() {
    List *list = eh_malloc(sizeof(List));
    list->size = 0;
    list->head = NULL;
    return list;
}

void l_push_back(List *list, void *data) {
    Node *new_node = eh_malloc(sizeof(Node));
    new_node->data = data;
    new_node->next = NULL;
    if (list->head == NULL) {
        list->head = new_node;
    } else {
        Node *current = list->head;
        while (current->next != NULL) {
            current = current->next;
        }
        current->next = new_node;
    }
    list->size++;
}

void l_push_front(List *list, void *data) {
    Node *new_node = eh_malloc(sizeof(Node));
    new_node->data = data;
    new_node->next = list->head;
    list->head = new_node;
    list->size++;
}

void *l_get(List *list, int index) {
    if (index < 0 || index >= list->size) {
        printf("Index out of bounds!\n");
        exit(1);
    }
    Node *current = list->head;
    for (int i = 0; i < index; ++i) {
        current = current->next;
    }
    return current->data;
}

void *l_pop_back(List *list) {
    if (list->size == 0) {
        printf("List is empty!\n");
        exit(1);
    }
    Node *current = list->head;
    Node *previous = NULL;
    while (current->next != NULL) {
        previous = current;
        current = current->next;
    }
    void *data = current->data;
    if (previous == NULL) {
        list->head = NULL;
    } else {
        previous->next = NULL;
    }
    free(current);
    list->size--;
    return data;
}

void *l_pop_front(List *list) {
    if (list->size == 0) {
        printf("List is empty!\n");
        exit(1);
    }
    Node *current = list->head;
    void *data = current->data;
    list->head = current->next;
    free(current);
    list->size--;
    return data;
}

void *l_remove_at(List *list, int index) {
    if (index < 0 || index >= list->size) {
        printf("Index out of bounds!\n");
        exit(1);
    }
    Node *current = list->head;
    Node *previous = NULL;
    for (int i = 0; i < index; ++i) {
        previous = current;
        current = current->next;
    }
    void *data = current->data;
    if (previous == NULL) {
        list->head = current->next;
    } else {
        previous->next = current->next;
    }
    free(current);
    list->size--;
    return data;
}

void l_add_all(List *list1, List *list2) {
    Node *current = list2->head;
    while (current != NULL) {
        l_push_back(list1, current->data);
        current = current->next;
    }
}

int l_contains(List *list, void *data, int (*compare)(void *, void *)) {
    Node *current = list->head;
    while (current != NULL) {
        if (compare(current->data, data) == 0) {
            return 1;
        }
        current = current->next;
    }
    return 0;
}

int l_index_of(List *list, void *data, int (*compare)(void *, void *)) {
    Node *current = list->head;
    int index = 0;
    while (current != NULL) {
        if (compare(current->data, data) == 0) {
            return index;
        }
        current = current->next;
        index++;
    }
    return -1;
}

int l_last_index_of(List *list, void *data, int (*compare)(void *, void *)) {
    Node *current = list->head;
    int index = -1;
    int i = 0;
    while (current != NULL) {
        if (compare(current->data, data) == 0) {
            index = i;
        }
        current = current->next;
        i++;
    }
    return index;
}

int l_is_equal(List *list1, List *list2, int (*compare)(void *, void *)) {
    if (list1->size != list2->size) {
        return 0;
    }
    Node *current1 = list1->head;
    Node *current2 = list2->head;
    while (current1 != NULL) {
        if (compare(current1->data, current2->data) != 0) {
            return 0;
        }
        current1 = current1->next;
        current2 = current2->next;
    }
    return 1;
}

int l_is_empty(List *list) {
    return list->size == 0;
}

int l_size(List *list) {
    return list->size;
}

int l_any(List *list, int (*predicate)(void *)) {
    Node *current = list->head;
    while (current != NULL) {
        if (predicate(current->data)) {
            return 1;
        }
        current = current->next;
    }
    return 0;
}

int l_all(List *list, int (*predicate)(void *)) {
    Node *current = list->head;
    while (current != NULL) {
        if (!predicate(current->data)) {
            return 0;
        }
        current = current->next;
    }
    return 1;
}

void l_quick_sort(List *list, int (*compare)(void *, void *)) {
    if (list->size > 1) {
        void *pivot = l_pop_front(list);
        List *less = l_create();
        List *greater = l_create();
        while (!l_is_empty(list)) {
            void *data = l_pop_front(list);
            if (compare(data, pivot) <= 0) {
                l_push_back(less, data);
            } else {
                l_push_back(greater, data);
            }
        }
        l_quick_sort(less, compare);
        l_quick_sort(greater, compare);
        while (!l_is_empty(less)) {
            l_push_back(list, l_pop_front(less));
        }
        l_push_back(list, pivot);
        while (!l_is_empty(greater)) {
            l_push_back(list, l_pop_front(greater));
        }
        l_free(less);
        l_free(greater);
    }
}

void *l_binary_search(List *list, void *data, int (*compare)(void *, void *)) {
    int low = 0;
    int high = list->size - 1;
    while (low <= high) {
        int mid = (low + high) / 2;
        void *mid_data = l_get(list, mid);
        int comparison = compare(mid_data, data);
        if (comparison < 0) {
            low = mid + 1;
        } else if (comparison > 0) {
            high = mid - 1;
        } else {
            return mid_data;
        }
    }
    return NULL;
}

void l_clear(List *list) {
    Node *current = list->head;
    while (current != NULL) {
        Node *next = current->next;
        free(current);
        current = next;
    }
    list->head = NULL;
    list->size = 0;
}

void l_for_each(List *list, void (*function)(void *)) {
    Node *current = list->head;
    while (current != NULL) {
        function(current->data);
        current = current->next;
    }
}

void l_map(List *list, void *(*function)(void *)) {
    Node *current = list->head;
    while (current != NULL) {
        current->data = function(current->data);
        current = current->next;
    }
}

List *l_filter(List *list, int (*predicate)(void *)) {
    List *new_list = l_create();
    Node *current = list->head;
    while (current != NULL) {
        if (predicate(current->data)) {
            l_push_back(new_list, current->data);
        }
        current = current->next;
    }
    return new_list;
}

List *l_filter2(List *list, int (*predicate)(void *, void *), void *arg) {
    List *new_list = l_create();
    Node *current = list->head;
    while (current != NULL) {
        if (predicate(current->data, arg)) {
            l_push_back(new_list, current->data);
        }
        current = current->next;
    }
    return new_list;
}

void *l_reduce(List *list, void *initial_value, void *(*function)(void *, void *)) {
    Node *current = list->head;
    while (current != NULL) {
        initial_value = function(initial_value, current->data);
        current = current->next;
    }
    return initial_value;
}

void l_reverse(List *list) {
    Node *current = list->head;
    Node *previous = NULL;
    while (current != NULL) {
        Node *next = current->next;
        current->next = previous;
        previous = current;
        current = next;
    }
    list->head = previous;
}

List *l_take(List *list, int n) {
    if (n > list->size) {
        n = list->size;
    }
    List *new_list = l_create();
    Node *current = list->head;
    for (int i = 0; i < n; i++) {
        l_push_back(new_list, current->data);
        current = current->next;
    }
    return new_list;
}

List *l_drop(List *list, int n) {
    if (n > list->size) {
        n = list->size;
    }
    List *new_list = l_create();
    Node *current = list->head;
    for (int i = 0; i < n; i++) {
        current = current->next;
    }
    while (current != NULL) {
        l_push_back(new_list, current->data);
        current = current->next;
    }
    return new_list;
}

List *l_take_while(List *list, int (*predicate)(void *)) {
    List *new_list = l_create();
    Node *current = list->head;
    while (current != NULL && predicate(current->data)) {
        l_push_back(new_list, current->data);
        current = current->next;
    }
    return new_list;
}

List *l_drop_while(List *list, int (*predicate)(void *)) {
    List *new_list = l_create();
    Node *current = list->head;
    while (current != NULL && predicate(current->data)) {
        current = current->next;
    }
    while (current != NULL) {
        l_push_back(new_list, current->data);
        current = current->next;
    }
    return new_list;
}

List *l_intersection(List *list1, List *list2, int (*compare)(void *, void *)) {
    List *new_list = l_create();
    Node *current = list1->head;
    while (current != NULL) {
        if (l_contains(list2, current->data, compare)) {
            l_push_back(new_list, current->data);
        }
        current = current->next;
    }
    return new_list;
}

void *l_max(List *list, int (*compare)(void *, void *)) {
    if (list->size == 0) {
        return NULL;
    }
    void *max = list->head->data;
    Node *current = list->head->next;
    while (current != NULL) {
        if (compare(current->data, max) > 0) {
            max = current->data;
        }
        current = current->next;
    }
    return max;
}

void *l_min(List *list, int (*compare)(void *, void *)) {
    if (list->size == 0) {
        return NULL;
    }
    void *min = list->head->data;
    Node *current = list->head->next;
    while (current != NULL) {
        if (compare(current->data, min) < 0) {
            min = current->data;
        }
        current = current->next;
    }
    return min;
}

void *l_find(List *list, int (*predicate)(void *)) {
    Node *current = list->head;
    while (current != NULL) {
        if (predicate(current->data)) {
            return current->data;
        }
        current = current->next;
    }
    return NULL;
}

void l_print(List *list, void (*print)(void *)) {
    Node *current = list->head;
    int i = 1;
    while (current != NULL) {
        printf("%d.: ", i++);
        print(current->data);
        current = current->next;
    }
}

void l_free(List *list) {
    l_clear(list);
    free(list);
}

#endif //JACKSONS_LINKED_LIST_LIBRARY_H