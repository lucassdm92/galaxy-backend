package com.galaxybck.model.dto;

public class ApiResponse<T> {

    private String responseCode;
    private String message;
    private T data;

    public ApiResponse(String responseCode, String message, T data) {
        this.responseCode = responseCode;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("200", "OK", data);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>("400", message, null);
    }

    public String getResponseCode() {
        return responseCode;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

}
