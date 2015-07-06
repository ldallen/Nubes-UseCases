# Nubes-UseCases
Some simple use cases of the Vertx-Nubes framework

## Requirements

Before trying to run the examples, be sure that you have a version of Vert.x Nubes with NubesServer class implemented.
You can find one [here](https://github.com/ldallen/vertx-nubes/tree/NubesServer).

##How to run the examples

If you want to run the examples, just clone the project and run :

```
gradlew start
```

If you just want to build the project, run : 

```
gradlew shadowJar
```
This command will create a jar, which you can run using the following command :

```
java -jar example.jar -conf conf.json
```
