.class public teste2.lan
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
    invokestatic teste2.lan/divMod(II)[Ljava/lang/Object;
    pop
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
.end method

