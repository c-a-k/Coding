/*
 * linkedlist.c
 *
 * Based on the implementation approach described in "The Practice 
 * of Programming" by Kernighan and Pike (Addison-Wesley, 1999).
 */

#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include "linkedlist.h"


void *emalloc(size_t n) {
    void *p;

    p = malloc(n);
    if (p == NULL) {
        fprintf(stderr, "malloc of %zu bytes failed", n);
        exit(1);
    }

    return p;
}


wordnode_t *new_word(char *w, int val) {
    assert( w != NULL);

    wordnode_t *temp = (wordnode_t *)emalloc(sizeof(wordnode_t));

    temp->w = strdup(w);
    temp->val = val;
    temp->next = NULL;

    return temp;
}


wordnode_t *add_front(wordnode_t *list, wordnode_t *new) {
    new->next = list;
    return new;
}


wordnode_t *add_end(wordnode_t *list, wordnode_t *new) {
    wordnode_t *curr;

    if (list == NULL) {
        new->next = NULL;
        return new;
    }

    for (curr = list; curr->next != NULL; curr = curr->next);
    curr->next = new;
    new->next = NULL;
    return list;
}


wordnode_t *peek_front(wordnode_t *list) {
    return list;
}


wordnode_t *remove_front(wordnode_t *list) {
    if (list == NULL) {
        return NULL;
    }

    return list->next;
}

void sort(wordnode_t *list){
    if (list == NULL || list->next == NULL) {
        return;
    }
    wordnode_t *curr;
    wordnode_t *compare;
    char *word1 = NULL;
    char *word2 = NULL;
    for(curr = list; curr->next!=NULL; curr=curr->next){
        for(compare=curr->next;compare!=NULL;compare=compare->next){
            word1 = (char *)realloc(word1, strlen(curr->w)*sizeof(char));
            word2 = (char *)realloc(word2, strlen(compare->w)*sizeof(char));
            strcpy(word1, curr->w);
            strcpy(word2, compare->w);
            if(strcmp(word1,word2)>0){
                strcpy(curr->w, word2);
                strcpy(compare->w, word1);
            }
        }
    }
}

/*Removes last node of list*/
void remove_end(wordnode_t *list) {
    wordnode_t *temp = list;
    wordnode_t *last = temp;
    while(temp !=NULL && temp->next!=NULL){
        last = temp;
        temp = temp->next;
    }
    free(last->next);
    last->next = NULL;

    return;
}

/*Converts the indexed word to all uppercase*/
void to_upper(wordnode_t *list, int index){
    int i;
    wordnode_t *curr=NULL;
    curr = list;
    for(i=0; i<index; i++){
        curr = curr->next;
    }
    char *word = (char *)malloc(size(list)*sizeof(char));
    strcpy(word, curr->w);
    for(i=0; i<strlen(word); i++){
        word[i] = toupper(word[i]);
    }
    strcpy(curr->w, word);
    return;
}

/*Coverts the indexed word to all lowercase*/
void to_lower(wordnode_t *list, int index){
    int i;
    wordnode_t *curr=NULL;
    curr = list;
    for(i=0; i<index; i++){
        curr = curr->next;
    }
    char *word = (char *)malloc(size(list)*sizeof(char));
    strcpy(word, curr->w);
    for(i=0; i<strlen(word); i++){
        word[i] = tolower(word[i]);
    }
    strcpy(curr->w, word);
    return;
}

/*Counts total number of characters in each word of list*/
int size(wordnode_t *list){
    wordnode_t *curr;
    int sum = 0;
    for (curr = list; curr != NULL; curr = curr->next){
        sum += strlen(curr->w);
    }
    return sum;
}

/*Number of characters in last word of string*/
int end_size(wordnode_t *list){
    wordnode_t *curr;
    for (curr = list; curr->next != NULL; curr = curr->next);
    return strlen(curr->w);
}

/*Counts number of characters up to but not including the indexed word*/
int size_to(wordnode_t *list, int index){
    wordnode_t *curr = list;
    int sum = 0;
    int i;
    for (i=0; i<index; i++){
        sum += strlen(curr->w);
        curr = curr->next;
    }
    return sum;
}

/*Counts and returns the number of nodes in list*/
int list_size(wordnode_t *list){
    wordnode_t *curr;
    int sum = 0;
    for (curr = list; curr != NULL; curr = curr->next){
        sum++;
    }
    return sum;
}

/*Function designed to find a word inside of a list
  Returns 0 if word is found, 1 if not found*/
int find_word(wordnode_t *list, char *word){
    wordnode_t *curr;
    int i;
    char *temp = NULL;
    temp = (char *)realloc(temp, strlen(word)*sizeof(char));
    strcpy(temp, word);
    for(i=0; i<strlen(temp); i++){
        temp[i] = tolower(temp[i]);
    }
    for (curr = list; curr != NULL; curr = curr->next){
        if(strncmp(temp, curr->w, strlen(temp))==0){
            return 0;
        }
    }
    free(temp);
    return 1;
}

int find_keyword(wordnode_t *list, char *word){
    wordnode_t *curr;
    for (curr = list; curr != NULL; curr = curr->next){
        if(strcmp(word, curr->w)==0){
            return 0;
        }
    }
    return 1;
}


void free_word(wordnode_t *word) {
    assert(word != NULL);

    if (word->w != NULL) {
        free(word->w);
    }
    free(word);
}


void apply(wordnode_t *list,
           void (*fn)(wordnode_t *list, void *),
           void *arg)
{
    for ( ; list != NULL; list = list->next) {
        (*fn)(list, arg);
    }
}
