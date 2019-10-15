# Hash Ketchum (copyrighted):

## Method:
1. Split into 224 bit blocks. Pad if necessary
2. divide those 224 blocks into seven 32-bit blocks
3. Mess with blocks like so:
    * block 1 ^ block 3
    * block 2 ^ block 7
    * block 3 ^ block 5
    * block 4 shift left twice
    * block 5 ^ block 7
    * block 6 is left alone
4. blocks are then placed in the following order: 7, 1, 2, 3, 4, 5, 6
5. repeat step 3-4 28 times.
6. return the monstrosity

## Explanaition:
Cryptographic hash functions are computationally efficient, deterministic, pre-image resistant,
and collision resistant. Our hash function, Hash Ketchum, meets all of these requirements. 

First, Hash Ketchum is computationally efficient. Let n be the length of the input string.
The string is split into 224 bit blocks, and a series of mathematical operations,
all of which can be calculated in linear time, are performed on the string 28 times.
The splitting of the string runs in O(n) time. The mathematical functions run in O(n) time.
Thus, Hash Ketchum runs in n + n = O(n) time. Polynomial run time is computationally efficient.

Second, Hash Ketchum is deterministic. The algorithm for Hash Ketchum produces the same output
for a given input every time. The only input given to Hash Ketchum is the provided string,
and since the steps are always the same, the output is output is always determined bu the input.

Third, Hash Ketchum is pre-image resistant.

Last, Hash Ketchum is collision resistant.