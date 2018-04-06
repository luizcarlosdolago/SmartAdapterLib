package br.com.lcl.smartadapterlib.model;


import br.com.lcl.library.annotations.BindTextView;
import br.com.lcl.library.annotations.LayoutRow;
import br.com.lcl.smartadapterlib.R;

/**
 * Created by luizlago on 16/08/17.
 */

@LayoutRow(R.layout.layout_account_row)
public class Account {

    @BindTextView(R.id.tvName)
    String mName;

    @BindTextView(R.id.tvSurname)
    String mSurname;

    public Account() {

    }

    public Account(String name, String surname) {
        mName = name;
        mSurname = surname;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getSurname() {
        return mSurname;
    }

    public void setSurname(String surname) {
        mSurname = surname;
    }

}
