#define _GNU_SOURCE

#include <stdlib.h>
#include <stdio.h>
#include <assert.h>
#include <fcntl.h>
#include <unistd.h>
#include <pthread.h>
#include <time.h>

#define NUM_ITERATIONS 1000

#ifdef VERBOSE
#define VERBOSE_PRINT(S, ...) printf (S, ##__VA_ARGS__);
#else
#define VERBOSE_PRINT(S, ...) ;
#endif

/**
 * You might find these declarations helpful.
 *   Note that Resource enum had values 1, 2 and 4 so you can combine resources;
 *   e.g., having a MATCH and PAPER is the value MATCH | PAPER == 1 | 2 == 3
 */
enum Resource            {    MATCH = 1, PAPER = 2,   TOBACCO = 4};
char* resource_name [] = {"", "match",   "paper", "", "tobacco"};

int signal_count [5];  // # of times resource signalled
int smoke_count  [5];  // # of times smoker with resource smoked

struct Agent {
  pthread_mutex_t mutex;
  pthread_cond_t  match;
  pthread_cond_t  paper;
  pthread_cond_t  tobacco;
  pthread_cond_t  smoke;
};

struct Agent* createAgent() {
  struct Agent* agent = malloc (sizeof (struct Agent));
  pthread_mutex_init(&(agent->mutex), NULL);
  pthread_cond_init(&(agent->paper), NULL);
  pthread_cond_init(&(agent->match), NULL);
  pthread_cond_init(&(agent->tobacco), NULL);
  pthread_cond_init(&(agent->smoke), NULL);
  return agent;
}
//global variables for resource pool
int got_match = 0;
int got_paper = 0;
int got_goods = 0; //this is tobacco

void *paper_smoker(void *p){
    struct Agent *a=p;
    pthread_mutex_lock(&(a->mutex));
    for(;;){
        pthread_cond_wait(&(a->paper), &(a->mutex));
        if(got_match>0 && got_goods>0){
            got_match--;
            got_goods--;
            smoke_count[PAPER]++;
            pthread_cond_signal(&(a->smoke));
        } else {
            got_paper++;
        }
        //check resources and signal accordingly
        if(got_paper>0 && got_goods>0) {
        pthread_cond_signal(&(a->match));
        } else if (got_paper>0 && got_match>0) {
        pthread_cond_signal(&(a->tobacco));
        } else if (got_goods>0 && got_match>0) {
        pthread_cond_signal(&(a->paper));
        }
    }
    pthread_mutex_unlock(&(a->mutex));
}

void *match_smoker(void *p){
    struct Agent *a=p;
    pthread_mutex_lock(&(a->mutex));
    for(;;){
        pthread_cond_wait(&(a->match), &(a->mutex));
        if(got_paper>0 && got_goods>0){
            got_paper--;
            got_goods--;
            smoke_count[MATCH]++;
            pthread_cond_signal(&(a->smoke));
        } else {
            got_match++;
        }
        //check resources and signal accordingly
        if(got_paper>0 && got_goods>0) {
        pthread_cond_signal(&(a->match));
        } else if (got_paper>0 && got_match>0) {
        pthread_cond_signal(&(a->tobacco));
        } else if (got_goods>0 && got_match>0) {
        pthread_cond_signal(&(a->paper));
        }
    }
    pthread_mutex_unlock(&(a->mutex));
}

void *tobacco_smoker(void *p){
    struct Agent *a=p;
    pthread_mutex_lock(&(a->mutex));
    for(;;){
        pthread_cond_wait(&(a->tobacco), &(a->mutex));
        if(got_match>0 && got_paper>0){
            got_match--;
            got_paper--;
            smoke_count[TOBACCO]++;
            pthread_cond_signal(&(a->smoke));
        } else {
            got_goods++;
        }
        //check resources and signal accordingly
        if(got_paper>0 && got_goods>0) {
        pthread_cond_signal(&(a->match));
        } else if (got_paper>0 && got_match>0) {
        pthread_cond_signal(&(a->tobacco));
        } else if (got_goods>0 && got_match>0) {
        pthread_cond_signal(&(a->paper));
        }
    }
    pthread_mutex_unlock(&(a->mutex));
}

/**
 * This is the agent procedure.  It is complete and you shouldn't change it in
 * any material way.  You can re-write it if you like, but be sure that all it does
 * is choose 2 random reasources, signal their condition variables, and then wait
 * wait for a smoker to smoke.
 */
void* agent (void* av) {
  sleep(1);
  struct Agent* a = av;
  static const int choices[]         = {MATCH|PAPER, MATCH|TOBACCO, PAPER|TOBACCO};
  static const int matching_smoker[] = {TOBACCO,     PAPER,         MATCH};
  
  pthread_mutex_lock(&(a->mutex));
    for (int i = 0; i < NUM_ITERATIONS; i++) {
      int r = random() % 3;
      signal_count [matching_smoker [r]] ++;
      int c = choices [r];
      if (c & MATCH) {
        VERBOSE_PRINT ("match available\n");
        pthread_cond_signal(&(a->match));
      }
      if (c & PAPER) {
        VERBOSE_PRINT ("paper available\n");
        pthread_cond_signal(&(a->paper));
      }
      if (c & TOBACCO) {
        VERBOSE_PRINT ("tobacco available\n");
        pthread_cond_signal(&(a->tobacco));
      }
      VERBOSE_PRINT ("agent is waiting for smoker to smoke\n");
      pthread_cond_wait(&(a->smoke), &(a->mutex));
    }
  pthread_mutex_unlock(&(a->mutex));
  return NULL;
}

int main (int argc, char** argv) {
  struct Agent *a = createAgent();
  //create pthreads
  pthread_t match, paper, tobacco, agnt;
  pthread_create(&match, NULL, match_smoker, a);
  pthread_create(&paper, NULL, paper_smoker, a);
  pthread_create(&tobacco, NULL, tobacco_smoker, a);
  pthread_create(&agnt, NULL, agent, a);
  pthread_join(agnt, NULL);
  assert (signal_count [MATCH]   == smoke_count [MATCH]);
  assert (signal_count [PAPER]   == smoke_count [PAPER]);
  assert (signal_count [TOBACCO] == smoke_count [TOBACCO]);
  assert (smoke_count [MATCH] + smoke_count [PAPER] + smoke_count [TOBACCO] == NUM_ITERATIONS);
  printf ("Smoke counts: %d matches, %d paper, %d tobacco\n",
          smoke_count [MATCH], smoke_count [PAPER], smoke_count [TOBACCO]);
}