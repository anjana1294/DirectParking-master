package com.directparking.app.util.validation;

import android.text.TextUtils;

import com.mobsandgeeks.saripaar.AnnotationRule;

import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

class BirthDateRule extends AnnotationRule<BirthDate, String> {

    protected BirthDateRule(BirthDate date) {
        super(date);
    }

    @Override
    public boolean isValid(final String date) {

        if (!TextUtils.isEmpty(date)) {
            try {
                DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                        .appendFixedDecimal(DateTimeFieldType.monthOfYear(), 2)
                        .appendLiteral("/")
                        .appendFixedDecimal(DateTimeFieldType.dayOfMonth(), 2)
                        .appendLiteral("/")
                        .appendFixedDecimal(DateTimeFieldType.year(), 4)
                        .toFormatter().withZoneUTC();

                DateTime enteredDate = formatter.parseDateTime(date);

                DateTime dateTime = new DateTime();
                DateTime minAllowedDate = dateTime.minusYears(100);
                DateTime maxAllowedDate = dateTime.minusYears(18);

                return (enteredDate.isAfter(minAllowedDate) || enteredDate.isEqual(minAllowedDate))
                        && (enteredDate.isBefore(maxAllowedDate) || enteredDate.isEqual(maxAllowedDate));
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
}
