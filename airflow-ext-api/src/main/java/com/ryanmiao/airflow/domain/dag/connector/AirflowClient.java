package com.ryanmiao.airflow.domain.dag.connector;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ryanmiao.airflow.domain.dag.connector.contract.AirflowRestApiRs;
import com.ryanmiao.airflow.domain.dag.connector.contract.DeleteDagRs;
import feign.Feign;
import feign.FeignException;
import feign.gson.GsonDecoder;
import java.io.IOException;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author ryan
 * @date 19-9-21 上午11:57
 */
@Component
public class AirflowClient {

    private AirflowConnector connector;
    private static final Logger LOGGER = LoggerFactory.getLogger(AirflowClient.class);
    private final ObjectMapper objectMapper;

    public AirflowClient(@Value("${feign.airflow}") String airflowHost,
        ObjectMapper objectMapper) {
        this.connector = Feign
            .builder().decoder(new GsonDecoder()).target(AirflowConnector.class, airflowHost);
        this.objectMapper = objectMapper;
    }

    public DeleteDagRs deleteDagRecord(String id) {
        DeleteDagRs deleteDagRs;
        try {
            deleteDagRs = this.connector.deleteRecord(id);
        } catch (FeignException ex) {
            String content = ex.contentUTF8();
            try {
                deleteDagRs = objectMapper.readValue(content, DeleteDagRs.class);
            } catch (IOException ioex) {
                throw new RuntimeException(ioex);
            }


        }
        return deleteDagRs;
    }

    public AirflowRestApiRs restApi(String api, Map<String, Object> params) {
        try {
            return this.connector.restApi(api, params);
        } catch (FeignException ex) {
            String content = ex.contentUTF8();
            try {
                return objectMapper.readValue(content, AirflowRestApiRs.class);
            } catch (IOException ioex) {
                throw new RuntimeException(ioex);
            }
        }
    }
}
