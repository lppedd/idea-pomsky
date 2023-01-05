package com.github.lppedd.idea.pomsky;

import java.lang.annotation.*;

/**
 * Interface that can be applied to classes or methods that have been
 * implemented to workaround an unwanted standard platform behavior.
 *
 * @author Edoardo Luppi
 */
@Documented
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.SOURCE)
public @interface Workaround {}
