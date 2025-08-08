#!/bin/bash

echo "🏥 Sistema de Exames Médicos - ST Diagnósticos"
echo "=============================================="
echo ""

# Verificar se Java está instalado
if ! command -v java &> /dev/null; then
    echo "❌ Java não encontrado. Por favor, instale o Java para continuar."
    exit 1
fi

if ! command -v javac &> /dev/null; then
    echo "❌ Compilador Java não encontrado. Por favor, instale o JDK."
    exit 1
fi

echo "✅ Java encontrado: $(java -version 2>&1 | head -n 1)"
echo ""

# Navegar para o diretório src
cd src

echo "🔨 Compilando o sistema com packages organizados..."

# Criar diretório para arquivos compilados
mkdir -p ../build

# Compilar recursivamente todos os arquivos Java para pasta build
find . -name "*.java" -print > sources.txt
javac -d ../build -cp ../build @sources.txt

if [ $? -eq 0 ]; then
    echo "✅ Compilação bem-sucedida!"
    echo "📁 Arquivos .class criados em: build/"
    echo ""
    echo "🚀 Executando demonstração do sistema..."
    echo "========================================"
    echo ""
    # Executar a classe principal com package a partir da pasta build
    cd ../build
    java br.ifpb.diagnosticos.sistema.SistemaExamesMedicos
    
    # Limpar arquivo temporário
    cd ../src
    rm -f sources.txt
else
    echo "❌ Erro na compilação!"
    rm -f sources.txt
    exit 1
fi
