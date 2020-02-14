# Save Data to a JSON File

## Saving Data to a JSON File
We'll use the `toJson(Object src, Appendable writer)` method from the `Gson` class to convert a Java data type into JSON and store it in a file. The `Gson()` constructor creates a `Gson` object with default configuration:
```
Gson gson = new Gson();
```
Now, we can call `toJson()` to convert and store Java objects.

### Primitives
Saving primitives to a JSON file is pretty straight-forward using GSON:
```
gson.toJson(123.45, new FileWriter(filePath));
```

### Custom Objects
```
public class User {
    private int id;
    private String name;
    private transient String nationality;
}
```
If a field is marked `transient`, it's ignored by default and not included in the JSON serialization or deserialization. As a result, the `nationality` field isn't present in the JSON output.

Also by default, Gson omits `null` fields during serialization. So if we consider this example:
```
gson.toJson(new User(1, null, "Unknown"), new FileWriter(filePath));
```
the file output will be:
```
{"id":1}
```

### Collections
We can store a collection of objects in a similar manner:
```
User[] users = new User[] { new User(1, "Mike"), new User(2, "Tom") };
gson.toJson(users, new FileWriter(filePath));
```

## Using GsonBuilder
In order to tweak the default Gson configuration settings, we can utilize the `GsonBuilder` class.

This class follows the builder pattern, and it's typically used by first invoking various configuration methods to set desired options, and finally calling the `create()` method:
```
Gson gson = new GsonBuilder()
  .setPrettyPrinting()
  .create();
```
Here, we're setting the pretty print option which is by default set to `false`. Similarly, to include `null` values in serialization, we can call `serializeNulls()`.

## Results
- `SaveJsonFileTest`

## References
- [Save Data to a JSON File with Gson](https://www.baeldung.com/gson-save-file)