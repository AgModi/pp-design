package com.example.ppdesign.dto.response;

import lombok.Data;

@Data
public class EmpCreateResp {

    private String message;

    public EmpCreateResp() {
    }

    public EmpCreateResp(String message) {
        this.message = message;
    }
}
