package com.testsmith.extensions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * JUnit 5 extension that retries failed tests.
 * Works with the {@link Retry} annotation.
 */
public class RetryExtension implements InvocationInterceptor {

    private static final Logger logger = LogManager.getLogger(RetryExtension.class);

    @Override
    public void interceptTestMethod(InvocationInterceptor.Invocation<Void> invocation,
                                    ReflectiveInvocationContext<Method> invocationContext,
                                    ExtensionContext extensionContext) throws Throwable {
        Retry retry = resolveRetryAnnotation(extensionContext);
        if (retry == null) {
            invocation.proceed();
            return;
        }

        int maxAttempts = retry.maxAttempts();
        Throwable lastException = null;

        // First attempt: go through the normal JUnit lifecycle (invocation.proceed can only be called once)
        try {
            invocation.proceed();
            return;
        } catch (Throwable t) {
            lastException = t;
            if (maxAttempts <= 1) throw t;
            logger.warn("Attempt 1/{} failed: {}. Retrying...", maxAttempts, t.getMessage());
        }

        // Subsequent attempts: re-run setup + test + teardown via reflection
        Object testInstance = extensionContext.getRequiredTestInstance();
        Method testMethod = invocationContext.getExecutable();

        for (int attempt = 2; attempt <= maxAttempts; attempt++) {
            try {
                runAnnotatedMethods(testInstance.getClass(), BeforeEach.class, testInstance);
                testMethod.invoke(testInstance);
                runAnnotatedMethods(testInstance.getClass(), AfterEach.class, testInstance);

                logger.info("Attempt {}/{} passed", attempt, maxAttempts);
                return;
            } catch (Throwable t) {
                lastException = unwrap(t);

                // Ensure @AfterEach runs even on failure
                try {
                    runAnnotatedMethods(testInstance.getClass(), AfterEach.class, testInstance);
                } catch (Exception ignored) {
                }

                if (attempt < maxAttempts) {
                    logger.warn("Attempt {}/{} failed: {}. Retrying...",
                            attempt, maxAttempts, lastException.getMessage());
                } else {
                    logger.error("Attempt {}/{} failed: {}. No retries left.",
                            attempt, maxAttempts, lastException.getMessage());
                }
            }
        }

        throw lastException;
    }

    private Retry resolveRetryAnnotation(ExtensionContext context) {
        Method testMethod = context.getRequiredTestMethod();
        Retry retry = testMethod.getAnnotation(Retry.class);
        if (retry != null) return retry;
        return context.getRequiredTestClass().getAnnotation(Retry.class);
    }

    private void runAnnotatedMethods(Class<?> clazz, Class<? extends Annotation> annotation, Object instance)
            throws Exception {
        List<Method> methods = new ArrayList<>();
        Class<?> current = clazz;
        while (current != null && current != Object.class) {
            for (Method m : current.getDeclaredMethods()) {
                if (m.isAnnotationPresent(annotation)) {
                    methods.add(m);
                }
            }
            current = current.getSuperclass();
        }

        // JUnit 5: @BeforeEach runs superclass-first, @AfterEach runs subclass-first
        if (annotation == AfterEach.class) {
            methods.sort(Comparator.comparingInt(m -> {
                int depth = 0;
                Class<?> c = m.getDeclaringClass();
                while (c != null && c != Object.class) {
                    depth++;
                    c = c.getSuperclass();
                }
                return -depth;
            }));
        }

        for (Method m : methods) {
            m.setAccessible(true);
            m.invoke(instance);
        }
    }

    private Throwable unwrap(Throwable t) {
        if (t instanceof InvocationTargetException && t.getCause() != null) {
            return t.getCause();
        }
        return t;
    }
}
