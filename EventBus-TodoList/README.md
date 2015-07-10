##EventBus use case : Todo-List editor

This is a small example to show how a user can interact with a database using the EventBus.

The interest of using the bus is that it allows realtime update, to all client listening through the bus.  
This way, when you add a task, all the other clients will instantly be informed of the changes in the database!

### How can you run it ?

You just need to run it like all the other examples with **gradlew start**, and then you have to go to this url :
*localhost:8080/todolist* (or directly *localhost:8080/todolist/list*) to play with the list :)

You can try to open several clients to see the realtime updates !

This example is not using a real database, see [jdbc example](https://github.com/ldallen/Nubes-UseCases/tree/master/jdbcAuth) for that.
