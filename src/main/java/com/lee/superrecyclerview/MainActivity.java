package com.lee.superrecyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
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
	private RefreshFootAdapter adapter;
	private LinearLayoutManager mLinearLayoutManager;
	private GridLayoutManager mGridLayoutManager;
	private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
	private Button add;
	private SwipeRefreshLayout mSwipeRefreshLayout;
	private int lastVisibleItem;
	public static MainActivity mMainActivity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mMainActivity = this;
		add = (Button) findViewById(R.id.add);
		mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.my_swiperefresh_view);
		mRecyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);

		initData();

		//创建refreshfootadapteradapter
		adapter = new RefreshFootAdapter(mDatas);

		mLinearLayoutManager = new LinearLayoutManager(this);
		//设置为垂直方向
		mLinearLayoutManager.setOrientation(OrientationHelper.VERTICAL);

//		mGridLayoutManager = new GridLayoutManager(this, 4);
//		mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL);
		mRecyclerView.setLayoutManager(mLinearLayoutManager);
		mRecyclerView.setAdapter(adapter);
		//给竖直显示的数据，画水平横线
		mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
				DividerItemDecoration.VERTICAL_LIST));
		//给水平显示的数据画线，划垂直线
		mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
				DividerItemDecoration.HORIZONTAL_LIST));
//		mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
		adapter.setOnItemClickLitener(new RefreshFootAdapter.OnItemClickLitener() {
			@Override
			public void onItemClick(View view, int position) {
				Toast.makeText(MainActivity.this, "点击事件" + position, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onItemLongClick(View view, int position) {
				Toast.makeText(MainActivity.this, "长点击" + position, Toast.LENGTH_SHORT).show();
				adapter.removeItem(position);
			}
		});
		add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				adapter.addItem("add item", 1);
			}
		});

		//添加默认的动画效果
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
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
//				停止刷新动画
//				mSwipeRefreshLayout.setRefreshing(false);
				adapter.notifyDataSetChanged();
				Toast.makeText(getApplicationContext(), "下拉刷新", Toast.LENGTH_SHORT).show();
			}
		});
		//上拉刷新
		mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);
				if(newState == recyclerView.SCROLL_STATE_IDLE
						&& lastVisibleItem + 1 == adapter.getItemCount()){

					MoreData();
					adapter.notifyDataSetChanged();
					//设置更改footview的状态
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
	//初始化数据
	protected void initData() {
		mDatas = new ArrayList<String>();
		for (int i = 'A'; i <= 'J'; i++) {
			mDatas.add("" + (char) i);
		}
	}
	//下拉刷新进行的操作
	private void fetchingNewData() {
		for(int i = 0;i<5;i++) {
			mDatas.add(i, "新数据" + i);
		}
		mSwipeRefreshLayout.setRefreshing(false);
	}
	//上拉刷新进行的操作
	private void MoreData() {
		adapter.changeMoreStatus(RefreshFootAdapter.LOADING_MORE);
		SystemClock.sleep(1000);
		for(int i = 0;i<5;i++) {
			mDatas.add( "新数据----" + i);
		}
	}
}
