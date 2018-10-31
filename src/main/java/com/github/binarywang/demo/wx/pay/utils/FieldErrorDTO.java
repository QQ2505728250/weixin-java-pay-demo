package com.github.binarywang.demo.wx.pay.utils;

import java.io.Serializable;

import org.springframework.validation.FieldError;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * {@link org.springframework.validation.FieldError}类的DTO版本。
 * <p>
 * FieldError字段错误内容用于表示发生错误是由于哪个字段引起的，以及出错原因。
 * 在响应返回给前端时，只需返回字段名、字段值、错误信息即可，省略了其他字段。
 *
 * @author YuJian
 * @see org.springframework.validation.FieldError
 * @since 0.1.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FieldErrorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字段名
     */
    private final String fieldName;

    /**
     * 字段值
     */
    private final String fieldValue;

    /**
     * 错误信息
     */
    private final String message;

    /**
     * @param fieldName  字段名
     * @param fieldValue 字段值
     * @param message    错误信息
     */
    public FieldErrorDTO(String fieldName, String fieldValue, String message) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.message = message;
    }

    /**
     * @param fieldError 错误字段对象
     */
    public FieldErrorDTO(FieldError fieldError) {
        this.fieldName = fieldError.getField();
        this.fieldValue = fieldError.getRejectedValue().toString();
        this.message = fieldError.getDefaultMessage();
    }

    /**
     * @return the fieldName
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * @return the fieldValue
     */
    public String getFieldValue() {
        return fieldValue;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

}
