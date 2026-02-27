#!/usr/bin/env bash

set -eu

if [ ! -d "ccd-definition/generalapplication" ]; then
  echo "Unable to locate general application CCD definition directory at ccd-definition/generalapplication."
  exit 1
fi

definition_input_dir=""
if [ -d "ga-ccd-definition" ]; then
  definition_input_dir=$(realpath "ga-ccd-definition")
else
  definition_input_dir=$(realpath "ccd-definition/generalapplication")
fi

definition_output_file="$(realpath ".")/build/ccd-development-config/ccd-civil-apps-dev.xlsx"
params=("$@")

CCD_CASE_TYPE_ID=GENERALAPPLICATION ./bin/utils/import-ccd-definition.sh "${definition_input_dir}" "${definition_output_file}" "${params[@]}"
