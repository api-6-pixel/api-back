#!/bin/bash

# === CONFIGURAÇÃO ===
HOST="db.fxrocxjbqfdbamwncjgt.supabase.co"
PORT=5432
DB="postgres"
USER="postgres"
PGPASSWORD="pixel"

# === BACKUP ===
DATA=$(date +%Y%m%d_%H%M%S)
ARQUIVO="backup_${DB}.dump"

echo "🔄 Iniciando backup do banco '$DB' em $DATA..."

PGPASSWORD=$PGPASSWORD pg_dump \
  -h "$HOST" \
  -p "$PORT" \
  -U "$USER" \
  -d "$DB" \
  -F c \
  -b \
  -v \
  -f "$ARQUIVO"

# === RESULTADO ===
if [ $? -eq 0 ]; then
  echo "✅ Backup criado com sucesso: $ARQUIVO"
else
  echo "❌ Erro ao gerar backup."
fi
