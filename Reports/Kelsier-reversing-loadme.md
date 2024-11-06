# Solution

## Description of the problem

The loadme challenge requires to inspect the contents of a builded apk to obtain the flag.

## Solution

First of all, I debugged the apk in Android Studio. Inside the app I obtained the resources, the manifest and the .dex files. In these files, I can see the bytecode of the classes in Smali. To facilitate me the work, I used an app (jadx) to translate the Smali code to Java code. There I found the DoStuff class. In that class there are multiple functions but the interesting one is start(Context ctx, String flag). Before explaining this function there are multiple Strings hidden in funtions, these functions with their results are:

    gu(): https://challs.reyammer.io/loadme/stage1.apk
    gf(): test.dex
    gc(): com.mobisec.stage1.LoadImage
    gm(): load

The start function calls da(dc(gu())). The dc() function gets the contents from the URL passed as parameter (this URL gives us a .apk file that cannot be opened because it is encrypted) and creates a file test.dex in the code_cache folder of the data of the app in the phone. The path of this file is passed as a parameter to the da() function. This function obtains the bytes from the file, decrypts them and write them inside a file in the same path. To perform this actions and observe the result I copied these functionalities inside a personal Java application. To make it work I created a variable private String pack = "com.mobisec.dexclassloader"; because the package of my app is not the same as the one in the original app (the package is used to decrypt). Also I stated that the result of the da() function should be a file called defStage1.apk. To obtain the app from the files of the phone I had to change its permissions using the command adb. 
Once I had the apk decrypted I opened it using jadx. As seen in the gc() and gm() functions, there is a class called LoadImage inside that class a method called load (which is used in the DoStuff app). Firts of all, as I did with the DoStuff class, there are some hidden strings that gave me hints:

    getAssetsName(): logo.png
    getCodeName(): logo.dex
    generateMethod(): check
    generateClass(): com.mobisec.stage2.Check

This class is very similar to the DoStuff class. In this case the xorKey is the bytes of the String "weneedtogodeeper" and the apk file is even more hidden. The .apk file we want to obtain has been encrypted and renamed as logo.png. This file can be found in the assets folder of loadme.apk after extracting its contents with 7-zip. After executing the code, with the logo.png inside the assets folder in my app files, I obtained the defStage2.apk (that is the output name I gave it). 
Finally I inspected its contents with jadx and inside the Check class there is the funtion check that compares the flag passed as parameter with the String "MOBISEC{dynamic_code_loading_can_make_everything_tricky_eh?}" (the flag).

## Optional Feedback
I think this challenge is good for learning how to hide a flag involving different .apk files and even change the .apk format to complicate it even more.
