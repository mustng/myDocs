#include "lib.h"

#ifndef STOREMANAGER_H_
#define STOREMANAGER_H_

void ListenToPipe(int* transactionPipe, int* pipe1, int* pipe2, ofstream& log) {
	char message[MAX_MESSAGE_SIZE];
	int responseCode = ListenForResponse(transactionPipe[Read], message);


	if (responseCode > 0) {
		string token;
		string originalMessage(message);
		string procID = strtok(message, " ");
		string procPID = strtok(NULL, " ");
		string transType = strtok(NULL, " ");
		string recordID = strtok(NULL, " ");
		int parsedProcID = atoi(procID.c_str());

		int updateVal = -1;
		int tableResult = -1;

		//WriteConsole("Store Manager received message: " + originalMessage + " @ " + GetCurrentTime());
		log << "Store Manager received message: " << originalMessage << " @ " << GetCurrentTime() << endl;

		if (transType == "U") {
			token = strtok(NULL, " ");
			updateVal = atoi(token.c_str());
			tableResult = TABLE_UPDATE(recordID.c_str(), updateVal);
		}
		else if (transType == "R") {
			int* readVal = 0;
			tableResult = TABLE_READ(recordID.c_str(), readVal);

			if (tableResult > 0) {
				// failure to read
			}
		}


		string response = transType + " " + patch::to_string(tableResult) + " " +  recordID;

		if (transType == "U") {
			response = response + " " + patch::to_string(updateVal);
		}

		int* responsePipe = 0;

		if (parsedProcID == 1) {
			responsePipe = pipe1;

			if (transType == "R") {
				if (tableResult == 0) {
					proc1ReadSuccess++;
				}

				proc1ReadCount++;
			}
			else if (transType == "U") {
				if (tableResult == 0) {
					proc1UpdateSuccess++;
				}

				proc1UpdateCount++;

			}
		}
		else if (parsedProcID == 2) {
			responsePipe = pipe2;

			if (transType == "R") {
				if (tableResult == 0) {
					proc2ReadSuccess++;
				}

				proc2ReadCount++;
			}
			else if (transType == "U") {
				if (tableResult == 0) {
					proc2UpdateSuccess++;
				}

				proc2UpdateCount++;

			}
		}

		SendMessage(responsePipe[Write], response);

		//WriteConsole("Store Manager at time sent message: "+ response + " @ " + GetCurrentTime());
		log << "Store Manager at time sent message: " << response << " @ " << GetCurrentTime() << endl;

	}
}

void StoreManagerProcess(char* initFileName, char* logFileName, int* transactionPipe, int* pipe1, int* pipe2) {
    signal(SIGUSR1, SignalHandler);
    close(transactionPipe[Write]);
    close(pipe1[Read]);
    close(pipe2[Read]);

    ReadInitFile(initFileName);
    ofstream log(logFileName, ios::out | ios::app);

    while (true) {
    	ListenToPipe(transactionPipe, pipe1, pipe2, log);
    }
}

#endif
