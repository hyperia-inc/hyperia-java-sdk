# hyperia-java-sdk #

This is a Java SDK for accessing the Hyperia Conversational Intelligence APIs.  With Hyperia, you can quickly build and integrate transcription and conversational intelligence into your application.


## Hyperia ##

Hyperia offers a programmable AI notetaker, real-time streaming call and meeting transcription and NLP analysis, and conversational intelligence as a service.

More information at: https://hyperia.net/



## API Key ##

To use Hyperia, you'll need to obtain an API key and attach that key to all requests. If you do not already have a key, please visit: https://hyperia.net/



## REST API Documentation ##

Hyperia offers dozens of REST API endpoints for doing real-time transcription, leveraging a programmable AI notetaker in Zoom/Google Meet/Teams calls,
or crawling or uploading media for analysis, ingestion, and indexing.  Workspace management, document management, search, and analytics APIs are also
provided.  Full API documentation is available at:

	https://hyperia.net/docs



## Requirements ##

The Java SDK is available for Java 8+, and is built using [Retrofit2](https://square.github.io/retrofit/)



## Getting Started with the Java SDK ##

First, build the SDK from source (Maven 3+ required):
```
git clone https://github.com/hyperia-inc/hyperia-java-sdk.git
cd hyperia-java-sdk
mvn clean install
```

Next, you may use the following code sample to stream audio for realtime transcription and NLP analysis:

```xml
cd hyperia-client-examples
mvn package
cd target
java -jar hyperia-client-examples-{version}.jar https://api.hyperia.net YOUR_API_KEY /path-to-hyperia-java-sdk-source/data/intro_to_ai.pcm
```
NOTE: Files must be raw PCM, 16000 hz, mono, signed 16 bit integer format.


To run the integration tests:
```
cd hyperia-client-integration-tests
mvn test -Ptest-run -DbaseUri=https://api.hyperia.net -DtestApiKey=YOUR_API_KEY
```

## Using in the code ##
There are following modules available:
* hyperia-model - request and response immutable data objects
* hyperia-retrofit-api - the api interface supported by the client
* hyperia-client - the client code
* hyperia-model-jackson - components to use [jackson](https://github.com/FasterXML/jackson) as json framework
* hyperia-retrofit-client - components to instantiate client with default http settings and jackson enabled
* hyperia-client-integration-tests - integration tests (disabled by default)
* hyperia-client-examples - java code examples

To use the client as is, add these dependencies:
```xml
<dependencies>
    <dependency>
        <groupId>net.hyperia</groupId>
        <artifactId>hyperia-client</artifactId>
        <version>${version}</version>
    </dependency>
    <dependency>
        <groupId>net.hyperia</groupId>
        <artifactId>hyperia-retrofit-client</artifactId>
        <version>${version}</version>
    </dependency>
</dependencies>
```
To adjust the retrofit configuration add only:
```xml
<dependency>
    <groupId>net.hyperia</groupId>
    <artifactId>hyperia-client</artifactId>
    <version>${version}</version>
</dependency>
```
And write your own code to configure the retrofit generator and use it to build alternative hyperia-retrofit-api implementation
