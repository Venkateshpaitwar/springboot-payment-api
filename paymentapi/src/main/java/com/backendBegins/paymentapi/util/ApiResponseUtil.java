package com.backendBegins.paymentapi.util;

import com.backendBegins.paymentapi.DTO.ApiResponse;

public class ApiResponseUtil {

    public static <T> ApiResponse<T> success(String message, T data){
        return new ApiResponse<>("success", message, data);
    }

    public static <T> ApiResponse<T> error(String message){
        return new ApiResponse<>("error", message, null);
    }
}