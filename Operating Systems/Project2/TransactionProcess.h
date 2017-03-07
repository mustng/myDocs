#ifndef TRANSACTIONPROCESS_H_
#define TRANSACTIONPROCESS_H_

#include <vector>
#include "lib.h"

string BuildMessage(int pipeID, string data) {
    string message;
    message.append(patch::to_string(pipeID));
    message.append(" ");
    message.append(patch::to_string(getpid()));
    message.append(" ");
    message.append(data);

    return message;
}

void TransactionProcess(int procID, char* logFileName, char* transFileName, int* transactionPipe, int* procPipe) {
	string procName = "Proc " + patch::to_string(procID);
	signal(SIGUSR1, SignalHandler);
	signal(SIGUSR2, SignalHandler);

    close(transactionPipe[Read]);
    close(procPipe[Write]);

    ofstream log(logFileName, ios::out | ios::app);
    ifstream transFile(transFileName);
    vector<string> transactions;

    for (std::string transCode; std::getline(transFile, transCode); ) {
        string message = BuildMessage(procID, transCode);
        transactions.push_back(message);
    }

    string currTime = "";
	for (unsigned int index = 0; index < transactions.size(); index++) {
		string message = transactions[index];
        SendMessage(transactionPipe[Write], message);

        //WriteConsole(procName + " sent message: " + message + " @ " + GetCurrentTime());
        log <<  procName << " sent message: " << message << " @ " << GetCurrentTime() << endl;

        char rawResponse[MAX_MESSAGE_SIZE];

        ListenForResponse(procPipe[Read], rawResponse);
        log <<  procName << " recieved response "  << " @ " << GetCurrentTime() << endl;
        //WriteConsole(procName + " sent message: " + message + " @ " + GetCurrentTime());

    	string response(rawResponse);


        if (procName == "Proc 2"){
            if (rawResponse[0] == 'U'){
                proc2UpdateCount++;
            } 
            else{
                proc2ReadCount++;
            } 
        }
        else{
            if (rawResponse[0] == 'U'){
                proc1UpdateCount++;
            } 
            else{
                proc1ReadCount++;
            }             
        }

        sleep(3);
	}

    while (true) { }
}

#endif /* TRANSACTIONPROCESS_H_ */
