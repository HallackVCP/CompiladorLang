.class public linked.lan
.super java/lang/Object

.method public <init>()V
    aload_0
    invokespecial java/lang/Object/<init>()V
    return
.end method

.method public static createList()LLList;
    .limit stack 25
    .limit locals 25

    new LList
    dup
    invokespecial LList/<init>()V
    astore 0
    aload 0
    aconst_null
    putfield LList/head LNode;
    aload 0
    iconst_0
    putfield LList/size I
    aload 0
    areturn
.end method

.method public static lastNode(LNode;)LNode;
    .limit stack 25
    .limit locals 25

    aload 0
    getfield Node/next LNode;
    aconst_null
    if_acmpne L1
    aload 0
    areturn
L0:
L1:
    aload 0
    getfield Node/next LNode;
    invokestatic linked.lan/lastNode(LNode;)LNode;
    areturn
.end method

.method public static addNode(LLList;I)V
    .limit stack 25
    .limit locals 25

    new Node
    dup
    invokespecial Node/<init>()V
    astore 2
    aload 2
    iload 1
    putfield Node/val I
    aload 2
    aconst_null
    putfield Node/next LNode;
    aload 0
    getfield LList/head LNode;
    aconst_null
    if_acmpne L0
    aload 0
    aload 2
    putfield LList/head LNode;
    goto L1
L0:
    aload 0
    getfield LList/head LNode;
    invokestatic linked.lan/lastNode(LNode;)LNode;
    astore 3
    aload 3
    aload 2
    putfield Node/next LNode;
L1:
    aload 0
    aload 0
    getfield LList/size I
    iconst_1
    iadd
    putfield LList/size I
    return
.end method

.method public static removeHead(LLList;)LNode;
    .limit stack 25
    .limit locals 25

    aload 0
    getfield LList/head LNode;
    aconst_null
    if_acmpeq L1
    aload 0
    getfield LList/head LNode;
    astore 1
    aload 0
    aload 0
    getfield LList/head LNode;
    getfield Node/next LNode;
    putfield LList/head LNode;
    aload 0
    aload 0
    getfield LList/size I
    iconst_1
    isub
    putfield LList/size I
    aload 1
    areturn
L0:
L1:
    aconst_null
    areturn
.end method

.method public static printList(LNode;)V
    .limit stack 25
    .limit locals 25

    aload 0
    aconst_null
    if_acmpeq L1
    getstatic java/lang/System/out Ljava/io/PrintStream;
    aload 0
    getfield Node/val I
    invokevirtual java/io/PrintStream/print(I)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 45
    invokevirtual java/io/PrintStream/print(C)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 62
    invokevirtual java/io/PrintStream/print(C)V
    aload 0
    getfield Node/next LNode;
    invokestatic linked.lan/printList(LNode;)V
L0:
L1:
    aload 0
    aconst_null
    if_acmpne L3
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 78
    invokevirtual java/io/PrintStream/print(C)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 85
    invokevirtual java/io/PrintStream/print(C)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 76
    invokevirtual java/io/PrintStream/print(C)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 76
    invokevirtual java/io/PrintStream/print(C)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 10
    invokevirtual java/io/PrintStream/print(C)V
L2:
L3:
    return
.end method

.method public static printLList(LLList;)V
    .limit stack 25
    .limit locals 25

    getstatic java/lang/System/out Ljava/io/PrintStream;
    aload 0
    getfield LList/size I
    invokevirtual java/io/PrintStream/print(I)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 58
    invokevirtual java/io/PrintStream/print(C)V
    aload 0
    getfield LList/head LNode;
    invokestatic linked.lan/printList(LNode;)V
    return
.end method

.method public static main([Ljava/lang/String;)V
    .limit stack 25
    .limit locals 25

    bipush 65
    istore 1
    invokestatic linked.lan/createList()LLList;
    astore 2
    aload 2
    iload 1
    invokestatic linked.lan/addNode(LLList;I)V
    iconst_5
    istore 3
    iconst_0
    istore 4
L0:
    iload 4
    iload 3
    if_icmpge L1
    iload 1
    iconst_1
    iadd
    istore 1
    aload 2
    iload 1
    invokestatic linked.lan/addNode(LLList;I)V
    iinc 4 1
    goto L0
L1:
    aload 2
    invokestatic linked.lan/printLList(LLList;)V
    aload 2
    invokestatic linked.lan/removeHead(LLList;)LNode;
    astore 5
    aload 2
    invokestatic linked.lan/printLList(LLList;)V
    return
.end method

