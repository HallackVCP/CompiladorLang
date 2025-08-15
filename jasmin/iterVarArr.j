.class public iterVarArr.lan
.super java/lang/Object

.method public <init>()V
    aload_0
    invokespecial java/lang/Object/<init>()V
    return
.end method

.method public static main([Ljava/lang/String;)V
    .limit stack 25
    .limit locals 25

    bipush 10
    newarray int
    astore 1
    bipush 10
    istore 2
    iconst_0
    istore 3
L0:
    iload 3
    iload 2
    if_icmpge L1
    aload 1
    iload 3
    iconst_2
    iload 3
    imul
    iastore
    iinc 3 1
    goto L0
L1:
    aload 1
    astore 5
    iconst_0
    istore 4
L3:
    iload 4
    aload 5
    arraylength
    if_icmpge L4
    aload 5
    iload 4
    iaload
    istore 3
    getstatic java/lang/System/out Ljava/io/PrintStream;
    iload 3
    invokevirtual java/io/PrintStream/print(I)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 32
    invokevirtual java/io/PrintStream/print(C)V
    iinc 4 1
    goto L3
L4:
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 46
    invokevirtual java/io/PrintStream/print(C)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 10
    invokevirtual java/io/PrintStream/print(C)V
    return
.end method

