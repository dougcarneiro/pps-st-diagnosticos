# Makefile para Sistema de Exames Médicos - ST Diagnósticos

# Configurações
JAVA_SRC_DIR = src
BUILD_DIR = build
LIB_DIR = lib
MAIN_CLASS = br.ifpb.diagnosticos.sistema.SistemaExamesMedicos
DEMO_CLASS = br.ifpb.diagnosticos.sistema.DemoConfiguracaoSistema
JAVA_FILES = $(shell find $(JAVA_SRC_DIR) -name "*.java")
SCRIPTS_DIR = scripts
CLASSPATH = $(BUILD_DIR):$(LIB_DIR)/*

.PHONY: all clean compile run demo check setup help test

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
	@echo "✅ Java encontrado: $$(java -version 2>&1 | head -n 1)"

# Setup inicial do projeto
setup: check
	@echo "📁 Criando estrutura de diretórios..."
	@mkdir -p $(BUILD_DIR)
	@mkdir -p dados
	@mkdir -p config
	@echo "✅ Estrutura criada com sucesso!"

# Compilar o projeto
compile: check setup
	@echo "🔨 Compilando sistema completo..."
	@echo "   Encontrados $$(echo $(JAVA_FILES) | wc -w) arquivos Java"
	@find $(JAVA_SRC_DIR) -name "*.java" > sources.tmp
	@javac -cp "$(LIB_DIR)/*" -d $(BUILD_DIR) @sources.tmp
	@if [ $$? -eq 0 ]; then \
		echo "✅ Compilação bem-sucedida!"; \
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

# Executar demonstração de configuração
demo: compile
	@echo ""
	@echo "🎯 Executando Demonstração de Configuração..."
	@echo "=============================================="
	@echo ""
	@java -cp "$(CLASSPATH)" $(DEMO_CLASS)

# Executar ambos os programas
test: run demo

# Compilar e executar (equivalente ao executar.sh)
start: compile run

# Limpar arquivos compilados
clean:
	@echo "🧹 Limpando arquivos compilados..."
	@rm -rf $(BUILD_DIR)
	@rm -f sources.tmp
	@echo "✅ Limpeza concluída!"

# Limpar tudo (incluindo configurações)
clean-all: clean
	@echo "🧹 Limpando configurações e dados..."
	@rm -rf config/*.properties
	@echo "✅ Limpeza completa concluída!"

# Mostrar informações do projeto
info:
	@echo "📊 Informações do Projeto"
	@echo "========================="
	@echo "Classes Java: $$(find $(JAVA_SRC_DIR) -name "*.java" | wc -l)"
	@echo "Diretório Source: $(JAVA_SRC_DIR)"
	@echo "Diretório Build: $(BUILD_DIR)"
	@echo "Classe Principal: $(MAIN_CLASS)"
	@echo "Classe Demo: $(DEMO_CLASS)"
	@echo ""
	@if [ -d $(BUILD_DIR) ]; then \
		echo "✅ Projeto compilado"; \
	else \
		echo "⚠️  Projeto não compilado"; \
	fi

# Ajuda
help:
	@echo "🏥 Sistema de Exames Médicos - ST Diagnósticos"
	@echo "================================================"
	@echo ""
	@echo "Comandos disponíveis:"
	@echo "  make ou make all      - Compila o projeto"
	@echo "  make compile        - Compila o sistema"
	@echo "  make run            - Compila e executa o sistema principal"
	@echo "  make demo           - Compila e executa a demonstração"
	@echo "  make start          - Equivalente ao executar.sh"
	@echo "  make test           - Executa sistema principal + demo"
	@echo "  make clean          - Remove arquivos compilados"
	@echo "  make clean-all      - Remove tudo (build + config)"
	@echo "  make info           - Mostra informações do projeto"
	@echo "  make check          - Verifica ambiente Java"
	@echo "  make setup          - Cria estrutura de diretórios"
	@echo "  make help           - Mostra esta ajuda"
	@echo ""
	@echo "Exemplos de uso:"
	@echo "  make run            - Execução rápida do sistema"
	@echo "  make demo           - Ver demonstração de configuração"
	@echo "  make clean run      - Limpar e recompilar"
	@echo ""
	@echo "Scripts legados (na pasta scripts/):"
	@echo "  ./scripts/executar.sh  - Script bash original"
	@echo "  ./scripts/compilar.sh  - Script de compilação original"