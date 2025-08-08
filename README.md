# Sistema de Exames Médicos - ST Diagnósticos

## 📋 Visão Geral

Sistema completo de controle de exames médicos e emissão de laudos, implementando **9 padrões de projeto GoF** de forma integrada e profissional. O projeto demonstra arquitetura por domínio de negócio, sistema de configuração avançado, carregamento de dados via CSV e build automatizado.

**Status**: ✅ **100% Funcional** | **42 classes Java** | **9 Padrões GoF** | **9 Domínios organizados**

## 🚀 Execução Rápida

### Método Recomendado (Makefile)
```bash
# Executar o sistema completo
make run

# Ver demonstração de configuração
make demo

# Ajuda completa
make help
```

### Métodos Alternativos
```bash
# Scripts bash (legados)
./scripts/executar.sh

# Execução manual
java -cp build br.ifpb.diagnosticos.sistema.SistemaExamesMedicos
```

## 📦 Arquitetura por Domínio de Negócio

O sistema utiliza **Domain-Driven Design (DDD)** com packages organizados por domínio de negócio:

```
projeto/
├── 📄 Makefile                  # Sistema de build automatizado
├── 📁 src/br/ifpb/diagnosticos/ # Código fonte principal
│   ├── 📋 modelo/               # Entidades (Paciente, Medico, Prioridade)
│   ├── 🔬 exames/               # Domínio de exames médicos
│   │   ├── tipos/               # Hemograma, Ultrassom, Ressonância
│   │   └── criadores/           # Factory Methods
│   ├── 📄 laudos/               # Domínio de laudos médicos
│   │   ├── tipos/               # Laudos específicos por tipo
│   │   └── formatos/            # PDF, HTML, Texto (Bridge)
│   ├── ✅ validacao/            # Chain of Responsibility
│   ├── 📧 notificacao/          # Observer Pattern
│   ├── 💰 financeiro/           # Strategy Pattern (descontos)
│   ├── 🎯 gestao/               # Priority Queue
│   ├── 🏥 sistema/              # Facade + classes principais
│   └── 🛠️ utils/               # Singleton, CSV, Configuração
├── 📁 build/                    # Arquivos compilados (.class)
├── 📁 config/                   # Configurações do sistema
│   └── sistema.properties       # Configurações persistentes
├── 📁 dados/                    # Arquivos CSV
│   ├── pacientes.csv           # Dados de pacientes
│   └── medicos.csv             # Dados de médicos
└── 📄 scripts/                  # Scripts bash legados
    ├── executar.sh             # Script original de execução
    └── compilar.sh             # Script original de compilação
```

## 🎨 Padrões de Projeto Implementados (9 GoF)

### 1. **Singleton** - Gerador de Números + Configuração
- **Package**: `utils`
- **Classes**: `GeradorNumeroExame`, `ConfiguracaoSistema`
- **Propósito**: Garantir instância única para numeração e configurações

### 2. **Strategy** - Descontos Configuráveis
- **Package**: `financeiro`
- **Classes**: `DescontoStrategy`, `DescontoConvenio`, `DescontoIdoso`
- **Propósito**: Aplicar diferentes políticas de desconto (configuráveis via properties)

### 3. **Factory Method** - Criação de Exames
- **Package**: `exames.criadores`
- **Classes**: `CriadorExame`, `CriadorHemograma`, etc.
- **Propósito**: Criar diferentes tipos de exames sem acoplamento

### 4. **Template Method** - Realização de Exames
- **Package**: `exames`
- **Classe**: `Exame` (método `realizarExame()`)
- **Propósito**: Definir algoritmo padrão para execução de exames

### 5. **Chain of Responsibility** - Validação
- **Package**: `validacao`
- **Classes**: `Validador`, `ValidadorBase`, `ValidacaoHemograma`, etc.
- **Propósito**: Validações sequenciais e extensíveis por tipo de exame

### 6. **Bridge** - Laudos e Formatos
- **Packages**: `laudos` e `laudos.formatos`
- **Classes**: `Laudo`, `FormatoLaudo`, `PDF`, `HTML`, `Texto`
- **Propósito**: Separar abstração (laudo) da implementação (formato)

### 7. **Observer** - Notificações Multi-canal
- **Package**: `notificacao`
- **Classes**: `Observador`, `EmailNotificador`, `SmsNotificador`, `WhatsAppNotificador`
- **Propósito**: Notificações automáticas configuráveis

### 8. **Memento** - Histórico de Observações
- **Package**: `laudos`
- **Classes**: `Observacao`, `ObservacaoMemento`, `HistoricoObservacao`
- **Propósito**: Controle de versão das observações

### 9. **Facade** - Interface Simplificada
- **Package**: `sistema`
- **Classe**: `LaboratorioFacade`
- **Propósito**: Interface unificada para operações complexas

## Sistema de Build Automatizado

### Comandos Principais (Makefile)

