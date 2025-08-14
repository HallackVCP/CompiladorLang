def readArr(v, sz):
    i = 0
    x = 0
    for _ in range(sz):
        x = int(input("Entrada para x: "))
        v[i] = x
        i = (i + 1)


def printArr(v, sz):
    print('{', end='')
    if (0 < sz):
        print(v[0], end='')
        i = 1
        for _ in range((sz - 1)):
            print(',', end='')
            print(v[i], end='')
            i = (i + 1)
    print('}', end='')
    print('\n', end='')


def main():
    x = 0
    v = [None] * (10)
    readArr(v, 10)
    printArr(v, 10)
    sort(v, 10)
    printArr(v, 10)


def sort(v, sz):
    i = 0
    j = 0
    k = 0
    aux = 0
    for _ in range(sz):
        j = (i + 1)
        k = i
        for _ in range((sz - j)):
            if (v[j] < v[k]):
                k = j
            j = (j + 1)
        aux = v[i]
        v[i] = v[k]
        v[k] = aux
        i = (i + 1)


if __name__ == "__main__":
    main()
