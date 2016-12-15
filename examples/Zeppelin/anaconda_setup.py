# Overview

This script installs anaconda python on a BigInsights on cloud 4.2 Enterprise cluster.

**Note** that these instructions do **NOT** work for Basic clusters because ssh is broken on basic clusters.

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
