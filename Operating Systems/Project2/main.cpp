#include <iostream>
#include <fstream>
#include <stdlib.h>
#include <unistd.h>
#include <strings.h>
#include <string.h>
#include <fcntl.h>
#include <fstream>
#include <ctime>
#include <vector>
#include <sstream>
#include <iterator>
#include <signal.h>
#include "lib.h"
#include "StoreManager.h"
#include "TransactionProcess.h"

using namespace std;

int main(int argc , char* argv[]) {
    if (argc == 1) {
		cout << endl <<"Error: You forgot to supply the proper arguments. Try again." << endl << endl;
		return 1;
    }

    char* initFileName = argv[1];
    char* logFileName = argv[2];
    char* transFile1 = argv[3];
    char* transFile2 = argv[4];

    int transactionPipe[2];
    CreatePipe(transactionPipe, "Transaction");

    int proc1Pipe[2];
    CreatePipe(proc1Pipe, "Proc1");

    int proc2Pipe[2];
    CreatePipe(proc2Pipe, "Proc2");

    WriteConsole("Forking StoreManager...");
    int storeManagerPID = fork();
    if (storeManagerPID == 0) {
		StoreManagerProcess(initFileName, logFileName, transactionPipe, proc1Pipe, proc2Pipe);
    }

    WriteConsole("Forking Proc1...");
    int proc1ID = fork();
    if (proc1ID == 0) { // Child Process
        TransactionProcess(1, logFileName, transFile1, transactionPipe, proc1Pipe);
    }

    WriteConsole("Forking Proc2...");
    int proc2ID = fork();
    if (proc2ID== 0) { // Child Process
        TransactionProcess(2, logFileName, transFile2, transactionPipe, proc2Pipe);
    }

    while (true) {
        char input;
        cout << "Please enter '1' for PROC1, '2' for PROC2, and anything else to quit" << endl;
        cin >> input;

        if (input == '1') {
            kill(proc1ID, SIGUSR1);
        }
        else if (input == '2') {
            kill(proc2ID, SIGUSR2);
        }
        else {

            kill(proc1ID, SIGKILL);
            kill(proc2ID, SIGKILL);
            kill(storeManagerPID, SIGKILL);

            return 0;
        }
    }

    WaitForAllChildProcesses();

    return 0;
}
