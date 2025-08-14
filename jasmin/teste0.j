.class public teste0.lan
.super java/lang/Object

.method public <init>()V
    aload_0
    invokespecial java/lang/Object/<init>()V
    return
.end method

.method public static main([Ljava/lang/String;)V
    .limit stack 25
    .limit locals 25

    iconst_5
    istore 1
    iload 1
    istore 2
    iload 1
    istore 3
    iconst_0
    istore 4
L0:
    iload 4
    iload 3
    if_icmpge L1
    iload 2
    istore 5
    iconst_0
    istore 6
L3:
    iload 6
    iload 5
    if_icmpge L4
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 42
    invokevirtual java/io/PrintStream/print(C)V
    iinc 6 1
    goto L3
L4:
    iload 2
    iconst_1
    isub
    istore 2
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 10
    invokevirtual java/io/PrintStream/print(C)V
    iinc 4 1
    goto L0
L1:
    return
.end method

