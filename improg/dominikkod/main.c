#include "func.h"

int main(int argc, char *argv[])
{
    if (argc > 1){
        ArgumentRead(argc, argv);
    }else{
        ConsoleRead();
    }
    return 0;
}