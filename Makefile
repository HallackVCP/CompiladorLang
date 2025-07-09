# Makefile para compilar, empacotar e executar o projeto CompiladorLang.
# Este arquivo funciona em Linux, macOS e Windows (com 'make' instalado).

# --- Variáveis de Configuração ---

# Comando para o compilador Java
JAVAC = javac
# Comando para a ferramenta de criação de JAR
JAR = jar

# Versão do Java para garantir compatibilidade
JAVA_VERSION = 21

# Diretórios do projeto
SRCDIR = src/main/java
BUILDDIR = target/classes

# Configurações do JAR final
JAR_NAME = CompiladorLang.jar
JAR_PATH = target/$(JAR_NAME)
MAIN_CLASS = org.example.Compiler

# Encontra automaticamente todos os arquivos .java no diretório de fontes
SOURCES = $(shell find $(SRCDIR) -type f -name "*.java")

# Gera a lista de arquivos .class correspondentes que serão criados no diretório de build
CLASSES = $(SOURCES:$(SRCDIR)/%.java=$(BUILDDIR)/%.class)

# Argumentos para a execução (pode ser sobrescrito na linha de comando)
ARGS = -i fat.txt


# --- Alvos (Targets) ---

# O alvo padrão que será executado ao rodar 'make' sem argumentos.
# Depende da criação do JAR.
.PHONY: all
all: $(JAR_PATH)

# Alvo para criar o JAR executável.
# Esta regra só será executada se algum arquivo .class for mais novo que o JAR existente.
$(JAR_PATH): $(CLASSES)
	@echo "--- Empacotando em um JAR executável ---"
	@mkdir -p target
	$(JAR) cfe $@ $(MAIN_CLASS) -C $(BUILDDIR) .

# Regra de Padrão para compilar arquivos .java em .class.
# Ensina ao 'make' como transformar um .java em um .class.
# Esta regra só será executada se um arquivo .java for mais novo que seu .class correspondente.
$(BUILDDIR)/%.class: $(SRCDIR)/%.java
	@echo "Compilando: $<"
	@mkdir -p $(@D)
	$(JAVAC) -d $(BUILDDIR) -sourcepath $(SRCDIR) -source $(JAVA_VERSION) -target $(JAVA_VERSION) $<

# Alvo para executar o programa.
# Exemplo de uso: make run
# Exemplo com argumentos: make run ARGS="-syn arrays.lang"
.PHONY: run
run: $(JAR_PATH)
	@echo "--- Executando o compilador com os argumentos: $(ARGS) ---"
	java -jar $(JAR_PATH) $(ARGS)

# Alvo para limpar todos os arquivos gerados.
.PHONY: clean
clean:
	@echo "--- Limpando o diretório de build ---"
	@rm -rf target

