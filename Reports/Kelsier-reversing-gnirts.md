# Solution

## Description of the problem

The gnirts challenge requires to inspect the contents of a builded apk to obtain the flag.

## Solution

First of all, I debugged the apk in Android Studio. Inside the app I obtained the resources, the manifest and the .dex files. In these files, I can see the bytecode of the classes in Smali. To facilitate me the work, I used an app (jadx) to translate the Smali code to Java code. There I found the FlagChecker class. In that class there is the method checkFlag(Context ctx, String flag). From there I learned that the core of the flag is of length 32, that is formed by 5 blocks divided by the character "-" (the result of the foo() function). The first block is formed by 5 lower case letters, the second block is formed by 7 numbers, the third block is formed by 5 upper case letters, the fourth block is formed by 4 characters that can be lower or upper case letters or numbers and the last block is formed by another 7 numbers. I obtained the hash of each block and the hash of the whole flag. The hash of the blocks are done with MD5 hash and the hash of the flag is done with SHA-256, I learned this using the function gs(). With this information I performed a brute froce attack on each block to obtain the whole flag. In order to perform this attacks I made five python scripts (that I ran at the same time to speed up the process), one for each block, adapting the code to the chracteristics of the block. Finally, I tried to compute the SHA-256 of the whole flag to check if it is correct. The flag obtained is: MOBISEC{peppa-9876543-BAAAM-A1z9-3133337}.

Example of one of the scripts created:

import hashlib
def to_letters(n):
    alphabet = 'abcdefghijklmnopqrstuvwxyz'
    result = ''
    while n > 0:
        n, remainder = divmod(n - 1, 26)
        result = alphabet[remainder] + result
    return result.zfill(5)

for i in range((27*26*26*26)+27*27-26, 26 ** 5):
    word = to_letters(i)
    block_bytes = word.encode()
    md = hashlib.md5()
    md.update(block_bytes)
    block_bytes = md.digest()
    hex_block_bytes = block_bytes.hex()
    print(word)
    if(hex_block_bytes=="6e9a4d130a9b316e9201238844dd5124"):
       print("Solution: " + word)
       break

## Optional Feedback

I think this challenge is good and fun for learning how to hide a flag inside a code, hashing each part of it. Also, I learnt how to brute force when there are strings in the flag and strings with numbers and letters.