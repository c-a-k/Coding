#define _GNU_SOURCE

#include <stdlib.h>
#include <stdio.h>
#include <assert.h>
#include "uthread.h"
#include "uthread_mutex_cond.h"

#define MAX_ITEMS 10
const int NUM_ITERATIONS = 200;
const int NUM_CONSUMERS  = 2;
const int NUM_PRODUCERS  = 2;

int producer_wait_count;     // # of times producer had to wait
int consumer_wait_count;     // # of times consumer had to wait
int histogram [MAX_ITEMS+1]; // histogram [i] == # of times list stored i items

int items = 0;

//create uthread mutex and condition variables
uthread_mutex_t mutx;
uthread_cond_t not_empty;
uthread_cond_t not_full;

void* producer (void* v){
  for(int i=0;i<NUM_ITERATIONS;i++){
    uthread_mutex_lock(mutx); //lock for this iteration
    while(items>=MAX_ITEMS){ //if buffer is full producer waits
      producer_wait_count++;
      uthread_cond_wait(not_full);
    }
    items++; //produce one to the buffer and update histogram
    histogram[items]+=1;
    assert(items<=MAX_ITEMS); //check if production was successful
    uthread_cond_signal(not_empty);
    uthread_mutex_unlock(mutx); //unlock for another thread
  }
  return NULL;
}

void* consumer (void* v){
  for(int i=0;i<NUM_ITERATIONS;i++){
    uthread_mutex_lock(mutx);//lock for this iteration
    while(items<=0){ //if buffer is empty consumer waits
      consumer_wait_count++;
      uthread_cond_wait(not_empty);
    }
    items--; //consume one from the buffer and update histogram
    histogram[items]+=1;
    assert(items>=0); //check if consumption was successful
    uthread_cond_signal(not_full);
    uthread_mutex_unlock(mutx); //unlock for another thread
  }
  return NULL;
}

int main (int argc, char** argv){
  uthread_t t[4];

  uthread_init (4);
  //create mutex and condition variables
  mutx = uthread_mutex_create();
  not_empty = uthread_cond_create (mutx);
  not_full = uthread_cond_create (mutx);
  //create uthreads
  t[0] = uthread_create(consumer, NULL);
  t[1] = uthread_create(consumer, NULL);
  t[2] = uthread_create(producer, NULL);
  t[3] = uthread_create(producer, NULL);
  //join uthreads
  uthread_join(t[0], NULL);
  uthread_join(t[1], NULL);
  uthread_join(t[2], NULL);
  uthread_join(t[3], NULL);
  
  printf ("producer_wait_count=%d\nconsumer_wait_count=%d\n", producer_wait_count, consumer_wait_count);
  printf ("items value histogram:\n");
  int sum=0;
  for (int i = 0; i <= MAX_ITEMS; i++) {
    printf ("  items=%d, %d times\n", i, histogram [i]);
    sum += histogram [i];
  }
  assert (sum == sizeof (t) / sizeof (uthread_t) * NUM_ITERATIONS);
}
