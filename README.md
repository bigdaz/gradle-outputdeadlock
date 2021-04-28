Demonstration of deadlock when resolving outputs that require resolving inputs

To see the deadlock in action:
1. `cd outputdeadlock-consumer`
2. `./gradlew clean outputDeadlock` --> This should run successfully, since only a single input file is resolved
3. Uncomment the additional `api` dependency in `build.gradle`
4. `./gradlew clean outputDeadlock` --> This will deadlock attempting to resolve the `compileClasspath` configuration in parallel


