# Bintang Fajarianto
# NIM 13519138

# April 10, 2023

from operation import ECB
from sha3 import SHA3_256
from ecdsa import ECC, ECDSA
from typing import Tuple

# NOTE: Chaquo Python doesn't support TypeAlias

def encrypt(plain_text: str, key: str) -> str:
    cipher = ECB(key)
    return cipher.encrypt(plain_text, True)

def decrypt(hex_text: str, key: str) -> str:
    cipher = ECB(key)
    return cipher.decrypt(hex_text, True)

def generate_key_pair() -> Tuple[str, str]:
    ecc = ECC()
    ecdsa = ECDSA(ecc)
    return ecdsa.generate_key_pair()

def sign(private_key: str, message: str) -> Tuple[int, int]:
    sha3 = SHA3_256()
    hash = sha3.keccak(message)

    ecc = ECC()
    ecdsa = ECDSA(ecc)
    return ecdsa.sign(private_key, hash)

def verify(public_key: str, message: str, r: int, s: int) -> bool:
    sha3 = SHA3_256()
    hash = sha3.keccak(message)

    ecc = ECC()
    ecdsa = ECDSA(ecc)
    return ecdsa.verify(public_key, hash, (r, s))

# Testing Digital Sign
'''
message = "ABCDE"

private_key = '03bb8e52126c1e0db5266fa48e648bd2068dd56af13237cedbdb7a27c5ae0f8397'
public_key = '033c9d7eedc9d0394ebc552ceecd117693e368267edb74c5c9ff1d4cd9a3685eba52d78fa7488d2426f4e33fc3ac913abca10d32da49797829eeaf36cfb8052c67'

signature = sign(private_key, message)
print(f"signature = {signature}")
verify = verify(public_key, message, signature[0], signature[1])
print(f"verify = {verify}")
'''
