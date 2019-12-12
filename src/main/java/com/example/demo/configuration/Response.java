
package com.example.demo.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.ZoneId;
import java.time.ZonedDateTime;


public class Response<T, E> {

    String returnMessage;

    ZonedDateTime respondTime = ZonedDateTime.now(ZoneId.systemDefault());

    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    String requestId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    T data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    E error;

    public int getStatus() {
        return status.value();
    }

    public void setStatus(int value) {
        status = HttpStatus.valueOf(value);
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public ZonedDateTime getRespondTime() {
        return respondTime;
    }

    public void setRespondTime(ZonedDateTime respondTime) {
        this.respondTime = respondTime;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public E getError() {
        return error;
    }

    public void setError(E error) {
        this.error = error;
    }
}
