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

    bipush 10
    istore 1
    bipush 20
    istore 2
    iload 1
    iload 2
    if_icmplt L2
    iconst_0
    goto L3
L2:
    iconst_1
L3:
    ifeq L0
    getstatic java/lang/System/out Ljava/io/PrintStream;
    iconst_1
    invokevirtual java/io/PrintStream/print(I)V
    goto L1
L0:
L1:
    iload 1
    bipush 10
    if_icmpeq L6
    iconst_0
    goto L7
L6:
    iconst_1
L7:
    ifeq L8
    iconst_0
    goto L9
L8:
    iconst_1
L9:
    ifeq L4
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 99
    invokevirtual java/io/PrintStream/print(I)V
    goto L5
L4:
    getstatic java/lang/System/out Ljava/io/PrintStream;
    iconst_2
    invokevirtual java/io/PrintStream/print(I)V
L5:
    iload 1
    bipush 100
    if_icmplt L12
    iconst_0
    goto L13
L12:
    iconst_1
L13:
    iload 2
    bipush 20
    if_icmpeq L14
    iconst_0
    goto L15
L14:
    iconst_1
L15:
    
    ifeq L10
    getstatic java/lang/System/out Ljava/io/PrintStream;
    iconst_3
    invokevirtual java/io/PrintStream/print(I)V
    goto L11
L10:
L11:
    return
.end method

