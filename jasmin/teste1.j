.class public teste1.lan
.super java/lang/Object

.method public <init>()V
    aload_0
    invokespecial java/lang/Object/<init>()V
    return
.end method

.method public static main([Ljava/lang/String;)V
    .limit stack 25
    .limit locals 25

    bipush 13
    istore 1
    iconst_5
    istore 2
    iload 1
    iload 2
    invokestatic teste1.lan/divMod(II)[Ljava/lang/Object;
    astore 3
    aload 3
    iconst_0
    aaload
    checkcast java/lang/Integer
    invokevirtual java/lang/Integer/intValue()I
    istore 4
    aload 3
    iconst_1
    aaload
    checkcast java/lang/Integer
    invokevirtual java/lang/Integer/intValue()I
    istore 5
    getstatic java/lang/System/out Ljava/io/PrintStream;
    iload 4
    invokevirtual java/io/PrintStream/print(I)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 10
    invokevirtual java/io/PrintStream/print(C)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    iload 5
    invokevirtual java/io/PrintStream/print(I)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 10
    invokevirtual java/io/PrintStream/print(C)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    iload 1
    invokevirtual java/io/PrintStream/print(I)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 10
    invokevirtual java/io/PrintStream/print(C)V
    return
.end method

.method public static divMod(II)[Ljava/lang/Object;
    .limit stack 25
    .limit locals 25

    iconst_5
    istore 2
    iconst_2
    anewarray java/lang/Object
    dup
    iconst_0
    iload 0
    iload 1
    idiv
    invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
    aastore
    dup
    iconst_1
    iload 0
    iload 1
    irem
    invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
    aastore
    areturn
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 122
    invokevirtual java/io/PrintStream/print(C)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 10
    invokevirtual java/io/PrintStream/print(C)V
.end method

