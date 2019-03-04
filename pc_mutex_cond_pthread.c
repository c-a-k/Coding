#define _GNU_SOURCE

#include <stdlib.h>
#include <stdio.h>
#include <assert.h>
#include <pthread.h>

#define MAX_ITEMS 10
const int NUM_ITERATIONS = 200;
const int NUM_CONSUMERS  = 2;
const int NUM_PRODUCERS  = 2;

int producer_wait_count;     // # of times producer had to wait
int consumer_wait_count;     // # of times consumer had to wait
int histogram [MAX_ITEMS+1]; // histogram [i] == # of times list stored i items

int items = 0;

//made mutex and conditions global
pthread_mutex_t mutx;
pthread_cond_t not_empty;
pthread_cond_t not_full;

void* producer (void* v){
  for(int i=0;i<NUM_ITERATIONS;i++){
    pthread_mutex_lock(&mutx);//lock for this iteration
    while(items>=MAX_ITEMS){ //if buffer is full producer waits
      producer_wait_count++;
      pthread_cond_wait(&not_full, &mutx);
    }
    items++; //produce one to the buffer and update histogram
    histogram[items]+=1;
    assert(items<=MAX_ITEMS); //check production
    pthread_cond_signal(&not_empty);
    pthread_mutex_unlock(&mutx); //unlock for another thread
  }
  return NULL;
}

void* consumer (void* v){
  for(int i=0;i<NUM_ITERATIONS;i++){
    pthread_mutex_lock(&mutx); //lock for this iteration
    while(items<=0){ //if buffer is empty consumer waits
      consumer_wait_count++;
      pthread_cond_wait(&not_empty, &mutx);
    }
    items--; //consume one from the buffer and update histogram
    histogram[items]+=1;
    assert(items>=0); //check consumption
    pthread_cond_signal(&not_full);
    pthread_mutex_unlock(&mutx); //unlock for another thread
  }
  return NULL;
}

int main (int argc, char** argv){
  pthread_t t[4];
  //initialize mutex and condition variables
  pthread_mutex_init(&mutx, NULL);
  pthread_cond_init(&not_empty, NULL);
  pthread_cond_init(&not_full, NULL);
  //create threads (2 producer, 2 consumer)
  pthread_create(&t[0], NULL, &consumer, NULL);
  pthread_create(&t[1], NULL, &consumer, NULL);
  pthread_create(&t[2], NULL, &producer, NULL);
	pthread_create(&t[3], NULL, &producer, NULL);
  //join threads
  pthread_join(t[0], NULL);
  pthread_join(t[1], NULL);
  pthread_join(t[2], NULL);
  pthread_join(t[3], NULL);
  
  printf ("producer_wait_count=%d\nconsumer_wait_count=%d\n", producer_wait_count, consumer_wait_count);
  printf ("items value histogram:\n");
  int sum=0;
  for (int i = 0; i <= MAX_ITEMS; i++) {
    printf ("  items=%d, %d times\n", i, histogram [i]);
    sum += histogram [i];
  }
  assert (sum == sizeof (t) / sizeof (pthread_t) * NUM_ITERATIONS);
}
