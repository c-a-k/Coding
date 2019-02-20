#ifndef _LINKEDLIST_H_
#define _LINKEDLIST_H_

typedef struct word wordnode_t;
struct word {
    char   *w;
    int     val;
    wordnode_t *next;
};

wordnode_t *new_word(char *w, int val);
wordnode_t *add_front(wordnode_t *, wordnode_t *);
wordnode_t *add_end(wordnode_t *, wordnode_t *);
wordnode_t *peek_front(wordnode_t *);
wordnode_t *remove_front(wordnode_t *);
void sort(wordnode_t *);
void remove_end(wordnode_t *);
int end_size(wordnode_t *list);
void to_upper(wordnode_t *list, int index);
void to_lower(wordnode_t *list, int index);
int size(wordnode_t *list);
int size_to(wordnode_t *list, int index);
int list_size(wordnode_t *list);
int find_word(wordnode_t *list, char *word);
int find_keyword(wordnode_t *list, char *word);
void        free_word(wordnode_t *);
void        apply(wordnode_t *, 
                void(*fn)(wordnode_t *, void *), void *arg);
#endif
