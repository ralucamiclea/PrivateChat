package ui;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dirtybits.privatechat.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raluca.miclea on 12/11/2017.
 */

public class MessageAdapter extends ArrayAdapter<Message> implements Filterable {

    private Context context;
    private List<Message> list;
    private List<Message> listBackup;
    private int layoutResID;

    public MessageAdapter(Context context, int layoutResourceID, List<Message> list) {
        super(context, layoutResourceID, list);
        this.context = context;
        this.list = list;
        this.listBackup = new ArrayList<>();
        this.listBackup.addAll(list);
        this.layoutResID = layoutResourceID;
    }

    public void remove(Message msg){
        list.remove(msg);
        listBackup.remove(msg);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemHolder itemHolder;
        View view = convertView;
        Message hItem = getItem(position);

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            itemHolder = new ItemHolder();

            view = inflater.inflate(layoutResID, parent, false);
            itemHolder.chat_Name = (TextView) view.findViewById(R.id.chat_name);
            itemHolder.chat_Header = (RelativeLayout) view.findViewById(R.id.chat_header);
            itemHolder.chat_image = (ImageView) view.findViewById(R.id.chat_image);

            view.setTag(itemHolder);

        } else {
            itemHolder = (ItemHolder) view.getTag();
        }

        itemHolder.chat_Name.setText(hItem.getName());
        itemHolder.chat_image.setImageResource(hItem.getImage());

        return view;
    }

    private static class ItemHolder {
        TextView chat_Name;
        RelativeLayout chat_Header;
        ImageView chat_image;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Message getItem(int position) {
        try {
            return list.get(position);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                ArrayList<Message> tempList=new ArrayList<Message>();
                if(constraint == null)
                    filterResults.values = listBackup;
                if(constraint != null && listBackup != null) {
                    int length=listBackup.size();
                    int i=0;
                    while(i<length){
                        Message item=listBackup.get(i);
                        Log.v("MessageAdapter", length + " " +item.getName() + " : " + constraint.toString());
                        if(item.getName().startsWith(constraint.toString())) {
                            Log.v("MessageAdapter", item.getName() + " : " + constraint.toString());
                            tempList.add(item);
                        }
                        i++;
                    }
                    filterResults.values = tempList;
                    filterResults.count = tempList.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list = (ArrayList<Message>) results.values;
                notifyDataSetChanged();
            }

        };
    }
}