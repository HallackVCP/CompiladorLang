def guessRoot(x, a):
    if (x < (a * a)):
        return guessRoot(x, (a / 2))
    return a


def sqrt(x):
    g = guessRoot(x, (x / 2.0))
    return sqrtrec(x, g)


def sqrtrec(x, x0):
    x1 = ((x0 + (x / x0)) / 2)
    diff = ((x1 * x1) - x)
    if (diff < 0.0):
        diff = ((0.0 - 1.01) * diff)
    if (diff < 1.0E-6):
        return x1
    return sqrtrec(x, x1)


def ln(x):
    a, n = dowToOneRec(x, 1.0)
    y = ((a - 1.0) / (a + 1.0))
    k = 0.0
    s = 0.0
    t = y
    for _ in range(20):
        s = (s + (t / ((2.0 * k) + 1.0)))
        t = ((t * y) * y)
        k = (k + 1.0)
    s = (2.0 * s)
    log = (((n - 1.0) * 2.3025851) + s)
    return log


def dowToOneRec(n, dc):
    if (n == 1.0):
        return n, dc
    else:
        if ((1.0 < n) and (n < 10.0)):
            return n, dc
        else:
            if (not ((not (10.0 < n)) and (not (n == 10.0)))):
                n, dc = dowToOneRec((n / 10.0), (dc + 1.0))
                return n, dc
            else:
                if (n < 1.0):
                    n, dc = dowToOneRec((n * 10.0), (dc - 1.0))
                    return n, dc
    return 0.0, 0.0


def lnb(base, x):
    return (ln(x) / ln(base))


def main():
    log = lnb(10.0, 255.0)
    print(log, end='')
    print('\n', end='')
    print(sqrt(2.0), end='')
    print('\n', end='')


if __name__ == "__main__":
    main()
