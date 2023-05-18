package ro.jtonic.handson.https.resource;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.LocalHostUriTemplateHandler;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.ResourceUtils;

@Configuration
public class SSLContextConfig {

  @Value("${server.ssl.key-store}")
  private String keyStorePath;

  @Value("${server.ssl.key-store-password}")
  private String keyStorePassword;

  @Bean
  public SSLContext sslContext() throws Exception {
    return SSLContextBuilder.create()
        .loadKeyMaterial(loadKeyStore(), keyStorePassword.toCharArray())
        .loadTrustMaterial(null, (chain, authType) -> true)
        .build();
  }

  @Bean
  public HttpClient httpClient(SSLContext sslContext) {
    return HttpClients.custom()
        .setSSLContext(sslContext)
        .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
        .build();
  }

  @Bean
  public HttpComponentsClientHttpRequestFactory requestFactory(HttpClient httpClient) {
    return new HttpComponentsClientHttpRequestFactory(httpClient);
  }

  @Bean
  @Primary
  public TestRestTemplate testRestTemplate(final ApplicationContext applicationContext, HttpComponentsClientHttpRequestFactory requestFactory) {
    final TestRestTemplate testRestTemplate = new TestRestTemplate(new RestTemplateBuilder());
    testRestTemplate.setUriTemplateHandler(new LocalHostUriTemplateHandler(applicationContext.getEnvironment(), "https"));
    testRestTemplate.getRestTemplate().setRequestFactory(requestFactory);
    return testRestTemplate;
  }

  private KeyStore loadKeyStore()
      throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
    KeyStore keyStore = KeyStore.getInstance("PKCS12");
    try (FileInputStream inputStream = new FileInputStream(ResourceUtils.getFile(keyStorePath))) {
      keyStore.load(inputStream, keyStorePassword.toCharArray());
    }
    return keyStore;
  }
}
