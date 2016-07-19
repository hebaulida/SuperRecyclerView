package com.lee.superrecyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
	private RecyclerView mRecyclerView;
	private List<String> mDatas;
	private RecyclerViewAdapter mAdapter;
	private LinearLayoutManager mLinearLayoutManager;
	private GridLayoutManager mGridLayoutManager;
	private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initData();

		//创建并设置Adapter
		mAdapter = new RecyclerViewAdapter(mDatas);
		mRecyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);

		mLinearLayoutManager = new LinearLayoutManager(this);
		//垂直方向
		mLinearLayoutManager.setOrientation(OrientationHelper.VERTICAL);

//		mGridLayoutManager = new GridLayoutManager(this, 4);

//		mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL);
		mRecyclerView.setLayoutManager(mLinearLayoutManager);
		mRecyclerView.setAdapter(mAdapter);
		mAdapter.setOnItemClickLitener(new RecyclerViewAdapter.OnItemClickLitener() {
			@Override
			public void onItemClick(View view, int position) {
				Toast.makeText(MainActivity.this, "点击事件" + position, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onItemLongClick(View view, int position) {
				Toast.makeText(MainActivity.this, "长点击" + position, Toast.LENGTH_SHORT).show();
			}
		});




	}
	protected void initData() {
		mDatas = new ArrayList<String>();
		for (int i = 'A'; i < 'z'; i++)
		{
			mDatas.add("" + (char) i);
		}
	}
}
