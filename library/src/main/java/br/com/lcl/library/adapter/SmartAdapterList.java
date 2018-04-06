package br.com.lcl.library.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

import br.com.lcl.library.annotations.BindImageView;
import br.com.lcl.library.annotations.BindTextView;
import br.com.lcl.library.annotations.LayoutRow;
import br.com.lcl.library.listeners.OnItemClickListener;
import br.com.lcl.library.listeners.OnViewCreatedListener;

/**
 * Created by luizlago on 15/08/17.
 */

public class SmartAdapterList<T> extends BaseAdapter {

    Context mContext;
    List<T> mItens;
    LayoutInflater mInflater;
    OnItemClickListener mOnItemClickListener;
    OnViewCreatedListener mOnViewCreatedListener;

    public SmartAdapterList(Context context, List<T> itens) {
        mContext = context;
        mItens = itens;
        mInflater = LayoutInflater.from( context );
    }

    @Override
    public int getCount() {
        return mItens.size();
    }

    @Override
    public Object getItem(int i) {
        return mItens.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if ( mItens != null ) {
            T item = mItens.get(position);
            Class<?> clazz = item.getClass();
            if ( clazz.isAnnotationPresent(LayoutRow.class) ) {
                if ( view == null ) {
                    Annotation layoutRowAnnotation = clazz.getAnnotation(LayoutRow.class);
                    LayoutRow layoutRow = (LayoutRow) layoutRowAnnotation;
                    view = mInflater.inflate(layoutRow.value(), null);
                }

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if ( mOnItemClickListener != null ) {
                            mOnItemClickListener.onItemClicked(mItens.get(position));
                        }
                    }
                });

                if ( mOnViewCreatedListener != null ) {
                    mOnViewCreatedListener.onViewCreated(view, mItens.get(position));
                }

                for (Field field : clazz.getDeclaredFields()) {
                    if ( field.isAnnotationPresent(BindTextView.class) ) {
                        bindTextView(view, field, item);
                    }
                    else if ( field.isAnnotationPresent(BindImageView.class) ) {
                        bindImageView(view, field, item);
                    }
                }

            }

        }

        return view;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void setOnViewCreatedListener(OnViewCreatedListener listener) {
        mOnViewCreatedListener = listener;
    }

    protected void bindTextView(View view, Field field, T item) {
        BindTextView bindTextView = (BindTextView) field.getAnnotation(BindTextView.class);
        TextView textView = (TextView) view.findViewById(bindTextView.value());
        try {
            if ( !field.isAccessible() ) {
                field.setAccessible(true);
            }
            Object value = field.get(item);
            textView.setText(String.valueOf(value));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    protected void bindImageView(View view, Field field, T item) {
        BindImageView biv = (BindImageView) field.getAnnotation(BindImageView.class);
        ImageView imageView = (ImageView) view.findViewById(biv.value());
        try {
            if ( !field.isAccessible() ) {
                field.setAccessible(true);
            }
            Object value = field.get(item);
            Class<?> type = field.getType();
            if ( type == String.class || type == Integer.class || type == int.class ) {
                Glide.with(mContext).load(value).into(imageView);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
