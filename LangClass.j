.class public LangClass
.super java/lang/Object

.method public <init>()V
    aload_0
    invokespecial java/lang/Object/<init>()V
    return
.end method

.method public static main([Ljava/lang/String;)V
    .limit stack 25
    .limit locals 25

    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 10
    iconst_5
    iconst_2
    imul
    iadd
    invokevirtual java/io/PrintStream/print(I)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 10
    iconst_5
    iadd
    iconst_2
    imul
    invokevirtual java/io/PrintStream/print(I)V
    return
.end method

