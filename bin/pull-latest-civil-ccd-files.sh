#!/usr/bin/env bash

branchName=$1

#Checkout specific branch pf  civil ccd definition
git clone https://github.com/hmcts/civil-ccd-definition.git
cd civil-ccd-definition

echo "Switch to ${branchName} branch on civil-ccd-definition"
git checkout ${branchName}
cd ..

#Clear previous files before we pull latest 
rm -rf e2e package.json yarn.lock codecept.conf.js

cp -r ./civil-ccd-definition/e2e .
cp -r ./civil-ccd-definition/package.json .
cp -r ./civil-ccd-definition/yarn.lock .
cp -r ./civil-ccd-definition/codecept.conf.js .
echo *
rm -rf ./civil-ccd-definition