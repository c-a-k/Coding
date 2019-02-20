#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <regex.h>
#include <ctype.h>
#include "linkedlist.h"
void magic_print(wordnode_t *keys, wordnode_t *values);

void printword(wordnode_t *n, void *arg) {
    char *format = (char *)arg;

    printf(format, n->w);
}

int main(int argc, char *argv[]) {
    char buffer[3];
    fgets(buffer, 3, stdin);
    fgets(buffer, 3, stdin);
    fgets(buffer, 3, stdin);
    /*Skips first two lines (not important)*/

    wordnode_t *exclusions = NULL;
    wordnode_t *lines = NULL;
    char* wordup;
    size_t wordlength = 400;
    int j;
    wordup = (char *)malloc(wordlength*sizeof(char));
    while(wordup[0] != ':'){ //reading in exclusion words
        getline(&wordup, &wordlength, stdin);
        char *tempex = NULL;
        tempex = (char *)realloc(tempex, strlen(wordup)*sizeof(char));
        strcpy(tempex, wordup);
        for(j=0; j<strlen(tempex); j++){
            tempex[j] = tolower(tempex[j]);
        }
        exclusions = add_end(exclusions, new_word(tempex, 0));
    }
    while(!feof(stdin)){ //reading in actual lines
        getline(&wordup, &wordlength, stdin);
        if(strcmp(wordup, "\n")!=0){ //skip if line is just "\n"
            lines = add_end(lines, new_word(wordup, 0));
        }
    }
    remove_end(lines); //duplicate lines bug (added new function)
    remove_end(exclusions); //duplicate lines bug (added new function)
    free(wordup);
    wordnode_t *liszt = NULL;
    char *line;
    char *word;
    wordnode_t *keywords = NULL;
    wordnode_t *answers = NULL;
    for(; lines!=NULL; lines=lines->next){
        liszt = NULL;
        line = lines->w;
        int index = 0,whitespace = 0,rightspace = 0,state = 0;
        word = strtok(line, " ");
        while(word!=NULL){ //causing
            liszt = add_end(liszt, new_word(word, 0));
            word = strtok(NULL, " ");
        }
        
        word = NULL;
        wordnode_t *curr = liszt;
        wordnode_t *answer = NULL;
        wordnode_t *temp = NULL;
        for(; curr!=NULL; curr=curr->next){
            if(find_word(exclusions, curr->w)){
                word = (char *)realloc(word, strlen(curr->w)*sizeof(char));
                strcpy(word, curr->w); //copy word and remove new line char
                word = strtok(word, "\n");

                int i;
                for(i=0; i<strlen(word); i++){
                    word[i] = toupper(word[i]);
                } //change the word to uppercase

                if(find_keyword(keywords, word)){
                    keywords = add_end(keywords, new_word(word, 0));
                    //add word to list of keywords
                }

                answer = NULL;
                for(temp = liszt; temp!=NULL; temp=temp->next){
                    answer = add_end(answer, new_word(temp->w, 0));
                    //loop makes a copy of liszt stored in answer
                }

                to_upper(answer, index); //calls function to make indexed word uppercase (see linkedlist.c)
                whitespace = 29 - size_to(answer, index) - index;
                while(whitespace<9){ //while loop to adjust for needed whitespace
                    whitespace+= size_to(answer,1)+1;
                    answer = remove_front(answer);
                }

                rightspace = whitespace + size(answer) + list_size(answer);
                if(rightspace>62){ //account for newline
                    rightspace -= 1+end_size(answer);
                    remove_end(answer);
                    state = 1;
                    while(rightspace>61){ //while loop to make sure string is cutting off properly
                        rightspace -= 1+end_size(answer);
                        remove_end(answer);
                    }
                }

                char *the_void = (char *)malloc(whitespace*sizeof(char));
                the_void = " ";
                for(i=0; i<whitespace; i++){
                    answer = add_front(answer, new_word(the_void, 0));
                } //adds whitespace nodes to the answer

                wordnode_t *tempanswer = answer;
                char *answer_string = (char *)malloc((size(answer)+list_size(answer))*sizeof(char));
                for(i=0; i<whitespace+1; i++){
                    strcat(answer_string, tempanswer->w);
                    tempanswer = tempanswer->next;
                } //takes all the whitespace and concatenates it to the asnwerstring
                
                while(tempanswer){
                    strcat(answer_string, the_void);
                    strcat(answer_string, tempanswer->w);
                    tempanswer = tempanswer->next;
                } //puts all the words into the answerstring with a space in between

                if(state){ //if the end of list is truncated we need to add the newlines character back
                    strcat(answer_string, "\n");
                }
                state=0;
                answers = add_end(answers, new_word(answer_string, 0));
                //adds the string to the list of lines
            }
            index++;
        }
    }
sort(keywords); //sorts the keywords in the linkedlist.c function
magic_print(keywords, answers);

while (lines) {
        lines = peek_front(lines);
        lines = remove_front(lines);
        free_word(lines);
    } 
    exit(0);
}

void magic_print(wordnode_t *keys, wordnode_t *values){
    wordnode_t *curr;
    wordnode_t *lines = values;
    char *magic_key = NULL;
    char *temp = NULL;
    char *line = NULL;

    for(curr = keys; curr; curr=curr->next){
        magic_key = (char *)realloc(magic_key, strlen(curr->w)*sizeof(char));
        strcpy(magic_key, " ");
        strcat(magic_key, curr->w); //copy current word to key
        strcat(magic_key, " ");
        for(lines = values; lines; lines=lines->next){
            line = (char *)realloc(line, (strlen(lines->w)+2)*sizeof(char));
            temp = (char *)realloc(temp, (strlen(lines->w)+2)*sizeof(char));
            strcpy(temp, lines->w);
            strcpy(line, temp);
            temp = strtok(temp, "\n");
            strcat(temp, " "); //remove newline and add space at end so strstr works properly
            if(strstr(temp,magic_key)){ //check if key is in a line
                printf("%s", line); //print that line
            }
        }
    }
    free(temp);
    free(magic_key);
    free(line);
    return;
}
