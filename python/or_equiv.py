def or_(a, b):
    return (not ((not a) and (not b)))


def main():
    a = True
    b = True
    print(or_(a, b), end='')
    print('\n', end='')
    a = True
    b = False
    print(or_(a, b), end='')
    print('\n', end='')
    a = False
    b = True
    print(or_(a, b), end='')
    print('\n', end='')
    a = False
    b = False
    print(or_(a, b), end='')
    print('\n', end='')


if __name__ == "__main__":
    main()
