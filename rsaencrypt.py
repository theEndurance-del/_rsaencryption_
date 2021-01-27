#! /usr/bin/env python3

import random
import base64

#function to check for integer
def isInteger(num):
    return (num % 1 ==0)

#function to generate a random integer
def primegen():
    prime = 0
    while (True):
        prime = random.randint(1, 1025)
        count = 0
        for x in range (1, prime + 1):
            modfactor = prime % x
            if(modfactor == 0):
                count = count + 1
            
        if (count == 2):
            break
    
    return prime

#function to check whether to number are co-prime or not
def gcd(a, b):
    count = 0
    for x in range(1, b):
        mod1 = a % x
        mod2 = b % x
        if mod1 == 0 and mod2 == 0:
            count = count + 1
    
    return count == 1

#function to generate decryption component
def privateKeyComponent(primeprod, lambda_n, exponent):
    var = 0
    x = 1
    for x in range(1,primeprod):
        var = (1 + (x * lambda_n)) / exponent
        if isInteger(var):
            var = int(var)
            break
    
    return var

#function to encrypt and decrypt the string
def encrypt(plain):
    prime1 = primegen()
    prime2 = primegen()

    n = prime1 * prime2
    lambda_n = (prime1 - 1) * (prime2 - 1)

    exponent = 0
    while(True):
        exponent = random.randint(0,lambda_n)
        if gcd(exponent, lambda_n):
            break

    decrypt = privateKeyComponent(n, lambda_n, exponent)

    print ("Public Key:  ({}, {})".format(exponent, n))
    print ("Private Key: ({}, {})".format(decrypt,n))

    #encrpting to RSA to base64
    rsa_encrypt_ascii = [ ((ord(x) ** exponent) % n) for x in plain ]
    rsa_encrypt = ''.join(chr(i) for i in rsa_encrypt_ascii)
    rsa_encrypt_bytes = rsa_encrypt.encode('utf8')
    rsa_encrypt_bytesB64 = base64.b64encode(rsa_encrypt_bytes)
    rsa_encrypt_base64 = rsa_encrypt_bytesB64.decode('utf8')

    print ("encrypted: {}".format(rsa_encrypt_base64))

    #edcrypting from base64 from RSA
    rsa_decrypt_bytes = rsa_encrypt_base64.encode('utf8')
    rsa_decrypt_bytesB64 = base64.b64decode(rsa_decrypt_bytes)
    rsa_decrypt_base64 = rsa_decrypt_bytesB64.decode('utf8')
    rsa_decrypt_ascii = [ ((ord(x) ** decrypt) % n) for x in rsa_decrypt_base64 ]
    rsa_decrypt = ''.join(chr(i) for i in rsa_decrypt_ascii)

    print ("decrypted: ",rsa_decrypt)

#driver segment
String = input("Enter String to be encrypted: ")
encrypt(String)
