# Bintang Fajarianto
# NIM 13519138

# April 11, 2023

from math import inf
from typing import Tuple

# NOTE: Chaquo Python doesn't support TypeAlias

def get_identity_point() -> Tuple[int, int]:
    return (inf, inf)


def is_identity_point(P: Tuple[int, int]) -> bool:
    return P == get_identity_point()
