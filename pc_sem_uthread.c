#define _GNU_SOURCE

#include <stdlib.h>
#include <stdio.h>
#include <assert.h>
#include "uthread.h"
#include "uthread_sem.h"

#define MAX_ITEMS 10
const int NUM_ITERATIONS = 200;
const int NUM_CONSUMERS  = 2;
const int NUM_PRODUCERS  = 2;

int histogram [MAX_ITEMS+1]; // histogram [i] == # of times list stored i items

//create uthread semaphores
uthread_sem_t lock;
uthread_sem_t full;
uthread_sem_t empty;

int items = 0;

void* producer (void* v) {
  for (int i=0; i<NUM_ITERATIONS; i++) {
    //wait for there to be space in the buffer
    uthread_sem_wait(empty);
    uthread_sem_wait(lock);
    //update items, histogram, and validate value of items
    items++;
    histogram[items]+=1;
    assert(items<=MAX_ITEMS);
    uthread_sem_signal(full);
    uthread_sem_signal(lock);
  }
  return NULL;
}

void* consumer (void* v) {
  for (int i=0; i<NUM_ITERATIONS; i++) {
    //wait for there to be items to consume
    uthread_sem_wait(full);
    uthread_sem_wait(lock);
    //update items, histogram, and validate value of items
    items--;
    histogram[items]+=1;
    assert(items>=0);
    uthread_sem_signal(empty);
    uthread_sem_signal(lock);
  }
  return NULL;
}

int main (int argc, char** argv) {
  uthread_t t[4];

  uthread_init (4);
  //initalize semaphores using uthread_sem_create
  lock = uthread_sem_create(1);
  full = uthread_sem_create(0);
  empty = uthread_sem_create(MAX_ITEMS);
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

  printf ("items value histogram:\n");
  int sum=0;
  for (int i = 0; i <= MAX_ITEMS; i++) {
    printf ("  items=%d, %d times\n", i, histogram [i]);
    sum += histogram [i];
  }
  assert (sum == sizeof (t) / sizeof (uthread_t) * NUM_ITERATIONS);
}
