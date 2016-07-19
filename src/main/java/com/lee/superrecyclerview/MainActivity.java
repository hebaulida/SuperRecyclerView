package com.lee.superrecyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
	private RecyclerView mRecyclerView;
	private List<String> mDatas;
	private RecyclerViewAdapter mAdapter;
	private RefreshFootAdapter adapter;
	private LinearLayoutManager mLinearLayoutManager;
	private GridLayoutManager mGridLayoutManager;
	private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
	private Button add;
	private SwipeRefreshLayout mSwipeRefreshLayout;
	private int lastVisibleItem;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		add = (Button) findViewById(R.id.add);
		mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.my_swiperefresh_view);

		//下拉刷新数据
		mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.google_green);
		mSwipeRefreshLayout.setColorSchemeColors(R.color.google_blue,
				R.color.google_red,
				R.color.google_green,
				R.color.google_black);
		mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				fetchingNewData();
//				mSwipeRefreshLayout.setRefreshing(false);
				adapter.notifyDataSetChanged();
				Toast.makeText(getApplicationContext(), "shuaxin", Toast.LENGTH_SHORT).show();
			}
		});


		initData();

		//创建并设置Adapter
//		mAdapter = new RecyclerViewAdapter(mDatas);
		mRecyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
		//创建refreshfootadapteradapter
		adapter = new RefreshFootAdapter(mDatas);

		mLinearLayoutManager = new LinearLayoutManager(this);
		//垂直方向
		mLinearLayoutManager.setOrientation(OrientationHelper.VERTICAL);

//		mGridLayoutManager = new GridLayoutManager(this, 4);

//		mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL);
		mRecyclerView.setLayoutManager(mLinearLayoutManager);
//		mRecyclerView.setAdapter(mAdapter);
		mRecyclerView.setAdapter(adapter);
//		//给竖直显示的数据，画水平横线
//		mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
//				DividerItemDecoration.VERTICAL_LIST));
//		//给水平显示的数据画线，划垂直线
//		mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
//				DividerItemDecoration.HORIZONTAL_LIST));
//		mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
//		mAdapter.setOnItemClickLitener(new RecyclerViewAdapter.OnItemClickLitener() {
//			@Override
//			public void onItemClick(View view, int position) {
//				Toast.makeText(MainActivity.this, "点击事件" + position, Toast.LENGTH_SHORT).show();
//			}
//
//			@Override
//			public void onItemLongClick(View view, int position) {
//				Toast.makeText(MainActivity.this, "长点击" + position, Toast.LENGTH_SHORT).show();
//				mAdapter.removeItem(position);
//			}
//		});
//
//		//添加默认的动画效果
//		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//		add.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				mAdapter.addItem("add item", 1);
//			}
//		});
		//上拉刷新
		mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);
				if(newState == recyclerView.SCROLL_STATE_IDLE
						&& lastVisibleItem + 1 == adapter.getItemCount()){
					MoreData();
					adapter.notifyDataSetChanged();
					adapter.changeMoreStatus(RefreshFootAdapter.PULLUP_LOAD_MORE);

				}
			}
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				lastVisibleItem =mLinearLayoutManager.findLastVisibleItemPosition();
			}
		});
	}
	protected void initData() {
		mDatas = new ArrayList<String>();
		for (int i = 'A'; i <= 'Z'; i++) {
			mDatas.add("" + (char) i);
		}
	}
	private void fetchingNewData() {
		for(int i = 0;i<5;i++) {
			mDatas.add(i, "新数据" + i);
		}
		mSwipeRefreshLayout.setRefreshing(false);
	}
	private void MoreData() {
		SystemClock.sleep(1000);
		for(int i = 0;i<5;i++) {
			mDatas.add( "新数据----" + i);
		}
	}
}
