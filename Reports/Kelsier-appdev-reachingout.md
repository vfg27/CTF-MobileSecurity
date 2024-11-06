# Solution

## Description of the problem

The reachingout challenge requires to develop an app that connects to a HTTP server to get some instructions to make a post
for solving a mathematical challenge in order to obtain the flag.

## Solution

I've solved the challenge by developing an app that makes three steps/threads on the main Activity on the onCreate function:

1) getRequest: makes an http GET request to the server and return the response from the server using a BufferedReader. The response is the URL with the location of the flag.
2) getRequestFlag: the same as before but this time to the page /flag. I obatined the next message:"How much is 3 + 6?" with the html of the page.
3) postRequestFlag: in this case I make a POST request using a OutputStream. The data sent to the server is a byte array based on the url parameters required with their values. After making the post I create a BufferedReader to read the response from the server with the flag.

To be able to make the challenge I had to give Internet permissions to the app and I had to declare usesCleartextTraffic to true because the server uses http instead of https.

## Optional Feedback

I think this challenge is good for learning how to connect our apps to servers and to learn the use of threads.