.class public iterVar.lan
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
    iload 1
    iconst_2
    iadd
    istore 2
    iconst_0
    istore 3
L0:
    iload 3
    iload 2
    if_icmpge L1
    getstatic java/lang/System/out Ljava/io/PrintStream;
    iload 3
    invokevirtual java/io/PrintStream/print(I)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 32
    invokevirtual java/io/PrintStream/print(C)V
    iinc 3 1
    goto L0
L1:
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 46
    invokevirtual java/io/PrintStream/print(C)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 10
    invokevirtual java/io/PrintStream/print(C)V
    return
.end method

