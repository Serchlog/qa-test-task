# JSONPlaceholder RestAssured Tests

JUnit 5 + Rest Assured tests that mirror the official JSONPlaceholder “fetch” guide.

- Java: 17
- Build: Maven
- HTTP: Rest Assured 5
- Assertions: Hamcrest + AssertJ

## Prerequisites
- JDK 17+
- Maven 3.9+
- Internet access (tests hit https://jsonplaceholder.typicode.com)

## Base URL
The tests read the base URL from:
1) JVM system property: -DbaseUrl=...
2) Environment variable: BASE_URL
3) Project default (Config.baseUrl())

Windows PowerShell examples:
```powershell
# One-off
mvn -DbaseUrl=https://jsonplaceholder.typicode.com test

# Or set env var for the session
$env:BASE_URL = "https://jsonplaceholder.typicode.com"
mvn test
```

## Run all tests
```powershell
mvn -DbaseUrl=https://jsonplaceholder.typicode.com test
```

## Run a specific test class or method
```powershell
# Class
mvn -DbaseUrl=https://jsonplaceholder.typicode.com -Dtest=GetPostsTests test

# Single method
mvn -DbaseUrl=https://jsonplaceholder.typicode.com -Dtest=GetPostsTests#getAllPosts_ok test
```

## Project layout
```
src/test/java/com/example/api/core       # RestClient, Specs, Config, client wrappers
src/test/java/com/example/api/tests      # Test classes
src/test/resources/                      # Optional: JSON Schemas (if used)
```

## What’s covered (aligned with the fetch guide)
- GET /posts (100 items; first has expected keys)
- GET /posts/1 (fields: id, title, body, userId)
- POST /posts (echo with id=101)
- PUT /posts/1 (echo full payload)
- PATCH /posts/1 (partial update echo)
- DELETE /posts/1 (returns {} or empty body)
- GET /posts/1/comments and /comments?postId=1 (5 comments; fields present)
- Empty list cases for non-existing ids (e.g., postId=101)


## Optional: add default baseUrl in surefire
If you don’t want to pass -DbaseUrl every time, add to pom.xml:
```xml
<build>
  <plugins>
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-surefire-plugin</artifactId>
      <version>3.2.5</version>
      <configuration>
        <systemPropertyVariables>
          <baseUrl>https://jsonplaceholder.typicode.com</baseUrl>
        </systemPropertyVariables>
      </configuration>
    </plugin>
  </plugins>
</build>
```

## Architecture
- core: HTTP config and clients (RestAssured spec, reusable response specs).
- domain: request/response models and factories.
- tests: only assertions and flows; no HTTP details.

## Parallel execution
JUnit 5 parallel is enabled via src/test/resources/junit-platform.properties.
Run:
```powershell
mvn -DbaseUrl=https://jsonplaceholder.typicode.com test
```

## Grouping (tags)
We tag tests as:
- critical: happy-path, fetch-guide aligned
- negative: error/edge cases

Run by tag:
```powershell
# Only critical
mvn -DbaseUrl=https://jsonplaceholder.typicode.com -DincludeTags=critical test

# Only negative
mvn -DbaseUrl=https://jsonplaceholder.typicode.com -DincludeTags=negative test
```

## Code style
Google Java Style enforced with Checkstyle (fails build on violations).
```powershell
mvn verify
```