# Overview

This script installs and configures toree on a BigInsights on cloud 4.2 Enterprise cluster.

Note that these instructions do NOT work for Basic clusters.

# Install Anaconda, Toree and Jupyter

Ssh into the mastermanager node, then run:

```bash
export BI_USER=snowch
export BI_PASS=changeme
export BI_HOST=bi-hadoop-prod-4118.bi.services.us-south.bluemix.net
```
Change the above values for your environment, then run the following.  The script attempts to be as idemopotent as possible so it shouldn't matter if you run it multiple times:

```bash
# abort if the script encounters an error or undeclared variables
set -euo

CLUSTER_NAME=$(curl -s -k -u $BI_USER:$BI_PASS  -X GET https://${BI_HOST}:9443/api/v1/clusters | python -c 'import sys, json; print(json.load(sys.stdin)["items"][0]["Clusters"]["cluster_name"]);')
echo Cluster Name: $CLUSTER_NAME

CLUSTER_HOSTS=$(curl -s -k -u $BI_USER:$BI_PASS  -X GET https://${BI_HOST}:9443/api/v1/clusters/${CLUSTER_NAME}/hosts | python -c 'import sys, json; items = json.load(sys.stdin)["items"]; hosts = [ item["Hosts"]["host_name"] for item in items ]; print(" ".join(hosts));')
echo Cluster Hosts: $CLUSTER_HOSTS

wget -c https://repo.continuum.io/archive/Anaconda2-4.1.1-Linux-x86_64.sh

# Install anaconda if it isn't already installed
[[ -d anaconda2 ]] || bash Anaconda2-4.1.1-Linux-x86_64.sh -b

# check toree python module is available, if not install it
./anaconda2/bin/python -c 'import toree' || ./anaconda2/bin/pip install toree

# Install toree into jupyter
./anaconda2/bin/jupyter toree install --spark_home=/usr/iop/current/spark-client/ --user --interpreters Scala,PySpark,SparkR  --spark_opts="--master yarn" --python_exec=${HOME}/anaconda2/bin/python2.7

# Install anaconda on all of the cluster nodes
for CLUSTER_HOST in ${CLUSTER_HOSTS}; 
do 
   if [[ "$CLUSTER_HOST" != "$BI_HOST" ]];
   then
      echo "*** Processing $CLUSTER_HOST ***"
      ssh $BI_USER@$CLUSTER_HOST "wget -q -c https://repo.continuum.io/archive/Anaconda2-4.1.1-Linux-x86_64.sh"
      ssh $BI_USER@$CLUSTER_HOST "[[ -d anaconda2 ]] || bash Anaconda2-4.1.1-Linux-x86_64.sh -b"

      # You can install your pip modules on each node using something like this:
      # ssh $BI_USER@$CLUSTER_HOST "${HOME}/anaconda2/bin/python -c 'import yourlibrary' || ${HOME}/anaconda2/pip install yourlibrary"
   fi
done

echo 'Finished installing'
```

# Run Jupyter

Everytime you want to run jupter, open a ssh session to the master manager node and run the following (enter password when prompted):

```bash
PYSPARK_PYTHON=${HOME}/anaconda2/bin/python2.7 ${HOME}/anaconda2/bin/jupyter notebook  --port=8888 --port-retries=0 --no-browser
```

Leave this terminal ^^ session running.  Back on your client, run the following (change for your environment):

```bash
export BI_USER=snowch
export BI_PASS=changeme
export BI_HOST=bi-hadoop-prod-4118.bi.services.us-south.bluemix.net

# E.g. The Jupyter Notebook is running at: http://localhost:8888/
export JUPYTER_PORT=8888

# set this to a free port on your client machine
export CLIENT_PORT=8989
```

Now run the following on your client machine (enter password when prompted):

```bash
ssh -N -L $CLIENT_PORT:localhost:$JUPYTER_PORT $BI_USER@$BI_HOST
```

Leave this session running ^^

This command binds the CLIENT_PORT on the client machine.  This port listens for network connections.  It then forwards all network traffic to JUPYTER_PORT on the master manager node.

Now open a web browser on your client machine to http://localhost:8989 (this port must be the same as the CLIENT_PORT)

# Test Jupyter

Try running a pyspark job:

```python
NUM_SAMPLES=1000000000
from random import random

def sample(p):
    x, y = random(), random()
    return 1 if x*x + y*y < 1 else 0

count = sc.parallelize(xrange(0, NUM_SAMPLES)).map(sample) \
             .reduce(lambda a, b: a + b)
print "Pi is roughly %f" % (4.0 * count / NUM_SAMPLES)
```

While it is running, execute `yarn application -list` on the cluster to verify the spark job is running on yarn.
