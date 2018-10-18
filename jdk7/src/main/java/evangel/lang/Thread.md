# Java™ Platform Standard Ed. 7

## `java.lang`
### `Thread`

### `Thread.State`
- `NEW`: A thread that has not yet started is in this state.
- `RUNNABLE`: A thread executing in the Java virtual machine is in this state.
- `BLOCKED`: A thread that is blocked waiting for a monitor lock is in this state.
- `WAITING`: A thread that is waiting indefinitely for another thread to perform a particular action is in this state.
- `TIMED_WAITING`: A thread that is waiting for another thread to perform an action for up to a specified waiting time is in this state.
- `TERMINATED`: A thread that has exited is in this state.