| Comando | Descrição | Uso |
|---------|-----------|-----|
| `make run` | Compila e executa sistema | Uso diário |
| `make demo` | Demonstração de configuração | Ver funcionalidades |
| `make clean` | Limpa arquivos compilados | Manutenção |
| `make info` | Informações do projeto | Status atual |
| `make help` | Ajuda completa | Descobrir comandos |

### Comandos Avançados

```bash
# Desenvolvimento
make compile         # Apenas compilar
make test            # Executar sistema + demo
make start           # Equivalente ao executar.sh

# Manutenção
make clean           # Limpar build
make clean-all       # Limpar tudo (build + config)
make check           # Verificar ambiente Java
make setup           # Criar estrutura de diretórios
```

## 🔧 Funcionalidades Avançadas

### 💾 Sistema de Configuração
- **Arquivo**: `config/sistema.properties`
- **Recursos**: Caminhos CSV, percentuais de desconto, dados do laboratório
- **Persistência**: Configurações salvas automaticamente
- **Flexibilidade**: Modificação dinâmica via `ConfiguracaoSistema`

```properties
# Exemplo de configuração
csv.pacientes.caminho=dados/pacientes.csv
csv.medicos.caminho=dados/medicos.csv
desconto.convenio.percentual=15
desconto.idoso.percentual=8
laboratorio.nome=ST DIAGNÓSTICOS
```

### 📁 Carregamento de Dados CSV
- **Pacientes**: `dados/pacientes.csv` (nome, cpf, convenio, idade)
- **Médicos**: `dados/medicos.csv` (nome, crm, especialidade)
- **Auto-criação**: Arquivos de exemplo criados automaticamente
- **Robustez**: Tratamento de erros e validação de dados

### 📊 Demonstrações Disponíveis

#### Sistema Principal (`make run`)
- Carregamento de 5 pacientes e 5 médicos via CSV
- Solicitação de 4 exames com diferentes prioridades
- Aplicação de descontos (Convênio 15%, Idoso 8%)
- Processamento por ordem de prioridade (URGENTE → POUCO_URGENTE → ROTINA)
- Geração de laudos em múltiplos formatos (PDF, HTML, Texto)
- Notificações automáticas (Email, SMS, WhatsApp)
- Validação específica por tipo de exame
- Histórico de modificações em observações

#### Demo de Configuração (`make demo`)
- Exibição de configurações atuais
- Modificação dinâmica de parâmetros
- Persistência de alterações
- Carregamento usando configurações
- Restauração de configurações originais

## 🎯 Vantagens da Arquitetura por Domínio

### ✅ Organização Profissional
- **Coesão Alta**: Classes relacionadas ficam juntas por contexto de negócio
- **Baixo Acoplamento**: Separação clara entre responsabilidades
- **Manutenibilidade**: Mudanças isoladas por domínio específico

### ✅ Escalabilidade e Flexibilidade
- **Novos Exames**: Adicionar facilmente em `exames.tipos`
- **Novos Formatos**: Expandir `laudos.formatos` conforme necessário
- **Novas Validações**: Estender `validacao` sem impacto
- **Novas Notificações**: Incluir em `notificacao` de forma isolada

### ✅ Compreensão do Negócio
- **Estrutura Intuitiva**: Reflete o domínio real do laboratório
- **Documentação Viva**: Código autodocumentado pela organização
- **Onboarding Rápido**: Novos desenvolvedores entendem facilmente

## 📊 Mapeamento Domínio → Padrões

| Domínio | Padrões Aplicados | Responsabilidade |
|---------|-------------------|------------------|
| **modelo** | Entities, Value Objects | Entidades de negócio |
| **exames** | Factory Method, Template Method | Criação e execução de exames |
| **laudos** | Bridge, Memento | Geração e versionamento |
| **validacao** | Chain of Responsibility | Validações por tipo |
| **notificacao** | Observer | Comunicação multi-canal |
| **financeiro** | Strategy | Políticas de desconto |
| **gestao** | Priority Queue | Gerenciamento de filas |
| **sistema** | Facade | Interface simplificada |
| **utils** | Singleton | Utilitários compartilhados |

## 🔧 Extensibilidade Prática

### Novo Tipo de Exame
```java
// 1. Criar tipo em exames.tipos
package br.ifpb.diagnosticos.exames.tipos;
public class Tomografia extends Exame {
    @Override
    protected void prepararPaciente() { /* específico */ }
    @Override  
    protected void realizarExameEspecifico() { /* específico */ }
}

// 2. Criar factory em exames.criadores
package br.ifpb.diagnosticos.exames.criadores;
public class CriadorTomografia extends CriadorExame {
    @Override
    public Exame criarExame(/* params */) {
        return new Tomografia(/* params */);
    }
}

// 3. Criar validação em validacao
package br.ifpb.diagnosticos.validacao;
public class ValidacaoTomografia extends ValidadorBase { /* regras */ }

// 4. Criar laudo em laudos.tipos
package br.ifpb.diagnosticos.laudos.tipos;
public class LaudoTomografia extends Laudo { /* específico */ }
```

