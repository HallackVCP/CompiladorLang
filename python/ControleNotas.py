class Aluno:
    def __init__(self):
        self.id = None
        self.notas = None

def novoAluno(nome):
    a = Aluno()
    a.id = nome
    a.notas = [None] * (4)
    for i in range(4):
        a.notas[i] = 0.0
    return a

def definirNota(a, i, v):
    if (i < 0):
        i = ((0 - 1) * i)
    i = (i % 4)
    a.notas[i] = v

def nota(a, i):
    if (i < 0):
        i = ((0 - 1) * i)
    i = (i % 4)
    return a.notas[i]

def imprimeAluno(a):
    print('A', end='')
    print('l', end='')
    print('u', end='')
    print('n', end='')
    print('o', end='')
    print(' ', end='')
    print(a.id, end='')
    print(' ', end='')
    for i in range(4):
        print(a.notas[i], end='')
        print(';', end='')
    print('\n', end='')


def main():
    aluno = novoAluno(150)
    definirNota(aluno, 0, 10.0)
    definirNota(aluno, 1, 9.5)
    definirNota(aluno, 2, 8.9)
    imprimeAluno(aluno)


if __name__ == "__main__":
    main()
