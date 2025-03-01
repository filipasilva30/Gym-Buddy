# Variáveis
JAVAC = javac
JAVA = java
SRC = $(wildcard *.java)
CLS = $(SRC:.java=.class)

# Alvo padrão
all: run

# Compilação do programa
compile: $(CLS)

%.class: %.java
	$(JAVAC) $<

# Execução do programa
run: compile
	$(JAVA) Main

# Limpeza dos arquivos .class
clean:
	rm -f *.class

# Alvo para ajudar no debug (opcional)
print-%:
	@echo $* = $($*)