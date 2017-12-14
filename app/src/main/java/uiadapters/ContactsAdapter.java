package uiadapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dirtybits.privatechat.R;

import java.util.ArrayList;
import java.util.List;

/**
* Created by raluca.miclea on 12/11/2017.
*/

public class ContactsAdapter extends ArrayAdapter<Contact> {

    private Context context;
    private List<Contact> list;
    private List<Contact> listBackup;
    private int layoutResID;

    public ContactsAdapter(Context context, int layoutResourceID, List<Contact> list) {
        super(context, layoutResourceID, list);
        this.context = context;
        this.list = list;
        this.listBackup = new ArrayList<>();
        this.listBackup.addAll(list);
        this.layoutResID = layoutResourceID;
        }

    public void remove(Contact cnt){
        list.remove(cnt);
        listBackup.remove(cnt);
    }

    public void add(Contact cnt){
        list.add(cnt);
        listBackup.add(cnt);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemHolder itemHolder;
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            itemHolder = new ItemHolder();

            view = inflater.inflate(layoutResID, parent, false);
            itemHolder.tName = (TextView) view.findViewById(R.id.tName);
            itemHolder.lHeader = (RelativeLayout) view.findViewById(R.id.lHeader);
            itemHolder.contact_image = (ImageView) view.findViewById(R.id.contact_image);

            view.setTag(itemHolder);
        } else {
            itemHolder = (ItemHolder) view.getTag();
        }

        final Contact hItem = list.get(position);

        itemHolder.tName.setText(hItem.getName());
        itemHolder.contact_image.setImageResource(hItem.getImage());

        return view;
        }

        private static class ItemHolder {
            TextView tName;
            ImageView contact_image;
            RelativeLayout lHeader;
        }

        @Override
        public int getCount() {
                return list.size();
        }

        @Nullable
        @Override
        public Contact getItem(int position) {
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
                    ArrayList<Contact> tempList=new ArrayList<Contact>();
                    if(constraint == null)
                        filterResults.values = listBackup;
                    if(constraint != null && listBackup != null) {
                        int length=listBackup.size();
                        int i=0;
                        while(i<length){
                            Contact item=listBackup.get(i);
                            Log.v("ContactAdapter", length + " " +item.getName() + " : " + constraint.toString());
                            if(item.getName().startsWith(constraint.toString())) {
                                Log.v("ContactAdapter", item.getName() + " : " + constraint.toString());
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
                    list = (ArrayList<Contact>) results.values;
                    notifyDataSetChanged();
                }

            };
        }
}
