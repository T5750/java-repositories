# Secure Hash

>Please remember that once this password hash is generated and stored in the database, you can not convert it back to the original password.

## Simple password security using MD5 algorithm
The [MD5 Message-Digest Algorithm](https://en.wikipedia.org/wiki/MD5) is a widely used [cryptographic hash function](https://en.wikipedia.org/wiki/Cryptographic_hash_function) that produces a 128-bit (16-byte) hash value. It’s very simple and straight forward; the **basic idea is to map data sets of variable length to data sets of a fixed length**.

- `MD5SaltMD5Simple`

It’s main advantages are that it is fast, and easy to implement. But it also means that it is susceptible to [brute-force](https://en.wikipedia.org/wiki/Brute-force_attack) and [dictionary attacks](https://en.wikipedia.org/wiki/Dictionary_attack).

## Making MD5 more secure using salt
>Wikipedia defines salt as **random data that are used as an additional input to a one-way function that hashes a password or pass-phrase**. In more simple words, salt is some randomly generated text, which is appended to the password before obtaining hash.

**Important**: We always need to use a [SecureRandom](https://docs.oracle.com/javase/6/docs/api/java/security/SecureRandom.html) to create good salts, and in Java, the `SecureRandom` class supports the `SHA1PRNG` pseudo random number generator algorithm, and we can take advantage of it.

`SHA1PRNG` algorithm is used as cryptographically strong pseudo-random number generator based on the `SHA-1` message digest algorithm. Note that if a seed is not provided, it will generate a **seed** from a true random number generator (**TRNG**).

- `MD5Salt`

**Important**: Please note that now you have to **store this salt value for every password you hash**. Because when user login back in system, you must use only originally generated salt to again create the hash to match with stored hash. If a different salt is used (we are generating random salt), then generated hash will be different.

## Medium password security using SHA algorithms
The [SHA (Secure Hash Algorithm)](https://en.wikipedia.org/wiki/Secure_Hash_Algorithm) is a family of cryptographic hash functions. It is very similar to MD5 except it **generates more strong hashes**.

Java has 4 implementations of SHA algorithm. They generate the following length hashes in comparison to MD5 (128-bit hash):
- `SHA-1` (Simplest one – 160 bits Hash)
- `SHA-256` (Stronger than `SHA-1` – 256 bits Hash)
- `SHA-384` (Stronger than `SHA-256` – 384 bits Hash)
- `SHA-512` (Stronger than `SHA-384` – 512 bits Hash)

A longer hash is more difficult to break. That’s the core idea.

To get any implementation of algorithm, pass it as parameter to `MessageDigest`. e.g.
```
MessageDigest md = MessageDigest.getInstance("SHA-1");
//OR
MessageDigest md = MessageDigest.getInstance("SHA-256");
```
`SHATest`

## Advanced password security using PBKDF2WithHmacSHA1 algorithm
This feature is essentially implemented using some **CPU intensive algorithms** such as **PBKDF2**, [Bcrypt](https://en.wikipedia.org/wiki/Bcrypt) or [Scrypt](https://en.wikipedia.org/wiki/Scrypt). These algorithms take a work factor (also known as security factor) or iteration count as an argument. This value determines how slow the hash function will be. When computers become faster next year we can increase the work factor to balance it out.

Java has implementation of “[PBKDF2](https://en.wikipedia.org/wiki/PBKDF2)” algorithm as “[PBKDF2WithHmacSHA1](https://docs.oracle.com/javase/7/docs/technotes/guides/security/StandardNames.html#SecretKeyFactory)“.
- `PBKDF2WithHmacSHA1Test`

## More Secure password hash using bcrypt and scrypt algorithms
### Java bcrypt with salt
- `BcryptTest`

### Java scrypt with salt
- `ScryptTest`

## Final Notes
1. Storing the text password with hashing is most dangerous thing for application security today.
2. MD5 provides basic hashing for generating secure password hash. Adding salt make it further stronger.
3. MD5 generates 128 bit hash. To make ti more secure, use SHA algorithm which generate hashes from 160-bit to 512-bit long. 512-bit is strongest.
4. Even SHA hashed secure passwords are able to be cracked with today’s fast hardwares. To beat that, you will need algorithms which can make the brute force attacks slower and minimize the impact. Such algorithms are PBKDF2, BCrypt and SCrypt.
5. Please take a well considered thought before applying appropriate security algorithm.

## References
- [Java Secure Hashing – MD5, SHA256, SHA512, PBKDF2, BCrypt, SCrypt](https://howtodoinjava.com/security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/)