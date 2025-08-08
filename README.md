# Sistema de Exames Médicos - ST Diagnósticos

Este projeto implementa um sistema completo de controle de exames médicos e emissão de laudos, utilizando múltiplos padrões de projeto para demonstrar flexibilidade, reutilização e fácil manutenção.

## 📋 Padrões de Projeto Implementados

### 1. **Singleton** - Gerador de Números
- **Classe**: `GeradorNumeroExame`
- **Propósito**: Garantir que os números sequenciais dos exames sejam únicos e não repetidos

### 2. **Strategy** - Descontos
- **Classes**: `DescontoStrategy`, `DescontoConvenio`, `DescontoIdoso`
- **Propósito**: Aplicar diferentes tipos de desconto de forma flexível

### 3. **Factory Method** - Criação de Exames
- **Classes**: `CriadorExame`, `CriadorHemograma`, `CriadorUltrassonografia`, `CriadorRessonanciaMagnetica`
- **Propósito**: Criar diferentes tipos de exames sem acoplar o código cliente às classes concretas

### 4. **Template Method** - Realização de Exames
- **Classe**: `Exame` (método `realizarExame()`)
- **Propósito**: Definir o algoritmo padrão para realizar exames, permitindo que subclasses customizem etapas específicas

### 5. **Chain of Responsibility** - Validação
- **Classes**: `Validador`, `ValidadorBase`, `ValidacaoHemograma`, `ValidacaoRessonancia`, `ValidacaoUltrassonografia`
- **Propósito**: Processar validações de forma sequencial e extensível

### 6. **Bridge** - Laudos e Formatos
- **Classes**: `Laudo`, `FormatoLaudo`, `PDF`, `HTML`, `Texto`
- **Propósito**: Separar a abstração (tipo de laudo) da implementação (formato de saída)

### 7. **Observer** - Notificações
- **Classes**: `Observador`, `EmailNotificador`, `SmsNotificador`, `WhatsAppNotificador`
- **Propósito**: Notificar pacientes automaticamente quando laudos estão disponíveis

### 8. **Memento** - Histórico de Observações
- **Classes**: `Observacao`, `ObservacaoMemento`, `HistoricoObservacao`
- **Propósito**: Salvar e restaurar estados anteriores das observações dos laudos

### 9. **Facade** - Interface Simplificada
- **Classe**: `LaboratorioFacade`
- **Propósito**: Fornecer uma interface simplificada para o sistema complexo

### 10. **Priority Queue** - Fila de Exames
- **Classe**: `FilaPrioridadeExames`
- **Propósito**: Gerenciar a ordem de processamento dos exames baseada em prioridade

## 🚀 Como Executar

### Compilação
```bash
# Navegar para o diretório src
cd src

# Compilar todos os arquivos Java
javac *.java
```

### Execução
```bash
# Executar o programa principal
java SistemaExamesMedicos
```

# Sistema de Exames Médicos - ST Diagnósticos

Este projeto implementa um sistema completo de controle de exames médicos e emissão de laudos, utilizando múltiplos padrões de projeto para demonstrar flexibilidade, reutilização e fácil manutenção.

## 📦 Arquitetura por Domínio de Negócio

O sistema foi organizado em **packages por domínio de negócio**, seguindo princípios de Domain-Driven Design (DDD), ao invés de organizar por padrões de projeto. Isso resulta em um código mais coeso e de fácil manutenção.

### 🏗️ Estrutura de Packages

