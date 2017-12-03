# COMP4007_testPhase
> This work is the test procedure of the COMP4007 final project

## Usage
1. Config [./COMP4007_Configfiles] on IntelliJ
2. Run
```sh
./COMP4007_Configfiles/src/AppKickstarter/Main.java
```
and
```sh
./COMP4007_Configfiles/src/AppKickstarter/Test.java
```
3. Stop the test program, view reports in
```sh
./COMP4007_Configfiles/report／
```

## Test Examples

Test examples are located in the following directory:
```sh
./COMP4007_Configfiles/testCase/
```
There are four test cases in total, each represents a different scenario. We randomly generated client information including clientId,numPerson,cost,reqTime and checkOut time for every case.

You could switch test case through [Image], located in ./COMP4007_Configfiles/src/AppKickstarter/Test.java.
![alt tag](https://raw.githubusercontent.com/MaureenZOU/COMP4007_testPhase/master/case.png)


```sh
checkConnection.csv //use one single client to test the capability of connection on the system.
hugeVolum.csv //use large client volum to test the system capacity on input flow.
normal.csv //check normal proceudre of the queueing system with 10 client as example.
queueTooLong.csv //check how the server deal with the queue too long situation.
```



## Standard Output
1. Output demonstrate the number of msg you receive and send for each type.
![alt tag](https://raw.githubusercontent.com/MaureenZOU/COMP4007_testPhase/master/out1.png)

<br />
<br />

2. Output for the client behavior in time order under excel report. 
![alt tag](https://raw.githubusercontent.com/MaureenZOU/COMP4007_testPhase/master/out2.png)
