package br.com.mo.financeiroapi.service.api;

import br.com.mo.financeiroapi.model.vo.CotacaoMoedaGenericResponseVO;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import javax.net.ssl.SSLContext;
import java.net.URI;
import org.springframework.stereotype.Service;

@Service
public class ApiService {

    private RequestEntity request;
    private ResponseEntity response;
    private final String MSGCONECTTIMEOUT = "connect timed out";
    private final String MSGREADTIMEOUT = "Read timed out";
    private static final int CONNECT_TIMEOUT = 3000;
    private static final int REQUEST_TIMEOUT = 7000;
    private static final int SOCKET_TIMEOUT = 5000;
    Logger logger = LoggerFactory.getLogger(ApiService.class);

    public CotacaoMoedaGenericResponseVO getCotacaoMoeda(Object pDados, String pUrl) throws Exception {
        pUrl = "https://economia.awesomeapi.com.br/last/USD-BRL";

        this.response = null;
        this.request = null;

        ResponseEntity<CotacaoMoedaGenericResponseVO> response = getHttps(new URI(pUrl), pDados, String.class, true);

        if (response.getStatusCode() == HttpStatus.REQUEST_TIMEOUT) {
            throw new Exception("Não foi possível recuperar token!");

        } else if (response.getStatusCode() != HttpStatus.OK) {
            throw new Exception("Erro ao realizar conexão, verifique a configuração!");
        }

        return response.getBody();
    }

    protected ResponseEntity getHttps(URI url, Object t, Class<?> classe, boolean pTimeOut) throws Exception {
        try {
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE.toString());

            if (request == null) {
                request = new RequestEntity(t, headers, HttpMethod.GET, url);
            }

            if (response == null) {
                response = createRestTemplate(pTimeOut).exchange(request, classe);
            }

            if (request != null) {
                logger.debug(request.toString());
            }

            if (response != null) {
                logger.debug(response.toString());
            }

        } catch (ResourceAccessException timeout) {
            if (timeout.getMostSpecificCause().getMessage().equals(MSGCONECTTIMEOUT)
                    || timeout.getMostSpecificCause().getMessage().equals(MSGREADTIMEOUT)) {
                response = new ResponseEntity(HttpStatus.REQUEST_TIMEOUT);

            }
        } catch (Exception ex) {
            response = new ResponseEntity(HttpStatus.BAD_REQUEST);

        } finally {
            if (response == null) {
                response = new ResponseEntity(HttpStatus.BAD_REQUEST);
            }

            return response;
        }
    }

    private RestTemplate createRestTemplate(boolean pTimeOut) throws Exception {

        TrustStrategy acceptingTrustStrategy = (cert, authType) -> true;
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
                NoopHostnameVerifier.INSTANCE);

        Registry<ConnectionSocketFactory> socketFactoryRegistry
                = RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("https", sslsf)
                        .register("http", new PlainConnectionSocketFactory())
                        .build();

        BasicHttpClientConnectionManager connectionManager
                = new BasicHttpClientConnectionManager(socketFactoryRegistry);

        CloseableHttpClient httpClient;

        RequestConfig requestConfig = RequestConfig.custom().build();

        if (pTimeOut) {
            requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(REQUEST_TIMEOUT)
                    .setConnectTimeout(CONNECT_TIMEOUT)
                    .setSocketTimeout(SOCKET_TIMEOUT)
                    .build();
        }

        httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig)
                .setSSLSocketFactory(sslsf)
                .setConnectionManager(connectionManager)
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory
                = new HttpComponentsClientHttpRequestFactory(httpClient);

        return new RestTemplate(requestFactory);
    }
}
