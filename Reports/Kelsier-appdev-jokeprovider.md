# Solution

## Description of the problem

The jokeprovider challenge requires to develop an app that access the Content Provider by another app. The target of the app is to obtain all jokes authored by "reyammer".

## Solution

I've solved the challenge by developing an app that when the MainActivity is created it creates a Cursor to the Content Provider passing the query where I specify that I want the jokes created by reyammer. After that I start extracting each joke (part of the flag) until there are no more jokes and I concatenate them to an empty String. Finally, I Log the complete String.

## Optional Feedback

I think this challenge is good for learning how to obatain data from a Content Provider