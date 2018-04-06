package br.com.lcl.library.annotations;

import android.support.annotation.IdRes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

/**
 * Created by luizlago on 15/08/17.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(FIELD)
public @interface BindImageView {
    @IdRes int value();
}
