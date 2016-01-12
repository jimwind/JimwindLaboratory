package me.jimwind.laboratory.android.activity.trywidget;

import me.jimwind.laboratory.R;
import me.jimwind.laboratory.android.activity.ui.BaseActivity;
import me.jimwind.laboratory.android.adapter.ListAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class TryListViewActivity extends BaseActivity implements
		OnItemClickListener {
	private ListView mListView;
	private ListAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.try_list);

		mListView = (ListView) findViewById(R.id.list);
		mAdapter = new ListAdapter(this);
		mAdapter.setListView(mListView);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		mAdapter.changeImageVisable(view, position);
	}

}
