#!/bin/bash

# === CONFIGURAÇÃO ===
HOST="db.fxrocxjbqfdbamwncjgt.supabase.co"
PORT=5432
DB="postgres"
USER="postgres"
PGPASSWORD="pixel"
ARQUIVO_RESTORE="backup_postgres.dump"  # <== altere aqui

# === RESTORE ===
echo "🔄 Iniciando restauração do backup '$ARQUIVO_RESTORE' no banco '$DB'..."

PGPASSWORD=$PGPASSWORD pg_restore \
  -h "$HOST" \
  -p "$PORT" \
  -U "$USER" \
  -d "$DB" \
  -v \
  --clean \
  "$ARQUIVO_RESTORE"

# === RESULTADO ===
if [ $? -eq 0 ]; then
  echo "✅ Restauração concluída com sucesso."
else
  echo "❌ Erro ao restaurar backup."
fi
