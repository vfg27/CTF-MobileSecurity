# Solution

## Description of the problem

The filehasher challenge requires to develop an app that receives a intent with a URI to a .dat file. We want to read that file, make it hash SHA-256 and return the result to the server with another intent.

## Solution

I've solved the challenge by developing an app that has an Activity that receives the intent and obtain the Uri from it. With the Uri obatined I call the function calcHash. This function gets the Uri and obtain the string with the path to the file. After that it creates a File object from it and with the File object and a FileInputStream I obtain a byte array with the data from the file. Then, using a MessageDigest, I obtain the hash SHA-256 from the bytes and transform the data to hexadecimal. Finally, I send a Intent with the hash obtained and the key "hash".

To be able to make the challenge I had to give READ_EXTERNAL_STORAGE permission to the app.

## Optional Feedback

I think this challenge is good for learning how to access files from the SD card and use them as we want.