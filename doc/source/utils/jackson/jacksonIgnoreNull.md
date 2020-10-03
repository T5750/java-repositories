# Jackson Ignore Null and Empty Fields

## Include.NON_NULL and Include.NON_EMPTY
To ignore Null fields in JSON, Jackson provides `Include.NON_NULL` and to ignore Empty fields Jackson provides `Include.NON_EMPTY`.
- `Include.NON_NULL`: Indicates that only properties with not null values will be included in JSON.
- `Include.NON_EMPTY`: Indicates that only properties that are not empty will be included in JSON. Non-empty can have different meaning for different objects such as `List` with size zero will be considered as empty. In case of `Map` to check empty `isEmpty()` is called.

## Results
- `JacksonIgnoreNullTest`

## References
- [Jackson Ignore Null and Empty Fields](https://www.concretepage.com/jackson-api/jackson-ignore-null-and-empty-fields)