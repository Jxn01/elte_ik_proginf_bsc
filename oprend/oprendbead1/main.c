#include <stdio.h>
#include <ctype.h>
#include "demand_type.h"
#include "menu.h"

int is_positive_number(const char *);

void init(int, char *[], demand_t *);

int main(int argc, char *argv[]) {
    demand_t *d = init_demand();

    init(argc, argv, d);
    main_menu(d);

    return 0;
}

void init(int argc, char *argv[], demand_t *d) {
    if (argc != 8) {
        fprintf(stderr,
                "Kérem argumentumként adja meg, hogy mely napokon hány emberre van szükség! pl.: 10 5 3 7 0 0 0\n");
        exit(1);
    }
    for (int i = 1; i < 8; i++) {
        if (!is_positive_number(argv[i])) {
            fprintf(stderr, "Az egyik argumentum nem egy természetes szám!\n");
            exit(1);
        }
        if (((int) strtol(argv[i], NULL, 10)) > 10) {
            fprintf(stderr, "Egy napon maximum 10 emberre van szükség!\n");
            exit(1);
        }
        d->week[i - 1] = (int) strtol(argv[i], NULL, 10);
    }
}

int is_positive_number(const char *str) {
    for (int i = 0; str[i] != '\0'; i++) {
        if (!isdigit(str[i])) { return 0; }
    }
    return 1;
}
