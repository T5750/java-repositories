# Gson vs Jackson

## Gson Serialization
### Simple Serialization
By default:
- All properties are serialized because they have no `null` values
- `dateOfBirth` field was translated with the default Gson date pattern
- Output is not formatted and JSON property names correspond to the Java entities

### Custom Serialization
Using a custom serializer allows us to modify the standard behavior. We can introduce an output formatter with HTML, handle `null` values, exclude properties from output, or add a new output.
- `ActorGsonSerializer`

In order to exclude the `director` property, the `@Expose` annotation is used for properties we want to consider:
```
public class MovieWithNullValue {
    @Expose
    private String imdbId;
    private String director;
    @Expose
    private List<ActorGson> actors;
}
```

Notice that:
- the output is formatted
- some property names are changed and contain HTML
- `null` values are included, and the `director` field is omitted
- `Date` is now in the `dd-MM-yyyy` format
- a new property is present – `N° Film`
- filmography is a formatted property, not the default JSON list

## Gson Deserialization
### Simple Deserialization
As was the case with the simple serializer:
- the JSON input names must correspond with the Java entity names, or they are set to `null`.
- `dateOfBirth` field was translated with the default Gson date pattern, ignoring the time zone.

### Custom Deserialization
Using a custom deserializer allows us to modify the standard deserializer behavior.
- `ActorGsonDeserializer`

## Jackson Serialization
### Simple Serialization
Some notes of interest:
- `ObjectMapper` is our Jackson serializer/deserializer
- The output JSON is not formatted
- By default, Java Date is translated to `long` value

### Custom Serialization
We can create a Jackson serializer for `ActorJackson` element generation by extending `StdSerializer` for our entity. Again note that the entity getters/setters must be `public`

## Jackson Deserialization
### Simple Deserialization
As was the case with the simple serializer:
- the JSON input names must correspond with the Java entity names, or they are set to `null`,
- `dateOfBirth` field was translated with the default Jackson date pattern, ignoring the time zone.

### Custom Deserialization
Using a custom deserializer allows us to modify the standard deserializer behavior.

Alternatively, we could have created a custom deserializer for the `ActorJackson` class, registered this module with our `ObjectMapper`, and deserialized the date using the `@JsonDeserialize` annotation on the `ActorJackson` entity.

The disadvantage of that approach is the need to modify the entity, which may not be ideal for cases when we don't have access to the input entity classes.

## Conclusion
Both Gson and Jackson are good options for serializing/deserializing JSON data, simple to use and well documented.

Advantages of Gson:
- Simplicity of `toJson`/`fromJson` in the simple cases
- For deserialization, do not need access to the Java entities

Advantages of Jackson:
- Built into all JAX-RS (Jersey, Apache CXF, RESTEasy, Restlet), and Spring framework
- Extensive annotation support

## Results
- `GsonJacksonTest`
- `JacksonGsonTest`

## References
- [Jackson vs Gson](https://www.baeldung.com/jackson-vs-gson)