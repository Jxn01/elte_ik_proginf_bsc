#ifndef MENU_H
#define MENU_H

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h> //fork
#include <wait.h> //waitpid
#include <sys/msg.h> //msgget
#include "applicant_reader.h"
#include "demand_type.h"

char *get_day(int);

void print_header();

void main_menu(demand_t *);

void data_load_menu();

void accept_applicants_menu();

void delete_applicants_menu();

void start_buses_menu();

void list_daily_menu(int);

void list_all_menu();

char *input(char *);

void exit_app();

char STATUS1[128] = "NO_FILE";
char STATUS2[128] = "NO_FILE";
int CURRENT_DAY = 0;
char OPTIONAL[128] = "";
char INPUT[128] = "";
demand_t *DEMAND;
List *APPLICANT_LIST;
List *WORKER_LIST;
demand_t *DEMAND_ACCEPTED_LIST;

void main_menu(demand_t *d) {
    DEMAND = d;
    DEMAND_ACCEPTED_LIST = init_demand();

    while (1) {
        print_header();
        printf("1.: ADATOK BEOLVASÁSA\n");
        if (strcmp(STATUS1, "OK") == 0 && strcmp(STATUS2, "OK") == 0) {
            printf("2.: JELENTKEZŐK FELVÉTELE\n");
            printf("3.: JELENTKEZŐK TÖRLÉSE\n");
            printf("4.: BUSZJÁRAT(OK) INDÍTÁSA\n");
            printf("5.: TEGNAPI LISTA KÉSZÍTÉSE\n");
            printf("6.: TELJES LISTA KÉSZÍTÉSE\n");
            printf("7.: KILÉPÉS\n");
        } else {
            printf("2.: KILÉPÉS\n");
        }
        int menu_option = (int) strtol(input(""), NULL, 10);
        if (menu_option == 0) {
            strcpy(OPTIONAL, "HIBA: A MENÜPONTOT NEM LEHET MEGYNITNI, A MENÜPONT SZÁMÁT PONTOSAN ADJA MEG!\n");
        } else {
            if (strcmp(STATUS1, "OK") == 0 && strcmp(STATUS2, "OK") == 0) {
                switch (menu_option) {
                    case 1:
                        data_load_menu();
                        break;
                    case 2:
                        accept_applicants_menu();
                        break;
                    case 3:
                        delete_applicants_menu();
                        break;
                    case 4:
                        start_buses_menu();
                        if (CURRENT_DAY == 6) {
                            CURRENT_DAY = 0;
                            for (int i = 0; i < 7; i++) {
                                l_clear(DEMAND_ACCEPTED_LIST->days[i]);
                                l_clear(DEMAND->days[i]);
                                DEMAND_ACCEPTED_LIST->week[i] = 0;
                            }
                        } else {
                            CURRENT_DAY++;
                        }
                        break;
                    case 5:
                        list_daily_menu(CURRENT_DAY - 1);
                        break;
                    case 6:
                        list_all_menu();
                        break;
                    case 7:
                        //exit_app();
                        exit(0);
                    default:
                        strcpy(OPTIONAL, "HIBA: ILYEN MENÜPONT NEM LÉTEZIK!\n");
                        break;
                }
            } else {
                switch (menu_option) {
                    case 1:
                        data_load_menu();
                        break;
                    case 2:
                        exit(0);
                    default:
                        strcpy(OPTIONAL, "HIBA: ILYEN MENÜPONT NEM LÉTEZIK!\n");
                        break;
                }
            }
        }
    }
}

