# iotgql project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/> .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```
./mvnw quarkus:dev
```

## Packaging and running the application

The application can be packaged using `./mvnw package`.
It produces the `iotgql-1.0.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/iotgql-1.0.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: `./mvnw package -Pnative`.

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: `./mvnw package -Pnative -Dquarkus.native.container-build=true`.

You can then execute your native executable with: `./target/iotgql-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/building-native-image>.

## Building custome jre

1. `quarkus.package.uber-jar=true` to `application.properties`
2. `mvn clean install`
3. execute `jdeps --list-deps target/{*-runner.jar}`
4. 
```
jlink --no-header-files --no-man-pages --output target/iotgqljre --compress=2 --strip-debug --module-path ${JAVA_HOME}/bin/jmods --add-modules {all the modules from previous command seperaed by ,}`.
```

5. or For building the docker image with custome jre rafer: [Dockerfile.customejre](https://github.com/Nikhil12894/iotgql_quarkus/blob/master/Dockerfile.customejar)


## Creating the public and private keys

First, it is necessary to generate a base key to be signed:

```
openssl genrsa -out baseKey.pem
```

From the base key generate the PKCS#8 private key:

```
openssl pkcs8 -topk8 -inform PEM -in baseKey.pem -out privateKey.pem -nocrypt
```

Using the private key you could generate a public (and distributable) key

```
openssl rsa -in baseKey.pem -pubout -outform PEM -out publicKey.pem
