# Solution

## Description of the problem

The blockchain challenge requires to inspect the contents of a builded apk to obtain the flag based on an unknow key value.

## Solution

First of all, I debugged the apk in Android Studio. Inside the app I obtained the resources, the manifest and the .dex files. In these files, I can see the bytecode of the classes in Smali. To facilitate me the work, I used an app (jadx) to translate the Smali code to Java code. There I found the FlagChecker class. In that class there is the method checkFlag(String keyStr, String flagStr) where the attributes are a key and the flag. From the key, the first, the last and the middle byte are taken and hashed with a MD5 algorithm. Then, the bytes of the flag are encrypted with the AES algorithm ten times and in each iteration the encryption key is hashed. Finally, the bytes are transformed to hexadeciamal and they are compared with "0eef68c5ef95b67428c178f045e6fc8389b36a67bbbd800148f7c285f938a24e696ee2925e12ecf7c11f35a345a2a142639fe87ab2dd7530b29db87ca71ffda2af558131d7da615b6966fb0360d5823b79c26608772580bf14558e6b7500183ed7dfd41dbb5686ea92111667fd1eff9cec8dc29f0cfe01e092607da9f7c2602f5463a361ce5c83922cb6c3f5b872dcc088eb85df80503c92232bf03feed304d669ddd5ed1992a26674ecf2513ab25c20f95a5db49fdf6167fda3465a74e0418b2ea99eb2673d4c7e1ff7c4921c4e2d7b".
To obatin the flag, I decided to perform a brute force attack. In order to do that I created a method decrypt(byte[] in, byte[] key) in a Java class and then I performed the the opposite functionality to checkFlag(String keyStr, String flagStr). I didin´t had any information about the key, so, trying all possible key value would be burdensome. On the other hand, I knew that the flag is encrypted with the hash of three bytes, this would be easier. I tried all possible three bytes values (2^24) I hashed them recursively ten times and stored their values in a List. After that, I just had to decrypt the bytes of the "comparision value" 10 times with those hashed keys to obtain the flag. After a while, I obtained MOBISEC{blockchain_failed_to_deliver_once_again}.

    public static byte[] decrypt(byte[] in, byte[] key) throws Exception {
        Key aesKey = new SecretKeySpec(key, "AES");
        Cipher decryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        decryptCipher.init(Cipher.DECRYPT_MODE, aesKey);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, decryptCipher);
        cipherOutputStream.write(in);
        cipherOutputStream.flush();
        cipherOutputStream.close();
        byte[] out = outputStream.toByteArray();
        return out;
    }

## Optional Feedback

I think this challenge is good for learning how to hide a flag using encryption methods. I´m still not sure which three bytes values match the result because I tried again the values that gave me the flag and I didn´t receive the flag. Also, It took me a while to understand that the decrypt method will return an Exception if you try to decrypt with the wrong flag, resulting in an application crash.