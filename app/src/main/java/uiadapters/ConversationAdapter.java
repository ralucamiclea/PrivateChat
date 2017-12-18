package uiadapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dirtybits.privatechat.R;

import java.util.ArrayList;

/**
 * Created by raluca.miclea on 12/13/2017.
 */

public class ConversationAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    ArrayList<Message> chatMessageList;

    public ConversationAdapter(Activity activity, ArrayList<Message> list) {
        chatMessageList = new ArrayList<>(list);
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (chatMessageList != null)
            return chatMessageList.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void add(Message object) {
        chatMessageList.add(object);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message message = (Message) chatMessageList.get(position);
        View vi = convertView;

        if (convertView == null)
            vi = inflater.inflate(R.layout.item_chatbubble, null);

        //print the message
        TextView msg = (TextView) vi.findViewById(R.id.message_text);
        msg.setText(message.getContent());

        LinearLayout layout = (LinearLayout) vi
                .findViewById(R.id.bubble_layout);
        LinearLayout parent_layout = (LinearLayout) vi
                .findViewById(R.id.bubble_layout_parent);

        // if message is mine then align to right
        if (message.getIsMine()) {
            Log.v("ConversationAdapter", "I sent a msg.");
            //layout.setBackgroundResource(R.drawable.ic_chat_bubble);
            parent_layout.setGravity(Gravity.RIGHT);
        }
        // If not mine then align to left
        else {
            Log.v("ConversationAdapter", "You sent a msg.");
            //layout.setBackgroundResource(R.drawable.ic_chat_bubble);
            parent_layout.setGravity(Gravity.LEFT);
        }
        return vi;
    }
}