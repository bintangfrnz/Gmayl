# Bintang Fajarianto
# NIM 13519138

# April 11, 2023

import random
from typing import Tuple
from point import *

# NOTE: Chaquo Python doesn't support TypeAlias

'''
ECC & Secp256k1 (Elliptic Curve Parameter)
reference:
- https://cryptobook.nakov.com/asymmetric-key-ciphers/elliptic-curve-cryptography-ecc#named-curves-example
- https://en.bitcoin.it/wiki/Secp256k1
'''

class ECC:
    def __init__(self) -> None:
        # The Curve
        # E: y² = x³ + ax + b

        # a = 0x0000000000000000000000000000000000000000000000000000000000000000
        # b = 0x0000000000000000000000000000000000000000000000000000000000000007
        self.a = 0
        self.b = 7

        # p = 0xFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFC2F
        self.p = (2 ** 256) - (2 ** 32) - (2 ** 9) - (2 ** 8) - (2 ** 7) - (2 ** 6) - (2 ** 4) - 1

        # G = (0x79BE667EF9DCBBAC55A06295CE870B07029BFCDB2DCE28D959F2815B16F81798, 0x483ada7726a3c4655da4fbfc0e1108a8fd17b448a68554199c47d08ffb10d4b8)
        self.G = (55066263022277343669578718895168534326250603453777594175500187360389116729240, 32670510020758816978083085130507043184471273380659243275938904335757337482424)

        # n = 0xFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364141
        self.n = 115792089237316195423570985008687907852837564279074904382605163141518161494337

    def inverse(self, a: int, m: int) -> int:
        copy_m, a = m, a % m

        x, y = 0, 1
        while a > 1:
            y, x = x - (m // a) * y, y
            a, m = m % a, a
        return y % copy_m

    def add(self, p1: Tuple[int, int], p2: Tuple[int, int]) -> Tuple[int, int]:
        # Addition with identity point
        if is_identity_point(p1):
            return p2
        if is_identity_point(p2):
            return p1

        # Calculate gradient m
        if p1 == p2:
            if (p1[1] == 0):
                return get_identity_point()

            # m = (3x₁² + a) / 2y₁
            m = ((3 * (p1[0] ** 2) + self.a) * self.inverse(2 * p1[1], self.p)) % self.p
        else:
            if (p1[0] - p2[0]) == 0:
                return get_identity_point()

            # m = (y₁ - y₂) / (x₁ - x₂)
            m = ((p1[1] - p2[1]) * self.inverse(p1[0] - p2[0], self.p)) % self.p

        # x = m² - x₁ - x₂
        x = (m ** 2 - p1[0] - p2[0]) % self.p
        # y = m * (x₁ - x) - y₁
        y = (m * (p1[0] - x) - p1[1]) % self.p

        return (x, y)

    def multiply(self, k: int, point: Tuple[int, int]) -> Tuple[int, int]:
        c_point = (point[0], point[1])
        binary = bin(k)[2:]

        for char in binary[1:]:
            c_point = self.add(c_point, c_point)
            if char == '1':
                c_point = self.add(c_point, point)
        return c_point

class ECDSA:
    def __init__(self, curve: ECC) -> None:
        self.curve = curve
        self.prefix = '03'

    def remove_prefix(self, text: str) -> str:
        return text[2:]

    def generate_key_pair(self) -> Tuple[str, str]:
        private_key = self.generate_private_key()
        return (private_key, self.generate_public_key(private_key))

    def generate_private_key(self) -> str:
        return self.prefix + hex(random.randint(1, self.curve.n - 1))[2:]

    def generate_public_key(self, private_key: str) -> str:
        pubkey_point = self.curve.multiply(int(self.remove_prefix(private_key), 16), self.curve.G)

        while (is_identity_point(pubkey_point) or (pubkey_point[0] % self.curve.n == 0)):
            private_key = self.generate_private_key()
            pubkey_point = self.curve.multiply(self.remove_prefix(private_key), self.curve.G)

        x = hex(pubkey_point[0])[2:].rjust(64, "0")
        y = hex(pubkey_point[1])[2:].rjust(64, "0")
        return self.prefix + x + y

    def sign(self, private_key: str, hash: str) -> Tuple[int, int]:
        while True:
            nonce = random.randint(1, self.curve.n - 1)
            r = self.curve.multiply(nonce, self.curve.G)[0] % self.curve.n
            if r == 0: continue

            s = (self.curve.inverse(nonce, self.curve.n) * (int(hash, 16) + int(self.remove_prefix(private_key), 16) * r)) % self.curve.n
            if s == 0: continue

            break

        return (r, s)

    def verify(self, public_key: str, hash: str, signature: Tuple[int, int]) -> bool:
        pk = self.remove_prefix(public_key)
        pk_point = (int(pk[:64], 16), int(pk[64:], 16))

        p1 = self.curve.multiply(self.curve.inverse(signature[1], self.curve.n) * (int(hash, 16)), self.curve.G)
        p2 = self.curve.multiply((self.curve.inverse(signature[1], self.curve.n) * signature[0]), pk_point)
        p3 = self.curve.add(p1, p2)

        return p3[0] == signature[0]

# Testing Generate Key Pair
'''
ecc = ECC()
ecdsa = ECDSA(ecc)
key = ecdsa.generate_key_pair()
print(key)
'''
