# Solution

## Description of the problem

The pincode challenge requires to inspect the contents of a builded apk to obtain the flag.

## Solution

First of all, I debugged the apk in Android Studio. Inside the app I obtained the resources, the manifest and the .dex files. In these files, I can see the bytecode of the classes in Smali. To facilitate me the work, I used an app (jadx) to translate the Smali code to Java code. There I found the MainActivity. 
In that class there is a method getFlag(String pin) that obtains the contents from the URL "https://challs.reyammer.io/pincode/" + pin. If the pin is correct we receive "ERROR: PIN is wrong", otherwise, we receive the flag. To obtain the correct pin I saw the class PinChecker. In that class there is the method checkPin(Context ctx, String pin).
This method hashes the pin using the MD5 algorithm 10,000 times and converts the final result into a string. There is only a pin that has as result of these operations "d04988522ddfed3133cc24fb6924eae9", if the result is that, the method returns true, this means the pin is correct and I can obtain the flag.
Taking all of this into account, I decided to create a python script that has the same functionality as the checkPin method. Once I have the script I obtain the correct pin using a brute force method (trying all possible combinations). 
In order to make it faster I created two scripts, one that goes from 000000 to 999999 and other that goes from 999999 to 000000 and I executed both at the same time. When I obtained the correct pin, I used a regular browser to access to "https://challs.reyammer.io/pincode/703958" and obtain the flag.

The python script I created is: 

import hashlib

for i in range(1000000):
    padded_number = str(i).zfill(6)
    print(padded_number)
    pin_bytes = padded_number.encode()
    for i in range(25):
        for j in range(400):
         md = hashlib.md5()
         md.update(pin_bytes)
         pin_bytes = md.digest()
    hex_pin_bytes = pin_bytes.hex()
    if(hex_pin_bytes == "d04988522ddfed3133cc24fb6924eae9"):
       print("Solution: " + padded_number)
       break

## Optional Feedback

I think this challenge is good and fun for learning how to exploit local checks in apps. Also I experimented how much time it takes using brute force methods.