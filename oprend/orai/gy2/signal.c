#include <stdlib.h>
#include <stdio.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>

int ii=0;

void handler(int signumber){
  ii++;
  printf("Signal with number %i has arrived\n",signumber);
}

int main(){

    
  signal(SIGTERM,handler); //handler = SIG_IGN - ignore the signal (not SIGKILL,SIGSTOP), 
                             //handler = SIG_DFL - back to default behavior 
  
  pid_t child=fork();
  if (child>0)
  {
    while(ii==0){pause();} //waits till a signal arrives 
    printf("Signal arrived\n",SIGTERM);
    int status;
    wait(&status); //gyerek folyamat befejezésére vár
    printf("Parent process ended\n");
  }
  else 
  {
    kill(getppid(),SIGTERM); 
    //1. parameter the pid number of process, we send the signal
    // 		if -1, then eacho of the processes of the same uid get the signal
    // 		we kill our bash as well! The connection will close
    //2. parameter the name or number of signal
    printf("Child process ended\n");  
  }
  return 0;
}
