#!/usr/bin/env bash
if [ "$TRAVIS_BRANCH" = 'master' ] && [ "$TRAVIS_PULL_REQUEST" == 'false' ]; then
    openssl aes-256-cbc -K $encrypted_99e0f175f049_key -iv $encrypted_99e0f175f049_iv -in cd/codesigning.asc.enc -out cd/codesigning.asc -d
    gpg --fast-import cd/codesigning.asc

    openssl aes-256-cbc -K $encrypted_99e0f175f049_key -iv $encrypted_99e0f175f049_iv -in apache-mail.properties.enc -out apache-mail.properties -d
    openssl aes-256-cbc -K $encrypted_99e0f175f049_key -iv $encrypted_99e0f175f049_iv -in aws-mail.properties.enc -out aws-mail.properties -d
    openssl aes-256-cbc -K $encrypted_99e0f175f049_key -iv $encrypted_99e0f175f049_iv -in java-mail.properties.enc -out java-mail.properties -d
fi
