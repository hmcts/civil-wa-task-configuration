#!/usr/bin/env bash

set -eu

refName=${1:-master}

git clone https://github.com/hmcts/civil-service.git
cd civil-service

if [[ "${refName}" =~ ^pr-?([0-9]+)$ ]]; then
  prNumber="${BASH_REMATCH[1]}"
  echo "Switch to PR ${prNumber} on civil-service"
  git fetch --depth=1 origin "refs/pull/${prNumber}/head"
  git checkout FETCH_HEAD
else
  echo "Switch to ${refName} branch on civil-service"
  git checkout "${refName}"
fi

cd ..
mkdir -p civil-bpmn

cp -r ./civil-service/src/main/resources/camunda ./civil-bpmn/.
rm -rf ./civil-service

./bin/import-bpmn-diagram.sh ./civil-bpmn/.
rm -rf ./civil-bpmn
