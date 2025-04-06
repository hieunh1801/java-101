# quarkus101


## Running the application in dev mode

```shell script
# run dev
./gradlew quarkusDev

# build
./gradlew build

# build jar file
./gradlew build -Dquarkus.package.jar.type=uber-jar

# build native file
./gradlew build -Dquarkus.native.enabled=true

# build with docker
./gradlew build -Dquarkus.native.enabled=true -Dquarkus.native.container-build=true
```

## Related Guides

- REST ([guide](https://quarkus.io/guides/rest)): A Jakarta REST implementation utilizing build time processing and
  Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on
  it.
- Camel Core ([guide](https://camel.apache.org/camel-quarkus/latest/reference/extensions/core.html)): Camel core
  functionality and basic Camel languages: Constant, ExchangeProperty, Header, Ref, Simple and Tokenize
- REST JSON-B ([guide](https://quarkus.io/guides/rest#json-serialisation)): JSON-B serialization support for Quarkus
  REST. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on
  it.
- REST Jackson ([guide](https://quarkus.io/guides/rest#json-serialisation)): Jackson serialization support for Quarkus
  REST. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it

## Provided Code

### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
