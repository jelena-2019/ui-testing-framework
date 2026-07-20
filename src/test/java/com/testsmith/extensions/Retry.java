package com.testsmith.extensions;

import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to retry flaky tests on failure.
 *
 * Usage:
 *   @Retry(maxAttempts = 3)
 *   @Test
 *   void myFlakyTest() { ... }
 *
 * Can be applied to individual test methods or to the entire test class.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(RetryExtension.class)
public @interface Retry {
    int maxAttempts() default 3;
}
