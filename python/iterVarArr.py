def main():
    v = [None] * (10)
    for i in range(10):
        v[i] = (2 * i)
    for i in v:
        print(i, end='')
        print(' ', end='')
    print('.', end='')
    print('\n', end='')


if __name__ == "__main__":
    main()
