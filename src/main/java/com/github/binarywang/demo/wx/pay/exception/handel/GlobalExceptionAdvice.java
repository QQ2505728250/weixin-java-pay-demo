package com.github.binarywang.demo.wx.pay.exception.handel;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.binarywang.demo.wx.pay.exception.MyOrderNotExistException;
import com.github.binarywang.demo.wx.pay.exception.PayException;
import com.github.binarywang.demo.wx.pay.utils.ErrorDTO;


/**
 * 全局异常通知
 * <p>
 *
 * @author denghaijing
 */
@ControllerAdvice
public class GlobalExceptionAdvice {


    @ExceptionHandler(PayException.class)
    @ResponseBody
    public ResponseEntity<ErrorDTO> handlePayException(PayException e) {
        return ResponseEntity.ok().body(new ErrorDTO(e.getMsg()));
    }

    @ExceptionHandler(MyOrderNotExistException.class)
    @ResponseBody
    public ResponseEntity<ErrorDTO> handleMyOrderNotExistException(MyOrderNotExistException e) {
        return ResponseEntity.ok().body(new ErrorDTO(e.getMsg()));
    }
}
