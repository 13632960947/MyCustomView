package com.example.administrator.mydemo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
RecyclerView mRecyclerView;
ViewGroup layoutContainer;
ViewGroup viewContainer;
//    layoutManager layoutManager;
    LinearLayoutManager layoutManager;
    MyAdapter myAdapter;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView(){
        mRecyclerView=findViewById(R.id.rcyView);
//         linearLayoutManager=new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        mRecyclerView.setLayoutManager(linearLayoutManager);
       layoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        myAdapter=new MyAdapter();
        myAdapter.setData(initData());
        View headView=LayoutInflater.from(this).inflate(R.layout.header,null);
        view= LayoutInflater.from(this).inflate(R.layout.footview,null);
        layoutContainer=view.findViewById(R.id.layout_container);
        viewContainer=view.findViewById(R.id.layout_view);
        view.setVisibility(View.GONE);
        view.setVisibility(View.GONE);
        viewContainer.setVisibility(View.GONE);
        layoutContainer.setVisibility(View.GONE);
        myAdapter.addFooterView(view);
        myAdapter.addHeaderView(headView);

        myAdapter.onAttachedToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(myAdapter);
        setLoadMore();
    }
    public List<String> initData(){
        List<String> list=new ArrayList<>();
        for(int i=0;i<39;i++){
            list.add(String.valueOf(i));
        }
        return list;
    }


    public void setLoadMore(){
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);

//                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
//                //当前屏幕所看到的子项个数
//                int visibleItemCount = layoutManager.getChildCount();
//                //当前RecyclerView的所有子项个数
//                int totalItemCount = layoutManager.getItemCount();
//                //RecyclerView的滑动状态
//                int state = recyclerView.getScrollState();
//                if(visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 2 ){
//                    view.setVisibility(View.VISIBLE);
//                    viewContainer.setVisibility(View.VISIBLE);
//                    layoutContainer.setVisibility(View.VISIBLE);
//
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            view.setVisibility(View.GONE);
//                            viewContainer.setVisibility(View.GONE);
//                            layoutContainer.setVisibility(View.GONE);
//                        }
//                    },3000);
//                }


                if(!canScrollVertically(1)){
                    view.setVisibility(View.VISIBLE);
                    viewContainer.setVisibility(View.VISIBLE);
                    layoutContainer.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            view.setVisibility(View.GONE);
                            viewContainer.setVisibility(View.GONE);
                            layoutContainer.setVisibility(View.GONE);
                        }
                    },3000);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });
    }
    public class MyAdapter extends BaseRecyclerViewAdapter<String>{

        @Override
        protected int getLayoutId(int viewType) {
            return R.layout.itemview_recyv;
        }

        @Override
        protected BaseHolder<String> createHolder(View view, int viewType) {
            return new MyHolder(view);
        }
    }

    public class MyHolder extends BaseRecyclerViewAdapter.BaseHolder<String>{

        public MyHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void findView() {

        }

        @Override
        public void bindHolder(String bean, int index) {

        }
    }

    /**
     * Check if this view can be scrolled vertically in a certain direction.
     *
     * @param direction Negative to check scrolling up, positive to check scrolling down.
     * @return true if this view can be scrolled in the specified direction, false otherwise.
     */
    public boolean canScrollVertically(int direction) {
        final int offset = mRecyclerView.computeVerticalScrollOffset();
        final int range = mRecyclerView.computeVerticalScrollRange() - mRecyclerView.computeVerticalScrollExtent()-DensityUtil.dip2px(this,60);
        if (range == 0) return false;
        if (direction < 0) {
            return offset > 0;
        } else {
            return offset < range - 1;
        }
    }
}
