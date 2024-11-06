# Solution

## Description of the problem

The justask challenge requires to develop an app that send Intents to another app in order to go through the activities and obtain the four parts of the flag (one in each Activity)

## Solution

I've solved the challenge by developing an app that makes four intents on the main Activity on the onCreate function:

1) PartOne: To obatain this part I had to create an Intent, indicating it className (package of the other app plus the route to the Activity). After that I had to execute the Intent, obtainind and printing it result. In order to do that I created an ActivityResultLauncher that collects the result of the intent, extract its data and obtain the keys of the bundle. After that I Log all the values that are Strings with the keys i obtained. This is done transforming the Bundle keys into a Iterator.
2) PartTwo: The same as PartOne. However, in this case I had to pass the action "com.mobisec.intent.action.JUSTASK" to the Intent.
3) PartThree: Same as PartOne. However, in this case the flag part is in the second value of the intent.
4) PartFour: Same as before, but in this case the objects inside the Bundle is another Bundle. To extract the flag part I had to develop an auxiliary method "recBundle" that itearates over all Bundles and prints the Strings inside them.

## Optional Feedback

I think this challenge is good for learning how to launch Intents to another app and how Bundles work and how iterate over them.