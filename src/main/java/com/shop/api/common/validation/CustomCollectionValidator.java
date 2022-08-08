package com.shop.api.common.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import java.util.Collection;

@Component
public class CustomCollectionValidator implements Validator {

    private SpringValidatorAdapter validatorAdapter;

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(target instanceof Collection){
            Collection collection = (Collection) target;

            for (Object object : collection) {
                validatorAdapter.validate(object, errors);
            }
        } else {
            validatorAdapter.validate(target, errors);
        }
    }
}
