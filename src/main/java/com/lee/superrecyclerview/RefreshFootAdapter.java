package com.lee.superrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by li on 2016/7/19.
 */
public class RefreshFootAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
	//上拉加载更多
	public static final int  PULLUP_LOAD_MORE=0;
	//正在加载中
	public static final int  LOADING_MORE=1;
	//上拉加载更多状态-默认为0
	private int load_more_status=0;
	private static final int TYPE_ITEM = 0;  //普通Item View
	private static final int TYPE_FOOTER = 1;  //顶部FootView
	private List<String> mDatas;
	public RefreshFootAdapter(List<String> mData) {
		super();
		this.mDatas = mData;
	}
	/**
	 * item显示类型
	 * @param parent
	 * @param viewType
	 * @return
	 */
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		//进行判断显示类型，来创建返回不同的View
		if(viewType==TYPE_ITEM){
			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
			RefreshFootAdapter.ItemViewHolder itemViewHolder = new RefreshFootAdapter.ItemViewHolder(view);
			return itemViewHolder;
		}else if(viewType==TYPE_FOOTER){
			View foot_view=LayoutInflater.from(parent.getContext()).inflate(R.layout.foot_item, parent, false);
			//这边可以做一些属性设置，甚至事件监听绑定
			//view.setBackgroundColor(Color.RED);
			FootViewHolder footViewHolder=new FootViewHolder(foot_view);
			return footViewHolder;
		}
		return null;
	}

	/**
	 * 数据的绑定显示
	 * @param holder
	 * @param position
	 */
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		if(holder instanceof ItemViewHolder) {
			((ItemViewHolder)holder).item_tv.setText(mDatas.get(position));
			holder.itemView.setTag(position);
		}else if(holder instanceof FootViewHolder){
			FootViewHolder footViewHolder=(FootViewHolder)holder;
			switch (load_more_status){
				case PULLUP_LOAD_MORE:
					footViewHolder.foot_view_item_tv.setText("上拉加载更多...");
					break;
				case LOADING_MORE:
					footViewHolder.foot_view_item_tv.setText("正在加载更多数据...");
					break;
			}
		}
	}

	/**
	 * 进行判断是普通Item视图还是FootView视图
	 * @param position
	 * @return
	 */
	@Override
	public int getItemViewType(int position) {
		// 最后一个item设置为footerView
		if (position + 1 == getItemCount()) {
			return TYPE_FOOTER;
		} else {
			return TYPE_ITEM;
		}
	}
	@Override
	public int getItemCount() {
		return mDatas.size()+1;
	}
	//自定义的ViewHolder，持有每个Item的的所有界面元素
	public static class ItemViewHolder extends RecyclerView.ViewHolder {
		public TextView item_tv;
		public ItemViewHolder(View view){
			super(view);
			item_tv = (TextView)view.findViewById(R.id.id_num);
		}
	}
	/**
	 * 底部FootView布局
	 */
	public static class FootViewHolder extends  RecyclerView.ViewHolder{
		private TextView foot_view_item_tv;
		public FootViewHolder(View view) {
			super(view);
			foot_view_item_tv=(TextView)view.findViewById(R.id.foot_view);
		}
	}

	//添加数据
	public void addItem(String data, int position) {
		mDatas.add(position, data);
		notifyItemInserted(position);
	}
	//删除数据
	public void removeItem(int position) {
		mDatas.remove(position);
		notifyItemRemoved(position);
	}

	/**
	 * //上拉加载更多
	 * PULLUP_LOAD_MORE=0;
	 * //正在加载中
	 * LOADING_MORE=1;
	 * //加载完成已经没有更多数据了
	 * NO_MORE_DATA=2;
	 * @param status
	 */
	public void changeMoreStatus(int status){
		load_more_status=status;
		notifyDataSetChanged();
	}
}