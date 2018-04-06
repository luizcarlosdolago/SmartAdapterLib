package br.com.lcl.smartadapterlib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.lcl.library.adapter.SmartAdapterList;
import br.com.lcl.library.listeners.OnItemClickListener;
import br.com.lcl.library.listeners.OnViewCreatedListener;
import br.com.lcl.smartadapterlib.model.Account;


public class MainActivity extends AppCompatActivity {

    ListView mListView;
    List<Account> mAccounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        createAccoounts();
        setAdapter();
    }

    protected void bindViews() {
        mListView = (ListView) findViewById(R.id.lvAccounts);
    }

    protected void createAccoounts() {
        mAccounts = new ArrayList<Account>();
        mAccounts.add( new Account("Account01", "SubAccount01") );
        mAccounts.add( new Account("Account02", "SubAccount02") );
        mAccounts.add( new Account("Account03", "SubAccount03") );
        mAccounts.add( new Account("Account04", "SubAccount04") );
        mAccounts.add( new Account("Account05", "SubAccount05") );
        mAccounts.add( new Account("Account06", "SubAccount06") );
        mAccounts.add( new Account("Account07", "SubAccount07") );
        mAccounts.add( new Account("Account08", "SubAccount08") );
    }

    protected void setAdapter() {
        SmartAdapterList<Account> adapter = new SmartAdapterList<>(this, mAccounts);

        adapter.setOnItemClickListener(new OnItemClickListener<Account>() {
            @Override
            public void onItemClicked(Account account) {
                Toast.makeText(MainActivity.this, account.getName() + " clicked !!!", Toast.LENGTH_SHORT).show();
            }
        });

        adapter.setOnViewCreatedListener(new OnViewCreatedListener<Account>() {
            @Override
            public void onViewCreated(View layout, final Account account) {
                ImageView ivMenu = layout.findViewById(R.id.ivMenu);
                if ( ivMenu != null ) {
                    ivMenu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PopupMenu popup = new PopupMenu(MainActivity.this, view);
                            popup.getMenuInflater().inflate(R.menu.account_menu, popup.getMenu());
                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {
                                    if ( item.getItemId() == R.id.action_edit ) {
                                        Toast.makeText(MainActivity.this, "Edit " + account.getName() + " Clicked!!!", Toast.LENGTH_SHORT).show();
                                    }
                                    else if ( item.getItemId() == R.id.action_delete ) {
                                        Toast.makeText(MainActivity.this, "Delete " + account.getName() + " Clicked!!!", Toast.LENGTH_SHORT).show();
                                    }
                                    return false;
                                }
                            });
                            popup.show();                    }
                    });
                }
            }
        });

        mListView.setAdapter( adapter );
    }

}
