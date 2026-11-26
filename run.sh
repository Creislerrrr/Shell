#!/usr/bin/env bash
# Compiles the shell into out/ and starts it.
set -e
cd "$(dirname "$0")"
rm -rf out
javac -d out $(find src -name '*.java')
exec java -cp out shell.Main "$@"
