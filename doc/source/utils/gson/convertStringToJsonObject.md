# Convert String to JsonObject

## Using JsonParser
The first approach we'll see for converting a JSON `String` to a `JsonObject` is a two-step process that uses the `JsonParser` class.
1. we need to parse our original `String`.
2. Once we have our `String` parsed in a `JsonElement` tree, we'll use the `getAsJsonObject()` method, which will return the desired result.

Gson provides us a parser called `JsonParser`, which parses the specified JSON `String` into a parse tree of `JsonElements`:
```
public JsonElement parse(String json) throws JsonSyntaxException
```

## Using fromJson
In our second approach, we'll see how to create a `Gson` instance and use the `fromJson` method. This method deserializes the specified JSON `String` into an object of the specified class:
```
public <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException
```

## Results
- `ConvertStringToJsonObjectTest`

## References
- [Convert String to JsonObject with Gson](https://www.baeldung.com/gson-string-to-jsonobject)