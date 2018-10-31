package com.github.binarywang.demo.wx.pay.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.FieldError;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 错误信息响应对象ErrorDTO。
 * <p>
 * 专门用于存储错误信息，作为响应信息对象返回。
 * 带有字段错误内容，用于表示发生错误是由于哪个字段引起的。
 *
 * @author YuJian
 * @see org.springframework.http.ResponseEntity
 * @since 0.5.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDTO implements Serializable {

    private static final long serialVersionUID = 3355960155184637733L;

    /**
     * 错误ID，用于标识具体某次的错误，便于将来排查
     */
    private final String errId;

    /**
     * 错误代码
     */
    private final String errCode;

    /**
     * 错误信息，必填。
     * 如：日期格式不正确
     */
    private final String errMsg;

    /**
     * 错误提示，用于提示用户修正。
     * 如：日期格式要求为yyyy-MM-dd（2014-07-09）
     */
    private final String errHint;

    /**
     * 错误字段，更详细的错误信息
     */
    private List<FieldErrorDTO> fieldErrors;

    /**
     * @param errMsg 错误信息
     */
    public ErrorDTO(String errMsg) {
        this(errMsg, null);
    }

    /**
     * @param errMsg  错误信息
     * @param errHint 错误提示
     */
    public ErrorDTO(String errMsg, String errHint) {
        this(null, errMsg, errHint, null);
    }

    /**
     * @param errCode     错误编码
     * @param errMsg      错误信息
     * @param errHint     错误提示
     * @param fieldErrors 错误字段对象集合
     */
    public ErrorDTO(String errCode, String errMsg, String errHint, List<FieldErrorDTO> fieldErrors) {
        this.errId = UUIDUtil.generateOriginalUUID();
        this.errCode = errCode;
        this.errMsg = errMsg;
        this.errHint = errHint;
        this.fieldErrors = fieldErrors;
    }

    /**
     * 错误字段集合中增加错误字段
     *
     * @param fieldName  字段名称
     * @param fieldValue 字段值
     * @param message    消息信息
     */
    public void add(String fieldName, String fieldValue, String message) {
        if (fieldErrors == null) {
            fieldErrors = new ArrayList<>();
        }
        fieldErrors.add(new FieldErrorDTO(fieldName, fieldValue, message));
    }

    /**
     * 错误字段集合中增加错误字段对象
     *
     * @param fieldError 错误字段对象
     */
    public void add(FieldError fieldError) {
        this.add(fieldError.getField(), fieldError.getRejectedValue().toString(), fieldError.getDefaultMessage());
    }

    /**
     * @return the fieldErrors
     */
    public List<FieldErrorDTO> getFieldErrors() {
        return fieldErrors;
    }

    /**
     * @param fieldErrors the fieldErrors to set
     */
    public void setFieldErrors(List<FieldErrorDTO> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    /**
     * @return the errId
     */
    public String getErrId() {
        return errId;
    }

    /**
     * @return the errCode
     */
    public String getErrCode() {
        return errCode;
    }

    /**
     * @return the errMsg
     */
    public String getErrMsg() {
        return errMsg;
    }

    /**
     * @return the errHint
     */
    public String getErrHint() {
        return errHint;
    }

}
