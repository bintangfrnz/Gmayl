# Bintang Fajarianto
# NIM 13519138

# April 10, 2023

'''
SHA3 (Keccak256)
reference: https://keccak.team/keccak_specs_summary.html
online test: https://emn178.github.io/online-tools/sha3_256.html
'''

from typing import List

class SHA3_256:
    def __init__(self) -> None:
        self.rate = 1088 // 8
        self.capacity = 512
        assert self.rate * 8 + self.capacity == 1600

        self.state = bytearray(200)
        self.pad = 0x06
        self.output_length = 256 // 8

        self.output = bytearray()

    def keccak(self, message: str) -> str:
        message_bytes = message.encode('utf-8')

        # Absorbing phase
        offset = 0
        block_size = 0
        while (offset < len(message_bytes)):
            block_size = min(len(message_bytes) - offset, self.rate)
            for idx in range(block_size):
                self.state[idx] ^= message_bytes[idx + offset]
            offset += block_size

            if (block_size == self.rate):
                self.state = self.keccak_f(self.state)
                block_size = 0

        # Padding and switch to next phase
        self.state[block_size] ^= self.pad
        if ((self.pad & 0x80) != 0) and (block_size == self.rate - 1):
            self.state = self.keccak_f(self.state)

        self.state[self.rate - 1] ^= 0x80
        self.state = self.keccak_f(self.state)

        # Squeezing phase
        while (self.output_length > 0):
            block_size = min(self.output_length, self.rate)
            self.output += self.state[:block_size]
            self.output_length -= block_size

            if (self.output_length > 0):
                self.state = self.keccak_f(self.state)

        return self.output.hex()

    def keccak_f(self, state: bytearray) -> bytearray:
        A = [[0 for _ in range(5)] for _ in range(5)]
        for x in range(5):
            for y in range(5):
                A[x][y] = self.load(state[self._(x, y):self._(x, y) + 8])

        A = self.round(A)
        state = bytearray(200)
        for x in range(5):
            for y in range(5):
                state[self._(x, y):self._(x, y) + 8] = [(A[x][y] >> (8 * i)) % 256 for i in range(8)]

        return state

    def round(self, A: List[List[int]]) -> List[int]:
        R = 1
        for _ in range(24):
            # θ step (theta)
            C = [A[x][0] ^ A[x][1] ^ A[x][2] ^ A[x][3] ^ A[x][4] for x in range(5)]
            D = [C[(x - 1) % 5] ^ self.rotl(C[(x + 1) % 5], 1) for x in range(5)]
            A = [[A[x][y] ^ D[x] for y in range(5)] for x in range(5)]

            # ρ and π step (rho and pi)
            x, y = 1, 0
            current = A[x][y]
            for t in range(24):
                x, y = y, (2 * x + 3 * y) % 5
                current, A[x][y] = A[x][y], self.rotl(current, (t + 1) * (t + 2) // 2)

            # χ step (chi)
            for y in range(5):
                T = [A[x][y] for x in range(5)]
                for x in range(5):
                    A[x][y] = T[x] ^ ((~T[(x + 1) % 5]) & T[(x + 2) % 5])

            # ι (iota)
            for j in range(7):
                R = ((R << 1) ^ ((R >> 7) * 0x71)) % 256
                if (R & 2):
                    A[0][0] ^= (1 << ((1 << j) - 1))

        return A

    def _(self, x: int, y: int) -> int:
        return 8 * (x + 5 * y)

    def load(self, b: List[int]) -> int:
        return sum((b[i] << (8 * i)) for i in range(8))

    def rotl(self, a: int, n: int) -> int:
        return ((a >> (64 - (n % 64))) + (a << (n % 64))) % (1 << 64)


# Testing SHA3_256 (Keccak)
'''
sha3 = SHA3_256()
digest = sha3.keccak("ABCDE")
print(f"digest = {digest}")
print(f"int digest = {int(digest, 16)}")
# digest = 034af02f68f8874b6668ccbee49143a64be435610e1282d93bf35fd80acce1fb
# int digest = 1489342925838839722911729043427998740623160904909125053258589682537935462907
'''
