#include <stdio.h>
#include <stdlib.h>
#include <ctype.h> //isdigit
#include <string.h>
#include <unistd.h> //fork
#include <wait.h> //waitpid
#include <sys/msg.h> //message
#include <sys/shm.h> //shared memory
#include <sys/sem.h>
#include <fcntl.h>

void validate(int argc, char **);
int is_positive_number(const char *);
void children_handler(int signum);

void children_handler(int signum) {
    if (signum == SIGUSR1) {
        printf("OKTATÓ: SZIGNÁL ÉRKEZETT, PHD1 INDULÁSRA KÉSZ!\n");
    } else if (signum == SIGUSR2) {
        printf("OKTATÓ: SZIGNÁL ÉRKEZETT, PHD2 INDULÁSRA KÉSZ!\n");
    } else {
        printf("OKTATÓ: Hisdigit(str[i]))IBA, NEM VÁRT SZIGNÁL!\n");
    }
}

int semaphor(const char* pathname, int semaphor_value){
    int semid;
    key_t key;

    key=ftok(pathname, 1);
    if((semid=semget(key,1,IPC_CREAT|S_IRUSR|S_IWUSR ))<0){
        perror("semget");
    }

    if(semctl(semid,0,SETVAL,semaphor_value)<0){
        perror("semctl");
    }

    return semid;
}


void semaphor_operation(int semid, int op){
    struct sembuf operation;

    operation.sem_num = 0;
    operation.sem_op  = op;
    operation.sem_flg = 0;

    if(semop(semid,&operation,1)<0)
        perror("semop");
}

void semaphor_delete(int semid){
    semctl(semid,0,IPC_RMID);
}

#define MSGSZ 650

typedef struct msgbuf {
    long mtype;
    char mtext[MSGSZ];
} message_buf;