```
br.ifpb.diagnosticos/
├── 📋 modelo/                    # Entidades e modelos de dados
│   ├── Paciente                 # Dados do paciente
│   └── Prioridade               # Enum de prioridades
│
├── 🔬 exames/                    # Domínio de exames médicos
│   ├── Exame                    # Classe base abstrata
│   ├── tipos/                   # Tipos específicos de exame
│   │   ├── Hemograma
│   │   ├── Ultrassonografia
│   │   └── Ressonancia
│   └── criadores/              # Factory Methods
│       ├── CriadorExame
│       ├── CriadorHemograma
│       ├── CriadorUltrassonografia
│       └── CriadorRessonanciaMagnetica
│
├── 📄 laudos/                    # Domínio de laudos médicos
│   ├── Laudo                    # Classe base abstrata
│   ├── Observacao               # Observações (Memento)
│   ├── ObservacaoMemento
│   ├── HistoricoObservacao
│   ├── tipos/                   # Tipos específicos de laudo
│   │   ├── LaudoHemograma
│   │   ├── LaudoUltrassonografia
│   │   └── LaudoRessonanciaMagnetica
│   └── formatos/               # Formatos de saída (Bridge)
│       ├── FormatoLaudo
│       ├── PDF
│       ├── HTML
│       └── Texto
│
├── ✅ validacao/                 # Domínio de validação de dados
│   ├── Validador               # Interface Chain of Responsibility
│   ├── ValidadorBase
│   ├── ValidacaoHemograma
│   ├── ValidacaoRessonancia
│   └── ValidacaoUltrassonografia
│
├── 📧 notificacao/              # Domínio de notificações (Observer)
│   ├── Observador
│   ├── EmailNotificador
│   ├── SmsNotificador
│   └── WhatsAppNotificador
│
├── 💰 financeiro/               # Domínio financeiro (Strategy)
│   ├── DescontoStrategy
│   ├── DescontoConvenio
│   └── DescontoIdoso
│
├── 🎯 gestao/                   # Gestão de filas e prioridades
│   └── FilaPrioridadeExames
│
├── 🏥 sistema/                  # Facade e sistema principal
│   ├── LaboratorioFacade
│   └── SistemaExamesMedicos
│
└── 🛠️ utils/                    # Utilitários (Singleton, CSV)
    ├── GeradorNumeroExame
    └── CarregadorCSV
```

## 📋 Padrões de Projeto Implementados

### 1. **Singleton** - Gerador de Números
- **Package**: `utils`
- **Classe**: `GeradorNumeroExame`
- **Propósito**: Garantir que os números sequenciais dos exames sejam únicos

### 2. **Strategy** - Descontos
- **Package**: `financeiro`
- **Classes**: `DescontoStrategy`, `DescontoConvenio`, `DescontoIdoso`
- **Propósito**: Aplicar diferentes políticas de desconto

### 3. **Factory Method** - Criação de Exames
- **Package**: `exames.criadores`
- **Classes**: `CriadorExame`, `CriadorHemograma`, etc.
- **Propósito**: Criar diferentes tipos de exames sem acoplamento

### 4. **Template Method** - Realização de Exames
- **Package**: `exames`
- **Classe**: `Exame` (método `realizarExame()`)
- **Propósito**: Definir algoritmo padrão para exames

### 5. **Chain of Responsibility** - Validação
- **Package**: `validacao`
- **Classes**: `Validador`, `ValidadorBase`, `ValidacaoHemograma`, etc.
- **Propósito**: Validações sequenciais e extensíveis

### 6. **Bridge** - Laudos e Formatos
- **Packages**: `laudos` e `laudos.formatos`
- **Classes**: `Laudo`, `FormatoLaudo`, `PDF`, `HTML`, `Texto`
- **Propósito**: Separar abstração (laudo) da implementação (formato)

### 7. **Observer** - Notificações
- **Package**: `notificacao`
- **Classes**: `Observador`, `EmailNotificador`, `SmsNotificador`, etc.
- **Propósito**: Notificações automáticas

### 8. **Memento** - Histórico de Observações
- **Package**: `laudos`
- **Classes**: `Observacao`, `ObservacaoMemento`, `HistoricoObservacao`
- **Propósito**: Controle de versão das observações

### 9. **Facade** - Interface Simplificada
- **Package**: `sistema`
- **Classe**: `LaboratorioFacade`
- **Propósito**: Interface unificada para o sistema

### 10. **Priority Queue** - Fila de Exames
- **Package**: `gestao`
- **Classe**: `FilaPrioridadeExames`
- **Propósito**: Gerenciamento de prioridades

## 🚀 Como Executar

### Opção 1 - Script Automático (Recomendado)
```bash
./executar.sh
```

### Opção 2 - Compilação Manual
```bash
# Navegar para o diretório src
cd src

# Compilar recursivamente todos os packages
find . -name "*.java" -print > sources.txt
javac -cp . @sources.txt

# Executar o sistema
java br.ifpb.diagnosticos.sistema.SistemaExamesMedicos

# Limpar
rm sources.txt
```

### Opção 3 - Makefile
```bash
cd src
make run-packages
```

## 🎯 Vantagens da Organização por Domínio

