##Authentication use case with `Shiro`

This example is just like the [jdbc example](https://github.com/ldallen/Nubes-UseCases/tree/master/jdbcAuth), because it uses the same database, and the `REDIRECT` authentication method.  
The only difference is that we create a shiro authentication provider, which uses a `.properties` file to get the users' information instead of getting them from the database like with `jdbc`.

