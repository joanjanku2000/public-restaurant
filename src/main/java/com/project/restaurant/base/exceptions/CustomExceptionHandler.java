package com.project.restaurant.base.exceptions;

import com.project.restaurant.base.dtos.ErrorFormat;
import com.project.restaurant.base.dtos.FieldValueError;
import com.project.restaurant.components.cities.dtos.CityCreateRequest;
import com.project.restaurant.components.cities.exceptions.BadRequestForCityException;
import com.project.restaurant.components.ingredients.dtos.IngredientCreateRequest;
import com.project.restaurant.components.ingredients.exceptions.IngredientBadRequestException;
import com.project.restaurant.components.items.dtos.ItemCreateRequest;
import com.project.restaurant.components.items.exceptions.ItemBadRequestException;
import com.project.restaurant.components.orders.dtos.OrderCreateRequest;
import com.project.restaurant.components.orders.exceptions.OrderBadRequestException;
import com.project.restaurant.components.user.dtos.UserCreateRequest;
import com.project.restaurant.components.user.exceptions.UserBadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.OptimisticLockException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = {NotFoundException.class
            , DuplicateException.class})
    protected ResponseEntity<Object> handleCustomExceptions(RuntimeException ex) {
        ErrorFormat errorBody = new ErrorFormat();
        errorBody.setMessage(ex.getMessage());
        errorBody.setException(ex.getClass().getSimpleName());
        errorBody.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleConstraintViolationException(MethodArgumentNotValidException ex) {
        log.info("Exception {} || {}", ex.getFieldError(), ex.getBindingResult());
        ErrorFormat errorBody = new ErrorFormat();
        errorBody.setMessage("Error in field: |" + ex.getFieldError().getField()
                + "|, given value : |" + ex.getFieldError().getRejectedValue() + "| Message: " + ex.getFieldError().getDefaultMessage());
        errorBody.setException(ex.getClass().getSimpleName());
        errorBody.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ItemBadRequestException.class})
    protected ResponseEntity<ItemCreateRequest> handleIngredientException(ItemBadRequestException ex) {
        List<FieldValueError> fieldValueErrorList
                = getFieldValueErrorList(ex.getMessage(), ex.getFieldName());
        ex.getItemCreateRequest().setFieldValueError(fieldValueErrorList);
        ex.getItemCreateRequest().setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(ex.getItemCreateRequest(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = OptimisticLockException.class)
    protected ResponseEntity<ErrorFormat> handleOptimisticLockException(OptimisticLockException ex) {
        ErrorFormat errorBody = new ErrorFormat();
        errorBody.setMessage(ex.getMessage());
        errorBody.setException(ex.getClass().getSimpleName());
        errorBody.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {IngredientBadRequestException.class})
    protected ResponseEntity<IngredientCreateRequest> handleIngredientException(IngredientBadRequestException ex) {
        List<FieldValueError> fieldValueErrorList
                = getFieldValueErrorList(ex.getMessage(), ex.getFieldName());
        ex.getIngredientCreateRequest().setFieldValueError(fieldValueErrorList);
        ex.getIngredientCreateRequest().setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(ex.getIngredientCreateRequest(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {BadRequestForCityException.class})
    protected ResponseEntity<CityCreateRequest> handleCityException(BadRequestForCityException ex) {
        List<FieldValueError> fieldValueErrorList
                = getFieldValueErrorList(ex.getMessage(), "name");
        ex.getCreateRequest().setFieldValueError(fieldValueErrorList);
        ex.getCreateRequest().setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(ex.getCreateRequest(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {OrderBadRequestException.class})
    protected ResponseEntity<OrderCreateRequest> handleCityException(OrderBadRequestException ex) {
        List<FieldValueError> fieldValueErrorList
                = getFieldValueErrorList(ex.getMessage(), ex.getField());
        ex.getOrderCreateRequest().setFieldValueError(fieldValueErrorList);
        ex.getOrderCreateRequest().setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(ex.getOrderCreateRequest(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UserBadRequestException.class})
    protected ResponseEntity<UserCreateRequest> handleCityException(UserBadRequestException ex) {
        List<FieldValueError> fieldValueErrorList
                = getFieldValueErrorList(ex.getMessage(), ex.getField());
        ex.getUserCreateRequest().setFieldValueError(fieldValueErrorList);
        ex.getUserCreateRequest().setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(ex.getUserCreateRequest(), HttpStatus.BAD_REQUEST);
    }

    private List<FieldValueError> getFieldValueErrorList(String message, String field) {
        FieldValueError fieldValueError = new FieldValueError();
        fieldValueError.setError(message);
        fieldValueError.setField(field);
        List<FieldValueError> fieldValueErrorList = new ArrayList<>();
        fieldValueErrorList.add(fieldValueError);
        return fieldValueErrorList;
    }

}