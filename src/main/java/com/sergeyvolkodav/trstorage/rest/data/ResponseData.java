package com.sergeyvolkodav.trstorage.rest.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sergeyvolkodav.trstorage.rest.enums.AppStatusCodes;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseData {

    private AppStatusCodes status;
    private String error;

    public ResponseData(AppStatusCodes status) {
        this.status = status;
    }

    public ResponseData(AppStatusCodes status, Exception error) {
        this.status = status;
        this.error = error.getLocalizedMessage();
    }

    public String getError() {
        return error;
    }

    public String getStatus() {
        return status.getStatus();
    }

}
