package com.list.move;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static android.view.View.VISIBLE;

public class ItemMoveAdapter extends RecyclerView.Adapter<ItemMoveAdapter.ViewHolderEx> {

    private String TAG = ItemMoveAdapter.class.getSimpleName();

    private Context mContext;
    private List<ItemBean> mItemBeans;
    private EventListener mEventListener;

    public interface EventListener {
        void onItemViewClicked(View v, boolean pinned);
    }

    public ItemMoveAdapter(Context context, List<ItemBean> mItemBeans) {
        this.mContext = context;
        this.mItemBeans = mItemBeans;
        setHasStableIds(true);
    }

    private void onItemClickMove(View v) {
        if (mEventListener != null) {
            mEventListener.onItemViewClicked(getParentViewHolderItemView(v), false);
        }
    }

    @Override
    public ViewHolderEx onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate((viewType == 0) ? R.layout.list_item_bar : R.layout.list_item_view, parent, false);
        return new ViewHolderEx(v, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolderEx holder, int position) {
        if (getItemViewType(position) != 0) {
            final ItemBean item = mItemBeans.get(position);
            holder.mNameTv.setText(item.getName());
            holder.mAddIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickMove(v);
                }
            });
            if (item.isAdd()) {
                holder.mLogoIv.setImageResource(item.getResId1());
                holder.mAddIv.setImageResource(R.drawable.ic_cd_y);
            } else {
                holder.mLogoIv.setImageResource(item.getResId2());
                holder.mAddIv.setImageResource(R.drawable.ic_cd_n);
            }
            holder.mContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.isAdd()) {
                        Log.i(TAG, "TAG:" + item.getTag());
                    } else {
                        onItemClickMove(v);
                    }
                }
            });
        } else {
            if (isBarVisible()) {
                holder.mBar.setVisibility(VISIBLE);
            } else {
                holder.mBar.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mItemBeans != null) return mItemBeans.size();
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (mItemBeans != null) return mItemBeans.get(position).getType();
        return 0;
    }

    //选择个数
    public int getAddCount() {
        int count = 0;
        for (ItemBean bean : mItemBeans) {
            if (bean.isAdd()) {
                count++;
            }
        }
        return count;
    }

    //Bar是否隐藏
    public boolean isBarVisible() {
        if (getAddCount() != 0 && (mItemBeans != null && getAddCount() != (mItemBeans.size() - 1))) { //全未选||全选
            return true;
        }
        return false;
    }

    public void onMoveItem(int fromPosition, int toPosition) {
        Log.i(TAG, "onMoveItem(fromPosition = " + fromPosition + ", toPosition = " + toPosition + ")");

        if (fromPosition == toPosition) {
            return;
        }

        ItemBean item = mItemBeans.remove(fromPosition);
        item.setAdd(!item.isAdd());
        mItemBeans.add(toPosition, item);

        notifyItemMoved(fromPosition, toPosition);

        //保存对象
        PrefUtils.saveItemBean(mContext, StringUtils.obj2String(mItemBeans));
    }

    public void setEventListener(EventListener eventListener) {
        mEventListener = eventListener;
    }

    /**
     * Gets directly child of RecyclerView (== {@link android.support.v7.widget.RecyclerView.ViewHolder#itemView}})
     *
     * @param view Child view of the RecyclerView's item
     * @return Item view
     */
    @Nullable
    public View getParentViewHolderItemView(@Nullable View view) {
        RecyclerView rv = getParentRecyclerView(view);
        if (rv == null) {
            return null;
        }
        return rv.findContainingItemView(view);
    }

    public RecyclerView getParentRecyclerView(@Nullable View view) {
        if (view == null) {
            return null;
        }
        ViewParent parent = view.getParent();
        if (parent instanceof RecyclerView) {
            return (RecyclerView) parent;
        } else if (parent instanceof View) {
            return getParentRecyclerView((View) parent);
        } else {
            return null;
        }
    }

    public static class ViewHolderEx extends RecyclerView.ViewHolder {
        public View mContainer;
        public ImageView mLogoIv;
        public TextView mNameTv;
        public TextView mDescTv;
        public ImageView mAddIv;

        public View mBar;

        public ViewHolderEx(View v, int viewType) {
            super(v);
            if (viewType != 0) {
                mContainer = v.findViewById(R.id.container);
                mLogoIv = (ImageView) v.findViewById(R.id.cd_item_logo);
                mNameTv = (TextView) v.findViewById(R.id.cd_item_name);
                mDescTv = (TextView) v.findViewById(R.id.cd_item_desc);
                mAddIv = (ImageView) v.findViewById(R.id.cd_item_add);
            } else {
                mBar = v.findViewById(R.id.cd_item_bar);
            }
        }
    }
}
