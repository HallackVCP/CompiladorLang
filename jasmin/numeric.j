.class public numeric.lan
.super java/lang/Object

.method public <init>()V
    aload_0
    invokespecial java/lang/Object/<init>()V
    return
.end method

.method public static guessRoot(FF)F
    .limit stack 25
    .limit locals 25

    fload 0
    fload 1
    fload 1
    fmul
    fcmpl
    iflt L2
    iconst_0
    goto L3
L2:
    iconst_1
L3:
    ifeq L0
    fload 0
    fload 1
    iconst_2
    i2f
    fdiv
    invokestatic numeric.lan/guessRoot(FF)F
    freturn
    goto L1
L0:
L1:
    fload 1
    freturn
.end method

.method public static sqrt(F)F
    .limit stack 25
    .limit locals 25

    fload 0
    fload 0
    ldc 2.0
    fdiv
    invokestatic numeric.lan/guessRoot(FF)F
    fstore 1
    fload 0
    fload 1
    invokestatic numeric.lan/sqrtrec(FF)F
    freturn
.end method

.method public static sqrtrec(FF)F
    .limit stack 25
    .limit locals 25

    fload 1
    fload 0
    fload 1
    fdiv
    fadd
    iconst_2
    i2f
    fdiv
    fstore 2
    fload 2
    fload 2
    fmul
    fload 0
    fsub
    fstore 3
    fload 3
    ldc 0.0
    fcmpl
    iflt L2
    iconst_0
    goto L3
L2:
    iconst_1
L3:
    ifeq L0
    ldc 0.0
    ldc 1.01
    fsub
    fload 3
    fmul
    fstore 3
    goto L1
L0:
L1:
    fload 3
    ldc 1.0E-6
    fcmpl
    iflt L6
    iconst_0
    goto L7
L6:
    iconst_1
L7:
    ifeq L4
    fload 2
    freturn
    goto L5
L4:
L5:
    fload 0
    fload 2
    invokestatic numeric.lan/sqrtrec(FF)F
    freturn
.end method

