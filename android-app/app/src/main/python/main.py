# Bintang Fajarianto
# NIM 13519138

# April 10, 2023

from operation import ECB

def encrypt(plain_text: str, key: str) -> str:
    cipher = ECB(key)
    return cipher.encrypt(plain_text, True)

def decrypt(hex_text: str, key: str) -> str:
    cipher = ECB(key)
    return cipher.decrypt(hex_text, True)