### Novo Formato de Saída
```java
package br.ifpb.diagnosticos.laudos.formatos;
public class JSON implements FormatoLaudo {
    @Override
    public String formatarCabecalho(String laboratorio, String data) { /* JSON */ }
    @Override
    public String formatarDadosPaciente(/* params */) { /* JSON */ }
    // ... outros métodos
}
```

## 🚀 Requisitos Funcionais Atendidos

| Requisito | Status | Implementação |
|-----------|--------|---------------|
| **R1** - Carregamento CSV | ✅ | `CarregadorCSV` com pacientes e médicos |
| **R2** - Sistema de Prioridades | ✅ | `FilaPrioridadeExames` (URGENTE → ROTINA) |
| **R3** - Tipos de Exame | ✅ | Hemograma, Ultrassom, Ressonância |
| **R4** - Formatos de Laudo | ✅ | PDF, HTML, Texto (Bridge Pattern) |
| **R5** - Sistema de Descontos | ✅ | Convênio 15%, Idoso 8% (configurável) |
| **R6** - Validação de Dados | ✅ | Chain of Responsibility por tipo |
| **R7** - Notificações | ✅ | Email, SMS, WhatsApp (Observer) |
| **R8** - Histórico | ✅ | Memento para observações |
| **R9** - Interface Simplificada | ✅ | LaboratorioFacade |

## 📈 Métricas do Projeto

- **📁 Classes Java**: 42 arquivos organizados
- **🎨 Padrões GoF**: 9 implementados corretamente
- **📦 Domínios**: 9 packages por área de negócio
- **🔧 Configurações**: Sistema completo de properties
- **📊 CSV**: Carregamento de pacientes e médicos
- **🛠️ Build**: Makefile com 11 comandos
- **📚 Documentação**: 8 arquivos de documentação

## 🎯 Exemplos de Saída

### Execução do Sistema (`make run`)
```
=== SISTEMA DE EXAMES MÉDICOS - ST DIAGNÓSTICOS ===

0. CARREGAMENTO DE DADOS
========================
Carregados 5 pacientes do arquivo dados/pacientes.csv
Carregados 5 médicos do arquivo dados/medicos.csv

Médicos disponíveis:
- Dr(a). Dr. João Silva (CRM: 12345) - Clínico Geral
- Dr(a). Dr. Maria Santos (CRM: 67890) - Radiologista

1. SOLICITAÇÃO DE EXAMES
========================
Valor original: R$ 80.0 - Valor com desconto: R$ 68.0
Exame HEMOGRAMA solicitado para João Silva

2. FILA DE EXAMES (Priority Queue)
===================================
1. Ressonancia - Paciente: Maria Santos - Prioridade: URGENTE
2. Ultrassonografia - Paciente: Pedro - Prioridade: POUCO_URGENTE

3. PROCESSAMENTO DOS EXAMES (Template Method)
==============================================
Processando exame de Ressonância Magnética...

4. GERAÇÃO DE LAUDOS (Bridge + Memento + Observer)
==================================================
=== FORMATO PDF ===
LABORATÓRIO ST DIAGNÓSTICOS
Paciente: Maria Santos - Idade: 72
TIPO: RESSONÂNCIA MAGNÉTICA
[...detalhes do laudo...]

📧 Email enviado para Maria Santos
📱 SMS enviado para (11) 99999-9999
```

## 🛡️ Qualidade e Padrões

### ✅ Princípios SOLID
- **S**ingle Responsibility: Cada classe tem uma responsabilidade
- **O**pen/Closed: Extensível sem modificar código existente
- **L**iskov Substitution: Subclasses substituem classes base
- **I**nterface Segregation: Interfaces específicas por necessidade
- **D**ependency Inversion: Dependências por abstrações

### ✅ Clean Code
- **Nomes Descritivos**: Classes e métodos autoexplicativos
- **Métodos Pequenos**: Responsabilidades bem definidas
- **Comentários Úteis**: Javadoc e explicações de negócio
- **Organização**: Estrutura consistente e lógica

## 🏆 Conclusão

Este projeto demonstra a implementação profissional de **9 padrões GoF** em um sistema real de exames médicos. A arquitetura por domínio de negócio, combinada com o sistema de configuração avançado e build automatizado, resulta em:

- ✅ **Código Profissional**: Organizado, testável e manutenível
- ✅ **Padrões Aplicados**: Implementação correta e integrada
- ✅ **Sistema Completo**: Do carregamento CSV até notificações
- ✅ **Arquitetura Sólida**: Domain-Driven Design aplicado
- ✅ **Ferramentas Modernas**: Build automatizado e configuração flexível

**Sistema 100% funcional e pronto para evolução!** 🚀
