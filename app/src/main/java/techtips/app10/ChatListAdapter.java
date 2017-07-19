package techtips.app10;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ChatListAdapter extends BaseAdapter {
    Activity activity;
    ArrayList<String> data;
    Context context;
    public ChatListAdapter(Activity activity, ArrayList<String> data, Context context)
    {
        this.activity=activity;
        this.data=data;
        this.context = context;
    }
    @Override
    public int getCount() {
        return data.size();
    }
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        view=activity.getLayoutInflater().inflate(R.layout.chat_list, null);
        TextView name=(TextView)view.findViewById(R.id.contact_name);
        TextView time=(TextView)view.findViewById(R.id.message_time);
        TextView message=(TextView)view.findViewById(R.id.message);
        ImageView picture = (ImageView) view.findViewById(R.id.user_dp);
        ImageView mute = (ImageView) view.findViewById(R.id.mute);
        name.setText(data.get(position).toString());
        time.setText("11:53 P.M.");
        message.setText("Some message");
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setTitle("");
                dialog.setContentView(R.layout.image_click);
                dialog.show();
            }
        });

        return view;
    }
}
