#ifndef LIB_H_
#define LIB_H_

#include <fstream>
#include <sys/wait.h>
#include <unistd.h>
#include <string>

#define MAX_MESSAGE_SIZE 180

using namespace std;

static int proc1ReadCount = 0;
static int proc1ReadSuccess = 0;
static int proc1UpdateCount = 0;
static int proc1UpdateSuccess = 0;

static int proc2ReadCount = 0;
static int proc2ReadSuccess = 0;
static int proc2UpdateCount = 0;
static int proc2UpdateSuccess = 0;

typedef const char* TABLE_ID;
typedef int TABLE_ELEM;

const int NAME_SIZE = 25;
const int SIZE = 50;

struct Widget {
  char id[NAME_SIZE + 1];
  int value;
} TABLE[SIZE];

enum PipeDirection {
    Read = 0,
    Write = 1
};

namespace patch
{
    template < typename T > std::string to_string( const T& n )
    {
        std::ostringstream stm ;
        stm << n ;
        return stm.str() ;
    }
}

int TABLE_UPDATE (TABLE_ID WHO, TABLE_ELEM VAL) {
    int failure = 1;
    for (int i = 0; i < SIZE - 1; i++) {
    	string w(WHO);
		string id(TABLE[i].id);

        if (w == id) {
            TABLE[i].value = VAL;
            failure = 0;
        }
    }

	return failure;
}


int TABLE_READ (TABLE_ID WHO, TABLE_ELEM* VAL) {
	int failure = 1;
	for (int i = 0; i < SIZE; i++) {
		string w(WHO);
		string id(TABLE[i].id);

        if (w == id) {
            VAL = &TABLE[i].value;
            failure = 0;
        }
	}

	return failure;
}

void ReadInitFile(char* fileName) {
    ifstream fs(fileName);
    int index = 0;

    while (fs >> TABLE[index].id) {
        fs >> TABLE[index].value;
        index ++;
 	}

    fs.close();
}

void WaitForAllChildProcesses() {
	    while (wait((int *)0) != -1);
}

string GetCurrentTime() {
      time_t rawtime;
      struct tm * timeinfo;
      char buffer[80];
      time (&rawtime);
      timeinfo = localtime(&rawtime);

      strftime(buffer,80,"%d-%m-%Y %I:%M:%S",timeinfo);
      std::string currTime(buffer);

      return currTime;
}

void SignalHandler(int signal) {
    if (signal == SIGUSR1) {
      cout << "Total read " << proc1ReadCount << " and " << proc1UpdateCount << " total update"<< endl;
      return;
    }
    else if (signal == SIGUSR2) {
    	cout << "Total read " << proc2ReadCount << " and " << proc2UpdateCount << " total update"<< endl;
      return;
    }
}

void WriteLog(ofstream &log, string message) {
	log << message << endl;
}

void WriteConsole(string message) {
	cout << message << endl;
}

void CreatePipe(int* userPipe, string name) {
	pipe(userPipe);
    WriteConsole("Creating " + name + " Pipe...");
}

void SendMessage(int pipeID, string message) {
	write(pipeID, message.c_str(), strlen(message.c_str()));
}

int ListenForResponse(int pipeID, char* response) {
    int readResult = read(pipeID, response, MAX_MESSAGE_SIZE);
    return readResult;
}

#endif /* LIB_H_ */
