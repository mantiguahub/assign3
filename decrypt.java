//name: michael antigua
//cmsc 203
Repeat key to match length of encryptedText
For each character in encryptedText:
    shift = (encryptedText[i] - key[i] + RANGE) % RANGE + LOWER_RANGE
Build result string from shifted chars
Return result
