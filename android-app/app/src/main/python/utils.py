# Bintang Fajarianto
# NIM 13519138

# March 5, 2023

from re import findall
from typing import List

# region Char <-> Bit
def format_char_to_bit(ch: str) -> str:
  return bin(ord(ch)).lstrip('0b').rjust(8, '0')

def format_bit_to_char(bit: str) -> str:
  return chr(int('0b' + bit, 2))

def string_to_bits(text: str) -> List[int]:
  return list(
    map(int, "".join(map(format_char_to_bit, text)))
  )

def bits_to_string(bit_rep: List[int]) -> str:
  grouped_bits = findall('.' * 8, "".join(map(str, bit_rep)))
  return "".join(
    map(format_bit_to_char, grouped_bits)
  )
# endregion

# region String <-> Hex
def string_to_hex(text: str) -> str:
  return text.encode().hex().upper()
def hex_to_string(text: str) -> str:
  return bytes.fromhex(text).decode()
# endregion

# region Int <-> Bit
def format_int_to_bit(num: int) -> str:
  return bin(num).lstrip('0b').rjust(8, '0')

def format_bit_to_int(bit: str) -> int:
  return int('0b' + bit, 2)

def int_to_bits(arr: List[int]) -> List[int]:
  return list(
    map(int, "".join(map(format_int_to_bit, arr)))
  )

def bits_to_int(bit_rep: List[int]) -> List[int]:
  grouped_bits = findall('.' * 8, "".join(map(str, bit_rep)))
  return list(
    map(format_bit_to_int, grouped_bits)
  )
# endregion

# region shift operation
def shift_left(arr: List[int], distance: int) -> List[int]:
  return arr[distance:] + arr[:distance]

def shift_right(arr: List[int], distance: int) -> List[int]:
  return arr[-distance:] + arr[:-distance]
# endregion


# Testing char <-> bit
'''
char = "a"
print(char)
bit = format_char_to_bit(char)
print(bit)
char = format_bit_to_char(bit)
print(char)
'''

# Testing string <-> bit_rep
'''
word = "test"
print(word)
bits = string_to_bits(word)
print(bits)
word = bits_to_string(bits)
print(word)
'''

# Testing string <-> hex
'''
word = "test"
print(word)
hex_str = string_to_hex(word)
print(hex_str)
word = hex_to_string(hex_str)
print(word)
'''

# Testing int <-> bit
'''
num = 1
print(num)
bit = format_int_to_bit(num)
print(bit)
num = format_bit_to_int(bit)
print(num)
'''

# Testing list[int] <-> bit_rep
'''
arr = [23, 5, 201]
print(arr)
bits = int_to_bits(arr)
print(bits)
arr = bits_to_int(bits)
print(arr)
'''
