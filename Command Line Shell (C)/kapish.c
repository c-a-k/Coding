#define _GNU_SOURCE
#include <sys/types.h>
#include <sys/wait.h>
#include <stdio.h> //printf, fgets, stdin
#include <stdlib.h> //malloc, free, wait, exit, setenv, and unsetenv
#include <unistd.h> //getcwd, chdir, fork, execvp
#include <string.h> //string functions (strtok, strcmp, etc.)
#include <errno.h> //error return code from exec
#include <signal.h> //ctrl-c - kill process command - SIGINT

char *argv[20]; //array of strings storing arguments
int argc; //number of arguments
pid_t pid, wpid; //process ID and wait PID

//function prototypes
void INTHandler(int signal);
void tokenize(char *input, char *seperator); //breaks down user command into separate arguments
void process_command(void); //process various commands (cd, setenv, unsetenv, etc.)
char *stripNewLine(char* stringy); //removes newline character from strings

int main() {
    signal(SIGINT, INTHandler); //for ctrl-c
    char rcInput[512]; //for .bashrc commands
    char usrInput[512]; //for user to input commands
    char rcfile[512]; //to store .bashrc file name + directory
    strcat(strcpy(rcfile, getenv("HOME")), "/.kapishrc");
    FILE *fp = fopen(rcfile, "r");
    if(!fp){
        perror("Error opening file");
        return(-1);
    }
    printf("? ");
    while(fgets(rcInput, 512, fp) != NULL) { //read user input
        printf("%s", rcInput);
        tokenize(rcInput, " "); //tokenize user input
        process_command(); //process command stored in global variable argv
        printf("? ");
    }
    while(fgets(usrInput, 512, stdin) != NULL) { //read user input
        tokenize(usrInput, " "); //tokenize user input
        process_command(); //process command stored in global variable argv
        printf("? ");
    }
    printf("\n");
    exit(0); //end loop and exit shell if user presses control-d (EOF)
	return 0;
}

void INTHandler(int signal) {
    if(pid<=0){
        printf("\n");
        exit(0);
    }
}

void tokenize(char *input, char *seperator) {
    int index=0; 
    char *item = strtok(input, seperator); //returns pointer last string seperated by space    
    if(strcmp(item, "exit\n") == 0) {
        exit(0); //quit program if first argument is exit
    }
    while(item != NULL && index<20) {
        argv[index] = item; //store token in argv
        item = strtok(NULL, seperator); //allows subsequent calls on input to seperate tokens
        index++;
    }
    argv[index] = NULL;
    argc = index; //set total number of arguments
}

void process_command() {  
    char *cwd = (char *) malloc(512);
    char *dir = (char *) malloc(512);
    int status;
    getcwd(cwd, 512); //store current working directory in cwd variable
    if (strcmp("setenv", argv[0]) == 0) { //setting environment variables
        if(argv[2]){ //if user specifies value
            printf("Changing value of %s from %s to %s\n", stripNewLine(argv[1]), getenv(stripNewLine(argv[1])), stripNewLine(argv[2]));
            setenv(stripNewLine(argv[1]), stripNewLine(argv[2]), 1);
        } else {
            printf("Creating environment variable %s\n", stripNewLine(argv[1]));
            setenv(stripNewLine(argv[1]), "", 1);
        }
    } else if (strcmp("unsetenv", argv[0]) == 0) { //unset environment variables
        printf("Removing environment variable %s\n", stripNewLine(argv[1]));
        unsetenv(stripNewLine(argv[1]));
    } else if (strcmp("pwd\n", argv[0]) == 0) { //print working directory command
        printf("%s\n",cwd);    
    } else if(strcmp("cd\n", argv[0]) == 0) { //home directory command
        chdir(getenv("HOME")); //use home environment variable to change
    } else if(strcmp("cd", argv[0]) == 0) { //change directory command
        if (strcmp(stripNewLine(argv[1]),"..") == 0) {
            strncpy(dir, cwd, strlen(cwd)-strlen(strrchr(cwd, '/'))); //copy entire path except current directory
            chdir(dir);
        } else if(chdir(stripNewLine(argv[1])) != 0) { //chdir returns a value of 0 if successful
            printf("Invalid directory\n"); 
        }
    } else { //user wants to execute a program (based off )
        pid = fork(); //get child process ID
        argv[argc-1] = stripNewLine(argv[argc-1]);
        if (pid == 0) { //child process code
            if (execvp(argv[0], argv) == -1) {
                perror("Execution error");
            }
            exit(EXIT_FAILURE);
        } else if (pid < 0) { //forking error
            perror("What the fork?");
        } else { //run parent process
            do {
            wpid = waitpid(pid, &status, WUNTRACED);
            } while (!WIFEXITED(status) && !WIFSIGNALED(status));
        } 
    }
    free(cwd);
    free(dir);
}

char *stripNewLine(char* stringy) {
    char* newLine;
    newLine = strrchr(stringy, '\n'); //returns pointer to existence of \n character
    if (newLine) {
        *newLine = '\0';
    }
    return stringy;
}
