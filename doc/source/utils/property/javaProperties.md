# Java Properties

## Loading Properties

### From Properties Files
```
String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
String appConfigPath = rootPath + "app.properties";
String catalogConfigPath = rootPath + "catalog";
Properties appProps = new Properties();
appProps.load(new FileInputStream(appConfigPath));
Properties catalogProps = new Properties();
catalogProps.load(new FileInputStream(catalogConfigPath));
String appVersion = appProps.getProperty("version");
assertEquals("1.0", appVersion);
assertEquals("files", catalogProps.getProperty("c1"));
```

### Load From XML files
```
String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
String iconConfigPath = rootPath + "icons.xml";
Properties iconProps = new Properties();
iconProps.loadFromXML(new FileInputStream(iconConfigPath));
assertEquals("icon1.jpg", iconProps.getProperty("fileIcon"));
```

## Get Properties
```
String appVersion = appProps.getProperty("version");
String appName = appProps.getProperty("name", "defaultName");
String appGroup = appProps.getProperty("group", "baeldung");
String appDownloadAddr = appProps.getProperty("downloadAddr");
assertEquals("1.0", appVersion);
assertEquals("TestApp", appName);
assertEquals("baeldung", appGroup);
assertNull(appDownloadAddr);
```

## Set Properties
```
appProps.setProperty("name", "NewAppName"); // update an old value
appProps.setProperty("downloadAddr", "www.baeldung.com/downloads"); // add new key-value pair
String newAppName = appProps.getProperty("name");
assertEquals("NewAppName", newAppName);
String newAppDownloadAddr = appProps.getProperty("downloadAddr");
assertEquals("www.baeldung.com/downloads", newAppDownloadAddr);
```

## Remove Properties
```
String versionBeforeRemoval = appProps.getProperty("version");
assertEquals("1.0", versionBeforeRemoval);
appProps.remove("version");    
String versionAfterRemoval = appProps.getProperty("version");
assertNull(versionAfterRemoval);
```

## Store

### Store to Properties Files
```
String newAppConfigPropertiesFile = rootPath + "newApp.properties";
appProps.store(new FileWriter(newAppConfigPropertiesFile), "store to properties file");
```

### Store to XML Files
```
String newAppConfigXmlFile = rootPath + "newApp.xml";
appProps.storeToXML(new FileOutputStream(newAppConfigXmlFile), "store to xml file");
```

## Other Common Operations
```
appProps.list(System.out); // list all key-value pairs
Enumeration<Object> valueEnumeration = appProps.elements();
while (valueEnumeration.hasMoreElements()) {
    System.out.println(valueEnumeration.nextElement());
}
Enumeration<Object> keyEnumeration = appProps.keys();
while (keyEnumeration.hasMoreElements()) {
    System.out.println(keyEnumeration.nextElement());
}
int size = appProps.size();
assertEquals(3, size);
```

## Default Property List
```
String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
String defaultConfigPath = rootPath + "default.properties";
Properties defaultProps = new Properties();
defaultProps.load(new FileInputStream(defaultConfigPath));
String appConfigPath = rootPath + "app.properties";
Properties appProps = new Properties(defaultProps);
appProps.load(new FileInputStream(appConfigPath));
assertEquals("1.0", appVersion);
assertEquals("TestApp", appName);
assertEquals("www.google.com", defaultSite);
```

## Properties and Encoding
By default, properties files are expected to be ISO-8859-1 (Latin-1) encoded

For XML files, the `loadFromXML()` method and the `storeToXML()` method use UTF-8 character encoding by default.

## References
- [Getting Started with Java Properties](https://www.baeldung.com/java-properties)