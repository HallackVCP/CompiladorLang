def fibonacci(n):
    if (n < 1):
        return n
    if (n == 1):
        return n
    return (fibonacci((n - 1)) + fibonacci((n - 2)))


def main():
    v = fibonacci(15)
    print(v, end='')
    print('\n', end='')


if __name__ == "__main__":
    main()
