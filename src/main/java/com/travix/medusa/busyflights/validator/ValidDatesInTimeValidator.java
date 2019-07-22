package com.travix.medusa.busyflights.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.travix.medusa.busyflights.domain.BusyFlightsRequest;
import com.travix.medusa.busyflights.util.DateHelpers;

import java.time.LocalDate;

public class ValidDatesInTimeValidator implements ConstraintValidator<ValidDatesInTime, BusyFlightsRequest> {

    @Override
    public void initialize(ValidDatesInTime constraint) {
    }

    @Override
    public boolean isValid(BusyFlightsRequest json, ConstraintValidatorContext constraintValidatorContext) {
        try {
            LocalDate departureDate = DateHelpers.getLocalDateFromString(json.getDepartureDate());
            LocalDate returnDate = DateHelpers.getLocalDateFromString(json.getReturnDate());

            return returnDate.isAfter(departureDate);
        } catch (Exception e) {
            return false;
        }
    }

}