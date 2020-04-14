#!/bin/bash
java -jar /opt/git/repo/autohome/target/autohome.jar &
lt -p 8111 -s autohome -h "http://serverless.social/"
