.class public or_equiv.lan
.super java/lang/Object

.method public <init>()V
    aload_0
    invokespecial java/lang/Object/<init>()V
    return
.end method

.method public static or(ZZ)Z
    .limit stack 25
    .limit locals 25

    iload 0
    ifeq L2
    iconst_0
    goto L3
L2:
    iconst_1
L3:
    ifeq L0
    iload 1
    ifeq L4
    iconst_0
    goto L5
L4:
    iconst_1
L5:
    goto L1
L0:
    iconst_0
L1:
    ifeq L6
    iconst_0
    goto L7
L6:
    iconst_1
L7:
    ireturn
.end method

.method public static main([Ljava/lang/String;)V
    .limit stack 25
    .limit locals 25

    iconst_1
    istore 1
    iconst_1
    istore 2
    getstatic java/lang/System/out Ljava/io/PrintStream;
    iload 1
    iload 2
    invokestatic or_equiv.lan/or(ZZ)Z
    invokevirtual java/io/PrintStream/print(Z)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 10
    invokevirtual java/io/PrintStream/print(C)V
    iconst_1
    istore 1
    iconst_0
    istore 2
    getstatic java/lang/System/out Ljava/io/PrintStream;
    iload 1
    iload 2
    invokestatic or_equiv.lan/or(ZZ)Z
    invokevirtual java/io/PrintStream/print(Z)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 10
    invokevirtual java/io/PrintStream/print(C)V
    iconst_0
    istore 1
    iconst_1
    istore 2
    getstatic java/lang/System/out Ljava/io/PrintStream;
    iload 1
    iload 2
    invokestatic or_equiv.lan/or(ZZ)Z
    invokevirtual java/io/PrintStream/print(Z)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 10
    invokevirtual java/io/PrintStream/print(C)V
    iconst_0
    istore 1
    iconst_0
    istore 2
    getstatic java/lang/System/out Ljava/io/PrintStream;
    iload 1
    iload 2
    invokestatic or_equiv.lan/or(ZZ)Z
    invokevirtual java/io/PrintStream/print(Z)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 10
    invokevirtual java/io/PrintStream/print(C)V
    return
.end method

