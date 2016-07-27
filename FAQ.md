*********************************************************************
### FAQ
*********************************************************************

- [gradlew: line 2: $'\r': command not found](./FAQ.md#gradlew-line-2-r-command-not-found)
- [Failed to create MD5 hash for file](./FAQ.md#failed-to-create-md5-hash-for-file)
- [Squirrel connection error - SSLHandshakeException: sun.security.validator.ValidatorException: PKIX path building failed](./FAQ.md#squirrel-connection-error---sslhandshakeexception-sunsecurityvalidatorvalidatorexception-pkix-path-building-failed)
- [Could not resolve all dependencies for / Could not download ...](./FAQ.md#could-not-resolve-all-dependencies-for--could-not-download-)

*********************************************************************
#### gradlew: line 2: $'\r': command not found

cygwin users may encounter the following error:

```
$ ../gradlew RunSquirrel
../gradlew: line 2: $'\r': command not found
../gradlew: line 8: $'\r': command not found
../gradlew: line 11: $'\r': command not found
../gradlew: line 14: $'\r': command not found
../gradlew: line 17: $'\r': command not found
../gradlew: line 18: syntax error near unexpected token `$'{\r''
'./gradlew: line 18: `warn ( ) {
```

The solution is to use gradlew.bat, e.g.

```
$ ../gradlew.bat RunSquirrel
```
*********************************************************************
#### Failed to create MD5 hash for file

```
$ ../gradlew.bat RunSquirrel
...
* What went wrong:
Failed to capture snapshot of output files for task 'DownloadSquirrel' during up
-to-date check.  See stacktrace for details.
> Failed to create MD5 hash for file C:\Users\IBM_ADMIN\Documents\GitHub\github2
\biginsight-examples\examples\SquirrelSQL\.gradle\2.9\taskArtifacts\cache.proper
ties.lock.
```
The solution is to set the `--project-cache-dir` argument, e.g.

```
$ ../gradlew.bat --project-cache-dir ../ RunSquirrel
```
*********************************************************************
#### Squirrel connection error - SSLHandshakeException: sun.security.validator.ValidatorException: PKIX path building failed

This probably means that you have changed your connection.properties to point to a different cluster and you are still using the old cluster's certificate.  To fix this:

- change to the directory containing connection.properties (this is the top level folder)
- run `./gradlew DownloadCertificate` to download the new certificate
- remove the old truststore.jks files.  From the top level folder, you can run `./gradlew DeleteTruststores` to do this

*********************************************************************
#### Could not resolve all dependencies for / Could not download ...

You may sometimes see an error like this:

```
  > Could not resolve all dependencies for configuration ':classpath'.
     > Could not download commons-codec.jar (commons-codec:commons-codec:1.6)
        > Could not get resource 'https://repo1.maven.org/maven2/commons-codec/commons-codec/1.6/commons-codec-1.6.jar'.
           > Could not GET 'https://repo1.maven.org/maven2/commons-codec/commons-codec/1.6/commons-codec-1.6.jar'.
              > Read timed out
```

This is due to gradle not being able to download some dependencies from a remote repository.  

Try rerunning the gradle task to fix this.  You may need to do this several times.
