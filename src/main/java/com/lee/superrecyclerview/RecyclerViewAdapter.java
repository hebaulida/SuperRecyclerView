package com.lee.superrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by li on 2016/7/18.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
	private List<String> mDatas;

	public RecyclerViewAdapter(List<String> mData) {
		super();
		this.mDatas = mData;
	}
	public interface OnItemClickLitener{
		void onItemClick(View view, int position);
		void onItemLongClick(View view , int position);
	}
	private OnItemClickLitener mOnItemClickLitener = null;
	public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener){
		this.mOnItemClickLitener = mOnItemClickLitener;
	}
	//创建新View，被LayoutManager所调用
	@Override
	public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
		RecyclerViewAdapter.MyViewHolder holder = new RecyclerViewAdapter.MyViewHolder(view);
		return holder;
	}
	//将数据与界面进行绑定的操作
	@Override
	public void onBindViewHolder(final RecyclerViewAdapter.MyViewHolder holder, final int position) {
		holder.mTextView.setText(mDatas.get(position));
		if (mOnItemClickLitener != null){
			holder.itemView.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v){
					int pos = holder.getLayoutPosition();
					mOnItemClickLitener.onItemClick(holder.itemView,pos);
				}
			});
			holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
				@Override
				public boolean onLongClick(View v){
					int pos = holder.getLayoutPosition();
					mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
					return true;
				}
			});
		}
	}
	//获取数据的数量
	@Override
	public int getItemCount() {
		return mDatas.size();
	}
	//自定义的ViewHolder，持有每个Item的的所有界面元素
	public static class MyViewHolder extends RecyclerView.ViewHolder {
		public TextView mTextView;
		public MyViewHolder(View view){
			super(view);
			mTextView = (TextView) view.findViewById(R.id.id_num);
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
}
