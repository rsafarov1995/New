package junit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Running as a test method.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Test {

    /**
     * Expect exactly passed throwable.
     */
    Class<? extends Throwable> expected() default Analyzer.Null.class;

}