.method public static ln(F)F
    .limit stack 25
    .limit locals 25

    fload 0
    ldc 1.0
    invokestatic numeric.lan/dowToOneRec(FF)[Ljava/lang/Object;
    astore 1
    aload 1
    iconst_0
    aaload
    checkcast java/lang/Float
    invokevirtual java/lang/Float/floatValue()F
    fstore 2
    aload 1
    iconst_1
    aaload
    checkcast java/lang/Float
    invokevirtual java/lang/Float/floatValue()F
    fstore 3
    fload 2
    ldc 1.0
    fsub
    fload 2
    ldc 1.0
    fadd
    fdiv
    fstore 4
    ldc 0.0
    fstore 5
    ldc 0.0
    fstore 6
    fload 4
    fstore 7
    bipush 20
    istore 8
    iconst_0
    istore 9
L0:
    iload 9
    iload 8
    if_icmpge L1
    fload 6
    fload 7
    ldc 2.0
    fload 5
    fmul
    ldc 1.0
    fadd
    fdiv
    fadd
    fstore 6
    fload 7
    fload 4
    fmul
    fload 4
    fmul
    fstore 7
    fload 5
    ldc 1.0
    fadd
    fstore 5
    iinc 9 1
    goto L0
L1:
    ldc 2.0
    fload 6
    fmul
    fstore 6
    fload 3
    ldc 1.0
    fsub
    ldc 2.3025851
    fmul
    fload 6
    fadd
    fstore 10
    fload 10
    freturn
.end method

.method public static dowToOneRec(FF)[Ljava/lang/Object;
    .limit stack 25
    .limit locals 25

    fload 0
    ldc 1.0
    fcmpl
    ifeq L2
    iconst_0
    goto L3
L2:
    iconst_1
L3:
    ifeq L0
    iconst_2
    anewarray java/lang/Object
    dup
    iconst_0
    fload 0
    invokestatic java/lang/Float/valueOf(F)Ljava/lang/Float;
    aastore
    dup
    iconst_1
    fload 1
    invokestatic java/lang/Float/valueOf(F)Ljava/lang/Float;
    aastore
    areturn
    goto L1
L0:
    ldc 1.0
    fload 0
    fcmpl
    iflt L6
    iconst_0
    goto L7
L6:
    iconst_1
L7:
    fload 0
    ldc 10.0
    fcmpl
    iflt L8
    iconst_0
    goto L9
L8:
    iconst_1
L9:
    
    ifeq L4
    iconst_2
    anewarray java/lang/Object
    dup
    iconst_0
    fload 0
    invokestatic java/lang/Float/valueOf(F)Ljava/lang/Float;
    aastore
    dup
    iconst_1
    fload 1
    invokestatic java/lang/Float/valueOf(F)Ljava/lang/Float;
    aastore
    areturn
    goto L5
L4:
    ldc 10.0
    fload 0
    fcmpl
    iflt L12
    iconst_0
    goto L13
L12:
    iconst_1
L13:
    ifeq L14
    iconst_0
    goto L15
L14:
    iconst_1
L15:
    fload 0
    ldc 10.0
    fcmpl
    ifeq L16
    iconst_0
    goto L17
L16:
    iconst_1
L17:
    ifeq L18
    iconst_0
    goto L19
L18:
    iconst_1
L19:
    
    ifeq L20
    iconst_0
    goto L21
L20:
    iconst_1
L21:
    ifeq L10
    fload 0
    ldc 10.0
    fdiv
    fload 1
    ldc 1.0
    fadd
    invokestatic numeric.lan/dowToOneRec(FF)[Ljava/lang/Object;
    astore 2
    aload 2
    iconst_0
    aaload
    checkcast java/lang/Float
    invokevirtual java/lang/Float/floatValue()F
    fstore 0
    aload 2
    iconst_1
    aaload
    checkcast java/lang/Float
    invokevirtual java/lang/Float/floatValue()F
    fstore 1
    iconst_2
    anewarray java/lang/Object
    dup
    iconst_0
    fload 0
    invokestatic java/lang/Float/valueOf(F)Ljava/lang/Float;
    aastore
    dup
    iconst_1
    fload 1
    invokestatic java/lang/Float/valueOf(F)Ljava/lang/Float;
    aastore
    areturn
    goto L11
L10:
    fload 0
    ldc 1.0
    fcmpl
    iflt L24
    iconst_0
    goto L25
L24:
    iconst_1
L25:
    ifeq L22
    fload 0
    ldc 10.0
    fmul
    fload 1
    ldc 1.0
    fsub
    invokestatic numeric.lan/dowToOneRec(FF)[Ljava/lang/Object;
    astore 3
    aload 3
    iconst_0
    aaload
    checkcast java/lang/Float
    invokevirtual java/lang/Float/floatValue()F
    fstore 0
    aload 3
    iconst_1
    aaload
    checkcast java/lang/Float
    invokevirtual java/lang/Float/floatValue()F
    fstore 1
    iconst_2
    anewarray java/lang/Object
    dup
    iconst_0
    fload 0
    invokestatic java/lang/Float/valueOf(F)Ljava/lang/Float;
    aastore
    dup
    iconst_1
    fload 1
    invokestatic java/lang/Float/valueOf(F)Ljava/lang/Float;
    aastore
    areturn
    goto L23
L22:
L23:
L11:
L5:
L1:
    iconst_2
    anewarray java/lang/Object
    dup
    iconst_0
    ldc 0.0
    invokestatic java/lang/Float/valueOf(F)Ljava/lang/Float;
    aastore
    dup
    iconst_1
    ldc 0.0
    invokestatic java/lang/Float/valueOf(F)Ljava/lang/Float;
    aastore
    areturn
.end method

.method public static lnb(FF)F
    .limit stack 25
    .limit locals 25

    fload 1
    invokestatic numeric.lan/ln(F)F
    fload 0
    invokestatic numeric.lan/ln(F)F
    fdiv
    freturn
.end method

.method public static main([Ljava/lang/String;)V
    .limit stack 25
    .limit locals 25

    ldc 10.0
    ldc 255.0
    invokestatic numeric.lan/lnb(FF)F
    fstore 1
    getstatic java/lang/System/out Ljava/io/PrintStream;
    fload 1
    invokevirtual java/io/PrintStream/print(F)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 10
    invokevirtual java/io/PrintStream/print(C)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    ldc 2.0
    invokestatic numeric.lan/sqrt(F)F
    invokevirtual java/io/PrintStream/print(F)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 10
    invokevirtual java/io/PrintStream/print(C)V
    return
.end method

