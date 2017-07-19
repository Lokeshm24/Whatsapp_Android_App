package techtips.app10;

import android.animation.ObjectAnimator;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    TextView counter;
    int count = 0;
    String valueOfCount;
    Animation animation;
    Button enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        animation = AnimationUtils.loadAnimation(this, R.anim.button_anim);
        counter = (TextView) findViewById(R.id.counter);
        enter = (Button) findViewById(R.id.enter);

        SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences("APP_FILE", MODE_PRIVATE);
        count = sharedPreferences.getInt("Count", 0);
        count++;
        valueOfCount = String.valueOf(count);
        counter.setText(valueOfCount);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Count", count);
        editor.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        count++;
        valueOfCount = String.valueOf(count);
        counter.setText(valueOfCount);
        SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences("APP_FILE", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Count", count);
        editor.commit();
    }

    public void newActivity(View view){
        count--;

        enter.startAnimation(animation);

        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);


        FetchContacts fetchContacts = new FetchContacts(this);
        fetchContacts.execute();
    }
}
