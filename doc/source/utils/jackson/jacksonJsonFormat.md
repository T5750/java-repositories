# Jackson @JsonFormat

## @JsonFormat
`@JsonFormat` handles the serialization of `Date`, `Enum`, `Collection` and `Number`. `@JsonFormat` decides how values of properties to be serialized. Find some of the attributes of `@JsonFormat` annotation.
- `shape`: Defines the structure to use for serialization, for example `JsonFormat.Shape.NUMBER` and `JsonFormat.Shape.STRING`.
- `pattern`: Pattern used in serialization and deserializations. For date, pattern contains `SimpleDateFormat` compatible definition.
- `locale`: `Locale` used in serialization. Default is system default locale.
- `timezone`: `TimeZone` used in serialization. Default is system default timezone.
- `lenient`: Useful in deserializations. It decides if lenient handling should be enabled or disabled.

## @JsonFormat with Enum
`@JsonFormat` can be used with Java `enum` in serialization to change between index (number) and textual name (string). `@JsonFormat` is used at `enum` level and not at property level. By default `enum` properties are serialized with its textual name as string. We can change it to property index (starting from 0) using `JsonFormat.Shape.NUMBER`.

## Results
- `JacksonJsonFormatTest`

## References
- [Jackson @JsonFormat Example](https://www.concretepage.com/jackson-api/jackson-jsonformat-example)