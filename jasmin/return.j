.class public return.lan
.super java/lang/Object

.method public <init>()V
    aload_0
    invokespecial java/lang/Object/<init>()V
    return
.end method

.method public static fn()[Ljava/lang/Object;
    .limit stack 25
    .limit locals 25

    iconst_3
    anewarray java/lang/Object
    dup
    iconst_0
    iconst_2
    iconst_1
    iadd
    invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
    aastore
    dup
    iconst_1
    bipush 97
    invokestatic java/lang/Character/valueOf(C)Ljava/lang/Character;
    aastore
    dup
    iconst_2
    iconst_0
    invokestatic java/lang/Boolean/valueOf(Z)Ljava/lang/Boolean;
    aastore
    areturn
.end method

.method public static main([Ljava/lang/String;)V
    .limit stack 25
    .limit locals 25

    invokestatic return.lan/fn()[Ljava/lang/Object;
    iconst_2
    aaload
    checkcast java/lang/Boolean
    invokevirtual java/lang/Boolean/booleanValue()Z
    istore 1
    invokestatic return.lan/fn()[Ljava/lang/Object;
    iconst_0
    aaload
    checkcast java/lang/Integer
    invokevirtual java/lang/Integer/intValue()I
    istore 2
    invokestatic return.lan/fn()[Ljava/lang/Object;
    iconst_1
    aaload
    checkcast java/lang/Character
    invokevirtual java/lang/Character/charValue()C
    istore 3
    getstatic java/lang/System/out Ljava/io/PrintStream;
    iload 1
    invokevirtual java/io/PrintStream/print(Z)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 10
    invokevirtual java/io/PrintStream/print(C)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    iload 2
    invokevirtual java/io/PrintStream/print(I)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 10
    invokevirtual java/io/PrintStream/print(C)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    iload 3
    invokevirtual java/io/PrintStream/print(C)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 10
    invokevirtual java/io/PrintStream/print(C)V
    return
.end method

