package techtips.app10;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;


public class FetchContacts extends AsyncTask{

    Context context;
    ArrayList<String> nameList = new ArrayList<String>();
    ArrayList<String> numberList = new ArrayList<String>();

    public FetchContacts(Context context){
        this.context = context;
    }


    public ArrayList getContacts(){
        return nameList;
    }
    public ArrayList getNumber(){
        return numberList;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        Cursor cursor = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            int idIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);

            String strName = cursor.getString(index);
            String strID = cursor.getString(idIndex);
            String strName1 = "";
            String strPhoneNumber = "";
            try {
                Cursor cursor1 = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                                ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                                ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE, new String[]{strID}, null);
                while (cursor1.moveToNext()) {
                    int phoneIndex = cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    if (phoneIndex >= 0) {
                        String strPhone = cursor1.getString(phoneIndex);
                        strName1 = strName;
                        strPhoneNumber = strPhone;
                    }
                }
            }
            catch (Exception ee) {

            }
            nameList.add(strName1);
            numberList.add(strPhoneNumber);
        }
        return null;
    }


    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        DB dbHelper = new DB(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("Contacts", null, null);
        ContentValues cv=new ContentValues();
        for (int i=0; i<nameList.size(); i++){
            if(nameList.get(i) != null || numberList.get(i) != null || nameList.get(i).trim() != "" || numberList.get(i).trim() != "") {
                cv.put("name", nameList.get(i));
                cv.put("phone", numberList.get(i));
            }
            db.insert("Contacts", null, cv);
        }
    }
}
