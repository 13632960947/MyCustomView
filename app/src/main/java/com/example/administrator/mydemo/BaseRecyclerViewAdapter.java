package com.example.administrator.mydemo;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

/**
 * Author:          Kevin <BR/>
 * CreatedTime:     2018/4/25 <BR/>
 * Desc:            RecyclerView的Adapter的基类，主要有4个功能。<BR/>
 * 1. Item的点击和长按事件。<BR/>
 * 2. 添加HeaderView和FooterView。并支持表格布局和瀑布流布局。<BR/>
 * 3. 支持多种类型Item。注意数据类型要一样。这里没有处理不同数据类型的情况。
 * 设置不同类型的item时，需要复写{@link #getItemType(int)} 和 {@link #getLayoutId(int)}方法<BR/>
 * 4. 添加上拉加载更多和没有更多数据的处理。本功能需要结合{@link com.yishuo.customview.live.refreshrecyclerview.PullLoadMoreRecyclerView}
 * 和{@link com.yishuo.customview.live.refreshrecyclerview.RefreshRecyclerView}使用
 * <p>
 * 泛型T： 数据的类型 <BR/>
 * <p/>
 * ModifyTime:      <BR/>
 * ModifyItems:     <BR/>
 *
 * @author Kiven
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseHolder> {


    public static final int TYPE_NORMAL = 0;
    /**
     * RecyclerView中的数据
     */
    public List<T> mDatas;


    /**
     * 设置数据
     *
     * @param datas 展示在列表中的数据
     */
    public void setData(List<T> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }


    public void addData(T bean) {
        mDatas.add(bean);
        notifyItemInserted(mHeaderViews.size() + mDatas.size() - 1);
    }

    public void addData(int index, T bean) {
        mDatas.add(index, bean);
        notifyItemInserted(mHeaderViews.size() + index);
    }

    /**
     * 添加数据
     *
     * @param datas 展示在列表中的数据
     */
    public void addDatas(List<T> datas) {
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
        mDatas.addAll(datas);
        notifyDataSetChanged();

    }

    /**
     * 根据数据的索引，返回对应的数据，当没有数据集合的时候，返回值为空
     */
    @Nullable
    public T getData(int index) {
        return mDatas == null ? null : mDatas.get(index);
    }

    /**
     * 获得所有的item的数量
     */
    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size() + mHeaderViews.size() + mFooterViews.size();
    }

    /**
     * 获得列表中除去Header和Footer的数据的数量
     */
    public int getDataCount() {
        return mDatas == null ? 0 : mDatas.size();
    }


    @Override
    public int getItemViewType(int position) {

        // 如果是Header
        if (isHeaderViewPos(position)) {
            return mHeaderViews.keyAt(position);
        }
        // 如果是Footer
        if (isFooterViewPos(position)) {

            return mFooterViews.keyAt(position - getHeadersCount() - mDatas.size());
        }
        // 普通的Item
        int index = position - getHeadersCount();
        return getItemType(index);
    }


    /**
     * 获得普通的itemType，如果子类中有不同的item要复写这个方法
     *
     * @param index 在mData中的位置，不能返回0。
     */
    public int getItemType(int index) {
        return TYPE_NORMAL;
    }

    public int getRealIndex(int index) {
        return index - getHeadersCount();
    }

    /**
     * 删除某个item，主要是对数据的处理
     *
     * @param index 在视图中的index
     */
    public void removeItem(int index) {
        mDatas.remove(getRealIndex(index));
        notifyItemRemoved(getRealIndex(index));
        notifyItemRangeRemoved(getRealIndex(index), getItemCount());
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderViews.get(viewType) != null) {
            return new EmptyHolder(mHeaderViews.get(viewType));
        }
        if (mFooterViews.get(viewType) != null) {
            return new EmptyHolder(mFooterViews.get(viewType));
        }

        // 创建ViewHolder
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(viewType), parent, false);
        return createHolder(view, viewType);
    }


    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {

        // 是Header或者Footer，不做处理
        if (isHeaderViewPos(position) || isFooterViewPos(position)) {
            return;
        }
        // 普通Item，获得数据对应的position，和数据，并处理
        final int index = position - getHeadersCount();
        final T bean = mDatas.get(index);
        final View itemView = holder.itemView;
        holder.bindHolder(bean, index);

        // 点击事件，这里没有为头和尾设置点击事件
        if (mListener != null && itemView != null) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(itemView, index);
                }
            });
        }
        // 长按事件
        if (mLongListener != null && itemView != null) {
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mLongListener.onItemLongClick(itemView, index);
                    return false;
                }
            });
        }
    }

    /**
     * 子类必须实现的方法，返回item布局对应的id
     *
     * @param viewType 展示的item的种类
     * @return 对应的资源文件ID
     */
    protected abstract int getLayoutId(int viewType);

    /**
     * 子类必须实现的方法，根据不同的itemType返回对应的Holder
     *
     * @param view     资源ID解析成的View
     * @param viewType 展示的item的种类
     * @return 展示的item对应的Holder，应为BaseHolder的子类
     */
    protected abstract BaseHolder<T> createHolder(View view, int viewType);

    /**
     * Holder的基类
     * <p>
     * 泛型K： 数据的类型
     */
    public static abstract class BaseHolder<K> extends RecyclerView.ViewHolder {

        public BaseHolder(View itemView) {
            super(itemView);
            findView();
        }

        /**
         * 所有的FindViewById的方法
         */
        protected abstract void findView();

        protected View findViewById(int id) {
            return itemView.findViewById(id);
        }

        /**
         * 设置数据的方法
         *
         * @param bean  item展示的数据的集合，封装成的对象
         * @param index item在data，数据中的真实位置
         */
        public abstract void bindHolder(K bean, int index);

    }

    /**
     * Header和Footer对应的ViewHolder的空实现
     */
    static class EmptyHolder<K> extends BaseHolder<K> {
        public EmptyHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void findView() {
        }

        @Override
        public void bindHolder(K bean, int index) {
        }

    }


    /*************************************** Listener *************************************/
    /**
     * item点击事件的监听者
     */
    protected BaseRecyclerViewAdapter.OnItemClickListener mListener;


    public interface OnItemClickListener {
        /**
         * 此处的index是item在Data中的index
         *
         * @param itemView
         * @param index    item在Data中的index
         */
        void onItemClick(View itemView, int index);
    }

    /**
     * 给RecyclerView的item设置点击事件的监听者
     *
     * @param li 具体的处理监听事件的操作者
     */
    public void setOnItemClickListener(OnItemClickListener li) {
        mListener = li;
    }

    /**
     * item长按事件的监听者
     */
    protected BaseRecyclerViewAdapter.OnItemLongClickListener mLongListener;

    public interface OnItemLongClickListener {
        /**
         * 此处的index是item在Data中的index
         *
         * @param itemView
         * @param index
         */
        void onItemLongClick(View itemView, int index);
    }

    /**
     * 给RecyclerView的item设置长按事件的监听者
     *
     * @param li 具体的处理监听事件的操作者
     */
    public void setOnItemLongClickListener(OnItemLongClickListener li) {
        mLongListener = li;
    }
    /*************************************** Listener ******************************************/


    /************************** 多个Header和Footer的实现 ******************************/
    // 实现的原理，是将每个Header和Footer作为一种itemType，绑定ItemViewType的值，并放在MAP中
    // 这里使用SparseArray可以存放int-Object类型的键值对

    protected static final int BASE_ITEM_TYPE_HEADER = 10000;
    protected static final int BASE_ITEM_TYPE_FOOTER = 20000;
    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFooterViews = new SparseArrayCompat<>();

    public int getHeadersCount() {
        return mHeaderViews.size();
    }

    public int getFootersCount() {
        return mFooterViews.size();
    }

    public void addHeaderView(View view) {
        mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, view);
        notifyItemInserted(getHeadersCount());
    }

    public void addFooterView(View view) {
        mFooterViews.put(mFooterViews.size() + BASE_ITEM_TYPE_FOOTER, view);
        notifyItemInserted(getItemCount());
    }

    public void removeFooterView() {
        mFooterViews.removeAt(0);
        notifyItemRemoved(getItemCount());
    }

    public void removeFooterView(View view) {
        if (view == null) {
            return;
        }
        int index = mHeaderViews.indexOfValue(view);
        if (index == -1) {
            return;
        }
        mHeaderViews.removeAt(index);
        notifyItemRemoved(getItemCount());
    }

    public View getHeaderView() {
        return getHeaderView(0);
    }

    public View getFooterView() {
        return getFooterView(0);
    }

    public View getHeaderView(int index) {
        return mHeaderViews.get(mHeaderViews.keyAt(index));
    }

    public View getFooterView(int index) {
        return mFooterViews.get(mFooterViews.keyAt(index));
    }

    /**
     * 根据在List中的位置判断是否是头或者尾
     *
     * @param position item在ListView中的位置
     */
    protected boolean isHeaderViewPos(int position) {
        return position < getHeadersCount();
    }

    protected boolean isFooterViewPos(int position) {
        return position >= getHeadersCount() + getDataCount();
    }

    /**
     * 如果是GridLayout时，Header和Footer应该占据一整行
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    // 如果是头或尾，直接占一行的数量
                    if (isHeaderViewPos(position) || isFooterViewPos(position)) {
                        return gridManager.getSpanCount();
                    } else {
                        return 1;
                    }
                }
            });
        }
    }

    /************************** 多个Header和Footer的实现 ******************************/


    /************************  深度订制，针对App添加两个FooterView  *********************/
    private View mLoadMoreView;
    private View mNoMoreDataView;
    private boolean mLoadMoreVisibility = false;
    private boolean mNoMoreDataVisibility = true;

