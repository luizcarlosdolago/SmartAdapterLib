# SmartAdapterLib

SmartAdapter is a library for use with listview easily without create a new adapter


Usage
-----

To use a SmartAdapter you need to create layout row

````
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
              android:orientation="vertical"
              android:layout_width="match_parent"
              android:layout_height="match_parent"
              android:padding="20dp">

    <TextView
        android:id="@+id/tvName"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Name"
        android:textSize="18sp"
        />

    <TextView
        android:id="@+id/tvSurname"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Surname"
        />

</LinearLayout>
````


Create a model or reuse a existent

````java
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

````


Using SmartAdapter

````java
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

        mListView.setAdapter( adapter );
    }
}
````


If you need to access the row in the list, add the OnViewCreatedListener callback as follows:

````java
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
````


Download

Step 1. Add the JitPack repository to your build file
````java
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```` 

Step 2. Add the dependency
`````java
	dependencies {
	        compile 'com.github.luizcarlosdolago:SmartAdapterLib:-SNAPSHOT'
	}
`````
