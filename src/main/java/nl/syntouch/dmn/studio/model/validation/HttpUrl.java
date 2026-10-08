package nl.syntouch.dmn.studio.model.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * An absolute http(s) URL with a host, e.g. {@code https://engine.example.com/engine-rest}.
 */
@URL(regexp = "^https?://[^\\s/?#]+[^\\s]*$", flags = Pattern.Flag.CASE_INSENSITIVE)
@ReportAsSingleViolation
@Constraint(validatedBy = {})
@Documented
@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, TYPE_USE})
@Retention(RUNTIME)
public @interface HttpUrl {
    String message() default "URL must be an absolute http(s) URL";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
