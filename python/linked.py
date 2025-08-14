class Node:
    def __init__(self):
        self.val = None
        self.next = None


class LList:
    def __init__(self):
        self.head = None
        self.size = None


def createList():
    n = LList()
    n.head = None
    n.size = 0
    return n


def lastNode(h):
    if (h.next == None):
        return h
    return lastNode(h.next)


def addNode(l, x):
    no = Node()
    no.val = x
    no.next = None
    if (l.head == None):
        l.head = no
    else:
        last = lastNode(l.head)
        last.next = no
    l.size = (l.size + 1)


def removeHead(l):
    if (l.head != None):
        node = l.head
        l.head = l.head.next
        l.size = (l.size - 1)
        return node
    return None


def printList(h):
    if (h != None):
        print(h.val, end='')
        print('-', end='')
        print('>', end='')
        printList(h.next)
    if (h == None):
        print('N', end='')
        print('U', end='')
        print('L', end='')
        print('L', end='')
        print('\n', end='')


def printLList(l):
    print(l.size, end='')
    print(':', end='')
    printList(l.head)


def main():
    k = 65
    l = createList()
    addNode(l, k)
    for _ in range(5):
        k = (k + 1)
        addNode(l, k)
    printLList(l)
    head = removeHead(l)
    printLList(l)


if __name__ == "__main__":
    main()
