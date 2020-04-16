# pset3.py
import sys

def AFill(n):
    a = [0, 1, 2, 3, 1, 2]
    for i in range(6, n + 1):
        a.append(min(a[i - 1], a[i - 4], a[i - 6]) + 1)
    
    # print(a)
    return a
        
def OptCoins(a):
    coins = [0, 0, 0]
    i = len(a) -1 
    while i > 0:
        if (a[i] - a[i - 1]) == 1:
            coins[0] += 1
            i -= 1
        elif (a[i] - a[i - 4]) == 1:
            coins[1] += 1
            i -= 4
        elif (a[i] - a[i - 6]) == 1:
            coins[2] += 1
            i -= 6
    
    return coins


if __name__ == "__main__":
    args = sys.argv
    n = int(args[1])

    a = AFill(n)
    coins = OptCoins(a)

    print(f'The best combo of coins is:\n1c: {coins[0]}\n4c: {coins[1]}\n6c: {coins[2]}')

