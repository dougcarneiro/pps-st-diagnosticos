#!/bin/bash

echo "🔄 Reorganizando código para packages por domínio de negócio..."

# Criar diretório temporário para as classes refatoradas
mkdir -p temp_refactor

# Lista de arquivos para processar
classes=(
    "ValidacaoRessonancia:br.ifpb.diagnosticos.validacao"
    "ValidacaoUltrassonografia:br.ifpb.diagnosticos.validacao"
    "FormatoLaudo:br.ifpb.diagnosticos.laudos.formatos"
    "HTML:br.ifpb.diagnosticos.laudos.formatos"
    "PDF:br.ifpb.diagnosticos.laudos.formatos"
    "Texto:br.ifpb.diagnosticos.laudos.formatos"
    "Laudo:br.ifpb.diagnosticos.laudos"
    "LaudoHemograma:br.ifpb.diagnosticos.laudos.tipos"
    "LaudoUltrassonografia:br.ifpb.diagnosticos.laudos.tipos"
    "LaudoRessonanciaMagnetica:br.ifpb.diagnosticos.laudos.tipos"
    "Observador:br.ifpb.diagnosticos.notificacao"
    "EmailNotificador:br.ifpb.diagnosticos.notificacao"
    "SmsNotificador:br.ifpb.diagnosticos.notificacao"
    "WhatsAppNotificador:br.ifpb.diagnosticos.notificacao"
    "Observacao:br.ifpb.diagnosticos.laudos"
    "ObservacaoMemento:br.ifpb.diagnosticos.laudos"
    "HistoricoObservacao:br.ifpb.diagnosticos.laudos"
    "FilaPrioridadeExames:br.ifpb.diagnosticos.gestao"
    "LaboratorioFacade:br.ifpb.diagnosticos.sistema"
    "CarregadorCSV:br.ifpb.diagnosticos.utils"
    "SistemaExamesMedicos:br.ifpb.diagnosticos.sistema"
)

echo "✅ Script de reorganização criado!"
echo "⚠️  Execute manualmente os movimentos devido à complexidade dos imports"
