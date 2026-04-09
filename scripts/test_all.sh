#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
MAVEN_TEST_CMD=(mvn test)

if [[ -n "${MAVEN_REPO_LOCAL:-}" ]]; then
  MAVEN_TEST_CMD=(mvn "-Dmaven.repo.local=${MAVEN_REPO_LOCAL}" test)
fi

echo "Running backend tests..."
cd "$ROOT_DIR/vendi_backend"
"${MAVEN_TEST_CMD[@]}"
echo "Backend tests passed."
