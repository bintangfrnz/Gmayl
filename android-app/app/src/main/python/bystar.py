# Bintang Fajarianto
# NIM 13519138

# March 5, 2023

import random

from constant import *
from utils import *
from typing import List
# remove dot to run this file

class Bystar:

  def format_key(self, bits: List[int]):
    if len(bits) == BLOCK_SIZE:
      return bits
    elif len(bits) < BLOCK_SIZE:
      return bits + [0] * (BLOCK_SIZE - len(bits))
    else:
      return bits[:BLOCK_SIZE]
    

  def generate_different_keys(self, bit_rep: List[int], iter: int) -> List[int]:
    # Generate new permutation table
    copy_table = PERMUTATION_TABLE.copy()
    random.seed(iter)
    random.shuffle(copy_table)

    # Permutation
    c_key = self.permutate(bit_rep, copy_table)

    # Convert to List[int] to execute XOR operation
    c_key = bits_to_int(c_key)

    # Split key to four parts
    quarter = [c_key[i:i+4] for i in range(0, len(c_key), 4)]

    # XOR 1st ^ 3rd and 2nd ^ 4th
    first_half = [quarter[0][i] ^ quarter[2][i] for i in range(len(quarter))]
    last_half = [quarter[1][i] ^ quarter[3][i] for i in range(len(quarter))]

    # Shift left the first half
    # Shift right the last half
    # by current iteration modulo total iteration
    c_key = shift_left(first_half, iter % ITERATION) + shift_right(last_half, iter % ITERATION)

    return int_to_bits(c_key)
    

  def generate_internal_key(self, key: str) -> List:
    bit_rep = self.format_key(string_to_bits(key))
    return [self.generate_different_keys(bit_rep, idx) for idx in range(ITERATION)]
  

  def encrypt(self, bit_rep: List[int], internal_keys: List[List[int]], toHex: bool = False) -> str:
    # Permutation
    c_cipher = self.permutate(bit_rep, IP_MATRIX)

    # Convert to List[int] to execute XOR operation
    c_cipher = bits_to_int(c_cipher)

    # Split current List[int]
    left, right = c_cipher[: len(c_cipher) // 2], c_cipher[len(c_cipher) // 2:]

    # Feitsel Network
    for idx in range(ITERATION)[::-1]:
      c_left = left.copy()
      temp = self.rotation(left, internal_keys[idx], idx)

      # XOR operation between right and rotation func result
      left = [temp[i] ^ right[i] for i in range(len(right))]
      right = c_left

    # Convert to bit rep to execute permutation
    c_cipher = int_to_bits(left + right)

    # Permutation
    c_cipher = self.permutate(c_cipher, INVERSE_IP_MATRIX)

    encrypted = bits_to_string(c_cipher)

    return string_to_hex(encrypted) if toHex else encrypted
  

  def decrypt(self, bit_rep: List[int], internal_keys: List[List[int]]) -> str:
    # Permutation
    c_plain = self.permutate(bit_rep, IP_MATRIX)

    # Convert to List[int] to execute XOR operation
    c_plain = bits_to_int(c_plain)

    # Split current List[int]
    left, right = c_plain[:len(c_plain) // 2], c_plain[len(c_plain) // 2:]

    # Feitsel Network
    for idx in range(ITERATION):
      c_right = right.copy()
      temp = self.rotation(right, internal_keys[idx], idx)

      # XOR operation between left and rotation func result
      right = [temp[i] ^ left[i] for i in range(len(left))]
      left = c_right

    # Convert to bit rep to execute permutation
    c_plain = int_to_bits(left + right)

    # Permutation
    c_plain = self.permutate(c_plain, INVERSE_IP_MATRIX)

    return bits_to_string(c_plain)
  

  def rotation(self, arr_int: List[int], key: List[int], iter: int) -> List[int]:
    # Convert to bit rep to execute shift operation
    c_text = int_to_bits(arr_int)

    # Split current bit rep
    length = len(c_text) // 2
    left, right = c_text[:length], c_text[length:]

    # Shift left the left side
    # Shift right the right side
    # by current iteration modulo length of left/right
    c_text = shift_left(left, iter % length) + shift_right(right, iter % length)

    # Convert to List[int] to execute XOR operation & subtitution
    c_text = bits_to_int(c_text)

    # XOR operation between c_text and key
    c_text = [key[i] ^ c_text[i] for i in range(len(c_text))]
    # Subtitution
    c_text = [S_BOX[num] for num in c_text]

    # Convert to bit rep to execute shift operation
    c_text = int_to_bits(c_text)

    # Split current bit rep
    length = len(c_text) // 2
    left, right = c_text[:length], c_text[length:]

    # Shift left the right side
    # Shift right the left side
    # by current iteration modulo length of left/right
    c_text = shift_left(right, iter % length) + shift_right(left, iter % length)

    return bits_to_int(c_text)
  

  def permutate(self, bit_rep: List[int], table: List[int]) -> List[int]:
    result = [0] * BLOCK_SIZE
    for idx, bit in enumerate(bit_rep):
      result[table[idx] - 1] = bit
    return result

# Testing Bystar Block Cipher
'''
cipher = Bystar()
encrypted = cipher.encrypt(string_to_bits("abcdefghijklmnop"), cipher.generate_internal_key(KEY))
print(encrypted)
decrypted = cipher.decrypt(string_to_bits(encrypted), cipher.generate_internal_key(KEY))
print(decrypted)
'''
