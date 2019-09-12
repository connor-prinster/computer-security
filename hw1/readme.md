# Running the Program

### Instructions:
This program requires *at most* three arguments
1. the composite key
2. the plaintext or the ciphertext
3. (optional) 'e' or 'd' for encryption or decryption process
    * if left blank, the program will both encrypt and decrypt the plaintext
    
### Examples:

#### Generic Commands:
To encrypt plaintext, run the program as such ` password <composite key> <plaintext> <e, d, neither>`

#### Example of running BOTH encryption and decryption
` ./password 1422555515 "attack at dawn"` will result in
 
 ```
 No specific instruction given. Will encrypt and decrypt
 Encrypted: 572225253539572544255724
 Decrypted: ATTACKATDAWN 
 ```

#### Example of running ONLY an encryption command
` ./password 1422555515 "attack at dawn" e` will result in 
```
Encrypted: 572225253539572544255724
```

#### Example of running ONLY a decryption command
`./password 1422555515 572225253539572544255724 d` will result in 
```
Decrypted: ATTACKATDAWN
```