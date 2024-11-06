# Solution

## Description of the problem

The whereareyou challenge requires to develop an app that starts a Service after receiving the intent with action com.mobisec.intent.action.GIMMELOCATION. The service has to give the current location when it receives the intent and it has to keep sending the current location after that.

## Solution

I've solved the challenge by developing an app that has a Service that is activated when it receives the intent mentioned, this is stated in the manifest. In the onStartCommand it creates a fusedLocationClient, a locationRequest and a locationCallback with the function that gets the location and send it (onLocationResult). After defining those variables it calls to startLocationUpdates. This function checks if the app has the corresponding permissions and starts making the location requests. When the service is destroyed, the location updates stops.

To be able to make the challenge I had to give ACCESS_FINE_LOCATION and ACCESS_COARSE_LOCATION permissions to the app. Also I had to add the "com.google.android.gms:play-services-location:21.0.1" dependency to the Gradle in order to be able to use fusedLocationClient, locationRequest and locationCallback.

## Optional Feedback

I think this challenge is good for learning how to obatain the current location of the device.