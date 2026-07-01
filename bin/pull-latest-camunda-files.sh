#!/usr/bin/env bash
# Pulls Camunda BPMN files from civil-service using git sparse-checkout.
# Only downloads the src/main/resources/camunda directory, not the entire repo.
#
# Usage: ./pull-latest-camunda-files.sh [ref]
#   ref: branch name or "pr-<number>" (default: master)

set -eu

REPO_URL="https://github.com/hmcts/civil-service.git"
REF_NAME="${1:-master}"
TEMP_DIR=".civil-service-sparse"
SPARSE_PATH="src/main/resources/camunda"

echo "Pulling camunda files from civil-service (ref: ${REF_NAME})"

rm -rf "${TEMP_DIR}"

if [[ "${REF_NAME}" =~ ^pr-?([0-9]+)$ ]]; then
  prNumber="${BASH_REMATCH[1]}"
  echo "Fetching PR ${prNumber} on civil-service"
  git init "${TEMP_DIR}"
  cd "${TEMP_DIR}"
  git remote add origin "${REPO_URL}"
  git sparse-checkout set --no-cone "${SPARSE_PATH}"
  git fetch --filter=blob:none --depth=1 origin "refs/pull/${prNumber}/head"
  git checkout FETCH_HEAD
  cd ..
else
  git clone --filter=blob:none --no-checkout --depth 1 --branch "${REF_NAME}" \
    "${REPO_URL}" "${TEMP_DIR}"
  cd "${TEMP_DIR}"
  git sparse-checkout set --no-cone "${SPARSE_PATH}"
  git checkout
  cd ..
fi

if [ ! -d "${TEMP_DIR}/${SPARSE_PATH}" ]; then
  echo "ERROR: ${SPARSE_PATH} not found in civil-service@${REF_NAME}"
  rm -rf "${TEMP_DIR}"
  exit 1
fi

# Copy camunda dir to workspace root so import-bpmn-diagram.sh can find
# both bin/shared/ and camunda/ relative to the same workspace path.
cp -r "${TEMP_DIR}/${SPARSE_PATH}" ./camunda
rm -rf "${TEMP_DIR}"

./bin/import-bpmn-diagram.sh .
rm -rf ./camunda

echo "Done: camunda files imported from civil-service@${REF_NAME}"
