# Jackson @JsonFilter

## Using @JsonFilter
**Step-1**: Create a class annotated with `@JsonFilter` and assign a filter name.

**Step-2**: Create the instance of `SimpleFilterProvider` and add our filter `studentFilter` using `addFilter()` method.
```
SimpleFilterProvider filterProvider = new SimpleFilterProvider();
filterProvider.addFilter("studentFilter",
        SimpleBeanPropertyFilter.serializeAllExcept("stdName", "stdCity"));
```

**Step-3**: Set the instance of `SimpleFilterProvider` to `ObjectMapper` using `setFilterProvider()` method.
```
ObjectMapper mapper = new ObjectMapper();
mapper.setFilterProvider(filterProvider);
```

**Step-4**: Now serialize the instance of `Student` class.
```
Student student = new Student("Mohit", 30, "ABCD", "Varanasi");
String jsonData = mapper.writerWithDefaultPrettyPrinter()
       .writeValueAsString(student);
System.out.println(jsonData);
```
`stdName` and `stdCity` properties will not be serialized. Find the output.
```
{
  "stdAge" : 30,
  "stdCollege" : "ABCD"
}
```

## SimpleBeanPropertyFilter
`SimpleFilterProvider` is the implementation of `PropertyFilter` that only uses property name to determine if it should be serialized or filtered out. Find some methods of `SimpleFilterProvider`.
- `serializeAllExcept()`: Serializes all properties except the properties configured to this method.
- `filterOutAllExcept()`: Serializes properties only configured to this method.
- `serializeAll()`: Serializes all properties and filters out nothing.

## @JsonFilter at Property Level
We can use `@JsonFilter` annotation on fields, methods and constructor parameters since Jackson 2.3. Here we will create two filters using `@JsonFilter` at property level and filter the properties using `serializeAllExcept()` and `filterOutAllExcept()` methods.

## Results
- `JacksonJsonFilterTest`

## References
- [Jackson @JsonFilter Example](https://www.concretepage.com/jackson-api/jackson-jsonfilter-example)