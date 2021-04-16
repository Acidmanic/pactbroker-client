
BASE=target/broker-client

rm -rf $BASE

mvn clean install && \
mkdir $BASE && \
cp target/application-standalone.jar $BASE && \
echo 'DIR=$(dirname $BASH_SOURCE)' > $BASE/run.sh && \
echo 'DIR=$(realpath $DIR)' >> $BASE/run.sh && \
echo 'JAR=$DIR/application-standalone.jar' >> $BASE/run.sh && \
echo 'java -jar $JAR $@' >> $BASE/run.sh && \
chmod +x $BASE/run.sh

