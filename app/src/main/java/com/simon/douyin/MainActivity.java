package com.simon.douyin;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.simon.douyin.adapter.CommonEmptyAdapter;
import com.simon.douyin.adapter.CommonViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private CommonEmptyAdapter mCommonEmptyAdapter;
    private List<String> mList = new ArrayList<>();
    private PagerSnapHelper snapHelper;
    private LinearLayoutManager llm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerview);

        initRecyclerview();
        initData();
    }

    private void initRecyclerview() {
        snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);

        llm = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(llm);
        // 如果Item够简单，高度是确定的，打开FixSize将提高性能
        mRecyclerView.setHasFixedSize(true);
        // 设置Item默认动画，加也行，不加也行
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setNestedScrollingEnabled(false);

        mCommonEmptyAdapter = new CommonEmptyAdapter(MainActivity.this, R.layout.layout_douyin_item, mList);
        mCommonEmptyAdapter.setItemDataListener(new CommonEmptyAdapter.ItemDataListener<String>() {
            @Override
            public void setItemData(CommonViewHolder holder, String dataBean) {
                TextView tv_item_content = holder.getView(R.id.tv_item_content);
                tv_item_content.setText(dataBean);
            }
        });

        mCommonEmptyAdapter.setOnItemEmptyClickListener(new CommonEmptyAdapter.OnItemEmptyClickListener() {
            @Override
            public void setOnEmptyClickListener() {
                initData();
            }

            @Override
            public void setOnItemClickListener(View view, int position) {

            }

            @Override
            public void setOnItemLongClickListener(View view, int position) {
                //  Log.i("Simon", "setOnItemLongClickListener = " + position);
            }
        });

        mCommonEmptyAdapter.setHasStableIds(true);
        mRecyclerView.setAdapter(mCommonEmptyAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE://停止滚动
                        View view = snapHelper.findSnapView(llm);
                        //TODO 销毁所有视频
                        Log.i("Simon", "销毁所有视频");
                        RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
                        if (viewHolder != null && viewHolder instanceof CommonViewHolder) {
                            //TODO  启动想要播放的视频
                            Log.i("Simon", "启动想要播放的视频");
                        }
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING://拖动
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING://惯性滑动
                        break;
                }

            }
        });
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            mList.add("第" + i + "条数据");
        }

        mCommonEmptyAdapter.notifyDataSetChanged();
    }


}
