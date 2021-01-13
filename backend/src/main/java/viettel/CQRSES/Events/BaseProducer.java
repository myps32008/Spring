package viettel.CQRSES.Events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseProducer {
    protected ObjectMapper objectMapper = new ObjectMapper();
    protected String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        }
        catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
