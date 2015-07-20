# Nubes-UseCases
Some simple use cases of the [Vertx-Nubes framework](https://github.com/aesteve/vertx-nubes)

## Requirements

Before trying to run the examples, be sure that you have the latest version of Vertx-Nubes, otherwise you may not be able to run some of them.

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

##How to create a new use case

If you want to create your own Nubes use case, you might find it useful to start with the [base project](https://github.com/ldallen/Nubes-UseCases/tree/master/baseProject).  
You will need to clone and install the vert.x 3 repositories for [web](https://github.com/vert-x3/vertx-web) and [core](https://github.com/vert-x3/vertx-stack) api.  
You will also need to clone and install the [Nubes](https://github.com/aesteve/vertx-nubes) repository.  
Nubes uses gradle so if you want it to be installed in your `.m2` repository, you can use the command `.\gradlew install` in the Nubes repository. 
This will add the `jar` file to your maven local directory.  

###Other repositories which might be useful

If you are planning to use a jdbc database, you will have to install the [jdbc-client repository](https://github.com/vert-x3/vertx-jdbc-client) and the [vertx-sql-common one](https://github.com/vert-x3/vertx-sql-common).  

If you want to use a mongo database, you will have to install the [mongo-client repository](https://github.com/vert-x3/vertx-mongo-client).  

For any authentication system, you will need to install [vertx-auth](https://github.com/vert-x3/vertx-auth). (For JDBC, you will also need the jdbc-client repository defined before).  

You can find any vertx repository that you need [here](https://github.com/vert-x3).







