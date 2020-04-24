#!/bin/bash
kill $(ps aux | grep 'lt -p 8111 -s autohome -h http://serverless.social/' | grep -v grep | awk '{print $2}')
sleep 1
lt -p 8111 -s autohome -h "http://serverless.social/"
