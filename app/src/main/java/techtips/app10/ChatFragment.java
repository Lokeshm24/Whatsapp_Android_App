package techtips.app10;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.attr.mode;

public class ChatFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

    ListView chatList;
    int count=0;
    ChatListAdapter adapter;
    ArrayList<String> arrayList;
    public ActionMode actionMode = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_main2, container, false);
        chatList= (ListView) view.findViewById(R.id.chatList);

        arrayList = new ArrayList<String>();

        DB dbHelper  = new DB(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cr=db.rawQuery("select name from Contacts LIMIT 15", null);

        while (cr.moveToNext()) {
            arrayList.add(cr.getString(0));
        }

        cr.close();

        adapter = new ChatListAdapter(getActivity(), arrayList, getContext());

        chatList.setAdapter(adapter);
        chatList.setOnItemClickListener(this);
        chatList.setOnItemLongClickListener(this);
        chatList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        chatList.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                if(checked){
                    count++;
                }
                else
                    count--;
                mode.setTitle(count+"");
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                actionMode = mode;
                MenuInflater inflater=actionMode.getMenuInflater();
                inflater.inflate(R.menu.chat_action_menu,menu);
//                setActionMode.isActionMode = true;
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.menu_pin:
                        return true;
                    case R.id.menu_delete:
                        return true;
                    case R.id.menu_mute:
                        return true;
                    case R.id.menu_archive:
                        return true;
                    case R.id.menu_addChatShortcut:
                        return true;
                    case R.id.menu_viewContact:
                        startActivity(new Intent(getContext(),ViewContact.class));
                        return true;
                    case R.id.menu_markAsUnread:
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                count=0;
            }
        });

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String name = arrayList.get(position).toString();
        Intent intent=new Intent(getActivity(), Chat.class);
        intent.putExtra("name", name);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        view.setSelected(true);
        return true;
    }

    @Override
    public void onPause(){
        super.onPause();
        if(actionMode!=null) {
            chatList.clearChoices();
            adapter.notifyDataSetChanged();
            actionMode.finish();
            actionMode = null;
            Toast.makeText(getActivity(), "Hello 1", Toast.LENGTH_SHORT).show();
        }
    }
}
