//Christian Knowles
//Struct, insert_at_head, insert_at_tail from https://www.geeksforgeeks.org/doubly-linked-list/
//get_size, insert_at_index, and deletions were original
//I thought about using global variable for head and tail, but either way works

#include <stdio.h>
#include <stdlib.h>

struct Node { 
    int value; 
    struct Node* next; //pointer to next node
    struct Node* prev; //pointer to previous node
};

void insert_at_head(struct Node** head, int data) {
    struct Node* new_node = (struct Node*)malloc(sizeof(struct Node));
    new_node->value = data;
    new_node->next = (*head);
    new_node->prev = NULL;
    if ((*head) != NULL){
        (*head)->prev = new_node;
    }
    (*head) = new_node;
}

void remove_head(struct Node** head){
    if((*head)==NULL){
        return; //does nothing if there's no head to begin with
    }
    struct Node* temp = (*head)->next;
    free(temp->prev); //free some space (I hope I'm doing this right)
    temp->prev = NULL; //set it to NULL rather than head
    (*head) = temp; //head is reposition to where temp is
}

void remove_tail(struct Node** head){ 
    struct Node* tail = *head;
    if (*head == NULL) { 
        return; //do nothing if no tail exists
    } 
    while (tail->next != NULL) {
        tail = tail->next; //traverse to tail
    }
    tail = tail->prev; //set tail to previous node
    free(tail->next); //free some space
    tail->next = NULL;
} 

void remove_at_index(struct Node** head, int index){
    if(index==0){
        (*head)=(*head)->next; //simple removal of head
        return;
    }
    if(index==get_size((*head))){
        remove_tail(head); //function call for edge case
        return;
    }
    if(index>get_size((*head)) || index<0){
        printf("index out of bounds!");
        return;
    }
    struct Node* curr = (*head);
    struct Node* precurr;
    for(int i=0; i<index; i++){
        precurr = curr;
        curr = curr->next;
    }
    precurr->next = curr->next;
    curr->next->prev = precurr;
}

void insert_at_tail(struct Node** head, int data) { 
    struct Node* new_node = (struct Node*)malloc(sizeof(struct Node)); 
    struct Node* tail = *head;
    new_node->value = data; 
    new_node->next = NULL; 
    if (*head == NULL) { 
        new_node->prev = NULL; 
        *head = new_node; 
        return; 
    } 
    while (tail->next != NULL) {
        tail = tail->next; 
    }
    tail->next = new_node; 
    new_node->prev = tail;
    return; 
} 

void insert_at_index(struct Node** head, int data, int index){
    struct Node* new_node = (struct Node*)malloc(sizeof(struct Node));
    new_node->value = data;
    if(index==0){
        insert_at_head(head, data); //function call for edge case
        return;
    }
    if(index==get_size((*head))){
        insert_at_tail(head, data); //function call for edge case
        return;
    }
    if(index>get_size((*head)) || index<0){
        printf("index out of bounds!");
        return;
    }
    struct Node* curr = (*head);
    struct Node* precurr;
    for(int i=0; i<index; i++){
        precurr = curr;
        curr = curr->next;
    }
    //places new_node in between precurr (index-1) and curr (index which becomes index+1)
    precurr->next = new_node;
    new_node->next = curr;
    curr->prev = new_node;
}

void print_list(struct Node* curr){
    struct Node* precurr;
    while(curr!=NULL){
        printf("%d ", curr->value);
        precurr = curr;
        curr = curr->next;
    }
}

int get_size(struct Node* curr){ //find out how many nodes you have
    int size = 0;
    struct Node* precurr;
    while(curr!=NULL){ //traverse list and increment
        size++;
        precurr = curr;
        curr = curr->next;
    }
    return size;
}

int main(){ //some function calls to test the code (all seems to work as intended)
    struct Node* head = NULL;
    insert_at_head(&head, 7);
    insert_at_head(&head, 9);
    insert_at_tail(&head, 11);
    insert_at_index(&head, 22, 0);
    insert_at_index(&head, 33, 4);
    insert_at_index(&head, 44, 2);
    remove_head(&head);
    remove_tail(&head);
    insert_at_tail(&head,14);
    remove_at_index(&head, 0);
    remove_at_index(&head, get_size(head));
    remove_at_index(&head, 1);
    print_list(head);
    printf("%d", get_size(head));
    return 0;
}