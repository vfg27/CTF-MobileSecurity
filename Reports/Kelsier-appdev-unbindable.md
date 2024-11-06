# Solution

## Description of the problem

The unbindable challenge requires to develop an app that connects to another app and start a service through a IBinder to obtain the flag. The manifest of the external app and the implemetation of the Service are included as data.

## Solution

I've solved the challenge by developing an app that has a MainActivity with the next elements:

1) ServiceConnection mConnection: In this variable is defined the methods that the app executes when is binded and unbinded to the Service. When is binded it creates a message with the code to register in the other app and send it with a Messenger. After that, it sends another message to retreive the flag (to tell the other app to send us the flag). To receive the message with the flag I had to create other Messenger with a IncomingHandler and I had to redirect the reply to it. When is unbinded it just prints a message.
2) IncomingHandler: This class handles the messages received. When it receives a message with the flag it extracts it from the Bundle and prints it.

When the mainActivity is created it genereates an Intent to the other app and binds to the Service in order to execute all the functionalities mentioned before and obatin the flag.

## Optional Feedback

I think this challenge is good for learning how to bind to Services in other apps and how to handle Messages.