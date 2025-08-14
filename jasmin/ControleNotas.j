.class public ControleNotas.lan
.super java/lang/Object

.method public <init>()V
    aload_0
    invokespecial java/lang/Object/<init>()V
    return
.end method

.method public static main([Ljava/lang/String;)V
    .limit stack 25
    .limit locals 25

    sipush 150
    invokestatic Aluno/novoAluno(I)LAluno;
    astore 1
    aload 1
    iconst_0
    ldc 10.0
    invokestatic Aluno/definirNota(LAluno;IF)V
    aload 1
    iconst_1
    ldc 9.5
    invokestatic Aluno/definirNota(LAluno;IF)V
    aload 1
    iconst_2
    ldc 8.9
    invokestatic Aluno/definirNota(LAluno;IF)V
    aload 1
    invokestatic Aluno/imprimeAluno(LAluno;)V
    return
.end method

