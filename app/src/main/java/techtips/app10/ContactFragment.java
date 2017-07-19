package techtips.app10;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.provider.ContactsContract;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ContactFragment extends Fragment implements AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener {

    ListView contactList;
    ArrayList<String> nameList = new ArrayList<String>();
    ArrayList<String> numberList = new ArrayList<String>();
    int count=0;
    ChatListAdapter arrayAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_main4, container, false);
        contactList = (ListView) view.findViewById(R.id.contacts);

        count++;

        DB dbHelper  = new DB(getContext());
        SQLiteDatabase db=dbHelper.getReadableDatabase();

        Cursor cr=db.rawQuery("select name, phone from Contacts ORDER BY name ASC", null);
        if(count==1) {
            while (cr.moveToNext()) {
                nameList.add(cr.getString(0));
                numberList.add(cr.getString(1));
            }
        }
        cr.close();
        arrayAdapter = new ChatListAdapter(getActivity(), nameList, getContext());
        contactList.setAdapter(arrayAdapter);
        contactList.setOnItemClickListener(this);
        contactList.setOnItemLongClickListener(this);
        arrayAdapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String strPhone = numberList.get(position);
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + strPhone));
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }
}