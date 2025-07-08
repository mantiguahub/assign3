//Name:Michael Antigua
//cmsc 203
If not isStringInBounds(plainText):
    return "The selected string is not in bounds, Try again."

Repeat key to match length of plainText
For each character in plainText:
    shift = (plainText[i] + key[i] - 2*LOWER_RANGE) % RANGE + LOWER_RANGE
Build result string from shifted chars
Return result
