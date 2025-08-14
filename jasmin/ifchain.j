.class public ifchain.lan
.super java/lang/Object

.method public <init>()V
    aload_0
    invokespecial java/lang/Object/<init>()V
    return
.end method

.method public static main([Ljava/lang/String;)V
    .limit stack 25
    .limit locals 25

    iconst_0
    istore 1
    new java/util/Scanner
    dup
    getstatic java/lang/System/in Ljava/io/InputStream;
    invokespecial java/util/Scanner/<init>(Ljava/io/InputStream;)V
    invokevirtual java/util/Scanner/nextInt()I
    istore 1
    iload 1
    bipush 10
    if_icmpge L1
    iconst_5
    iload 1
    if_icmpge L2
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 65
    invokevirtual java/io/PrintStream/print(C)V
    goto L3
L2:
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 90
    invokevirtual java/io/PrintStream/print(C)V
L3:
L0:
L1:
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 68
    invokevirtual java/io/PrintStream/print(C)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 10
    invokevirtual java/io/PrintStream/print(C)V
    return
.end method

