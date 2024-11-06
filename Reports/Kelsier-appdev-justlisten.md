# Solution

## Description of the problem

The justlisten challenge requires to develop an app that receives a intent with action "com.mobisec.intent.action.FLAG_ANNOUNCEMENT",
the flag we search is in the Intent's bundle, under the "flag" key.

## Solution

I've solved the challenge by developing an app where I create a receiver that filter the intents with the action mentioned before. To make this 
I created a class "MyBroadcastReceiver.java" that receives the Intent and extract the flag using the "flag" key. Finally this class makes a log with the flag. In order to make it work I created a receiver in the manifest and I initializated a MyBroadcastReceiver object with an IntentFilter in the MainActivity.onCreate function.

## Optional Feedback

I think this challenge is good for learning about intents and receivers. 
It would be useful to remind that the TAG of the Log must be "MOBISEC".