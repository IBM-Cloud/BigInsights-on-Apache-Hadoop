#!/bin/bash

SPARK_HOME=/usr/iop/4.3.0.0/spark2/
SYSTEMML_CLASS=org.apache.sysml.api.DMLScript
SYSTEMML_JAR=/usr/iop/current/systemml-client/lib/systemml.jar
SYSTEMML_SCRIPTS=/usr/iop/current/systemml-client/scripts

submit()
{
${SPARK_HOME}/bin/spark-submit \
    --master yarn --deploy-mode cluster \
    --class ${SYSTEMML_CLASS} ${SYSTEMML_JAR} \
    -f ${SYSTEMML_SCRIPTS}/$1 \
    -exec spark \
    -nvargs $2
}

# Abort if any command fails
set -e

echo
echo "Submit genLinearRegressionData"
submit datagen/genLinearRegressionData.dml 'numSamples=1000 numFeatures=50 maxFeatureValue=5 maxWeight=5 addNoise=FALSE b=0 sparsity=0.7 output=linRegData.csv format=csv perc=0.5'
rc=$?

echo
echo "Submit Sample"
submit utils/sample.dml 'X=linRegData.csv sv=perc.csv O=linRegDataParts ofmt=csv'
rc=$?

echo
echo "Submit splitXY train"
submit utils/splitXY.dml 'X=linRegDataParts/1 y=51 OX=linRegData.train.data.csv OY=linRegData.train.labels.csv ofmt=csv'
rc=$?

echo
echo "Submit splitXY test data"
submit utils/splitXY.dml 'X=linRegDataParts/2 y=51 OX=linRegData.test.data.csv OY=linRegData.test.labels.csv ofmt=csv'
rc=$?

echo
echo "Submit LinearRegDS"
submit algorithms/LinearRegDS.dml 'X=linRegData.train.data.csv Y=linRegData.train.labels.csv B=betas.csv fmt=csv'
rc=$?

### This Steps is commented out for now
### See JIRA: https://issues.apache.org/jira/browse/SYSTEMML-955
# echo
# echo "Submit GLM-predict"
# submit algorithms/GLM-predict.dml 'X=linRegData.test.data.csv Y=linRegData.test.labels.csv B=betas.csv fmt=csv'
# rc=$?

