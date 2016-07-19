package com.lee.superrecyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
	private RecyclerView mRecyclerView;
	private List<String> mDatas;
	private RecyclerViewAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initData();

		//创建并设置Adapter
		mAdapter = new RecyclerViewAdapter(mDatas);
		mRecyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		mRecyclerView.setAdapter(mAdapter);

	}
	protected void initData() {
		mDatas = new ArrayList<String>();
		for (int i = 'A'; i < 'z'; i++)
		{
			mDatas.add("" + (char) i);
		}
	}
}
