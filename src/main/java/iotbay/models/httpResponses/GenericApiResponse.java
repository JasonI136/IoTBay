package iotbay.models.httpResponses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenericApiResponse<T> {
    private int statusCode;
    private String message;
    private boolean error;
    private T data;
}
