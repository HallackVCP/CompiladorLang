def printBoard(board, nl, nc):
    i = 0
    for _ in range(nl):
        j = 0
        for _ in range(nc):
            print(board[i][j], end='')
            j = (j + 1)
        print('\n', end='')
        i = (i + 1)


def startBoard(c, nl, nc):
    board = [None] * (nl)
    i = 0
    for _ in range(nl):
        j = 0
        board[i] = [None] * (nc)
        for _ in range(nc):
            board[i][j] = c
            j = (j + 1)
        i = (i + 1)
    return board


def set(board, x, y):
    board[x][y] = 'A'


def main():
    board = startBoard('*', 3, 4)
    printBoard(board, 3, 4)
    set(board, 1, 2)
    printBoard(board, 3, 4)


if __name__ == "__main__":
    main()
