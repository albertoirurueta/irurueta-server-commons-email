#!/usr/bin/env bash
if [ "$TRAVIS_BRANCH" = 'master' ] && [ "$TRAVIS_PULL_REQUEST" == 'false' ]; then
    gpg --fast-import config/codesigning.asc
fi
