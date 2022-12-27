package com.fyrm.fyrm_service.infrastructure.spring.mvc.controller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/fyrm")
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FyrmApiController {

}
