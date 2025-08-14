.class public sort.lan
.super java/lang/Object

.method public <init>()V
    aload_0
    invokespecial java/lang/Object/<init>()V
    return
.end method

.method public static readArr([II)V
    .limit stack 25
    .limit locals 25

    iconst_0
    istore 2
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
    new java/util/Scanner
    dup
    getstatic java/lang/System/in Ljava/io/InputStream;
    invokespecial java/util/Scanner/<init>(Ljava/io/InputStream;)V
    invokevirtual java/util/Scanner/nextInt()I
    istore 3
    aload 0
    iload 2
    iload 3
    iastore
    iload 2
    iconst_1
    iadd
    istore 2
    iinc 5 1
    goto L0
L1:
    return
.end method

.method public static printArr([II)V
    .limit stack 25
    .limit locals 25

    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 123
    invokevirtual java/io/PrintStream/print(C)V
    iconst_0
    iload 1
    if_icmplt L2
    iconst_0
    goto L3
L2:
    iconst_1
L3:
    ifeq L0
    getstatic java/lang/System/out Ljava/io/PrintStream;
    aload 0
    iconst_0
    iaload
    invokevirtual java/io/PrintStream/print(I)V
    iconst_1
    istore 2
    iload 1
    iconst_1
    isub
    istore 3
    iconst_0
    istore 4
L4:
    iload 4
    iload 3
    if_icmpge L5
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 44
    invokevirtual java/io/PrintStream/print(C)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    aload 0
    iload 2
    iaload
    invokevirtual java/io/PrintStream/print(I)V
    iload 2
    iconst_1
    iadd
    istore 2
    iinc 4 1
    goto L4
L5:
    goto L1
L0:
L1:
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 125
    invokevirtual java/io/PrintStream/print(C)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 10
    invokevirtual java/io/PrintStream/print(C)V
    return
.end method

.method public static main([Ljava/lang/String;)V
    .limit stack 25
    .limit locals 25

    iconst_0
    istore 1
    bipush 10
    newarray int
    astore 2
    aload 2
    bipush 10
    invokestatic sort.lan/readArr([II)V
    aload 2
    bipush 10
    invokestatic sort.lan/printArr([II)V
    aload 2
    bipush 10
    invokestatic sort.lan/sort([II)V
    aload 2
    bipush 10
    invokestatic sort.lan/printArr([II)V
    return
.end method

.method public static sort([II)V
    .limit stack 25
    .limit locals 25

    iconst_0
    istore 2
    iconst_0
    istore 3
    iconst_0
    istore 4
    iconst_0
    istore 5
    iload 1
    istore 6
    iconst_0
    istore 7
L0:
    iload 7
    iload 6
    if_icmpge L1
    iload 2
    iconst_1
    iadd
    istore 3
    iload 2
    istore 4
    iload 1
    iload 3
    isub
    istore 8
    iconst_0
    istore 9
L3:
    iload 9
    iload 8
    if_icmpge L4
    aload 0
    iload 3
    iaload
    aload 0
    iload 4
    iaload
    if_icmplt L8
    iconst_0
    goto L9
L8:
    iconst_1
L9:
    ifeq L6
    iload 3
    istore 4
    goto L7
L6:
L7:
    iload 3
    iconst_1
    iadd
    istore 3
    iinc 9 1
    goto L3
L4:
    aload 0
    iload 2
    iaload
    istore 5
    aload 0
    iload 2
    aload 0
    iload 4
    iaload
    iastore
    aload 0
    iload 4
    iload 5
    iastore
    iload 2
    iconst_1
    iadd
    istore 2
    iinc 7 1
    goto L0
L1:
    return
.end method

