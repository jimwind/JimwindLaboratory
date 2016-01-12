package me.jimwind.laboratory.android.adapter;

import me.jimwind.laboratory.R;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListAdapter extends BaseAdapter {
    private Context mContext;
    private int mLastPosition;
    private ListView mListView;
    public ListAdapter(Context context) {
        this.mContext = context;
        mLastPosition = -1;
    }
 
    @Override
    public int getCount() {
        return 30;
    }
 
    @Override
    public Object getItem(int position) {
        return null;
    }
 
    @Override
    public long getItemId(int position) {
        return 0;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(convertView == null ) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.list_item, null);
            holder =new Holder();
            holder.textView = (TextView)convertView.findViewById(R.id.textView);
            holder.UEFAView = (ImageView)convertView.findViewById(R.id.image_uefa);
            holder.mascotView = (ImageView)convertView.findViewById(R.id.image_mascot);
            holder.hint = convertView.findViewById(R.id.hint_image);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        //上下拖动ListView时会调用这里
        Log.e("jimwind","----- getView ----- position = "+position +" mLastPosition = "+mLastPosition);
        if(mLastPosition == position){
            holder.hint.setVisibility(View.VISIBLE);
        }else{
            holder.hint.setVisibility(View.GONE);
        }
        holder.textView.setText("Hello,It is " + position);
        holder.mascotView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(mContext, "start record ...", Toast.LENGTH_LONG).show();
			}
        	
        });
        return convertView;
    }
     
    class Holder {
        TextView textView;
        ImageView UEFAView;
        ImageView mascotView;
        View hint;
    }
     
    public void changeImageVisable(View view,int position) {
    	Log.e("jimwind","position = "+position +" mLastPosition = "+mLastPosition);
    	if(mLastPosition != position && mLastPosition != -1){
    		if(mListView == null){
    			Toast.makeText(mContext, "请给此adapter设置ListView", Toast.LENGTH_LONG).show();
    			return;
    		}
    		Log.e("jimwind"," FirstVisiblePosition = "+mListView.getFirstVisiblePosition());
   			View lv = mListView.getChildAt(mLastPosition - mListView.getFirstVisiblePosition());
   			if(lv != null){
   				View hint = lv.findViewById(R.id.hint_image);
   				hint.setVisibility(View.GONE);
   			}
    		mLastPosition = position;
            Holder holder = (Holder) view.getTag();
            holder.hint.setVisibility(View.VISIBLE);
    	} else if(mLastPosition  == -1){
    		mLastPosition = position;
            Holder holder = (Holder) view.getTag();
            holder.hint.setVisibility(View.VISIBLE);
    	}
    }
    public void setListView(ListView listView){
    	mListView = listView;
    }
}
