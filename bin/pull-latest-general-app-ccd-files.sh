#!/usr/bin/env bash

branchName=$1

#Checkout specific branch pf  civil general apps ccd definition
git clone https://github.com/hmcts/civil-general-apps-ccd-definition.git
cd civil-general-apps-ccd-definition

echo "Switch to ${branchName} branch on civil-general-apps-ccd-definition"
git checkout ${branchName}
cd ..

#Clear previous files before we pull latest 
rm -rf e2e package.json yarn.lock codecept.conf.js

cp -r ./civil-general-apps-ccd-definition/e2e .
cp -r ./civil-general-apps-ccd-definition/package.json .
cp -r ./civil-general-apps-ccd-definition/yarn.lock .
cp -r ./civil-general-apps-ccd-definition/codecept.conf.js .
echo *
rm -rf ./civil-general-apps-ccd-definition