void data_load_menu() {
    print_header();
    input("ADJA MEG A JELENTKEZŐK FÁJL ELÉRÉSI ÚTVONALÁT!");
    if (strcmp(STATUS1, "OK") == 0) {
        l_free(APPLICANT_LIST);
    }
    List *P = read_file_to_list(INPUT);
    //List *P = read_file_to_list("jelentkezok.txt");
    l_print(P, (void (*)(void *)) print_applicant_w_days);
    if (P == NULL) {
        strcpy(STATUS1, "CANT_READ");
        return;
    }
    if (l_size(P) == 0) {
        strcpy(STATUS1, "EMPTY_FILE");
        return;
    }
    APPLICANT_LIST = P;
    strcpy(STATUS1, "OK");

    print_header();

    input("ADJA MEG A DOLGOZÓK FÁJL ELÉRÉSI ÚTVONALÁT!");
    if (strcmp(STATUS2, "OK") == 0) {
        l_free(WORKER_LIST);
    }
    List *Q = read_file_to_list(INPUT);
    //List *Q = read_file_to_list("dolgozok.txt");
    l_print(Q, (void (*)(void *)) print_applicant);
    if (Q == NULL) {
        strcpy(STATUS2, "CANT_READ");
        return;
    }
    if (l_size(Q) == 0) {
        strcpy(STATUS2, "EMPTY_FILE");
        return;
    }
    WORKER_LIST = Q;
    strcpy(STATUS2, "OK");
}

int is_in(void *data, List *B) { // a little lambda function
    return l_contains(B, data, (int (*)(void *, void *)) compare_applicant) ? 0 : 1;
}

void accept_applicants_menu() {
    print_header();
    l_quick_sort(APPLICANT_LIST, (int (*)(void *, void *)) compare_applicant);
    List *todays_list = l_filter2(APPLICANT_LIST, (int (*)(void *, void *)) filter_applicant_by_wd, &CURRENT_DAY);
    List *filtered_list = l_filter2(todays_list, (int (*)(void *, void *)) is_in,
                                    DEMAND_ACCEPTED_LIST->days[CURRENT_DAY]);
    printf("ADATOK: <NÉV>\n");
    l_print(filtered_list, (void (*)(void *)) print_applicant);
    input("ADJA MEG ANNAK A SORNAK A SZÁMÁT AMELYIKET FEL SZERETNÉ VENNI (HA NEM SZERETNE SEMMIT AKKOR NYOMJON ENTERT)!");
    if (strcmp(INPUT, "") != 0) {
        int line_number = (int) strtol(INPUT, NULL, 10);
        if (line_number <= 0 || line_number > l_size(filtered_list)) {
            strcpy(OPTIONAL, "HIBA: ILYEN SORSZÁMÚ SOR NEM LÉTEZIK!\n");
        } else {
            applicant_t *a = l_get(filtered_list, line_number - 1);
            if (l_contains(DEMAND_ACCEPTED_LIST->days[CURRENT_DAY], a, (int (*)(void *, void *)) compare_applicant)) {
                strcpy(OPTIONAL, "HIBA: EZ A JELENTKEZŐ MÁR FELVAN VÉVE!\n");
            } else if (DEMAND_ACCEPTED_LIST->week[CURRENT_DAY] >= DEMAND->week[CURRENT_DAY]) {
                strcpy(OPTIONAL, "HIBA: NINCS TÖBB HELY!\n");
            } else {
                l_push_front(DEMAND_ACCEPTED_LIST->days[CURRENT_DAY], a);
                DEMAND_ACCEPTED_LIST->week[CURRENT_DAY]++;
                strcpy(OPTIONAL, "SIKERES FELVÉTEL!\n");
            }
        }
    }
    l_free(todays_list);
    l_free(filtered_list);
}

void delete_applicants_menu() {
    print_header();
    l_quick_sort(DEMAND_ACCEPTED_LIST->days[CURRENT_DAY], (int (*)(void *, void *)) compare_applicant);
    printf("ADATOK: <NÉV>\n");
    l_print(DEMAND_ACCEPTED_LIST->days[CURRENT_DAY], (void (*)(void *)) print_applicant);
    input("ADJA MEG ANNAK A SORNAK A SZÁMÁT AMELYIKET TÖRÖLNI SZERETNÉ (HA NEM SZERETNE SEMMIT AKKOR NYOMJON ENTERT)!");
    if (strcmp(INPUT, "") != 0) {
        int line_number = (int) strtol(INPUT, NULL, 10);
        if (line_number <= 0 || line_number > l_size(DEMAND_ACCEPTED_LIST->days[CURRENT_DAY])) {
            strcpy(OPTIONAL, "HIBA: ILYEN SORSZÁMÚ SOR NEM LÉTEZIK!\n");
        } else {
            l_remove_at(DEMAND_ACCEPTED_LIST->days[CURRENT_DAY], line_number - 1);
            DEMAND_ACCEPTED_LIST->week[CURRENT_DAY]--;
            strcpy(OPTIONAL, "SIKERES TÖRLÉS!\n");
        }
    }
}

