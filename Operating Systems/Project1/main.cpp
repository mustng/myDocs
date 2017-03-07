#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/ipc.h>
#include <sys/sem.h>
#include <time.h>
#include <sys/wait.h>
#include <sys/shm.h>
#include <sys/ipc.h>
#include <string>
#include <iostream>

using namespace std;

union semun {
    int val; // Value for SETVAL
    struct semid_ds *buf; // Buffer for IPC_STATE, IP_SET
    unsigned short *array; // Array for GETALL, SETALL
    struct seminfo *__buf; // Buffer for IPC_INFO (Linux-specific)
};

enum SemaphoreType {
	Access = 0,
	Produce = 1,
	Consume = 2
};

const key_t SEMAPHORE_KEY = 52441800;         
int semaphoreID = 0;

void SeedRandomNumberGenerator();
int CreateSharedMemory(int memorySize);
int ReleaseSharedMemory(int memoryID);
int *GetSharedMemory(int memoryID);

void SetDefaultQueueValues(int* queue, int length);

void WaitForAllChildProcesses();


int CreateSemaphore(int key, int count);
void SetSemaphoreThreshold(int semID, SemaphoreType type, int threshold);
void RequestSemaphore(int semID, SemaphoreType type);
void ReleaseSemaphore(int semID, SemaphoreType type);
void ClearSemaphores(int semID);

void PrintHeader();
void PrintAction(int val, int queueIndex, int sem1, int sem2, int sem3, const char* action, int totalProduced, int totalConsumed, int* head, int* tail);

int main( int argc , char* argv[] ) {
    const int QUEUE_SIZE = 10;

	if (argc == 1) {
		printf("\nError: You forgot to supply a number of producers, consumers and things to add. Try again.\n\n");
		return 1;
	}

	int producers = atoi(argv[1]);
	int consumers = atoi(argv[2]);
	int itemCount = atoi(argv[3]);

 	int queueMemoryID = CreateSharedMemory(QUEUE_SIZE);
 	int* circularQueue = GetSharedMemory(queueMemoryID);
    SetDefaultQueueValues(circularQueue, QUEUE_SIZE);

    int headMemoryID = CreateSharedMemory(1);
    int* head = GetSharedMemory(headMemoryID);
    *head = 0;

    int tailMemoryID = CreateSharedMemory(1);
    int* tail = GetSharedMemory(tailMemoryID);
    *tail = 0;

    int producedCountMemoryID = CreateSharedMemory(1);
    int* producedCount = GetSharedMemory(producedCountMemoryID);
    *producedCount = 0;

    int consumedCountMemoryID = CreateSharedMemory(1);
    int* consumedCount = GetSharedMemory(consumedCountMemoryID);
    *consumedCount = 0;

    int remainderMemoryID = CreateSharedMemory(1);
    int* remainder = GetSharedMemory(remainderMemoryID);
    *remainder = 0;

	struct sembuf arg;

	semaphoreID = CreateSemaphore(SEMAPHORE_KEY, 3);

	PrintHeader();

    try {
        for (int producer = 0; producer < producers; producer++) {
            int producerPID = fork();
            if(producerPID == 0) { // Enter Child process
                SeedRandomNumberGenerator();

                for (int item = 0; item < itemCount; item++) {
                    RequestSemaphore(semaphoreID, Produce);
                    RequestSemaphore(semaphoreID, Access);

                    int accessIndex = *tail % QUEUE_SIZE;
                    int generatedValue = rand() % 1000 + 1;
                    circularQueue[*tail] = generatedValue;
                    (*tail)++;
                    (*producedCount)++;

                    PrintAction(
                        generatedValue,
                        accessIndex,
                        semctl(semaphoreID, Access, GETVAL, arg.sem_num),
                        semctl(semaphoreID, Produce, GETVAL, arg.sem_num),
                        semctl(semaphoreID, Consume, GETVAL, arg.sem_num),
                        "produced",
                        *producedCount,
                        *consumedCount,
                        head,
                        tail
                    );

                    ReleaseSemaphore(semaphoreID, Consume);
                    ReleaseSemaphore(semaphoreID, Access);
                }

                exit(0);
            }
        }

        int totalToConsume = (producers * itemCount);
        int amountPerConsumes = (producers * itemCount) / consumers;
        *remainder = (producers * itemCount) % consumers;

        for (int consumer = 0; consumer < consumers; consumer++) {
            int consumerPID = fork();
            if(consumerPID == 0) { // Enter Child process
                int additional = 0;
                if (*remainder > 0) {
                    (*remainder)--;
                    additional = 1;
                }

                for (int i = 0; i < amountPerConsumes + additional; i++) {
                    RequestSemaphore(semaphoreID, Consume);
                    RequestSemaphore(semaphoreID, Access);

                    int accessIndex = *head % QUEUE_SIZE;
                    int dequeuedValue = circularQueue[accessIndex];
                    (*head)++;
                    (*consumedCount)++;

                    PrintAction(
                        dequeuedValue,
                        accessIndex,
                        semctl(semaphoreID, Access, GETVAL, arg.sem_num),
                        semctl(semaphoreID, Produce, GETVAL, arg.sem_num),
                        semctl(semaphoreID, Consume, GETVAL, arg.sem_num),
                        "consumed",
                        *producedCount,
                        *consumedCount,
                        head,
                        tail
                    );

                    ReleaseSemaphore(semaphoreID, Produce);
                    ReleaseSemaphore(semaphoreID, Access);
                }

                exit(0);
            }
        }
    }
    catch (exception e) {
        printf("%s", e.what());
    }

    WaitForAllChildProcesses();

    // Cleanup
 	ReleaseSharedMemory(queueMemoryID);
 	ReleaseSharedMemory(headMemoryID);
 	ReleaseSharedMemory(tailMemoryID);
 	ReleaseSharedMemory(producedCountMemoryID);
 	ReleaseSharedMemory(consumedCountMemoryID);
 	ReleaseSharedMemory(remainderMemoryID);
    ClearSemaphores(semaphoreID);

 	return 0;
}

