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