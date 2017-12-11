package ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dirtybits.privatechat.R;

import java.util.List;

/**
 * Created by raluca.miclea on 12/11/2017.
 */

public class ContactsAdapter extends ArrayAdapter<Contact> {

private Context context;
private List<Contact> list;
private int layoutResID;

public ContactsAdapter(Context context, int layoutResourceID, List<Contact> list) {
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
}
