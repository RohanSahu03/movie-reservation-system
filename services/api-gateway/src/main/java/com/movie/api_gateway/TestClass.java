package com.movie.api_gateway;


import com.movie.common.dto.ApiResponse;

public class TestClass {

    public void test() {

        ApiResponse<String> response =
                ApiResponse.success("Gateway is working");

        System.out.println(response);
    }
}