# Makefile para Sistema de Exames Médicos - ST Diagnósticos

# Configurações
JAVA_SRC_DIR = src
BUILD_DIR = build
LIB_DIR = lib
MAIN_CLASS = br.ifpb.diagnosticos.sistema.SistemaExamesMedicos
JAVA_FILES = $(shell find $(JAVA_SRC_DIR) -name "*.java")
CLASSPATH = $(BUILD_DIR):$(LIB_DIR)/*

.PHONY: all clean compile run check help clean-output

# Target padrão
all: compile

# Verificar se Java está instalado
check:
	@echo "🔍 Verificando ambiente..."
	@if ! command -v java > /dev/null 2>&1; then \
		echo "❌ Java não encontrado. Instale o Java para continuar."; \
		exit 1; \
	fi
	@if ! command -v javac > /dev/null 2>&1; then \
		echo "❌ Compilador Java não encontrado. Instale o JDK."; \
		exit 1; \
	fi
	@echo " Java encontrado: $$(java -version 2>&1 | head -n 1)"

# Compilar o projeto
compile: check
	@echo "🔨 Compilando sistema completo..."
	@echo "   Encontrados $$(echo $(JAVA_FILES) | wc -w) arquivos Java"
	@find $(JAVA_SRC_DIR) -name "*.java" > sources.tmp
	@javac -cp "$(LIB_DIR)/*" -d $(BUILD_DIR) @sources.tmp
	@if [ $$? -eq 0 ]; then \
		echo " Compilação bem-sucedida!"; \
		echo "📁 Arquivos .class criados em: $(BUILD_DIR)/"; \
		rm -f sources.tmp; \
	else \
		echo "❌ Erro na compilação!"; \
		rm -f sources.tmp; \
		exit 1; \
	fi

# Executar o sistema principal
run: compile
	@echo ""
	@echo "🚀 Executando Sistema de Exames Médicos..."
	@echo "==========================================="
	@echo ""
	@java -cp "$(CLASSPATH)" $(MAIN_CLASS)

# Limpar arquivos compilados
clean:
	@echo "🧹 Limpando arquivos compilados..."
	@rm -rf $(BUILD_DIR)
	@rm -f sources.tmp
	@echo " Limpeza concluída!"

# Limpar pasta output (preservando .output)
clean-output:
	@echo "🧹 Limpando pasta output (preservando .output)..."
	@if [ -d output ]; then \
		find output -type f ! -name ".output" -delete; \
		find output -type d ! -path "output" -delete; \
		echo " Pasta output limpa com sucesso!"; \
	else \
		echo " Pasta output não encontrada"; \
	fi

# Mostrar informações do projeto
info:
	@echo "📊 Informações do Projeto"
	@echo "========================="
	@echo "Classes Java: $$(find $(JAVA_SRC_DIR) -name "*.java" | wc -l)"
	@echo "Diretório Source: $(JAVA_SRC_DIR)"
	@echo "Diretório Build: $(BUILD_DIR)"
	@echo "Classe Principal: $(MAIN_CLASS)"
	@echo ""
	@if [ -d $(BUILD_DIR) ]; then \
		echo " Projeto compilado"; \
	else \
		echo "⚠️  Projeto não compilado"; \
	fi

# Ajuda
help:
	@echo "🏥 Sistema de Exames Médicos - ST Diagnósticos"
	@echo "================================================"
	@echo ""
	@echo "Comandos disponíveis:"
	@echo "  make ou make all    - Compila o projeto"
	@echo "  make compile        - Compila o sistema"
	@echo "  make run            - Compila e executa o sistema principal"
	@echo "  make clean          - Remove arquivos compilados"
	@echo "  make clean-output   - Limpa pasta output (preserva .output)"
	@echo "  make info           - Mostra informações do projeto"
	@echo "  make check          - Verifica ambiente Java"
	@echo "  make help           - Mostra esta ajuda"
	@echo ""
	@echo "Exemplos de uso:"
	@echo "  make run            - Execução rápida do sistema"
	@echo "  make clean run      - Limpar e recompilar"
	@echo ""