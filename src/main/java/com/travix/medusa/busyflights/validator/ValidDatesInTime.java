package com.travix.medusa.busyflights.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValidDatesInTimeValidator.class})
public @interface ValidDatesInTime {

    String message() default "Date is in wrong format or return date is lessthan departure data, expected in dd-MM-yyyy";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}