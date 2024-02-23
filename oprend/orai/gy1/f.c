#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>  //fork
#include <sys/wait.h> //waitpid
#include <errno.h> 

void kisfeladat();

int main()
{
   pid_t parent = getpid();

   pid_t  child=fork(); //forks make a copy of variables
   printf("Hello %i  \n", getpid());

   printf("Kisfeladat \n");
   kisfeladat();

   return 0;
}

void kisfeladat()
{
   pid_t child=fork();
   if(child<0){
      perror("The fork calling was not succesful");
   }
   if(child>0){
      printf("Parent process: \n");
      waitpid(child, NULL, 0);
   }else{

   }
}