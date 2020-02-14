# Convert JSON to a Map

## Passing Map.class
In general, Gson provides the following API in its `Gson` class to convert a JSON string to an object:
```
public <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException;
```
- `classOfT`: is the class of the object which we intend the JSON to parse into.
- If there are duplicate keys, though, coercion will fail and it will throw a `JsonSyntaxException`.

## Using TypeToken
To overcome the problem of [type-erasure](https://www.baeldung.com/java-type-erasure) for the generic types, `Gson` has an overloaded version of the API:
```
public <T> T fromJson(String json, Type typeOfT) throws JsonSyntaxException;
```

We can construct a `Map` with its type parameters using Gson's `TypeToken`. The `TypeToken` class returns an instance of `ParameterizedTypeImpl` that preserves the type of the key and value even at runtime

## Using Custom JsonDeserializer
When we need fine-grained control over the construction of our `Map` object, we can implement a custom deserializer of type `JsonDeserializer<Map>`.
- `StringDateMapDeserializer`

## Results
- `ConvertJsonToMapTest`

## References
- [Convert JSON to a Map Using Gson](https://www.baeldung.com/gson-json-to-map)