#!/usr/bin/env bash

APP_PATH=$1
SKYWALKING_AGENT="-javaagent:/opt/skywalking/agent/skywalking-agent.jar"
SKYWALKING_AGENT_CODE="-Dskywalking.agent.application_code=$2"
SPRING_PROFILE="--spring.cloud.config.profile=$3"

#Kill running service
killPid(){
    local pid=`jps -v|grep "$1"|awk '{print $1}'|xargs`
    echo "Firsh,kill $1 old process: $pid"
    if [ -n ${pid} ];
        then kill -9 ${pid}
    fi

    pid=`jps -v|grep "$1"|awk '{print $1}'|xargs`
    echo "Check pid=$pid."
    [[ -n ${pid} ]] && killPid $1
}

#Run service
run(){
    echo "Startup $APP_PATH/$1.jar"
    if [ -e "$APP_PATH/$1.jar" ];
        then rm -f "$APP_PATH/$1.log"
             setsid java -Xms800M -Xmx800M ${SKYWALKING_AGENT} ${SKYWALKING_AGENT_CODE} -jar ${APP_PATH}/$1.jar ${SPRING_PROFILE} >${APP_PATH}/$1.log 2>&1 &
#             setsid java -Xms256M -Xmx800M -jar ${APP_PATH}/$1.jar ${SPRING_PROFILE} >${APP_PATH}/$1.log 2>&1 &
            echo "Finish run $APP_PATH/$1.jar"
        else
            echo "Not found file: $APP_PATH/$1.jar"
    fi
}

#kill service
killPid $2
#run service
run $2