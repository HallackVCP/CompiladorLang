def fn():
    return (2 + 1), 'a', False


def main():
    x = fn()[2]
    y = fn()[0]
    z = fn()[1]
    print(x, end='')
    print('\n', end='')
    print(y, end='')
    print('\n', end='')
    print(z, end='')
    print('\n', end='')


if __name__ == "__main__":
    main()
