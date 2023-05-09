# Bintang Fajarianto
# NIM 13519138

# March 5, 2023

import random
from abc import ABC, abstractmethod
from typing import List

from bystar import Bystar
from constant import *
from utils import string_to_bits, bits_to_string, string_to_hex, hex_to_string
# remove dot to run this file

# region OperationMode
class OperationMode(ABC):
  def __init__(self, key: str) -> None:
    super().__init__()
    self.bystar = Bystar()
    self.internal_key = self.bystar.generate_internal_key(key)
  
  def execute_per_block(self, text: str):
    bit_rep = string_to_bits(text)

    for idx in range(0, len(bit_rep), BLOCK_SIZE):
      block = bit_rep[idx:idx + BLOCK_SIZE]
      if len(block) < BLOCK_SIZE:
        block += [0] * (BLOCK_SIZE - len(block))
      yield block
  
  @abstractmethod
  def encrypt(self, text: str, toHex: bool = False) -> str:
    pass

  @abstractmethod
  def decrypt(self, text: str, fromHex: bool = False) -> str:
    pass
# endregion OperationMode


# region ECB Mode
class ECB(OperationMode):
  def encrypt(self, plain_text: str, toHex: bool = False) -> str:
    result = ""
    for block in super().execute_per_block(plain_text):
      result += self.bystar.encrypt(block, self.internal_key, toHex)

    return result
  
  def decrypt(self, cipher_text: str, fromHex: bool = False) -> str:
    cipher_text = hex_to_string(cipher_text) if (fromHex) else cipher_text

    result = ""
    for block in super().execute_per_block(cipher_text):
      result += self.bystar.decrypt(block, self.internal_key)

    return result
# endregion ECB Mode

# region CBC Mode
class CBC(OperationMode):
  def __init__(self, key: str) -> None:
    super().__init__(key)
    self.c_val = self.generate_iv(BLOCK_SIZE)

  def generate_iv(self, size: int) -> List[int]:
    return [random.randint(0, 1) for _ in range(size)]
  
  def encrypt(self, plain_text: str, toHex: bool = False) -> str:
    result = bits_to_string(self.c_val)
    for block in super().execute_per_block(plain_text):
      self.c_val = string_to_bits(
        self.bystar.encrypt([self.c_val[i] ^ block[i] for i in range(BLOCK_SIZE)], self.internal_key)
      )
      result += bits_to_string(self.c_val)

    return string_to_hex(result) if toHex else result
  
  def decrypt(self, cipher_text: str, fromHex: bool = False) -> str:
    cipher_text = hex_to_string(cipher_text) if (fromHex) else cipher_text

    result = ""
    first = True
    for block in super().execute_per_block(cipher_text):
      if first:
        self.c_val = block
        first = False
        continue

      c_decrypted = string_to_bits(self.bystar.decrypt(block, self.internal_key))
      result += bits_to_string([self.c_val[i] ^ c_decrypted[i] for i in range(BLOCK_SIZE)])
      self.c_val = block

    return result
# endregion CBC Mode

# region Counter Mode
class Counter(OperationMode):
  def __init__(self, key: str) -> None:
    super().__init__(key)
    self.format = '{:0' + str(BLOCK_SIZE) + 'b}'
    self.counter = 0

  def generate_counter(self, size: int) -> List[int]:
    random.seed(size)
    self.counter = [int(bit) for bit in f'{self.format}'.format(random.randint(0, 2**size - 1))]

  def increment_counter(self, c_counter: List[int]) -> List[int]:
    num = int('0b' + ''.join(map(str, c_counter)), 2)
    num = (num + 1) % (2**BLOCK_SIZE - 1)
    return [int(bit) for bit in f'{self.format}'.format(num)]
  
  def encrypt(self, plain_text: str, toHex: bool = False) -> str:
    self.generate_counter(BLOCK_SIZE)

    result = ""
    for block in super().execute_per_block(plain_text):
      c_encrypted = string_to_bits(
        self.bystar.encrypt(self.counter, self.internal_key)
      )
      result += bits_to_string([c_encrypted[i] ^ block[i] for i in range(BLOCK_SIZE)])
      self.counter = self.increment_counter(self.counter)

    return string_to_hex(result) if toHex else result
  
  def decrypt(self, cipher_text: str, fromHex: bool = False) -> str:
    cipher_text = hex_to_string(cipher_text) if (fromHex) else cipher_text
    return self.encrypt(cipher_text)
# endregion Counter Mode

# Testing ECB Mode
'''
cipher = ECB(KEY)
encrypted = cipher.encrypt(PLAIN_TEXT, True)
print(encrypted)
decrypted = cipher.decrypt(encrypted, True)
print(decrypted)
'''

# Testing CBC Mode
'''
cipher = CBC(KEY)
encrypted = cipher.encrypt(PLAIN_TEXT)
print(encrypted)
decrypted = cipher.decrypt(encrypted)
print(decrypted)
'''

# Testing Counter Mode
'''
cipher = Counter(KEY)
encrypted = cipher.encrypt(PLAIN_TEXT)
print(encrypted)
decrypted = cipher.decrypt(encrypted)
print(decrypted)
'''
