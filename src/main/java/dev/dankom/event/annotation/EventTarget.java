package dev.dankom.event.annotation;

import dev.dankom.event.type.Event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventTarget {
    Advanced advanced() default @Advanced(isUsed = false);
    Class<? extends Event> event();
}
