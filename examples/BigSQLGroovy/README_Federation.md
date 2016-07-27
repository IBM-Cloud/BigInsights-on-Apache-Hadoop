
# README for Big SQL Federation

BIGSQL should be setup such that federation is on and appropriate library are in place to allow wrapper to function beetween BIGSQL and data source.

**NOTE:** *Federation is not yet available on BigInsights on Cloud clusters*

## Setup client keystore for SSL

Make sure to get the CA certificate file from DASHDB and copy it to `/home/bigsql/sqllib/security/keystore/DigiCertGlobalRootCA.crt` on the BigSQL host.  E.g. using scp:

```
scp DigiCertGlobalRootCA.crt biadmin@<bigsql head node>:/home/bigsql/sqllib/security/keystore/DigiCertGlobalRootCA.crt
```

Ssh into the Big SQL head node and go to keystory directory:

```
cd /home/bigsql/sqllib/security/keystore
```

Create keystore db:

```
/home/bigsql/sqllib/gskit/bin/gsk8capicmd_64 -keydb -create -db "dashclient.kdb" -pw "myCli3ntPassw0rd" -stash
/home/bigsql/sqllib/gskit/bin/gsk8capicmd_64 -cert -add -db "dashclient.kdb" -pw "myCli3ntPassw0rd" -label "DigiCert" -file "/home/bigsql/sqllib/security/keystore/DigiCertGlobalRootCA.crt" -format ascii -fips
```

Update database manager configuration to use key and stash file:

```
db2 update dbm cfg using SSL_CLNT_KEYDB /home/bigsql/sqllib/security/keystore/dashclient.kdb 
db2 update dbm cfg using SSL_CLNT_STASH /home/bigsql/sqllib/security/keystore/dashclient.sth 
```


## Catalog remote database

You need DBADM privilege to perform following. As bigsql on master-2 (Big SQL Head Node). Do the following.

Uncatalog if needed:
```
db2 UNCATALOG DATABASE BLUDB
db2 UNCATALOG NODE DASHNODE
```

Catalog the remote server:
```
db2 CATALOG TCPIP NODE DASHNODE REMOTE <remote server ip> SERVER <remote server port> SECURITY SSL
```

Catalog the remote database:
```
db2 CATALOG DATABASE BLUDB AS BLUDB AT NODE DASHNODE
```

### Test connection

```
db2 connect to BLUDB user <user> using <password>
```


