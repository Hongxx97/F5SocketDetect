export  JAVA_HOME=/usr/local/src/java/jdk1.8
export  AMLSERVER_HOME=/home/weblogic/F5SocketDetect/
CLASSPATH=$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
CLASSPATH=$CLASSPATH:$AMLSERVER_HOME/bin
CLASSPATH=$CLASSPATH:$AMLSERVER_HOME/lib/commons-logging.jar
CLASSPATH=$CLASSPATH:$AMLSERVER_HOME/lib/commons-logging-api-1.0.4.jar
CLASSPATH=$CLASSPATH:$AMLSERVER_HOME/lib/istcommon.jar
CLASSPATH=$CLASSPATH:$AMLSERVER_HOME/lib/log4j-1.2.8.jar
CLASSPATH=$CLASSPATH:$AMLSERVER_HOME/lib/jdom.jar
CLASSPATH=$CLASSPATH:$AMLSERVER_HOME/lib/servlet.jar
export CLASSPATH
#$JAVA_HOME/bin/java -Xms128m -Xmx128m com.ist.f5.LocalDetectInfo2F5   >/dev/null &
$JAVA_HOME/bin/java -Xms128m -Xmx128m com.ist.f5.LocalDetectInfo2F5  > nohup.out &
