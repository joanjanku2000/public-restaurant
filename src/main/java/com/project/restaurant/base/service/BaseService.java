package com.project.restaurant.base.service;


public class BaseService {
    protected boolean isStringValid(String str,int minlength){
        return str != null && !str.isEmpty() && str.length() > minlength;
    }
}
