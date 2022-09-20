#!/usr/bin/env bash

set -ex
workspace=${1}
env=${2}

s2sSecret=${S2S_SECRET:-AABBCCDDEEFFGGHH}

if [[ "${env}" == 'prod' ]]; then
  s2sSecret=${S2S_SECRET_PROD}
fi

serviceToken=$($(realpath $workspace)/bin/utils/idam-lease-service-token.sh civil_service \
  $(docker run --rm toolbelt/oathtool --totp -b ${s2sSecret}))
dmnFilepath="$(realpath $workspace)/src/main/resources"
bpmnFilepath="$(realpath $workspace)/camunda"
if [ -d ${bpmnFilepath} ]
then
  allFiles=$(find ${dmnFilepath} -name '*.dmn')+$(find ${bpmnFilepath} -name '*.bpmn')
else
  allFiles=$(find ${dmnFilepath} -name '*.dmn')
fi

for file in ${allFiles}
do
  uploadResponse=$(curl --insecure -v --silent -w "\n%{http_code}" --show-error -X POST \
    ${CAMUNDA_BASE_URL:-http://localhost:9404}/engine-rest/deployment/create \
    -H "Accept: application/json" \
    -H "ServiceAuthorization: Bearer ${serviceToken}" \
    -F "deployment-name=$(basename ${file})" \
    -F "deploy-changed-only=true" \
    -F "file=@${allFiles}/$(basename ${file})")

upload_http_code=$(echo "$uploadResponse" | tail -n1)
upload_response_content=$(echo "$uploadResponse" | sed '$d')

if [[ "${upload_http_code}" == '200' ]]; then
  echo "$(basename ${file}) diagram uploaded successfully (${upload_response_content})"
  continue;
fi

echo "$(basename ${file}) upload failed with http code ${upload_http_code} and response (${upload_response_content})"
continue;

done
exit 0;
