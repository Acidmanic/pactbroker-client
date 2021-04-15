
BASE=target/broker-client

mvn clean install && \
mkdir $BASE && \
cp target/application-standalone.jar $BASE && \
echo 'java -jar application-standalone.jar $@' > $BASE/run.sh && \
chmod +x $BASE/run.sh

