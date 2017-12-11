package ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dirtybits.privatechat.R;

import java.util.List;

/**
 * Created by raluca.miclea on 12/11/2017.
 */

public class MessageAdapter extends ArrayAdapter<Message> {

    private Context context;
    private List<Message> list;
    private int layoutResID;

    public MessageAdapter(Context context, int layoutResourceID, List<Message> list) {
        super(context, layoutResourceID, list);
        this.context = context;
        this.list = list;
        this.layoutResID = layoutResourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemHolder itemHolder;
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            itemHolder = new ItemHolder();

            view = inflater.inflate(layoutResID, parent, false);
            itemHolder.chat_Name = (TextView) view.findViewById(R.id.chat_name);
            itemHolder.chat_Header = (RelativeLayout) view.findViewById(R.id.chat_header);

            view.setTag(itemHolder);

        } else {
            itemHolder = (ItemHolder) view.getTag();
        }

        final Message hItem = list.get(position);

        itemHolder.chat_Name.setText(hItem.getName());

        return view;
    }

    private static class ItemHolder {
        TextView chat_Name;
        RelativeLayout chat_Header;
    }
}