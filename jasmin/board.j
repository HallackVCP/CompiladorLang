.class public board.lan
.super java/lang/Object

.method public <init>()V
    aload_0
    invokespecial java/lang/Object/<init>()V
    return
.end method

.method public static printBoard([[CII)V
    .limit stack 25
    .limit locals 25

    iconst_0
    istore 3
    iload 1
    istore 4
    iconst_0
    istore 5
L0:
    iload 5
    iload 4
    if_icmpge L1
    iconst_0
    istore 6
    iload 2
    istore 7
    iconst_0
    istore 8
L3:
    iload 8
    iload 7
    if_icmpge L4
    getstatic java/lang/System/out Ljava/io/PrintStream;
    aload 0
    iload 3
    aaload
    iload 6
    caload
    invokevirtual java/io/PrintStream/print(C)V
    iload 6
    iconst_1
    iadd
    istore 6
    iinc 8 1
    goto L3
L4:
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 10
    invokevirtual java/io/PrintStream/print(C)V
    iload 3
    iconst_1
    iadd
    istore 3
    iinc 5 1
    goto L0
L1:
    return
.end method

.method public static startBoard(CII)[[C
    .limit stack 25
    .limit locals 25

    iload 1
    anewarray [C
    astore 3
    iconst_0
    istore 4
    iload 1
    istore 5
    iconst_0
    istore 6
L0:
    iload 6
    iload 5
    if_icmpge L1
    iconst_0
    istore 7
    aload 3
    iload 4
    iload 2
    newarray char
    aastore
    iload 2
    istore 8
    iconst_0
    istore 9
L3:
    iload 9
    iload 8
    if_icmpge L4
    aload 3
    iload 4
    aaload
    iload 7
    iload 0
    castore
    iload 7
    iconst_1
    iadd
    istore 7
    iinc 9 1
    goto L3
L4:
    iload 4
    iconst_1
    iadd
    istore 4
    iinc 6 1
    goto L0
L1:
    aload 3
    areturn
.end method

.method public static set([[CII)V
    .limit stack 25
    .limit locals 25

    aload 0
    iload 1
    aaload
    iload 2
    bipush 65
    castore
    return
.end method

.method public static main([Ljava/lang/String;)V
    .limit stack 25
    .limit locals 25

    bipush 42
    iconst_3
    iconst_4
    invokestatic board.lan/startBoard(CII)[[C
    astore 1
    aload 1
    iconst_3
    iconst_4
    invokestatic board.lan/printBoard([[CII)V
    aload 1
    iconst_1
    iconst_2
    invokestatic board.lan/set([[CII)V
    aload 1
    iconst_3
    iconst_4
    invokestatic board.lan/printBoard([[CII)V
    return
.end method

