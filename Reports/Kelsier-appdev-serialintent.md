# Solution

## Description of the problem

The serialintent challenge requires to develop an app that sends an Intent to launch an Activity in another app (com.mobisec.serialintent). This activity sends us the flag shuffled and in an object called FlagContainer. This class is in the other app and it has a method called getFlag() to obtain the flag.

## Solution

I've solved the challenge by developing an app that sends an Intent to the other app in the mainActivity in order to launch the Activity that sends me the FlagContainer. The response with the FlagContainer is received also in the mainActivity thanks to a ActivityResultLauncher<Intent>. This object is inside a Bundle with key "flag" in an Intent. Once I have the Bundle I need to load the path to the class FlagContainer from the other app in order to call the method getFlag. To do that I use a PathClassLoader to load the sourceDir from the other app. After that I load the class FlagContainer and I set that the FlagContainer class as the Class Loader of the Bundle. Finally I extract the method from the class and I make it accesible (because it is private) and I invoke it, obtaining the flag and printing it.

## Optional Feedback

I think this challenge is good for learning how to access the contents of other apps (classes, methods...) in the same device. However, it would have been useful to indicate that the data of the other application had to be accessed.