//    /**
//     * 添加加载更多的View
//     */
//    public void addLoadMoreView(Context context) {
//        addLoadMoreView(context, R.layout.live_itemview_footer_load_more);
//    }


    /**
     * 添加加载更多的View
     */
    public void addLoadMoreView(Context context, @LayoutRes int layoutRes) {
        mLoadMoreView = LayoutInflater.from(context).inflate(layoutRes, null);
        mFooterViews.put(mFooterViews.size() + BASE_ITEM_TYPE_FOOTER, mLoadMoreView);
        notifyItemInserted(getItemCount());
    }

//    /**
//     * 添加加载更多的View
//     */
//    public void addLoadMoreView(View loadMoreView) {
//        if (loadMoreView == null) {
//            throw new IllegalArgumentException("LoadMoreView 不能为空");
//        }
//
//        mLoadMoreView = loadMoreView;
//        mFooterViews.put(mFooterViews.size() + BASE_ITEM_TYPE_FOOTER, mLoadMoreView);
//        notifyItemInserted(getItemCount());
//    }
//
//    /**
//     * 显示加载更多View
//     */
//    public void showLoadMoreView() {
//        if (mLoadMoreView != null) {
//            //mLoadMoreView.setVisibility(View.VISIBLE);
//            mLoadMoreVisibility = true;
//            ViewGroup container = mLoadMoreView.findViewById(R.id.ll_container);
//            container.setVisibility(View.VISIBLE);
//        }
//    }
//
//
//    /**
//     * 隐藏加载更多View
//     */
//    public void hideLoadMoreView() {
//        if (mLoadMoreView != null) {
//            //mLoadMoreView.setVisibility(View.GONE);
//            mLoadMoreVisibility = false;
//            ViewGroup container = mLoadMoreView.findViewById(R.id.ll_container);
//            container.setVisibility(View.GONE);
//        }
//    }

    /**
     * 获取LoadMoreView，可能为null
     *
     * @return
     */
    public View getLoadMoreView() {
        return mLoadMoreView;
    }


