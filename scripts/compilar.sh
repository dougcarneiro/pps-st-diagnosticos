#!/bin/bash

echo "🚀 Compilação Simplificada - ST Diagnósticos"
echo "============================================"

# Verificar Java
if ! command -v javac &> /dev/null; then
    echo "❌ Compilador Java não encontrado!"
    exit 1
fi

echo "✅ Java encontrado: $(java -version 2>&1 | head -n 1)"

# Criar diretório build
mkdir -p build

echo "🔨 Compilando sistema completo..."

# Compilar todos os arquivos Java de uma vez
find src -name "*.java" > sources.txt
javac -d build @sources.txt src/Main.java

if [ $? -eq 0 ]; then
    echo "✅ Compilação bem-sucedida!"
    echo "📁 Arquivos .class em: build/"
    echo ""
    echo "▶️  Para executar use: java -cp build:. src/Main"
    echo "▶️  Ou use: ./executar.sh (execução automática)"
    
    rm sources.txt
else
    echo "❌ Erro na compilação!"
    rm sources.txt
    exit 1
fi