void PrintHeader() {
	printf("\npid\titem\tloc\tsem1\tsem2\tsem3\taction\t\ttot prod\ttot con\thead\ttail\n");
}

void PrintAction(int val, int queueIndex, int sem1, int sem2, int sem3, const char* action, int totalProduced, int totalConsumed, int* head, int* tail) {
    printf("%i\t%i\t%i\t%i\t%i\t%i\t%s\t%i\t\t%i\t%i\t%i\n",
        getpid(),
        val,
        queueIndex,
        sem1,
        sem2,
        sem3,
        action,
        totalProduced,
        totalConsumed,
        *head,
        *tail
    );
}

void SeedRandomNumberGenerator() {
    srand(time(NULL) ^ (getpid()<<16));;
}

int CreateSharedMemory(int memorySize) {
    int memoryID = shmget(IPC_PRIVATE, memorySize * sizeof(int), 0777);

 	if (memoryID == -1) {
        printf("\nError allocating shared memory with shmget.\n");
        exit(1);
 	}

 	return memoryID;
}

int *GetSharedMemory(int memoryID) {
    static const void* BAD_VALUE = (void*) -1;
    int* sharedMemory = (int *)shmat(memoryID, (void *)0, 0);

    if(sharedMemory == BAD_VALUE) {
        printf("Error when calling shmat for:\n\tMemory ID: %i", memoryID);
    }

    return sharedMemory;
}

int ReleaseSharedMemory(int memoryID) {
    int result = shmctl(memoryID, IPC_RMID, 0);
    if (result == -1) {
          printf("\nError releasing shared memory for:\n\tMemory ID: %i\n\tError Code: %i", memoryID, result);
          exit(1);
     }

     return result;
}

void SetDefaultQueueValues(int* queue, int length) {
    for(int i = 0; i< length; i++) {
        queue[i] = -1;
    }
}

int CreateSemaphore(int key, int count) {
	int semID = semget(key, count, 0600 | IPC_CREAT);

	if (semID == -1) {
		printf("\nError: There was an error creating a semaphore context. I really don't know what to tell you. Google it.\n");
		exit(1);
	}

	SetSemaphoreThreshold(semID, Access, 1);
	SetSemaphoreThreshold(semID, Produce, 10);
	SetSemaphoreThreshold(semID, Consume, 0);

	return semID;
}

void SetSemaphoreThreshold(int semID, SemaphoreType type, int threshold) {
    union semun sem_val;
    sem_val.val = threshold;

	int result = semctl(semID, (int)type, SETVAL, sem_val);

	if (result == -1) {
		printf("\nError: Failure setting Semaphore threshold for SemID %i\n", semID);
		exit(1);
	}
}

void RequestSemaphore(int semID, SemaphoreType type) {
	struct sembuf decrementOperation = { (short unsigned int)type, -1, 0 };
	int result = semop(semID, &decrementOperation, 1);

	if (result == -1) {
        string semType;
        switch(type) {
            case Access:
                semType = "Access";
                break;
            case Produce:
                semType = "Produce";
                break;
            case Consume:
                semType = "Consume";
                break;
        }

		std::cout <<
            "\nError: Failure performing semop to request semaphore\nSemaphore ID: "
                << semID <<
            "\nSemaphore Type: "
                << semType << "\n";

		exit(1);
	}
}

void ReleaseSemaphore(int semID, SemaphoreType type) {
	struct sembuf incrementOperation = { (short unsigned int)type, 1, 0 };

	int result = semop(semID, &incrementOperation, 1);

	if (result == -1) {
		string semType;
		switch(type) {
		    case Access:
			semType = "Access";
			break;
		    case Produce:
			semType = "Produce";
			break;
		    case Consume:
			semType = "Consume";
			break;
		}

		std::cout <<
            "\nError: Failure performing semop to release semaphore\nSemaphore ID: "
                << semID <<
            "\nSemaphore Type: "
                << semType << "\n";

		exit(1);
	}
}

void ClearSemaphores(int semID) {
    struct sembuf arg;
    int result = semctl(semID, 0, IPC_RMID, arg);
    if (result == -1) {
        printf("\nError Clearing Semaphores for Key#: %i\n", semID);
    }
}

void WaitForAllChildProcesses() {
    while (wait((int *)0) != -1);
}