void bus_handler(int signum) {
    if (signum == SIGUSR1) {
        printf("SZŐLÉSZET: SZIGNÁL ÉRKEZETT: %d\n", signum);
        printf("SZŐLÉSZET: BUSZ1 INDULÁSRA KÉSZ!\n");
    } else if (signum == SIGUSR2) {
        printf("SZŐLÉSZET: SZIGNÁL ÉRKEZETT: %d\n", signum);
        printf("SZŐLÉSZET: BUSZ2 INDULÁSRA KÉSZ!\n");
    } else {
        printf("SZŐLÉSZET: HIBA, NEM VÁRT SZIGNÁL!\n");
    }
}

#define MSGSZ 650

typedef struct msgbuf {
    long mtype;
    char mtext[MSGSZ];
} message_buf;

void start_buses_menu() {
    print_header();
    if (DEMAND_ACCEPTED_LIST->week[CURRENT_DAY] == 0) {
        strcpy(OPTIONAL, "NEM LETT INDÍTVA BUSZ, NINCS SENKI AKI MENNE!\n");
        return;
    }
    int bus_count = DEMAND_ACCEPTED_LIST->week[CURRENT_DAY] > 5 ? 2 : 1;
    printf("SZŐLÉSZET: BUSZOK KÉSZÍTÉSE...\n");
    printf("SZŐLÉSZET: A BUSZOK SZÁMA: %d\n", bus_count);

    //SIGNAL INIT BEGIN
    struct sigaction sigact;
    sigact.sa_handler = bus_handler;
    sigemptyset(&sigact.sa_mask);
    sigact.sa_flags = 0;

    sigaction(SIGUSR1, &sigact, NULL);
    sigaction(SIGUSR2, &sigact, NULL);
    //SIGNAL INIT END

    //PIPE INIT BEGIN
    int pipe_sz_to_b1[2];
    if (pipe(pipe_sz_to_b1) < 0) {
        printf("PIPE ERROR\n");
        exit(1);
    }

    int pipe_sz_to_b2[2];
    if (pipe(pipe_sz_to_b2) < 0) {
        printf("PIPE ERROR\n");
        exit(1);
    }
    //PIPE INIT END

    //MESSAGE QUEUE INIT BEGIN
    int msqid;
    key_t key;
    message_buf sbuf;
    size_t buflen;

    key = 1234;
    if ((msqid = msgget(key, IPC_CREAT | 0666)) < 0) {
        perror("msgget");
        exit(1);
    }
    //MESSAGE QUEUE INIT END

    pid_t bus1 = fork();
    if (bus1 < 0) {
        printf("FORK ERROR\n");
        exit(1);
    } else if (bus1 == 0) { //CHILD PROCESS 1

        //SIGNAL SEND BEGIN
        printf("BUSZ1: INDULÁSRA KÉSZ!\n");
        kill(getppid(), SIGUSR1);
        //SIGNAL SEND END

        sleep(1);
        //PIPE READ BEGIN
        printf("BUSZ1: CSŐBŐL OLVASÁS...\n");
        char buffer[650];
        close(pipe_sz_to_b1[1]); //close write end
        read(pipe_sz_to_b1[0], &buffer, sizeof(buffer)); //read from pipe
        close(pipe_sz_to_b1[0]); //close read end
        printf("BUSZ1: CSŐBŐL OLVASÁS VÉGE!\n");
        //PIPE READ END

        //DATA PROCESS BEGIN
        printf("BUSZ1: FELDOLGOZÁS...\n");
        List *full_list = l_create();
        char *token = strtok(buffer, "\n");
        int dummy = {0, 0, 0, 0, 0, 0, 0};
        while (token != NULL) {
            applicant_t *a = create_applicant(token, &dummy);
            l_push_back(full_list, a);
            token = strtok(NULL, "\n");
        }
        List *bus1_list = l_take(full_list, 5);
        l_free(full_list);

        printf("BUSZ1: ŐKET KELL BEHOZNI...\n");
        l_print(bus1_list, (void (*)(void *)) print_applicant);

        List *bus1_present = l_intersection(bus1_list, WORKER_LIST, (int (*)(void *, void *)) compare_applicant);
        l_free(bus1_list);
        //DATA PROCESS END

        //MESSAGE QUEUE SEND BEGIN
        strcpy(buffer, "");
        for (int i = 0; i < l_size(bus1_present); i++) {
            char *name = ((applicant_t *) l_get(bus1_present, i))->name;
            strncat(buffer, name, 64);
            strncat(buffer, "\n", 1);
        }
        strncat(buffer, "\0", 1);
        l_free(bus1_present);

        sbuf.mtype = 1;
        strncpy(sbuf.mtext, buffer, MSGSZ);
        buflen = strlen(sbuf.mtext) + 1;

        if (msgsnd(msqid, &sbuf, buflen, IPC_NOWAIT) < 0) {
            printf("%d, %ld, %s, %zu\n", msqid, sbuf.mtype, sbuf.mtext, buflen);
            perror("msgsnd");
            exit(1);
        } else {
            printf("BUSZ1: ÜZENETKÜLDÉS SIKERES!\n");
        }
        //MESSAGE QUEUE SEND END

        //CHILD PROCESS 1 END
        printf("BUSZ1: TERMINÁL!\n");
        kill(getpid(), SIGTERM);
    } else { // PARENT PROCESS
        if (bus_count == 2) {
            pid_t bus2 = fork();
            if (bus2 < 0) {
                printf("FORK ERROR\n");
                exit(1);
            } else if (bus2 == 0) { //CHILD PROCESS 2

                //SIGNAL SEND BEGIN
                printf("BUSZ2: INDULÁSRA KÉSZ!\n");
                kill(getppid(), SIGUSR2);
                //SIGNAL SEND END

                sleep(1);
                //PIPE READ BEGIN
                printf("BUSZ2: CSŐBŐL OLVASÁS...\n");
                char buffer[650];
                close(pipe_sz_to_b2[1]); //close write end
                read(pipe_sz_to_b2[0], &buffer, sizeof(buffer)); //read from pipe
                close(pipe_sz_to_b2[0]); //close read end
                printf("BUSZ2: CSŐBŐL OLVASÁS VÉGE!\n");
                //PIPE READ END

                //DATA PROCESS BEGIN
                printf("BUSZ2: FELDOLGOZÁS...\n");
                List *full_list = l_create();
                char *token = strtok(buffer, "\n");
                int dummy = {0, 0, 0, 0, 0, 0, 0};
                while (token != NULL) {
                    applicant_t *a = create_applicant(token, &dummy);
                    l_push_back(full_list, a);
                    token = strtok(NULL, "\n");
                }
                List *bus2_list = l_drop(full_list, 5);
                l_free(full_list);

                printf("BUSZ2: ŐKET KELL BEHOZNI...\n");
                l_print(bus2_list, (void (*)(void *)) print_applicant);

                List *bus2_present = l_intersection(bus2_list, WORKER_LIST,
                                                    (int (*)(void *, void *)) compare_applicant);
                l_free(bus2_list);
                //DATA PROCESS END

                //MESSAGE QUEUE SEND BEGIN
                strcpy(buffer, "");
                for (int i = 0; i < l_size(bus2_present); i++) {
                    char *name = ((applicant_t *) l_get(bus2_present, i))->name;
                    strncat(buffer, name, 64);
                    strncat(buffer, "\n", 1);
                }
                strncat(buffer, "\0", 1);
                l_free(bus2_present);

                sbuf.mtype = 2;
                strncpy(sbuf.mtext, buffer, MSGSZ);
                buflen = strlen(sbuf.mtext) + 1;

                if (msgsnd(msqid, &sbuf, buflen, IPC_NOWAIT) < 0) {
                    printf("%d, %ld, %s, %zu\n", msqid, sbuf.mtype, sbuf.mtext, buflen);
                    perror("msgsnd");
                    exit(1);
                } else {
                    printf("BUSZ2: ÜZENETKÜLDÉS SIKERES!\n");
                }
                //MESSAGE QUEUE SEND END

                //CHILD PROCESS 2 END
                printf("BUSZ2: TERMINÁL!\n");
                kill(getpid(), SIGTERM);
            } else { //PARRENT PROCESS WITH 2 CHILDREN

                //SIGNAL HANDLING BEGIN
                sigset_t sigset;
                sigfillset(&sigset);
                sigdelset(&sigset, SIGUSR1);
                sigdelset(&sigset, SIGUSR2);
                sigsuspend(&sigset);
                //SIGNAL HANDLING END

                //PIPE WRITING BEGIN
                printf("SZŐLÉSZET: CSŐBE ÍRÁS BUSZ1-NEK...\n");
                char buffer[650];
                for (int i = 0; i < l_size(DEMAND_ACCEPTED_LIST->days[CURRENT_DAY]); i++) {
                    strncat(buffer, ((applicant_t *) l_get(DEMAND_ACCEPTED_LIST->days[CURRENT_DAY], i))->name, 64);
                    strncat(buffer, "\n", 1);
                }
                close(pipe_sz_to_b1[0]); //close read
                write(pipe_sz_to_b1[1], &buffer, sizeof(buffer)); //write to pipe
                close(pipe_sz_to_b1[1]); //close write

                printf("SZŐLÉSZET: CSŐBE ÍRÁS BUSZ2-NEK...\n");
                strcpy(buffer, "");
                for (int i = 0; i < l_size(DEMAND_ACCEPTED_LIST->days[CURRENT_DAY]); i++) {
                    strncat(buffer, ((applicant_t *) l_get(DEMAND_ACCEPTED_LIST->days[CURRENT_DAY], i))->name, 64);
                    strncat(buffer, "\n", 1);
                }
                close(pipe_sz_to_b2[0]); //close read
                write(pipe_sz_to_b2[1], &buffer, sizeof(buffer)); //write to pipe
                close(pipe_sz_to_b2[1]); //close write
                printf("SZŐLÉSZET: CSŐBE ÍRÁS KÉSZ!\n");
                //PIPE WRITING END

                sleep(2);
                //MESSAGE QUEUE RECEIVE BEGIN
                message_buf rbuf1;
                message_buf rbuf2;
                if (msgrcv(msqid, &rbuf1, MSGSZ, 1, 0) < 0) {
                    perror("msgrcv");
                    exit(1);
                } else {
                    printf("SZŐLÉSZET: ÜZENETFOGADÁS SIKERES BUSZ1-TŐL!\n");
                }

                if (msgrcv(msqid, &rbuf2, MSGSZ, 2, 0) < 0) {
                    perror("msgrcv");
                    exit(1);
                } else {
                    printf("SZŐLÉSZET: ÜZENETFOGADÁS SIKERES BUSZ2-TŐL!\n");
                }
                //MESSAGE QUEUE RECEIVE END

                //PROCESSING MESSAGE QUEUE DATA BEGIN
                printf("SZŐLÉSZET: BUSZ1 FELDOLGOZÁS...\n");
                List *bus1_workers = l_create();
                char *token = strtok(rbuf1.mtext, "\n");
                int dummy = {0, 0, 0, 0, 0, 0, 0};
                while (token != NULL) {
                    applicant_t *a = create_applicant(token, &dummy);
                    l_push_back(bus1_workers, a);
                    token = strtok(NULL, "\n");
                }
                printf("SZŐLÉSZET: BUSZ1 FELDOLGOZÁS KÉSZ!\n");
                printf("SZŐLÉSZET: BUSZ1 FELDOLGOZOTT ADATAI:\n");
                l_print(bus1_workers, (void (*)(void *)) print_applicant);

                printf("SZŐLÉSZET: BUSZ2 FELDOLGOZÁS...\n");
                List *bus2_workers = l_create();

                char *token2 = strtok(rbuf2.mtext, "\n");
                while (token2 != NULL) {
                    applicant_t *a = create_applicant(token2, &dummy);
                    l_push_back(bus2_workers, a);
                    token2 = strtok(NULL, "\n");
                }
                printf("SZŐLÉSZET: BUSZ2 FELDOLGOZÁS KÉSZ!\n");
                printf("SZŐLÉSZET: BUSZ2 FELDOLGOZOTT ADATAI:\n");
                l_print(bus2_workers, (void (*)(void *)) print_applicant);
                l_clear(DEMAND->days[CURRENT_DAY]);
                l_add_all(DEMAND->days[CURRENT_DAY], bus1_workers);
                l_add_all(DEMAND->days[CURRENT_DAY], bus2_workers);
                l_free(bus1_workers);
                l_free(bus2_workers);
                //PROCESSING MESSAGE QUEUE DATA END

                //WAITING FOR CHILDREN TO TERMINATE
                waitpid(bus1, NULL, 0);
                waitpid(bus2, NULL, 0);
                input("A FŐMENÜBE VALÓ VISSZALÉPÉSHEZ NYOMJON MEG EGY GOMBOT!\n");
                //REMOVING MESSAGE QUEUE
                msgctl(msqid, IPC_RMID, NULL);
            }
        } else { //PARENT PROCESS WITH 1 CHILD

            //SIGNAL HANDLING BEGIN
            sigset_t sigset;
            sigfillset(&sigset);
            sigdelset(&sigset, SIGUSR1);
            sigsuspend(&sigset);
            //SIGNAL HANDLING END

            //PIPE WRITING BEGIN
            printf("SZŐLÉSZET: CSŐBE ÍRÁS BUSZ1-NEK...\n");
            char buffer[650];
            for (int i = 0; i < l_size(DEMAND_ACCEPTED_LIST->days[CURRENT_DAY]); i++) {
                strncat(buffer, ((applicant_t *) l_get(DEMAND_ACCEPTED_LIST->days[CURRENT_DAY], i))->name, 64);
                strncat(buffer, "\n", 1);
            }
            close(pipe_sz_to_b1[0]); //close read
            write(pipe_sz_to_b1[1], &buffer, sizeof(buffer)); //write to pipe
            close(pipe_sz_to_b1[1]); //close write
            printf("SZŐLÉSZET: CSŐBE ÍRÁS KÉSZ!\n");
            //PIPE WRITING END

            sleep(2);
            //MESSAGE QUEUE RECEIVE BEGIN
            message_buf rbuf;
            if (msgrcv(msqid, &rbuf, MSGSZ, 1, 0) < 0) {
                perror("msgrcv");
                exit(1);
            } else {
                printf("SZŐLÉSZET: ÜZENETFOGADÁS SIKERES BUSZ1-TŐL!\n");
            }
            //MESSAGE QUEUE RECEIVE END

            //PROCESSING MESSAGE QUEUE DATA BEGIN
            printf("SZŐLÉSZET: BUSZ1 FELDOLGOZÁS...\n");
            List *bus1_workers = l_create();
            char *token = strtok(rbuf.mtext, "\n");
            int dummy = {0, 0, 0, 0, 0, 0, 0};
            while (token != NULL) {
                applicant_t *a = create_applicant(token, &dummy);
                l_push_back(bus1_workers, a);
                token = strtok(NULL, "\n");
            }
            printf("SZŐLÉSZET: BUSZ1 FELDOLGOZÁS KÉSZ!\n");
            printf("SZŐLÉSZET: BUSZ1 FELDOLGOZOTT ADATAI:\n");
            l_print(bus1_workers, (void (*)(void *)) print_applicant);
            l_clear(DEMAND->days[CURRENT_DAY]);
            l_add_all(DEMAND->days[CURRENT_DAY], bus1_workers);
            l_free(bus1_workers);
            //PROCESSING MESSAGE QUEUE DATA END

            //WAITING FOR CHILD TO TERMINATE
            waitpid(bus1, NULL, 0);
            input("A FŐMENÜBE VALÓ VISSZALÉPÉSHEZ NYOMJON MEG EGY GOMBOT!\n");
            //REMOVING MESSAGE QUEUE
            msgctl(msqid, IPC_RMID, NULL);
        }
    }
}

