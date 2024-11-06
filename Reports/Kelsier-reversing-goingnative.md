# Solution

## Description of the problem

The goingnative challenge requires to inspect the contents of a builded apk to obtain the flag.

## Solution

First of all, I debugged the apk in Android Studio. Inside the app I obtained the resources, the manifest and the .dex files. In these files, I can see the bytecode of the classes in Smali. To facilitate me the work, I used an app (jadx) to translate the Smali code to Java code. There I found the FlagChecker class. In that class there is the method checkFlag(Context ctx, String flag). From there I learned that the core of the flag has two parts divided by "-". Also I saw that the second part is formed by six digts. These parts are passed to a function helloFromTheOtherSide(). This function is implemented in a library. This library is a .so file written in native code and its contents cannot be obtained through this tool. To access I unpacked the contents of the app and in order to read the library I had to use Ghidra. I created a project in Ghidra and I imported the .so file. There I accessed the contents of native code and I was able to read them trough the decompiler. I found the function helloFromTheOtherSide() and I saw how the first part of the core of the flag is compared by slices with multiple conditions (length = 12, formed by different words divided by low bar, starting with n, finished by o...) and how the 6 digits number is given in hexadecimal. Taking all of this into account I formed the flag: MOBISEC{native_is_so-031337}.
## Optional Feedback

I think this challenge is good and fun for learning how to hide a flag inside native code. Also, I learnt how to use Ghidra and access the contents of native code.