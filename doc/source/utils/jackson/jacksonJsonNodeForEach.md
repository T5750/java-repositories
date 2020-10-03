# Jackson JsonNode.forEach() with Java 8 Consumer

Jackson has provided JsonNode.forEach() method which will accept Java 8 consumer definition to iterate each node. The consumer accepts only super classes of JsonNode. It has been defined as below.
```
forEach(Consumer<? super JsonNode>  arg)
```
We can define consumer only with super class of JsonNode. Now find the simple example to parse JSON and iterating it using Java 8 consumer.

```
JsonFactory jsonFactory = new JsonFactory();
JsonParser jp = jsonFactory.createJsonParser(new File("D:/cp/info.json"));
jp.setCodec(new ObjectMapper());
JsonNode jsonNode = jp.readValueAsTree();
Consumer<JsonNode> data = (JsonNode node) -> System.out.println(node.asText());
jsonNode.forEach(data);
```

## Results
- `JacksonJsonNodeForEachTest`

## References
- [Jackson JsonNode.forEach() with Java 8 Consumer](https://www.concretepage.com/jackson-api/jackson-jsonnode-foreach-with-java-8-consumer)