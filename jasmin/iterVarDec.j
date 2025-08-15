.class public iterVarDec.lan
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
    istore 1
    bipush 21
    istore 2
    iload 1
    iconst_2
    iadd
    istore 3
    iconst_0
    istore 2
L0:
    iload 2
    iload 3
    if_icmpge L1
    getstatic java/lang/System/out Ljava/io/PrintStream;
    iload 2
    invokevirtual java/io/PrintStream/print(I)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 32
    invokevirtual java/io/PrintStream/print(C)V
    iinc 2 1
    goto L0
L1:
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 46
    invokevirtual java/io/PrintStream/print(C)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 10
    invokevirtual java/io/PrintStream/print(C)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    iload 2
    invokevirtual java/io/PrintStream/print(I)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 10
    invokevirtual java/io/PrintStream/print(C)V
    return
.end method

