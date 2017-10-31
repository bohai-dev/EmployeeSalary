package com.bohai.employeeSalary.vo;

public class CommonResponseHead {

    /**
     * 成功为00000
     */
    private String responseCode = "00000";
    
    /**
     * 异常消息
     */
    private String message;
    

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
