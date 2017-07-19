package techtips.app10;

import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class ViewContact extends AppCompatActivity{
Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);


        toolbar = (Toolbar) findViewById(R.id.viewContact_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Hello");
        toolbar.inflateMenu(R.menu.chat_menu);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }


    }
}
