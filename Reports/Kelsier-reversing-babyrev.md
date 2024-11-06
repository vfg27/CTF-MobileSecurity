# Solution

## Description of the problem

The babyrev challenge requires to inspect the contents of a builded apk to obtain the flag.

## Solution

First of all, I debugged the apk in Android Studio. Inside the app I obtained the resources, the manifest and the .dex files. In these files, I can see the bytecode of the classes in Smali. 
To facilitate me the work, I used an app (jadx) to translate the Smali code to Java code. There I found the class FlagChecker. With this class I obtined the flag. In that class I saw the text that gives the hints to obtain the flag:
	
	flag.startsWith("MOBISEC{") 
	&& new StringBuilder(flag).reverse().toString().charAt(0) == '}' 
	&& flag.length() == 35 && flag.toLowerCase().substring(8).startsWith("this_is_") 
	&& new StringBuilder(flag).reverse().toString().toLowerCase().substring(1).startsWith(ctx.getString(R.string.last_part)) 
	&& flag.charAt(17) == '_' 
	&& flag.charAt((int) (3 * Math.pow(2, 3))) == flag.charAt(((int) Math.pow(Math.pow(2.0d, 2.0d), 2.0d)) + 1) 
	&& bam(flag.toUpperCase().substring(3 * 2 * 3, (int) (Math.pow(5, 2) - 1.0d))).equals("ERNYYL") 
	&& flag.toLowerCase().charAt(16) == 'a' && flag.charAt(16) == flag.charAt(26) 
	&& flag.toUpperCase().charAt(25) == flag.toUpperCase().charAt(26) + 1

Once I obtained the "structure" of the flag and all its components with the methods inside the FlagChecker class, I saw that the flag had the characters alternated with uppers and lower cases. The key obtained was: MOBISEC{ThIs_iS_A_ReAlLy_bAsIc_rEv}

## Optional Feedback

I think this challenge is good and fun for learning how to obtain the Java code and resources from a builded apk. Also I learnt how much you can obfuscate a String in a code.