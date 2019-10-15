# Hash Ketchum (copyrighted):

## Circuit Diagram
Found in the **hw3/circuit_diagram.jpg** file

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
5. repeat step 3-4 64 times.
6. combine the blocks by adding them and performing a mod to get it back down to size
7. return the monstrosity

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

Third, Hash Ketchum is pre-image resistant. The returned hash is always the same length, so the length
of the input does not affect the hashed output. Changing one letter of the input creates an entirely
different output. For example, the string "pikachu" produces the hash "Og+S4XIO2k/ynG/ynIZ1Xt7kSPkIfzL61K+cvQ=="
and the nearly identical string "Pikachu" produces the hash "qdEtCFY/pTaPZupA0lldWieDmGKnmg7AdgieMQ==", which is
completely different.

Last, Hash Ketchum is collision resistant. The hash function evenly distributes the different values across
the possible hash values. This size constraint ensures that the hash is always the same length, regardless
of the input, and limits the possible values of the hash. However, since there 2^224 possiblities for Hash
Ketchum, collisions will be few and far between.