int main(int argc, char *argv[]) {
    validate(argc, argv);

    struct sigaction sigact;
    sigact.sa_handler = children_handler;
    sigemptyset(&sigact.sa_mask);
    sigact.sa_flags = 0;

    sigaction(SIGUSR1, &sigact, NULL);
    sigaction(SIGUSR2, &sigact, NULL);

    int pipefd1[2];
    if (pipe(pipefd1) < 0) {
        printf("Hiba a csőnyitás során!\n");
        exit(1);
    }

    int pipefd2[2];
    if (pipe(pipefd2) < 0) {
        printf("Hiba a csőnyitás során!\n");
        exit(1);
    }

    int msqid;
    key_t key;
    key = getpid();
    if ((msqid = msgget(key, IPC_CREAT | 0666)) < 0) {
        perror("msgget");
        exit(1);
    }

    key_t mem_key;
    int sh_mem_id;
    char *sh_mem_ptr;
    mem_key = ftok(argv[0], 1);
    if ((sh_mem_id = shmget(mem_key, 1024, IPC_CREAT | 0666)) < 0) {
        perror("shmget");
        exit(1);
    }

    if ((sh_mem_ptr = shmat(sh_mem_id, NULL, 0)) == (char *) -1) {
        perror("shmat");
        exit(1);
    }

    char* init = "Napi lista: \n\0";
    strcpy(sh_mem_ptr, init);

    int semid = semaphor(argv[0], 0);

    pid_t child1 = fork();
    if(child1 < 0){
        printf("Hiba a forkolás során!\n");
        exit(1);
    } else if(child1 == 0){ //child process 1

        kill(getppid(), SIGUSR1);

        sleep(1);
        char buffer[64];

        close(pipefd1[1]); //close write end
        read(pipefd1[0], &buffer, sizeof(buffer)); //read from pipe
        close(pipefd1[0]); //close read end

        char* location = strtok(buffer, ";");
        char* time = strtok(NULL, ";");

        printf("PHD1: AZ OKTATÓTÓL KAPOTT, CSÖVÖN KERESZTÜL KÜLDÖTT SZÖVEG: %s %s\n", location, time);

        //tfh. ő érkezik meg hamarabb
        message_buf sbuf;
        size_t buflen;

        sleep(1);
        char* msg = "Nagy a tömeg, egy óra múlva a Trafalgár téren!";
        sbuf.mtype = 1;
        strcpy(sbuf.mtext, msg);
        buflen = strlen(sbuf.mtext) + 1;

        if (msgsnd(msqid, &sbuf, buflen, IPC_NOWAIT) < 0) {
            printf("%d, %ld, %s, %zu\n", msqid, sbuf.mtype, sbuf.mtext, buflen);
            perror("msgsnd");
            exit(1);
        }

        sbuf.mtype = 2;
        strcpy(sbuf.mtext, msg);
        buflen = strlen(sbuf.mtext) + 1;

        if (msgsnd(msqid, &sbuf, buflen, IPC_NOWAIT) < 0) {
            printf("%d, %ld, %s, %zu\n", msqid, sbuf.mtype, sbuf.mtext, buflen);
            perror("msgsnd");
            exit(1);
        }

        char* places = "PHD1: British Museum, Tower, Big Ben\n\0";
        semaphor_operation(semid, 1);
        strcat(sh_mem_ptr, places);
        semaphor_operation(semid, -1);
        shmdt(sh_mem_ptr);

    } else { // parent process
        pid_t child2 = fork();
        if(child2 < 0){
            printf("Hiba a forkolás során!\n");
            exit(1);
        } else if(child2 == 0){ // child process 2

            kill(getppid(), SIGUSR2);

            sleep(1);
            char buffer[64];
            close(pipefd2[1]); //close write end
            read(pipefd2[0], &buffer, sizeof(buffer)); //read from pipe
            close(pipefd2[0]); //close read end

            char* location = strtok(buffer, ";");
            char* time = strtok(NULL, ";");

            printf("PHD2: AZ OKTATÓTÓL KAPOTT, CSÖVÖN KERESZTÜL KÜLDÖTT SZÖVEG: %s %s\n", location, time);

            message_buf rbuf;
            if (msgrcv(msqid, &rbuf, MSGSZ, 2, 0) < 0) {
                perror("msgrcv");
                exit(1);
            }

            printf("PHD2: A PHD1-TŐL KAPOTT ÜZENET: %s\n", rbuf.mtext);

            char* places = "PHD2: Westminster, London Eye, Soho\n\0";
            semaphor_operation(semid, 1);
            strcat(sh_mem_ptr, places);
            semaphor_operation(semid, -1);
            shmdt(sh_mem_ptr);


        } else { //parent process

            char* time = argv[2];
            char* location = argv[1];

            sigset_t sigset;
            sigfillset(&sigset);
            sigdelset(&sigset, SIGUSR1);
            sigdelset(&sigset, SIGUSR2);
            sigsuspend(&sigset);

            char buffer[64] = "";
            strcpy(buffer, location);
            strcat(buffer, ";");
            strcat(buffer, time);

            close(pipefd1[0]); //close read
            write(pipefd1[1], &buffer, sizeof(buffer)); //write to pipe
            close(pipefd1[1]); //close write

            close(pipefd2[0]); //close read
            write(pipefd2[1], &buffer, sizeof(buffer)); //write to pipe
            close(pipefd2[1]); //close write

            message_buf rbuf;
            if (msgrcv(msqid, &rbuf, MSGSZ, 1, 0) < 0) {
                perror("msgrcv");
                exit(1);
            }

            printf("OKTATÓ: A PHD1-TŐL KAPOTT ÜZENET: %s\n", rbuf.mtext);

            char* places = "OKTATÓ: Picadilly Circus, Buckingham Palace, Hyde Park\n\0";
            semaphor_operation(semid, 1);
            strcat(sh_mem_ptr, places);
            semaphor_operation(semid, -1);

            waitpid(child1, NULL, 0);
            waitpid(child2, NULL, 0);
            printf("OKTATÓ: PHD-K TERMINÁLTAK\n");

            printf("%s", sh_mem_ptr);
            shmdt(sh_mem_ptr);
            wait(NULL);
            semaphor_delete(semid);
            shmctl(sh_mem_id,IPC_RMID,NULL);
        }
    }
    return 0;
}

int is_positive_number(const char *str) {
    for (int i = 0; str[i] != '\0'; i++) {
        if (!isdigit(str[i])) { return 0; }
    }
    return 1;
}

void validate(int argc, char *argv[]){
    if (argc != 3) {
        fprintf(stderr,
                "Kérem argumentumként adja meg, hogy hol és mikor legyen a találkozó!\n");
        exit(1);
    }
    if (!is_positive_number(argv[2])) {
        fprintf(stderr, "Az idő argumentum nem egy természetes szám!\n");
        exit(1);
    }
    int n = (int) strtol(argv[2], NULL, 10);
    if (n < 0 || n > 23) {
        fprintf(stderr, "Az időnek 0 és 23 között kell lennie!\n");
        exit(1);
    }
    if(strlen(argv[1]) > 64){
        fprintf(stderr, "A hely neve maximum 64 karakter lehet!\n");
        exit(1);
    }
    if(strlen(argv[1]) < 1){
        fprintf(stderr, "A hely neve minimum 1 karakter kell legyen!\n");
        exit(1);
    }
}
