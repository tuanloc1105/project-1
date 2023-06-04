package vn.com.tdtu.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import vn.com.tdtu.common.log.LOG;

import java.util.Map;

@Component
public class HttpUtil {

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    private final RestTemplate noSslRestTemplate;

    @Autowired
    public HttpUtil(ObjectMapper objectMapper,
                    @Qualifier("restTemplate") RestTemplate restTemplate,
                    @Qualifier("noSslRestTemplate") RestTemplate noSslRestTemplate) {
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
        this.noSslRestTemplate = noSslRestTemplate;
    }

    public <T> ResponseEntity<T> callApi(Object data, String url, HttpMethod method, Map<String, String> requestHeader, ParameterizedTypeReference<T> respModel, boolean sslCheck) throws JsonProcessingException {
        LOG.info("STARTING FETCHING API TO ENDPOINT %s WITH VALUE \n %s", url, LoggingUtil.maskValue(this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data)));
        HttpHeaders headers = new HttpHeaders();
        if (requestHeader != null && !requestHeader.isEmpty()) {
            for (Map.Entry<String, String> entry : requestHeader.entrySet()) {
                headers.set(entry.getKey(), entry.getValue());
            }
        }
        HttpEntity<Object> requestEntity = new HttpEntity<>(data, headers);
        try {
            ResponseEntity<T> response;
            if (sslCheck) {
                response = this.restTemplate.exchange(url, method, requestEntity, respModel);
            } else {
                response = this.noSslRestTemplate.exchange(url, method, requestEntity, respModel);
            }
            LOG.info("[API RESPONSE] \n %s", LoggingUtil.maskValue(this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response.getBody())));
            return response;
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw e;
        } finally {
            LOG.info("FETCHING API COMPLETED");
        }
    }

}
