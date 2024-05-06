## Zeal QA case study

This project is built with Java 17. JUnit 5 is used as a test framework and Gradle 8.5 as a build tool

#### Running the tests

You can easily run tests by executing _./gradlew clean test_ in cmd or manually(_WikiTest.java_) via favourite IDE

#### Possible future improvements

- Implement a more robust logging mechanism (SL4J, Log4J, etc.)
- DTOs can be used to represent API responses (e.g. `SearchResponse`, `SearchResult`)
- Move configuration (urls) to a separate file (e.g. config.properties)
- Parameterize tests to run with different data sets

#### Additional test scenarios ideas for Search API:
- searches with different combinations of project and language codes
- searches with no matching results returned
- default limit behavior (50 results per page)
- minimum, maximum, and out-of-bound limit values (0,1,100,101)
- error handling for unsupported projects and language codes
- for title search with max limit: all returned titles contain search query

I chose these scenarios because of wide range cover of common and unusual situations.
This helps ensure that the API is reliable and easy to use, no matter what kind of input or conditions we encounter.

#### Bonus
_To create a page via Create Page API, you need to provide an Authorization Header. Describe how you would adapt testing/automation?_

- As per instructions on [this](https://api.wikimedia.org/wiki/Authentication) page we will need to create our access token for successful authorization
- Modify API request headers to include the token. Authorisation header example: `Authorization: Bearer <Your_Access_Token>`
- Store credentials in a secure way (e.g. environment variables, external configuration files or secure storages) and load them in the test code
- Handle situations where the token expires (in our case - every 4 hours) - either by preemptively [refreshing](https://api.wikimedia.org/wiki/Authentication#5._Refresh_token) the token before it expires or by catching errors related to token expiration and then obtaining a new token
