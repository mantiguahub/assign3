//name:michael antigua
//cmsc 203
If not isStringInBounds(plainText):
    return "The selected string is not in bounds, Try again."

Create 8x8 matrix using key (fill remaining slots with other printable ASCII chars, skipping duplicates)
Split plainText into digraphs (pairs). If odd length, add filler character (e.g., 'X')

For each digraph:
    Find positions in matrix
    If same row: replace with chars to right (wrap around)
    If same column: replace with chars below (wrap around)
    Else: replace with chars in same row, other’s column
Build result string
Return result
