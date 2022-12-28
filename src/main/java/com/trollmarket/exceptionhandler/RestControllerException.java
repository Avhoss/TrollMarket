package com.trollmarket.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestControllerException {

    //digunakan jika object tidak ditemukan, contohnya pada findById, namun eror message ini perlu dithrow pada method yang akan menggunakan nya
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(ObjectNotFound e) {

        ErrorResponse response = new ErrorResponse();
        response.setMessage(e.getMessage());
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setTimeStamp(System.currentTimeMillis());
        response.setKeterangan("eror dari ObjectNotFound");

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    //akan dijalankan otomatis ketika ada runtime exception, misal memasukkan nilai tidak sesuai tipe data
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(RuntimeException e) {

        ErrorResponse response = new ErrorResponse();
        response.setMessage(e.getMessage());
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setTimeStamp(System.currentTimeMillis());
        response.setKeterangan("eror dari RuntimeException");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    //akan dijalankan otomatis ketika argument method tidak valid
    //contoh nya input data null pada field yang telah di set NotNull
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException e) {

        Map<String, String> errors = new HashMap<>();

//        e.getBindingResult().getAllErrors().forEach( error -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        }); cara penulisan lainnya seperti dibawah ini

        e.getBindingResult()
                .getAllErrors()
                .forEach(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });

        ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
        response.setMessage(errors);
        response.setTimeStamp(System.currentTimeMillis());
        response.setKeterangan("eror dari MethodArgumentNotValidException");
        return new ResponseEntity<>(response, HttpStatus.resolve(406));
    }
}
