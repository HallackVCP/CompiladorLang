.class public teste12.lan
.super java/lang/Object

.method public <init>()V
    aload_0
    invokespecial java/lang/Object/<init>()V
    return
.end method

.method public static even(I)Z
    .limit stack 25
    .limit locals 25

    iload 0
    iconst_0
    if_icmpne L0
    iconst_1
    ireturn
L0:
    iload 0
    iconst_1
    isub
    invokestatic teste12.lan/odd(I)Z
    ireturn
L1:
.end method

.method public static odd(I)Z
    .limit stack 25
    .limit locals 25

    iload 0
    iconst_0
    if_icmpne L0
    iconst_0
    ireturn
L0:
    iload 0
    iconst_1
    isub
    invokestatic teste12.lan/even(I)Z
    ireturn
L1:
.end method

.method public static main([Ljava/lang/String;)V
    .limit stack 25
    .limit locals 25

    iconst_3
    invokestatic teste12.lan/even(I)Z
    istore 1
    iload 1
    ifeq L0
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 80
    invokevirtual java/io/PrintStream/print(C)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 65
    invokevirtual java/io/PrintStream/print(C)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 82
    invokevirtual java/io/PrintStream/print(C)V
    goto L1
L0:
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 73
    invokevirtual java/io/PrintStream/print(C)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 77
    invokevirtual java/io/PrintStream/print(C)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 80
    invokevirtual java/io/PrintStream/print(C)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 65
    invokevirtual java/io/PrintStream/print(C)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 82
    invokevirtual java/io/PrintStream/print(C)V
L1:
    return
.end method

