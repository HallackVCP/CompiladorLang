.class public fib.lan
.super java/lang/Object

.method public <init>()V
    aload_0
    invokespecial java/lang/Object/<init>()V
    return
.end method

.method public static fibonacci(I)I
    .limit stack 25
    .limit locals 25

    iload 0
    iconst_1
    if_icmplt L2
    iconst_0
    goto L3
L2:
    iconst_1
L3:
    ifeq L0
    iload 0
    ireturn
    goto L1
L0:
L1:
    iload 0
    iconst_1
    if_icmpeq L6
    iconst_0
    goto L7
L6:
    iconst_1
L7:
    ifeq L4
    iload 0
    ireturn
    goto L5
L4:
L5:
    iload 0
    iconst_1
    isub
    invokestatic fib.lan/fibonacci(I)I
    iload 0
    iconst_2
    isub
    invokestatic fib.lan/fibonacci(I)I
    iadd
    ireturn
.end method

.method public static main([Ljava/lang/String;)V
    .limit stack 25
    .limit locals 25

    bipush 15
    invokestatic fib.lan/fibonacci(I)I
    istore 1
    getstatic java/lang/System/out Ljava/io/PrintStream;
    iload 1
    invokevirtual java/io/PrintStream/print(I)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 10
    invokevirtual java/io/PrintStream/print(C)V
    return
.end method

