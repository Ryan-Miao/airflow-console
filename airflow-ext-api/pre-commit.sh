#!/usr/bin/env bash


mvn clean install pmd:check  pmd:cpd  checkstyle:check  findbugs:check