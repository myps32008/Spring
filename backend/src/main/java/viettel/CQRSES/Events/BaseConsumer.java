/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viettel.CQRSES.Events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseConsumer {

    protected ObjectMapper objectMapper = new ObjectMapper();

    protected <T> T fromBinary(String object, Class<T> resultType) {
        try {
            return objectMapper.readValue(object, resultType);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
