# REST API Security Guide

## Difference between authentication vs. authorization
- authentication: is user really someone who he claims to be
- authorization: what user is allowed to do

## Four Ways to Secure RESTful Web Services
### 1. BASIC Authentication
You use login/password forms – it’s basic authentication only.

### 2. DIGEST Authentication
This authentication method makes use of a hashing algorithms to encrypt the password (called **password hash**) entered by the user before sending it to the server.

Please remember that once this password hash is generated and stored in database, you can not convert it back to original password.

### 3. Client CERT Authentication
This is a mechanism in which a trust agreement is established between the server and the client through certificates. They must be signed by an agency established to ensure that the certificate presented for authentication is legitimate, which is known as CA.

Using this technique, when the client attempts to access a protected resource, instead of providing a username or password, it presents the certificate to the server. The certificate contains the user information for authentication including security credentials, besides a unique private-public key pair. The server then determines if the user is legitimate through the CA. Additionally, it must verify whether the user has access to the resource. This mechanism must use HTTPS as the communication protocol as we don’t have a secure channel to prevent anyone from stealing the client’s identity.

### 4. OAUTH2 API Keys
They require you to provide API key and API secret to rightly identify you. These API key and secret are some random encoded string which is impossible to guess.

## REST API Security Implementations
### JAX-RS SecurityContext instance
The `javax.ws.rs.core.SecurityContext` interface provides access to security-related information for a request and is very similar to `javax.servlet.http.HttpServletRequest`.

You access the `SecurityContext` by injecting an instance into a class field, setter method, or method parameter using the `javax.ws.rs.core.Context` annotation e.g. in below code `sc.isUserInRole()` is used to check authorization for user.
```
@GET
@Produces("text/plain;charset=UTF-8")
@Path("/hello")
public String sayHello(@Context SecurityContext sc) {
    if (sc.isUserInRole("admin"))  
        return "Hello World!";
    throw new SecurityException("User is unauthorized.");
}
```

### JAR-RS annotations for method level authorization
JAX-RS provides below annotations for this purpose.
- [@PermitAll](https://docs.oracle.com/javaee/6/api/javax/annotation/security/PermitAll.html)
- [@DenyAll](https://docs.oracle.com/javaee/6/api/javax/annotation/security/DenyAll.html)
- [@RolesAllowed](https://docs.oracle.com/javaee/6/api/javax/annotation/security/RolesAllowed.html)

```
@RolesAllowed("ADMIN")
@PUT
@Path("/users/{id}")
public Response updateUserById(@PathParam("id") int id) {
    //Update the User resource
    UserDatabase.updateUser(id);
    return Response.status(200).build();
}
```

## REST API Security Best Practices
1. Use only HTTPS protocol so that your whole communication is always encrypted.
2. Never send auth credentials or API keys as query param. They appear in URL and can be logged or tracked easily.
3. Use hardest encryption level always. It will help in having more confidence.
4. For resources exposed by RESTful web services, it’s important to make sure any PUT, POST, and DELETE request is protected from Cross Site Request Forgery.
5. Always validate the input data asap it is received in server method. Use only primitive data as input parameter as much as possible.
6. Rely on framework provided validation features as they are tested by large community already.

## References
- [REST API Security Guide](https://howtodoinjava.com/security/rest-api-security-guide/)