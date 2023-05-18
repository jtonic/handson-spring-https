# Handling certificates


## Server side

1. identity.p12
    > keytool -genkeypair -alias server -keyalg RSA -keysize 2048 -validity 365 -keystore identity.p12 -storetype PKCS12
    > 
    > __keystore pwd:__ server_key_pwd

2. export the certificate

   > keytool -exportcert -alias server -file server-certificate.crt -keystore identity.p12 -storetype PKCS12

3. http-trust.p12 
    > keytool -importcert -alias server -file server-certificate.crt -keystore http-trust.p12 -storetype PKCS12
    > 
    > __keystore pwd:__ server_trust_pwd


## Client side

1. client-keystore.p12 
    > keytool -genkeypair -alias client -keyalg RSA -keysize 2048 -validity 365 -keystore client-keystore.p12 -storetype PKCS12
    > 
    > __keystore pwd:__ client_key_pwd

2. export certificate
    > keytool -export -alias client -file client-certificate.crt -keystore client-keystore.p12 -storetype PKCS12

3. import the client certificate in server-trustore
    > keytool -import -alias client -file client-certificate.crt -keystore http-trust.p12

## Show certs

> keytool -list -v -keystore client-keystore.p12 -storetype PKCS12

> keytool -list -v -keystore http-trust.p12 -storetype PKCS12
