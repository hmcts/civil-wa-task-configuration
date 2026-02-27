#!/usr/bin/env bash

set -eu

ccdRepoName="civil-general-apps-ccd-definition"
branchName=${1:-master}

# Checkout specific branch of GA CCD definitions.
git clone https://github.com/hmcts/${ccdRepoName}.git
cd "${ccdRepoName}"

echo "Switch to ${branchName} branch on ${ccdRepoName}"
git checkout "${branchName}"
cd ..

rm -rf ./ga-ccd-definition
cp -r "./${ccdRepoName}/ga-ccd-definition" .

rm -rf "./${ccdRepoName}"
