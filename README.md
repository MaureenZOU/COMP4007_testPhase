# COMP4007_testPhase
> This work is the test procedure of the COMP4007 final project

In this document we will first introduce test examples, test case implementation, and how to interpret the result.

## Test Examples

Test examples are located in the following directory:
```sh
./COMP4007_Configfiles/testCase/
```
There are four test cases in total, each represents a different scenario. We randomly generated client information including clientId,numPerson,cost,reqTime and checkOut time for every case. 

```sh
checkConnection.csv //use one single client to test the capability of connection on the system.
hugeVolum.csv //use large client volum to test the system capacity on input flow.
normal.csv //check normal proceudre of the queueing system with 10 client as example.
queueTooLong.csv //check how the server deal with the queue too long situation.
```

## Usage
1. Please first open the whole file COMP4007_Configfiles in IntelliJ IDE.
2. Start running the server, the main function is located in Main.java under AppKickstarter
3. Run the main function in Test.java under AppKickstarter (Please change the file directory for your own path)
4. After all the client stream finish, stop the test program, you will see the standard output.

## Standard Output
1. Output demonstrate the number of msg you receive and send for each type.
2. 
Multi-layer Proceptron

![alt tag](https://raw.githubusercontent.com/MaureenZOU/traffic_prediction/master/dnn.png)

LSTM

![alt tag](https://raw.githubusercontent.com/MaureenZOU/traffic_prediction/master/lstm.png)



## Experiment Result

Result Comparison on different network structure

![alt tag](https://raw.githubusercontent.com/MaureenZOU/traffic_prediction/master/Screen%20Shot%202017-08-16%20at%2011.47.44%20PM.png)
