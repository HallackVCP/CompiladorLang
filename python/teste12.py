def even(n):
    if (n == 0):
        return True
    else:
        return odd((n - 1))


def odd(n):
    if (n == 0):
        return False
    else:
        return even((n - 1))


def main():
    r = even(3)
    if r:
        print('P', end='')
        print('A', end='')
        print('R', end='')
    else:
        print('I', end='')
        print('M', end='')
        print('P', end='')
        print('A', end='')
        print('R', end='')


if __name__ == "__main__":
    main()
