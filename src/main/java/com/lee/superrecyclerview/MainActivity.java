package com.lee.superrecyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
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
	private Button add;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		add = (Button) findViewById(R.id.add);
		initData();

		//创建并设置Adapter
		mAdapter = new RecyclerViewAdapter(mDatas);
		mRecyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);

		mLinearLayoutManager = new LinearLayoutManager(this);
		//垂直方向
		mLinearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);

//		mGridLayoutManager = new GridLayoutManager(this, 4);

//		mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL);
		mRecyclerView.setLayoutManager(mLinearLayoutManager);
		mRecyclerView.setAdapter(mAdapter);
//		//给竖直显示的数据，画水平横线
//		mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
//				DividerItemDecoration.VERTICAL_LIST));
//		//给水平显示的数据画线，划垂直线
//		mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
//				DividerItemDecoration.HORIZONTAL_LIST));
//		mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
		mAdapter.setOnItemClickLitener(new RecyclerViewAdapter.OnItemClickLitener() {
			@Override
			public void onItemClick(View view, int position) {
				Toast.makeText(MainActivity.this, "点击事件" + position, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onItemLongClick(View view, int position) {
				Toast.makeText(MainActivity.this, "长点击" + position, Toast.LENGTH_SHORT).show();
				mAdapter.removeItem(position);
			}
		});

		//添加默认的动画效果
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mAdapter.addItem("add item",1);
			}
		});

	}
	protected void initData() {
		mDatas = new ArrayList<String>();
		for (int i = 'A'; i <= 'G'; i++)
		{
			mDatas.add("" + (char) i);
		}
	}
}
