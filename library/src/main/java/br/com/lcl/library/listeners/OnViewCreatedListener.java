package br.com.lcl.library.listeners;

import android.view.View;

/**
 * Created by luizlago on 16/08/17.
 */

public interface OnViewCreatedListener<T> {
    void onViewCreated(View layout, T item);
}
