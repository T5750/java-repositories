# CompletableFuture

## Callbacks and Chaining
- `thenAccept()`: If We want to run some code after receiving some value from Future
- `thenApply()`: If We want to run some code after receiving value from Future and then want to return some value
- `thenRun()`: If We want to run some code after completion of the Future and dont want to return any value

## allOf
The `CompletableFuture.allOf` static method allows to wait for completion of all of the Futures

## Exception Handling
- `exceptionally`
- `handle`
- `whenComplete`

## Tests
- `CompletableFutureSimpleTest`
- `CompletableFutureThenApplyTest`
- `CompletableFutureThenComposeTest`
- `CompletableFutureExceptionTest`
- `CompletableFutureExample`
- `CompletableFutureTest`

## References
- [Write Clean Asynchronous Code With CompletableFuture Java-8](https://nirajsonawane.github.io/2019/01/27/Write-Clean-asynchronous-code-with-CompletableFuture-Java-8/)
- [CompletableFuture](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html)