### ✅ **Coesão Alta**
- Classes relacionadas ao mesmo domínio ficam juntas
- Facilita manutenção e evolução

### ✅ **Baixo Acoplamento**
- Separação clara entre responsabilidades
- Cada package tem um propósito específico

### ✅ **Facilita Testes**
- Testes podem ser organizados por domínio
- Mocks e stubs mais específicos

### ✅ **Escalabilidade**
- Fácil adicionar novos tipos de exame em `exames.tipos`
- Novos formatos de laudo em `laudos.formatos`
- Novas validações em `validacao`

### ✅ **Compreensão do Negócio**
- Estrutura reflete o domínio real
- Desenvolvedores entendem melhor o contexto

## 📊 Mapeamento Domínio → Padrões

| Domínio | Padrões Aplicados |
|---------|-------------------|
| **Modelo** | Value Objects, Entities |
| **Exames** | Factory Method, Template Method |
| **Laudos** | Bridge, Memento |
| **Validação** | Chain of Responsibility |
| **Notificação** | Observer |
| **Financeiro** | Strategy |
| **Gestão** | Priority Queue |
| **Sistema** | Facade |
| **Utils** | Singleton |

## 🔧 Extensibilidade por Domínio

### Novo Tipo de Exame
```java
// 1. Criar em exames.tipos
package br.ifpb.diagnosticos.exames.tipos;
public class Tomografia extends Exame { ... }

// 2. Criar criador em exames.criadores  
package br.ifpb.diagnosticos.exames.criadores;
public class CriadorTomografia extends CriadorExame { ... }

// 3. Criar validação em validacao
package br.ifpb.diagnosticos.validacao;
public class ValidacaoTomografia extends ValidadorBase { ... }

// 4. Criar laudo em laudos.tipos
package br.ifpb.diagnosticos.laudos.tipos;
public class LaudoTomografia extends Laudo { ... }
```

### Novo Formato de Laudo
```java
package br.ifpb.diagnosticos.laudos.formatos;
public class JSON implements FormatoLaudo { ... }
```

### Nova Notificação
```java
package br.ifpb.diagnosticos.notificacao;
public class TelegramNotificador implements Observador { ... }
```

## 🎉 Benefícios Alcançados

- ✅ **Organização Intuitiva**: Estrutura reflete o negócio real
- ✅ **Manutenibilidade**: Mudanças isoladas por domínio
- ✅ **Testabilidade**: Testes organizados por contexto
- ✅ **Escalabilidade**: Fácil extensão em qualquer domínio
- ✅ **Padrões Aplicados**: 10 padrões GoF integrados naturalmente
- ✅ **Clean Architecture**: Separação clara de responsabilidades

**A organização por domínio de negócio resulta em um código mais limpo, coeso e profissional!**

## 🎯 Funcionalidades Demonstradas

1. **Solicitação de Exames**: Criação de diferentes tipos de exames com prioridades e descontos
2. **Fila de Prioridade**: Organização automática dos exames por urgência
3. **Processamento**: Execução dos exames seguindo o Template Method
4. **Validação**: Aplicação de regras específicas usando Chain of Responsibility
5. **Emissão de Laudos**: Geração em múltiplos formatos (Texto, HTML, PDF)
6. **Notificações**: Envio automático via email, SMS e WhatsApp
7. **Histórico**: Gerenciamento de versões das observações usando Memento
8. **Descontos**: Aplicação flexível de diferentes políticas de desconto

## 📊 Saída do Sistema

O programa demonstra todos os padrões implementados com saída detalhada mostrando:
- Criação e priorização de exames
- Aplicação de descontos
- Processamento na ordem de prioridade
- Validações específicas por tipo de exame
- Geração de laudos em diferentes formatos
- Notificações automáticas
- Gerenciamento de histórico de observações

## 🏗️ Extensibilidade

O sistema foi projetado para ser facilmente extensível:
- **Novos tipos de exame**: Implementar `Exame` e criar `CriadorExame` correspondente
- **Novos formatos de laudo**: Implementar `FormatoLaudo`
- **Novos tipos de validação**: Implementar `ValidadorBase`
- **Novos tipos de notificação**: Implementar `Observador`
- **Novos tipos de desconto**: Implementar `DescontoStrategy`

Todos seguindo o princípio Open/Closed, permitindo extensão sem modificação do código existente.