//    public void addNoMoreDataView(Context context) {
//        addNoMoreDataView(context, R.layout.live_itemview_footer_no_more_data);
//    }

    public void addNoMoreDataView(Context context, @LayoutRes int layoutRes) {
        mNoMoreDataView = LayoutInflater.from(context).inflate(layoutRes, null);
        mFooterViews.put(mFooterViews.size() + BASE_ITEM_TYPE_FOOTER, mNoMoreDataView);
        notifyItemInserted(getItemCount());
    }

    public void addNoMoreDataView(View view) {
        mNoMoreDataView = view;
        mFooterViews.put(mFooterViews.size() + BASE_ITEM_TYPE_FOOTER, mNoMoreDataView);
        notifyItemInserted(getItemCount());
    }

//    public void showNoMoreDataView() {
//        if (mNoMoreDataView != null) {
//            //mNoMoreDataView.setVisibility(View.VISIBLE);
//            mNoMoreDataVisibility = true;
//            ViewGroup container = mNoMoreDataView.findViewById(R.id.constraint_layout_container);
//            container.setVisibility(View.VISIBLE);
//        }
//    }
//
//    public void hideNoMoreDataView() {
//        if (mNoMoreDataView != null) {
//            //mNoMoreDataView.setVisibility(View.GONE);
//            mNoMoreDataVisibility = false;
//            ViewGroup container = mNoMoreDataView.findViewById(R.id.constraint_layout_container);
//            container.setVisibility(View.GONE);
//        }
//    }

    public boolean isLoadMoreVisibility() {
        return mLoadMoreVisibility;
    }

    public boolean isNoMoreDataVisibility() {
        return mNoMoreDataVisibility;
    }
    /************************  深度订制，针对App添加两个FooterView  *********************/
}
