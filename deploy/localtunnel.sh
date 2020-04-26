#!/bin/bash
pid=$(ps aux | grep 'lt -p 8111 -s autohome -h http://serverless.social/' | grep -v grep | awk '{print $2}')
if ! test -z "$pid" 
then
   echo "$pid is running. killing it"
   kill $pid
   sleep 2
fi
lt -p 8111 -s autohome -h "http://serverless.social/"
