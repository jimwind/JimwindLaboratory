package me.jimwind.laboratory.android.activity.trywidget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.jimwind.laboratory.R;
import me.jimwind.laboratory.android.activity.ui.BaseActivity;
import me.jimwind.laboratory.android.view.CustomExpandableListView;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class TryExpandableListViewActivity extends BaseActivity implements
		OnClickListener {
	private CustomExpandableListView listview = null;
	private List<String> parent = null;
	private Map<String, List<String>> map = null;
	private Context mContext = null;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.try_expandablelist);
		mContext = this;
		listview = (CustomExpandableListView)this.findViewById(R.id.list);
		initData();
		listview.setAdapter(new MyAdapter());
		listview.setGroupIndicator(null);
		listview.setOnGroupExpandListener(new OnGroupExpandListener(){

			@Override
			public void onGroupExpand(int groupPosition) {
				// TODO Auto-generated method stub
				for(int i=0, count = listview.getExpandableListAdapter().getGroupCount(); i<count; i++){
					if(groupPosition != i){
						listview.collapseGroup(i);
					}
				}
			}
		});
	}


	private void initData(){
		parent = new ArrayList<String>();
		parent.add("word");
		parent.add("sentence");
		parent.add("dialog");
		
		map = new HashMap<String, List<String>>();
		List<String> words = new ArrayList<String>();
		words.add("hello");
		words.add("happy");
		words.add("world");
		map.put("word", words);
		
		List<String> sentences = new ArrayList<String>();
		sentences.add("Hello world!");
		sentences.add("How are you?");
		map.put("sentence", sentences);
		
		List<String> dialog = new ArrayList<String>();
		dialog.add("Excuse me, is a libray nearby?");
		dialog.add("Yes, there is a library between the drugstore and the post office");
		dialog.add("Thank you very much.");
		dialog.add("You are welcome");
		map.put("dialog",dialog);
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	class MyAdapter extends BaseExpandableListAdapter{

		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return parent.size();
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
			String key = parent.get(groupPosition);
			int size = map.get(key).size();
			return size;
		}

		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return parent.get(groupPosition);
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			String key = parent.get(groupPosition);
			return (map.get(key).get(childPosition));
		}

		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return groupPosition;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return childPosition;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public View getGroupView(final int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if(convertView == null){
				LayoutInflater inflater = (LayoutInflater)TryExpandableListViewActivity.this
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.expandablelistview_parent, null);
			}
			TextView tv = (TextView)convertView.findViewById(R.id.type_text);
			tv.setText(TryExpandableListViewActivity.this.parent.get(groupPosition));
			ImageView icon = (ImageView)convertView.findViewById(R.id.icon);
			icon.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(mContext, TryExpandableListViewActivity.this.parent.get(groupPosition), Toast.LENGTH_LONG).show();
					
				}
				
			});
			return convertView;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			String key = TryExpandableListViewActivity.this.parent.get(groupPosition);
			final String info = map.get(key).get(childPosition);
			if(convertView == null){
				LayoutInflater inflater = (LayoutInflater)TryExpandableListViewActivity.this
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.expandablelistview_child, null);
			}
			TextView tv = (TextView)convertView.findViewById(R.id.content_text);
			tv.setText(info);
			ImageView icon = (ImageView)convertView.findViewById(R.id.icon);
			icon.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(mContext, info, Toast.LENGTH_LONG).show();
				}
				
			});
			return convertView;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return true;
		}
		
	}
}