void list_daily_menu(int day) {
    if (day == -1) { day = 6; }
    print_header();
    l_quick_sort(DEMAND->days[day], (int (*)(void *, void *)) compare_applicant);
    printf("%s", get_day(day));
    printf(" (%d/%d)\n", DEMAND->week[day], l_size(DEMAND->days[day]));
    l_print(DEMAND->days[day], (void (*)(void *)) print_applicant);
    input("A FŐMENÜBE VALÓ VISSZALÉPÉSHEZ NYOMJON MEG EGY GOMBOT!");
}

void list_all_menu() {
    print_header();
    for (int i = 0; i < 7; i++) {
        list_daily_menu(i);
    }
}

void print_header() {
    system("clear");
    printf("SZŐLÉSZET NAPSZÁMOS KEZELŐ RENDSZER\n");
    char st1[64] = "";
    char st2[64] = "";
    char *day = get_day(CURRENT_DAY);
    if (strcmp(STATUS1, "CANT_READ") == 0) { strcpy(st1, "NINCS BEOLVASVA"); }
    else { strcpy(st1, STATUS1); }
    if (strcmp(STATUS2, "CANT_READ") == 0) { strcpy(st2, "NINCS BEOLVASVA"); }
    else { strcpy(st2, STATUS2); }
    if (strcmp(STATUS1, "OK") == 0 && strcmp(STATUS2, "OK") == 0) {
        printf("JELENTKEZŐK ÁLLAPOTA: %s\n", st1);
        printf("MUNKÁSOK ÁLLAPOTA: %s\n", st2);
        printf("MAI NAP: %s\n", day);
        int prev = CURRENT_DAY - 1;
        if (prev < 0) { prev = 6; }
        printf("ELŐZŐ NAPI KVÓTA: %d/%d\n", DEMAND->week[prev], l_size(DEMAND->days[prev]));
        printf("SZÜKSÉGES/ELFOGADOTT JELENTKEZÉSEK SZÁMA: %d/%d\n", DEMAND->week[CURRENT_DAY],
               DEMAND_ACCEPTED_LIST->week[CURRENT_DAY]);
    }
    if (strcmp(OPTIONAL, "") != 0) { printf("%s\n", OPTIONAL); }
    strcpy(OPTIONAL, "");
    printf("------------------------------------------------------\n");
}

char *get_day(int day) {
    switch (day) {
        case 0:
            return "HÉTFŐ";
        case 1:
            return "KEDD";
        case 2:
            return "SZERDA";
        case 3:
            return "CSÜTÖRTÖK";
        case 4:
            return "PÉNTEK";
        case 5:
            return "SZOMBAT";
        case 6:
            return "VASÁRNAP";
        default:
            return "HIBA";
    }
}

char *input(char *msg) {
    strcpy(INPUT, "");
    printf("%s\n", msg);
    printf("> ");
    fgets(INPUT, 128, stdin);
    size_t len = strlen(INPUT);
    if (len > 0 && INPUT[len - 1] == '\n') {
        INPUT[len - 1] = '\0';
    }
    return INPUT;
}

void exit_app() {
    l_free(APPLICANT_LIST);
    l_free(WORKER_LIST);
    free_demand(DEMAND);
    free_demand(DEMAND_ACCEPTED_LIST);
}

#endif //MENU_H
