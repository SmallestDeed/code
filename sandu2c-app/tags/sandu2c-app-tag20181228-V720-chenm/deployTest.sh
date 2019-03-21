#!/usr/bin/env bash

git checkout -b test
git push -f origin test
git checkout dev
git branch -D test