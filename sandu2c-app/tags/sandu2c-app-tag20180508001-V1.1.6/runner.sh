#!/usr/bin/env bash

APP_PATH=$1

#Kill running service
killPid(){
    local pid=`jps -v|grep "$1"|awk '{print $1}'|xargs`
    echo "Firsh,kill $1 old process: $pid"
    if [ -n $pid ];
        then kill -9 $pid
    fi

    pid=`jps -v|grep "$1"|awk '{print $1}'|xargs`
    echo "Check pid=$pid."
    [[ -n $pid ]] && killPid $1
}

#Run service
run(){
    echo "Startup $APP_PATH/$1"
    if [ -e "$APP_PATH"/$1 ];
        then
            rm -f "$APP_PATH"/$1.log
            java -jar "$APP_PATH"/$1 --spring.profiles.active=dev >"$APP_PATH"/$1.log 2>&1 &
            sleep 6s
            echo "Finish run $APP_PATH/$1"
        else
            echo "Not found file: $APP_PATH/$1"
    fi
}

#kill service
killPid $2
#run service
run $2