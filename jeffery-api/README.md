# jeffery-web

jeffery-web is your new project powered by [Ktor](http://ktor.io) framework.

<img src="https://repository-images.githubusercontent.com/40136600/f3f5fd00-c59e-11e9-8284-cb297d193133" alt="Ktor" width="100" style="max-width:20%;">

Company website: web.discord.lukeshay.com Ktor Version: 1.5.2 Kotlin Version: 1.4.10
BuildSystem: [Gradle with Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html)

# Ktor Documentation

Ktor is a framework for quickly creating web applications in Kotlin with minimal effort.

* Ktor project's [Github](https://github.com/ktorio/ktor/blob/master/README.md)
* Getting started with [Gradle](http://ktor.io/quickstart/gradle.html)
* Getting started with [Maven](http://ktor.io/quickstart/maven.html)
* Getting started with [IDEA](http://ktor.io/quickstart/intellij-idea.html)

Selected Features:

* [CORS](#cors-documentation-jetbrainshttpswwwjetbrainscom)
* [Routing](#routing-documentation-jetbrainshttpswwwjetbrainscom)
* [Sessions](#sessions-documentation-jetbrainshttpswwwjetbrainscom)
* [Status Pages](#status-pages-documentation-jetbrainshttpswwwjetbrainscom)
* [Static Content](#static-content-documentation-jetbrainshttpswwwjetbrainscom)

## CORS Documentation ([JetBrains](https://www.jetbrains.com))

Enable Cross-Origin Resource Sharing (CORS)

### Description

Ktor by default provides an interceptor for implementing proper support for Cross-Origin Resource
Sharing (`CORS`).

### Usage

## Basic usage:

First of all, install the `CORS` feature into your application.

```kotlin
fun Application.main() {
  ...
  install(CORS)
  ...
}
```

The default configuration to the CORS feature handles only `GET`, `POST` and `HEAD` HTTP methods and
the following headers:

```
HttpHeaders.Accept
HttpHeaders.AcceptLanguages
HttpHeaders.ContentLanguage
HttpHeaders.ContentType
```

## Advanced usage:

Here is an advanced example that demonstrates most of CORS-related API functions

```kotlin
fun Application.main() {
  ...
  install(CORS)
  {
    method(HttpMethod.Options)
    header(HttpHeaders.XForwardedProto)
    anyHost()
    host("my-host")
    // host("my-host:80")
    // host("my-host", subDomains = listOf("www"))
    // host("my-host", schemes = listOf("http", "https"))
    allowCredentials = true
    allowNonSimpleContentTypes = true
    maxAge = Duration.ofDays(1)
  }
  ...
}
```

### Options

* `method("HTTP_METHOD")` : Includes this method to the white list of Http methods to use CORS.
* `header("header-name")` : Includes this header to the white list of headers to use CORS.
* `exposeHeader("header-name")` : Exposes this header in the response.
* `exposeXHttpMethodOverride()` : Exposes `X-Http-Method-Override` header in the response
* `anyHost()` : Allows any host to access the resources
* `host("hostname")` : Allows only the specified host to use `CORS`, it can have the port number, a
  list of subDomains or the supported schemes.
* `allowCredentials` : Includes `Access-Control-Allow-Credentials` header in the response
* `allowNonSimpleContentTypes`: Inclues `Content-Type` request header to the white list for values
  other than simple content types.
* `maxAge`: Includes `Access-Control-Max-Age` header in the response with the given max age()

## Routing Documentation ([JetBrains](https://www.jetbrains.com))

Allows to define structured routes and associated handlers.

### Description

Routing is a feature that is installed into an Application to simplify and structure page request
handling. This page explains the routing feature. Extracting information about a request, and
generating valid responses inside a route, is described on the requests and responses pages.

```application.install(Routing) {
    get("/") {
        call.respondText("Hello, World!")
    }
    get("/bye") {
        call.respondText("Good bye, World!")
    }

```

`get`, `post`, `put`, `delete`, `head` and `options` functions are convenience shortcuts to a
flexible and powerful routing system. In particular, get is an alias
to `route(HttpMethod.Get, path) { handle(body) }`, where body is a lambda passed to the get
function.

### Usage

## Routing Tree

Routing is organized in a tree with a recursive matching system that is capable of handling quite
complex rules for request processing. The Tree is built with nodes and selectors. The Node contains
handlers and interceptors, and the selector is attached to an arc which connects another node. If
selector matches current routing evaluation context, the algorithm goes down to the node associated
with that selector.

Routing is built using a DSL in a nested manner:

```kotlin
route("a") { // matches first segment with the value "a"
  route("b") { // matches second segment with the value "b"
     get {…} // matches GET verb, and installs a handler
     post {…} // matches POST verb, and installs a handler
  }
}
```

```kotlin
method(HttpMethod.Get) { // matches GET verb
   route("a") { // matches first segment with the value "a"
      route("b") { // matches second segment with the value "b"
         handle { … } // installs handler
      }
   }
}
```kotlin
route resolution algorithms go through nodes recursively discarding subtrees where selector didn't match.

Builder functions:
* `route(path)` – adds path segments matcher(s), see below about paths
* `method(verb)` – adds HTTP method matcher.
* `param(name, value)` – adds matcher for a specific value of the query parameter
* `param(name)` – adds matcher that checks for the existence of a query parameter and captures its value
* `optionalParam(name)` – adds matcher that captures the value of a query parameter if it exists
* `header(name, value)` – adds matcher that for a specific value of HTTP header, see below about quality

## Path
Building routing tree by hand would be very inconvenient. Thus there is `route` function that covers most of the use cases in a simple way, using path.

`route` function (and respective HTTP verb aliases) receives a `path` as a parameter which is processed to build routing tree. First, it is split into path segments by the `/` delimiter. Each segment generates a nested routing node.

These two variants are equivalent:

```kotlin
route("/foo/bar") { … } // (1)

route("/foo") {
   route("bar") { … } // (2)
}
```

### Parameters

Path can also contain parameters that match specific path segment and capture its value
into `parameters` properties of an application call:

```kotlin
get("/user/{login}") {
   val login = call.parameters["login"]
}
```

When user agent requests `/user/john` using `GET` method, this route is matched and `parameters`
property will have `"login"` key with value `"john"`.

### Optional, Wildcard, Tailcard

Parameters and path segments can be optional or capture entire remainder of URI.

* `{param?}` –- optional path segment, if it exists it's captured in the parameter
* `*` –- wildcard, any segment will match, but shouldn't be missing
* `{...}` –- tailcard, matches all the rest of the URI, should be last. Can be empty.
* `{param...}` –- captured tailcard, matches all the rest of the URI and puts multiple values for
  each path segment into `parameters` using `param` as key. Use `call.parameters.getAll("param")` to
  get all values.

Examples:

```kotlin
get("/user/{login}/{fullname?}") { … }
get("/resources/{path...}") { … }
```

## Quality

It is not unlikely that several routes can match to the same HTTP request.

One example is matching on the `Accept` HTTP header which can have multiple values with specified
priority (quality).

```kotlin
accept(ContentType.Text.Plain) { … }
accept(ContentType.Text.Html) { … }
```

The routing matching algorithm not only checks if a particular HTTP request matches a specific path
in a routing tree, but it also calculates the quality of the match and selects the routing node with
the best quality. Given the routes above, which match on the Accept header, and given the request
header `Accept: text/plain; q=0.5, text/html` will match `text/html` because the quality factor in
the HTTP header indicates a lower quality fortext/plain (default is 1.0).

The Header `Accept: text/plain, text/*` will match `text/plain`. Wildcard matches are considered
less specific than direct matches. Therefore the routing matching algorithm will consider them to
have a lower quality.

Another example is making short URLs to named entities, e.g. users, and still being able to prefer
specific pages like `"settings"`. An example would be

* `https://twitter.com/kotlin` -– displays user `"kotlin"`
* `https://twitter.com/settings` -- displays settings page

This can be implemented like this:

```kotlin
get("/{user}") { … }
get("/settings") { … }
```

The parameter is considered to have a lower quality than a constant string, so that even
if `/settings` matches both, the second route will be selected.

### Options

No options()

## Sessions Documentation ([JetBrains](https://www.jetbrains.com))

Adds supports for sessions: with the payload in the client or the server

### Description

Sessions provide a mechanism to persist data between different HTTP requests. Typical use cases
include storing a logged-in user's ID, the contents of a shopping basket, or keeping user
preferences on the client. In Ktor, you can implement sessions by using cookies or custom headers,
choose whether to store session data on the server or pass it to the client, sign and encrypt
session data, and more.

You can configure sessions in the following ways:

* `How to pass data between the server and client`: using cookies or custom headers. Cookies suit
  better for plain HTML applications while custom headers are intended for APIs.
* `Where to store the session payload`: on the client or server. You can pass the serialized
  session's data to the client using a cookie/header value or store the payload on the server and
  pass only a session ID.
* `How to serialize session data`: using a default format, JSON, or a custom engine.
* `Where to store the payload on the server`: in memory, in a folder, or Redis. You can also
  implement a custom storage for keeping session data.
* `How to transform the payload`: you can sign or encrypt data sent to the client for security
  reasons.

### Usage

## Installation

Before installing a session, you need to create a `data class` for storing session data, for
example:

```kotlin
data class LoginSession(val username: String, val count: Int)
```

You need to create several data classes if you are going to use several sessions.

After creating the required data classes, you can install the `Sessions` feature by passing it to
the `install` function in the application initialization code. Inside the `install` block, call
the `cookie` or `header` function depending on how you want to pass data between the server and
client:

```kotlin
import io.ktor.features.*
import io.ktor.sessions.*
// ...
fun Application.module() {
    install(Sessions) {
        cookie<LoginSession>("LOGIN_SESSION")
    }
}
```

You can now set the session content, modify the session, or clear it.

### Multiple Sessions

If you need several sessions in your application, you need to create a separate data class for each
session. For example, you can create separate data classes for storing a user login and settings:

```kotlin
data class LoginSession(val username: String, val count: Int)
data class SettingsSession(val username: String, val settings: Settings)
```

You can store a username on the server in a directory storage and pass user preferences to the
client.

```kotlin
install(Sessions) {
    cookie<LoginSession>("LOGIN_SESSION", directorySessionStorage(File(".sessions"), cached = true))
    cookie<SettingsSession>("SETTINGS_SESSION")
}
```

Note that session names should be unique.

## Set Session Content

To set the session content for a specific `route`, use the `call.sessions` property. The set method
allows you to create a new session instance:

```kotlin
routing {
    get("/") {
        call.sessions.set(LoginSession(name = "John", value = 1))
    }
}
```

To get the session content, you can call `get` receiving one of the registered session types as type
parameter:

```kotlin
routing {
    get("/") {
        val loginSession: LoginSession? = call.sessions.get<LoginSession>()
    }
}
```

To modify a session, for example, to increment a counter, you need to call the copy method of the
data class:

```kotlin
val loginSession = call.sessions.get<LoginSession>() ?: LoginSession(username = "Initial", count = 0)
call.sessions.set(session.copy(value = loginSession.count + 1))
```

When you need to clear a session for any reason (for example, when a user logs out), call the clear
function:

```kotlin
call.sessions.clear<LoginSession>()
```

### Options

* `cookie` -- defines a session for a specific cookie name()

## Status Pages Documentation ([JetBrains](https://www.jetbrains.com))

Allow to respond to thrown exceptions.

### Description

The `StatusPages` feature allows Ktor applications to respond appropriately to any failure state.

### Usage

## Installation

This feature is installed using the standard application configuration:

```kotlin
fun Application.main() {
    install(StatusPages)
}
```

## Exceptions

The exception configuration can provide simple interception patterns for calls that result in a
thrown exception. In the most basic case, a `500` HTTP status code can be configured for any
exceptions.

```kotlin
install(StatusPages) {
    exception<Throwable> { cause ->
        call.respond(HttpStatusCode.InternalServerError)
    }
}
```

More specific responses can allow for more complex user interactions.

```kotlin
install(StatusPages) {
    exception<AuthenticationException> { cause ->
        call.respond(HttpStatusCode.Unauthorized)
    }
    exception<AuthorizationException> { cause ->
        call.respond(HttpStatusCode.Forbidden)
    }
}
```

These customizations can work well when paired with custom status code responses, e.g. providing a
login page when a user has not authenticated.

Each call is only caught by a single exception handler, the closest exception on the object graph
from the thrown exception. When multiple exceptions within the same object hierarchy are handled,
only a single one will be executed.

```kotlin
install(StatusPages) {
    exception<IllegalStateException> { cause ->
        fail("will not reach here")
    }
    exception<ClosedFileSystemException> {
        throw IllegalStateException()
    }
}
intercept(ApplicationCallPipeline.Fallback) {
    throw ClosedFileSystemException()
}
```

Single handling also implies that recursive call stacks are avoided. For example, this configuration
would result in the created IllegalStateException propagating to the client.

```kotlin
install(StatusPages) {
    exception<IllegalStateException> { cause ->
        throw IllegalStateException("")
    }
}
```

## Logging Exceptions

It is important to note that adding the handlers above will "swallow" the exceptions generated by
your routes. In order to log the actual errors generated, you can either log the `cause` manually,
or simply re-throw it as shown below:

```kotlin
install(StatusPages) {
    exception<Throwable> { cause ->
        call.respond(HttpStatusCode.InternalServerError, "Internal Server Error")
        throw cause
    }
}
```

## Status

The `status` configuration provides a custom actions for status responses from within the
application. Below is a basic configuration that provides information about the HTTP status code
within the response text.

```kotlin
install(StatusPages) {
    status(HttpStatusCode.NotFound) {
        call.respond(TextContent("${it.value} ${it.description}", ContentType.Text.Plain.withCharset(Charsets.UTF_8), it))
    }
}
```

## StatusFile

While the `status` configuration provides customizable actions on the response object, the more
common solution is to provide an error HTML page that visitors will see on an error or authorization
failure. The `statusFile` configuration provides that type of functionality.

```kotlin
install(StatusPages) {
    statusFile(HttpStatusCode.NotFound, HttpStatusCode.Unauthorized, filePattern = "error#.html")
}
```

This will resolve two resources from the classpath.

* On a `404`, it will return `error404.html`.
* On a `401`, it will return `error401.html`.

The `statusFile` configuration replaces any `#` character with the value of the status code within
the list of configured statuses.

## Redirections using StatusPages

When doing redirections by executing `call.respondRedirect("/moved/here", permanent = true)`, the
rest of the callee function is executed. So when doing redirections inside guard clauses, you have
to return the function.

```kotlin
routing {
    get("/") {
        if (condition) {
            return@get call.respondRedirect("/invalid", permanent = false)
        }
        call.respondText("Normal response")
    }
}
```

Other frameworks, use exceptions on redirect, so the normal flow is broken and you can execute
redirections in guard clauses or subfunctions without having to worry about returning in all the
subfunction chain. You can use the StatusPages feature to simulate this:

```kotlin
fun Application.module() {
    install(StatusPages) {
        exception<HttpRedirectException> { e ->
            call.respondRedirect(e.location, permanent = e.permanent)
        }
    }
    routing {
        get("/") {
            if (condition) {
                redirect("/invalid", permanent = false)
            }
            call.respondText("Normal response")
        }
    }
}

class HttpRedirectException(val location: String, val permanent: Boolean = false) : RuntimeException()
fun redirect(location: String, permanent: Boolean = false): Nothing = throw HttpRedirectException(location, permanent)
```

### Options

* `exceptions` - Configures response based on mapped exception classes
* `status` - Configures response to status code value
* `statusFile` - Configures standard file response from classpath()

## Static Content Documentation ([JetBrains](https://www.jetbrains.com))

Serves static files from defined locations.

### Description

Ktor has built-in support for serving static content. This can come in useful when you want to serve
style sheets, scripts, images, etc.

### Usage

Whether we're creating a website or an HTTP endpoint, many applications need to serve files (such as
stylesheets, scripts, images, etc.), While it is certainly possible with Ktor to load the contents
of a file and send it in response to a request, given this is such a common functionality, Ktor
simplifies the entire process for us with the `static` Feature.

The first step in defining a static route is to define the path under which the content should be
served. For instance, if you want everything under the route `assets` to be treated as static
content, you need to add the following to your application setup:

```kotlin
routing {
    static("assets") {

    }
}
```

The next step is to define where we want the content to be served from, which can be either

* [A folder](Serving_Static_Content.md#folders)
* [Embedded application resources](Serving_Static_Content.md#embedded-application-resources)

## Folders

In order to serve the contents from a folder, we need to specify the folder name using the `files`
function. The path is **always relative to the application path**:

```kotlin
routing {
    static("assets") {
      files("css")
    }
}
```

`files("css")` would then allow for any file located in the folder `css` to be served as static
content under the given URL pattern, which in this case is `assets`. This means that a request to

`/assets/stylesheet.css` would serve the file `/css/stylesheet.css`

We can have as many folders as we like under a single path. For instance the following would also be
valid:

```kotlin
routing {
    static("assets") {
      files("css")
      files("js")
    }
}
```

### Serving individual files

In addition to serving files from folders, we can also specify individuals files we would like to
make available by using the `file` function. Optionally this takes a second argument which allows us
to map a physical filename to a virtual one:

```kotlin
routing {
    static("static") {
        file("image.png")
        file("random.txt", "image.png")
    }
}
```

### Defining a default file

For a specific path, we can also define the default file to be loaded:

```kotlin
routing {
    static("assets") {
      files("css")
      default("index.html")
    }
}
```

which would cause a request to `/assets/` to serve `index.html`.

### Changing the default root folder

Ktor also provides us the ability to specify a different root folder from where contents is served.
This is useful for instance if we want to dynamically define where contents should be served from,
or even use absolute paths.

We can do this by setting the value of the `staticRootFolder` property:

```kotlin
static("docs") {
    staticRootFolder = File("/system/folder/docs")
    files("public")
}
```

which would then map any request to `/docs` to the physical folder `/system/folder/docs/public`.

## Embedded Application Resources

We can embed content as resources in our applications and serve these using the `resource`
and `resources` functions:

```kotlin
static("assets") {
    resources("css")
}
```

`resources("css")` would then allow for any file located under the resource `css` to be served as
static content under the given URL pattern, which in this case is `assets`. This means that a
request to

`/assets/stylesheet.cs` would serve the file `/css/stylesheet.cs`

We can have as many resources as we like under a single path. For instance the following would also
be valid:

```kotlin
routing {
    static("assets") {
      resources("css")
      resources("js")
    }
}
```

### Serving individual resources

In addition to serving files from resources, we can also specify individuals files we would like to
make available by using the `resource` function. Optionally this takes a second argument which
allows us to map a physical filename to a virtual one:

```kotlin
routing {
    static("static") {
        resource("image.png")
        resource("random.txt", "image.png")
    }
}
```

### Defining a default resource

For a specific path, we can also define the default file to be loaded:

```kotlin
routing {
    static("assets") {
      resources("css")
      defaultResource("index.html")
    }
}
```

### Changing the default resource package

Ktor also provides us the ability to specify a different base resource package from where contents
is served.

We can do this by setting the value of the `staticBasePackage` property:

```kotlin
static("docs") {
    staticBasePackage = File("/system/folder/docs")
    files("public")
}
```

## Sub-routes

If we want to have sub-routes, we can nest `static` functions:

```kotlin
static("assets") {
    files("css")
    static("themes") {
        files("data")
    }
}
```

allowing for `/assets/themes` to load files from the `/data`

## Handling errors

If the request content is not found, Ktor will automatically respond with a `404 Not Found` HTTP
status code. For more information about personalising error handling, please
see [status pages](status_pages.md)

## Customising Content Type header

Ktor automatically looks up the content type of a file based on its extension and sets the
appropriate `Content-Type` header. The list of supported MIME types is defined in the `mimelist.csv`
resource file located in `ktor-server-core` artifact.

### Options

No options()

# Reporting Issues / Support

Please use [our issue tracker](https://youtrack.jetbrains.com/issues/KTOR) for filing feature
requests and bugs. If you'd like to ask a question, we
recommmend [StackOverflow](https://stackoverflow.com/questions/tagged/ktor) where members of the
team monitor frequently.

There is also community support on
the [Kotlin Slack Ktor channel](https://app.slack.com/client/T09229ZC6/C0A974TJ9)

# Reporting Security Vulnerabilities

If you find a security vulnerability in Ktor, we kindly request that you reach out to the JetBrains
security team via
our [responsible disclosure process](https://www.jetbrains.com/legal/terms/responsible-disclosure.html)
.

# Contributing

Please see [the contribution guide](CONTRIBUTING.md) and the [Code of conduct](CODE_OF_CONDUCT.md)
before contributing.

TODO: contribution of features guide (link)