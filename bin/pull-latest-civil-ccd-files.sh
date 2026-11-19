#!/usr/bin/env bash

branchName=$1
# The civil-service ref whose ccd-definition/SOURCE_OF_TRUTH decides where the CCD definitions come from
# (see below). Branch, tag, full commit SHA, or pr-N for the head of pull request N.
civilServiceRef=${2:-master}

#Checkout specific branch pf  civil ccd definition
git clone https://github.com/hmcts/civil-ccd-definition.git
cd civil-ccd-definition

echo "Switch to ${branchName} branch on civil-ccd-definition"
git checkout ${branchName}
cd ..

#Clear previous files before we pull latest
rm -rf e2e playwright-e2e wa plugins package.json yarn.lock codecept.conf.js playwright.config.ts playwright-categories.json tsconfig.json

# The Civil CCD definitions are moving from civil-ccd-definition into civil-service. civil-service's
# bin/shared/pull-ccd-definitions.sh copies ccd-definition/civil and ccd-definition/generalapplication from
# whichever repo is their source of truth at civilServiceRef: civil-ccd-definition at branchName while
# ccd-definition/SOURCE_OF_TRUTH is absent from civil-service (as before the move), civil-service once it
# is present. It is fetched from civil-service master, and if anything fails this script fails rather than
# carry on without definitions. The test code below still comes from civil-ccd-definition at branchName.
pullScript=$(mktemp)
if ! curl -fsSL --retry 3 "https://raw.githubusercontent.com/hmcts/civil-service/master/bin/shared/pull-ccd-definitions.sh" -o "${pullScript}" \
  || ! bash "${pullScript}" "${civilServiceRef}" "${branchName}" ./ccd-definition; then
  echo "ERROR: could not copy the CCD definitions." >&2
  rm -f "${pullScript}"
  rm -rf ./civil-ccd-definition
  exit 1
fi
rm -f "${pullScript}"

cp -r ./civil-ccd-definition/e2e .
cp -r ./civil-ccd-definition/wa .
cp -r ./civil-ccd-definition/playwright-e2e .
cp -r ./civil-ccd-definition/playwright-categories.json .
cp -r ./civil-ccd-definition/tsconfig.json .
cp -r ./civil-ccd-definition/plugins .
cp -r ./civil-ccd-definition/package.json .
cp -r ./civil-ccd-definition/bin/run-commands-series.sh ./bin/run-commands-series.sh
cp -r ./civil-ccd-definition/yarn.lock .
cp -r ./civil-ccd-definition/.yarnrc.yml .
cp -r ./civil-ccd-definition/.yarn .
cp -r ./civil-ccd-definition/codecept.conf.js .
cp -r ./civil-ccd-definition/playwright.config.ts .
cp -r ./civil-ccd-definition/saucelabs.conf.js .
echo *
rm -rf ./civil-ccd-definition
