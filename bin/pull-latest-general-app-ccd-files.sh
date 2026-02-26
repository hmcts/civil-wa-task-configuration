#!/usr/bin/env bash

set -eu

branchName=${1:-master}

echo "civil-general-apps-ccd-definition has been merged into civil-ccd-definition."
echo "Pulling from civil-ccd-definition instead."

exec "$(dirname "$0")/pull-latest-civil-ccd-files.sh" "${branchName}"

