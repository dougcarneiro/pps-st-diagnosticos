JAVA_FILES = $(wildcard *.java)
CLASS_FILES = $(JAVA_FILES:.java=.class)

.PHONY: all clean run compile

all: compile

compile: $(CLASS_FILES)

%.class: %.java
	javac $<

run: compile
	java SistemaExamesMedicos

clean:
	rm -f *.class

help:
	@echo "Comandos disponíveis:"
	@echo "  make compile  - Compila todos os arquivos Java"
	@echo "  make run      - Compila e executa o sistema"
	@echo "  make clean    - Remove arquivos .class"
	@echo "  make help     - Mostra esta ajuda"
