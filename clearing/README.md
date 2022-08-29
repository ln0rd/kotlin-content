## Clearing

This project is a service that consumes transaction events and performs clearing processes.

### Lint

To configure kotlin lint in IntelliJ IDEA, run the following Gradle task:

```shell
gradle ktlintApplyToIdea
```

Additional Gradle tasks:

- Lint check only

```shell
gradle ktlintCheck
```

- Lint format

```shell
gradle ktlintFormat
```

### Tests

To run unit and integration tests, use:

```shell
gradle test
```

To run end-to-end tests, use:

```shell
gradle test-e2e
```

To run Migration

```
gradle flywayMigrate -i
```

### Overall verification

Complete verification of this project can be achieved using the gradle task `check`. This task depends on the following
tasks, so it usually takes some time to complete:

- lint
- test
- test-e2e
