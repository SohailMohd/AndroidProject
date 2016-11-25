package ca.uoit.msohail.charityapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 100485582 on 11/20/2016.
 */

public class NearbyCharity_ArrayAdapter extends BaseAdapter {

    private Context context = null;
    private List<NearbyCharityInfo> charities = null;

    public NearbyCharity_ArrayAdapter(Context context, List<NearbyCharityInfo> charities) {
        this.context = context;
        this.charities = charities;
    }

    @Override
    public int getCount() {
        return charities.size();
    }

    @Override
    public Object getItem(int position) {
        return charities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        NearbyCharityInfo charityInfo = charities.get(position);

        if(convertView == null){
            //no previous view
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.nbcharity_singlelist, parent, false);
        }
        else {


        }

        TextView name = (TextView)convertView.findViewById(R.id.lbl_charity_name);
        name.setText(charityInfo.getCharityName());
        TextView descrip = (TextView)convertView.findViewById(R.id.setCharityDescription);
        descrip.setText(charityInfo.getCharityDescrip());
        TextView address = (TextView)convertView.findViewById(R.id.setCharityAddress);
        address.setText(charityInfo.getCharityAddress());
        TextView phone = (TextView)convertView.findViewById(R.id.setCharityPhone);
        phone.setText(charityInfo.getCharityPhone());
        TextView website = (TextView)convertView.findViewById(R.id.setCharityWebsite);
        website.setText(charityInfo.getCharityWebsite());

        return convertView;
    }
}
