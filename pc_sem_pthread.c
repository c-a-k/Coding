#define _GNU_SOURCE

#include <stdlib.h>
#include <stdio.h>
#include <assert.h>
#include <semaphore.h>
#include <pthread.h>

#define MAX_ITEMS 10
const int NUM_ITERATIONS = 200;
const int NUM_CONSUMERS  = 2;
const int NUM_PRODUCERS  = 2;

int histogram [MAX_ITEMS+1]; // histogram [i] == # of times list stored i items

//create semaphores
sem_t lock;
sem_t full;
sem_t empty;

int items = 0;

void* producer (void* v) {
  for (int i=0; i<NUM_ITERATIONS; i++) {
    //wait for there to be space in the buffer
    sem_wait(&empty);
    sem_wait(&lock);
    //update items, histogram, and validate value of items
    items++;
    histogram[items]+=1;
    assert(items<=MAX_ITEMS);
    sem_post(&full);
    sem_post(&lock);
  }
  return NULL;
}

void* consumer (void* v) {
  for (int i=0; i<NUM_ITERATIONS; i++) {
    //wait for there to be items to consume
    sem_wait(&full);
    sem_wait(&lock);
    //update items, histogram, and validate value of items
    items--;
    histogram[items]+=1;
    assert(items>=0);
    sem_post(&empty);
    sem_post(&lock);
  }
  return NULL;
}

int main (int argc, char** argv) {
  pthread_t t[4];
  //initalize semaphores
  sem_init(&lock, 0, 1);
  sem_init(&full, 0, 0);
  sem_init(&empty, 0, MAX_ITEMS);
  //create pthreads (2 consumer, 2 producer)
  pthread_create(&t[0], NULL, &consumer, NULL);
  pthread_create(&t[1], NULL, &consumer, NULL);
  pthread_create(&t[2], NULL, &producer, NULL);
  pthread_create(&t[3], NULL, &producer, NULL);
  //join pthreads
  pthread_join(t[0], NULL);
  pthread_join(t[1], NULL);
  pthread_join(t[2], NULL);
  pthread_join(t[3], NULL);

  printf ("items value histogram:\n");
  int sum=0;
  for (int i = 0; i <= MAX_ITEMS; i++) {
    printf ("  items=%d, %d times\n", i, histogram [i]);
    sum += histogram [i];
  }
  assert (sum == sizeof (t) / sizeof (pthread_t) * NUM_ITERATIONS);
}
