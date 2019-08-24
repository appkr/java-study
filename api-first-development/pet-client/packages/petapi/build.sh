#!/usr/bin/env bash

set -e

BIN=openapi-generator
PROJECT=petapi

BIN_PATH=$(which "${BIN}")
if [[ "${BIN_PATH}" = "" ]]; then
    echo -e "\033[31m${BIN} 바이너리를 찾을 수 없습니다\033[0m";
    echo -e "쉘에서 \033[1mbrew install ${BIN}\033[0m 명령으로 다운로드 받은 후 재시도하세요"
    exit 1;
fi;

SPEC_PATH=$1
if [[ "${SPEC_PATH}" = "" ]]; then
    echo -e "\033[31mAPI 스펙 파일 경로를 제출하지 않았습니다\033[0m";
    echo ""
    echo "사용법:"
    echo -e "  ~/pet-client/packages/${PROJECT} $ \033[1mbash build.sh api.yml\033[0m"
    echo -e "  e.g. bash build.sh https://raw.githubusercontent.com/appkr/spring-study/master/api-first-development/pet-service/src/main/resources/swagger/api.yml"
    exit 1;
fi;

confirm() {
    read -r -p "${1:-Are you sure? [y/N]} " response
    case "$response" in
        [yY][eE][sS]|[yY])
            true
            ;;
        *)
            false
            ;;
    esac
}

confirm "기존 빌드에서 생성된 API 클라이언트 코드를 삭제할까요? N을 선택하면 기존 빌드 파일을 덮어씁니다. [y/N]" \
    && rm -rf php

if [[ "${SPEC_PATH}" == http* ]]; then
    echo -e "\033[32mAPI 스펙을 내려받고 있습니다...\033[0m";
    echo ""
    curl -s -XGET "${SPEC_PATH}" > api.yml
else
    echo -e "\033[32m로컬 API 스펙을 사용합니다\033[0m";
    echo ""
    [ ! -f "api.yml" ] && cp "${SPEC_PATH}" api.yml
fi

echo -e "API 클라이언트 코드를 생성합니다\033[0m";
echo ""

$BIN_PATH generate \
    --generator-name php \
    --config config-php.json \
    --input-spec api.yml \
    --output php \
    --skip-validate-spec

echo ""
echo -e "\033[32mcomposer.json 파일에 버전을 추가합니다\033[0m"
echo ""
ruby -r json -r date -e "json = JSON.load(File.read('./php/composer.json')); json['version'] = DateTime.now().strftime('%Y%m%d%H%I'); File.write('./php/composer.json', JSON.pretty_generate(json))"

echo -e "\033[32m새로 생성된 코드를 검토하고, API 클라이언트를 교체하세요\033[0m"
echo -e "  ~/pet-client/packages/${PROJECT} $ \033[1mcd ../..\033[0m"
echo -e "  ~/pet-client $ \033[1mcomposer require example/${PROJECT} -vvv\033[0m"