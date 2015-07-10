# Nubes-UseCases
Some simple use cases of the [Vertx-Nubes framework](https://github.com/aesteve/vertx-nubes)

## Requirements

Before trying to run the examples, be sure that you have a recent version of Vertx-Nubes with NubesServer implemented.  
For the authentication examples, you will also need [this version](https://github.com/ldallen/vertx-nubes/tree/authentications) of Nubes.

##How to run the examples

If you want to run the examples, just clone the project and run :

```
gradlew start
```

If you just want to build the project, run : 

```
gradlew shadowJar
```
This will create a jar, which you can run using the following command :

```
java -jar example.jar -conf conf.json
```
