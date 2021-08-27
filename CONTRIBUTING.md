# Contributing to the Trello/Github Webhooks API

To get up and running, install the npm package and run the full build:

```
npm i trellogithubapi

./gradlew clean build

java -jar trello-github-webhooks-api-**<version number>**.jar
```

## Testing your changes

### Unit Testing
All changes must have accompanying unit test written in JUnit using the Mockito framework.  The code coverage number itself is not as important as the quality of testing.

## Formatting

**Prettier formatting is used with the community
[Java plugin](https://github.com/jhipster/prettier-java)**
