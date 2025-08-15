.class public AFD.lan
.super java/lang/Object

.method public <init>()V
    aload_0
    invokespecial java/lang/Object/<init>()V
    return
.end method

.method public static mkAutomata(I)LAFD;
    .limit stack 25
    .limit locals 25

    new AFD
    dup
    invokespecial AFD/<init>()V
    astore 1
    aload 1
    iload 0
    anewarray [LTransition;
    putfield AFD/st [[LTransition;
    aload 1
    iload 0
    newarray int
    putfield AFD/numt [I
    iconst_0
    istore 2
    iload 0
    istore 3
    iconst_0
    istore 4
L0:
    iload 4
    iload 3
    if_icmpge L1
    aload 1
    getfield AFD/numt [I
    iload 2
    iconst_1
    iastore
    iload 2
    iconst_1
    iadd
    istore 2
    iinc 4 1
    goto L0
L1:
    aload 1
    iload 0
    putfield AFD/numStates I
    aload 1
    iconst_0
    putfield AFD/start I
    aload 1
    areturn
.end method

.method public static setFinal(LAFD;I)V
    .limit stack 25
    .limit locals 25

    aload 0
    getfield AFD/numt [I
    iload 1
    iconst_0
    aload 0
    getfield AFD/numt [I
    iload 1
    iaload
    isub
    iastore
    return
.end method

.method public static isFinal(LAFD;I)Z
    .limit stack 25
    .limit locals 25

    aload 0
    getfield AFD/numt [I
    iload 1
    iaload
    iconst_0
    if_icmplt L0
    iconst_0
    goto L1
L0:
    iconst_1
L1:
    ireturn
.end method

.method public static setNumTransitions(LAFD;II)V
    .limit stack 25
    .limit locals 25

    aload 0
    getfield AFD/st [[LTransition;
    iload 1
    iload 2
    anewarray Transition
    aastore
    aload 0
    getfield AFD/numt [I
    iload 1
    iload 2
    iconst_1
    iadd
    iastore
    return
.end method

.method public static addTransition(LAFD;ICI)V
    .limit stack 25
    .limit locals 25

    iconst_0
    istore 4
    iconst_1
    istore 5
    aload 0
    getfield AFD/numt [I
    iload 1
    iaload
    invokestatic AFD.lan/abs(I)I
    iconst_1
    isub
    istore 6
    iconst_0
    istore 7
L0:
    iload 7
    iload 6
    if_icmpge L1
    aload 0
    getfield AFD/st [[LTransition;
    iload 1
    aaload
    iload 4
    aaload
    aconst_null
    if_acmpeq L7
    iconst_0
    goto L8
L7:
    iconst_1
L8:
    ifeq L5
    iload 5
    goto L6
L5:
    iconst_0
L6:
    ifeq L4
    aload 0
    getfield AFD/st [[LTransition;
    iload 1
    aaload
    iload 4
    new Transition
    dup
    invokespecial Transition/<init>()V
    aastore
    aload 0
    getfield AFD/st [[LTransition;
    iload 1
    aaload
    iload 4
    aaload
    iload 2
    putfield Transition/sym C
    aload 0
    getfield AFD/st [[LTransition;
    iload 1
    aaload
    iload 4
    aaload
    iload 3
    putfield Transition/dest I
    iconst_0
    istore 5
L3:
L4:
    iload 4
    iconst_1
    iadd
    istore 4
    iinc 7 1
    goto L0
L1:
    return
.end method

.method public static abs(I)I
    .limit stack 25
    .limit locals 25

    iconst_0
    iload 0
    if_icmpge L1
    iload 0
    ireturn
L0:
L1:
    iconst_0
    iload 0
    isub
    ireturn
.end method

.method public static printAutomata(LAFD;)V
    .limit stack 25
    .limit locals 25

    iconst_0
    istore 1
    aload 0
    getfield AFD/numStates I
    istore 2
    iconst_0
    istore 3
L0:
    iload 3
    iload 2
    if_icmpge L1
    aload 0
    iload 1
    invokestatic AFD.lan/isFinal(LAFD;I)Z
    ifeq L4
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 42
    invokevirtual java/io/PrintStream/print(C)V
L3:
L4:
    getstatic java/lang/System/out Ljava/io/PrintStream;
    iload 1
    invokevirtual java/io/PrintStream/print(I)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 32
    invokevirtual java/io/PrintStream/print(C)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    aload 0
    getfield AFD/numt [I
    iload 1
    iaload
    invokestatic AFD.lan/abs(I)I
    iconst_1
    isub
    invokevirtual java/io/PrintStream/print(I)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 32
    invokevirtual java/io/PrintStream/print(C)V
    aload 0
    getfield AFD/st [[LTransition;
    iload 1
    aaload
    aconst_null
    if_acmpne L9
    iconst_0
    goto L10
L9:
    iconst_1
L10:
    ifeq L7
    iconst_0
    aload 0
    getfield AFD/numt [I
    iload 1
    iaload
    invokestatic AFD.lan/abs(I)I
    iconst_1
    isub
    if_icmplt L11
    iconst_0
    goto L12
L11:
    iconst_1
L12:
    goto L8
L7:
    iconst_0
L8:
    ifeq L6
    iconst_0
    istore 4
    aload 0
    getfield AFD/numt [I
    iload 1
    iaload
    invokestatic AFD.lan/abs(I)I
    iconst_1
    isub
    istore 5
    iconst_0
    istore 6
L13:
    iload 6
    iload 5
    if_icmpge L14
    aload 0
    getfield AFD/st [[LTransition;
    iload 1
    aaload
    iload 4
    aaload
    aconst_null
    if_acmpeq L17
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 91
    invokevirtual java/io/PrintStream/print(C)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    aload 0
    getfield AFD/st [[LTransition;
    iload 1
    aaload
    iload 4
    aaload
    getfield Transition/sym C
    invokevirtual java/io/PrintStream/print(C)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 32
    invokevirtual java/io/PrintStream/print(C)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 45
    invokevirtual java/io/PrintStream/print(C)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 62
    invokevirtual java/io/PrintStream/print(C)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 32
    invokevirtual java/io/PrintStream/print(C)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    aload 0
    getfield AFD/st [[LTransition;
    iload 1
    aaload
    iload 4
    aaload
    getfield Transition/dest I
    invokevirtual java/io/PrintStream/print(I)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 93
    invokevirtual java/io/PrintStream/print(C)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 32
    invokevirtual java/io/PrintStream/print(C)V
    iload 4
    iconst_1
    iadd
    istore 4
L16:
L17:
    iinc 6 1
    goto L13
L14:
L5:
L6:
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 10
    invokevirtual java/io/PrintStream/print(C)V
    iload 1
    iconst_1
    iadd
    istore 1
    iinc 3 1
    goto L0
L1:
    return
.end method

.method public static delta(LAFD;IC)I
    .limit stack 25
    .limit locals 25

    aload 0
    getfield AFD/st [[LTransition;
    iload 1
    aaload
    aconst_null
    if_acmpne L4
    iconst_0
    goto L5
L4:
    iconst_1
L5:
    ifeq L2
    iconst_0
    aload 0
    getfield AFD/numt [I
    iload 1
    iaload
    invokestatic AFD.lan/abs(I)I
    iconst_1
    isub
    if_icmplt L6
    iconst_0
    goto L7
L6:
    iconst_1
L7:
    goto L3
L2:
    iconst_0
L3:
    ifeq L1
    iconst_0
    istore 3
    aload 0
    getfield AFD/numt [I
    iload 1
    iaload
    invokestatic AFD.lan/abs(I)I
    iconst_1
    isub
    istore 4
    iconst_0
    istore 5
L8:
    iload 5
    iload 4
    if_icmpge L9
    aload 0
    getfield AFD/st [[LTransition;
    iload 1
    aaload
    iload 3
    aaload
    getfield Transition/sym C
    iload 2
    if_icmpne L12
    aload 0
    getfield AFD/st [[LTransition;
    iload 1
    aaload
    iload 3
    aaload
    getfield Transition/dest I
    ireturn
L11:
L12:
    iload 3
    iconst_1
    iadd
    istore 3
    iinc 5 1
    goto L8
L9:
L0:
L1:
    iconst_0
    iconst_1
    isub
    ireturn
.end method

.method public static runAFDHelper(LAFD;III[C)I
    .limit stack 25
    .limit locals 25

    iload 2
    iload 3
    if_icmpge L1
    aload 0
    aload 0
    iload 1
    aload 4
    iload 2
    caload
    invokestatic AFD.lan/delta(LAFD;IC)I
    iload 2
    iconst_1
    iadd
    iload 3
    aload 4
    invokestatic AFD.lan/runAFDHelper(LAFD;III[C)I
    ireturn
L0:
L1:
    iload 1
    ireturn
.end method

.method public static runAFD(LAFD;[CI)I
    .limit stack 25
    .limit locals 25

    aload 0
    aload 0
    getfield AFD/start I
    iconst_0
    iload 2
    aload 1
    invokestatic AFD.lan/runAFDHelper(LAFD;III[C)I
    ireturn
.end method

.method public static myAFD()LAFD;
    .limit stack 25
    .limit locals 25

    iconst_3
    invokestatic AFD.lan/mkAutomata(I)LAFD;
    astore 0
    aload 0
    iconst_0
    iconst_2
    invokestatic AFD.lan/setNumTransitions(LAFD;II)V
    aload 0
    iconst_0
    bipush 97
    iconst_0
    invokestatic AFD.lan/addTransition(LAFD;ICI)V
    aload 0
    iconst_0
    bipush 98
    iconst_1
    invokestatic AFD.lan/addTransition(LAFD;ICI)V
    aload 0
    iconst_1
    iconst_2
    invokestatic AFD.lan/setNumTransitions(LAFD;II)V
    aload 0
    iconst_1
    bipush 97
    iconst_0
    invokestatic AFD.lan/addTransition(LAFD;ICI)V
    aload 0
    iconst_1
    bipush 98
    iconst_2
    invokestatic AFD.lan/addTransition(LAFD;ICI)V
    aload 0
    iconst_2
    iconst_2
    invokestatic AFD.lan/setNumTransitions(LAFD;II)V
    aload 0
    iconst_2
    bipush 97
    iconst_2
    invokestatic AFD.lan/addTransition(LAFD;ICI)V
    aload 0
    iconst_2
    bipush 98
    iconst_2
    invokestatic AFD.lan/addTransition(LAFD;ICI)V
    aload 0
    iconst_2
    invokestatic AFD.lan/setFinal(LAFD;I)V
    aload 0
    areturn
.end method

.method public static main([Ljava/lang/String;)V
    .limit stack 25
    .limit locals 25

    invokestatic AFD.lan/myAFD()LAFD;
    astore 1
    iconst_4
    newarray char
    astore 2
    iconst_0
    istore 3
    aload 2
    iconst_0
    bipush 97
    castore
    aload 2
    iconst_1
    bipush 98
    castore
    aload 2
    iconst_2
    bipush 98
    castore
    aload 2
    iconst_3
    bipush 97
    castore
    aload 1
    aload 2
    iconst_4
    invokestatic AFD.lan/runAFD(LAFD;[CI)I
    istore 3
    getstatic java/lang/System/out Ljava/io/PrintStream;
    iload 3
    invokevirtual java/io/PrintStream/print(I)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 32
    invokevirtual java/io/PrintStream/print(C)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    aload 1
    iload 3
    invokestatic AFD.lan/isFinal(LAFD;I)Z
    invokevirtual java/io/PrintStream/print(Z)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 10
    invokevirtual java/io/PrintStream/print(C)V
    aload 2
    iconst_0
    bipush 97
    castore
    aload 2
    iconst_1
    bipush 98
    castore
    aload 2
    iconst_2
    bipush 97
    castore
    aload 2
    iconst_3
    bipush 98
    castore
    aload 1
    aload 2
    iconst_4
    invokestatic AFD.lan/runAFD(LAFD;[CI)I
    istore 3
    getstatic java/lang/System/out Ljava/io/PrintStream;
    iload 3
    invokevirtual java/io/PrintStream/print(I)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 32
    invokevirtual java/io/PrintStream/print(C)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    aload 1
    iload 3
    invokestatic AFD.lan/isFinal(LAFD;I)Z
    invokevirtual java/io/PrintStream/print(Z)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    bipush 10
    invokevirtual java/io/PrintStream/print(C)V
    return
.end method

