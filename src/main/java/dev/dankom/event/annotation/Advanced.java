package dev.dankom.event.annotation;


import jdk.nashorn.internal.ir.annotations.Ignore;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({/* No Targets Allowed */})
@Retention(RetentionPolicy.RUNTIME)
public @interface Advanced {
    boolean isUsed() default true;
    Class<?>[] narrow() default { Ignore.class };
}
