package com.sergeyvolkodav.trstorage.utils.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = MoneyValidation.class)
@Documented
public @interface CheckMoney {

    String message() default "Check money format failed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
