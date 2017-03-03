package com.list.move;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;


import com.h6ah4i.android.widget.advrecyclerview.animator.DraggableItemAnimator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ItemMoveAdapter mItemMoveAdapter;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this);

        //adapter
        mItemMoveAdapter = new ItemMoveAdapter(this, initData());
        mItemMoveAdapter.setEventListener(new ItemMoveAdapter.EventListener() {

            @Override
            public void onItemViewClicked(View v, boolean pinned) {
                int position = mRecyclerView.getChildAdapterPosition(v);
                Log.i(TAG, "onItemViewClick position:" + position);
                if (position != RecyclerView.NO_POSITION) {
                    mItemMoveAdapter.onMoveItem(position, mItemMoveAdapter.getAddCount());
                }
            }
        });

        DraggableItemAnimator animator = new DraggableItemAnimator();
        animator.setSupportsChangeAnimations(false);
        animator.setMoveDuration(600);
        animator.setListener(new DraggableItemAnimator.ItemAnimatorListener() {
            @Override
            public void onRemoveFinished(RecyclerView.ViewHolder item) {
            }

            @Override
            public void onAddFinished(RecyclerView.ViewHolder item) {
            }

            @Override
            public void onMoveFinished(RecyclerView.ViewHolder item) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mItemMoveAdapter.notifyDataSetChanged();
                    }
                }, 600);
            }

            @Override
            public void onChangeFinished(RecyclerView.ViewHolder item) {
            }
        });
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mItemMoveAdapter);
        mRecyclerView.setItemAnimator(animator);
    }

    @Override
    protected void onDestroy() {
        if (mRecyclerView != null) {
            mRecyclerView.setItemAnimator(null);
            mRecyclerView.setAdapter(null);
            mRecyclerView = null;
        }

        mLayoutManager = null;
        super.onDestroy();
    }

    private List<ItemBean> initData() {
        List<ItemBean> mItemBeans = (List<ItemBean>) StringUtils.str2Object(PrefUtils.getItemBean(this));
        if (mItemBeans == null || (mItemBeans != null && mItemBeans.size() == 0)) {
            mItemBeans = new ArrayList<>();
            mItemBeans.add(new ItemBean(0, "none", 0, 0, null, null, false));
            mItemBeans.add(new ItemBean(1, "browser", R.drawable.ic_cd_browser_y, R.drawable.ic_cd_browser_n, "AAA", "111", false));
            mItemBeans.add(new ItemBean(1, "clock", R.drawable.ic_cd_clock_y, R.drawable.ic_cd_clock_n, "BBB", "222", false));
            mItemBeans.add(new ItemBean(1, "play", R.drawable.ic_cd_play_y, R.drawable.ic_cd_play_n, "CCC", "333", false));
            mItemBeans.add(new ItemBean(1, "shake", R.drawable.ic_cd_shak_y, R.drawable.ic_cd_shake_n, "DDD", "444", false));
            mItemBeans.add(new ItemBean(1, "tel", R.drawable.ic_cd_tel_y, R.drawable.ic_cd_tel_n, "EEE", "555", false));
            mItemBeans.add(new ItemBean(1, "browser1", R.drawable.ic_cd_browser_y, R.drawable.ic_cd_browser_n, "FFF", "555", false));
            mItemBeans.add(new ItemBean(1, "clock1", R.drawable.ic_cd_clock_y, R.drawable.ic_cd_clock_n, "GGG", "666", false));
            mItemBeans.add(new ItemBean(1, "play1", R.drawable.ic_cd_play_y, R.drawable.ic_cd_play_n, "HHH", "777", false));
            mItemBeans.add(new ItemBean(1, "shake1", R.drawable.ic_cd_shak_y, R.drawable.ic_cd_shake_n, "III", "888", false));
        }
        return mItemBeans;
    }

}
