package com.irish.aop;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Token {
    boolean save() default false ;
    boolean remove() default false ;
}