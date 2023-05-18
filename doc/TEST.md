# How to test the Web API

## curl commands

> curl -k --cert-type P12 --cert client-keystore.p12:client_key_pwd https://localhost:8443/keepalive
 
> curl -k --cert-type P12 --cert server-keystore.p12:client_key_pwd https://localhost:8443/keepalive

> curl -k --cert-type P12 --cert server-truststore.p12:server_trust_pwd https://localhost:8443/keepalive

## Postman

1. import postman from `companion/postman/SpringBoot.postman_collection.json`

2. Add the `server-keystore.p12` in settings/certificates as can be seen in the picture below. For pwd see `src/main/resources/application.yml`

![img.png](img.png)