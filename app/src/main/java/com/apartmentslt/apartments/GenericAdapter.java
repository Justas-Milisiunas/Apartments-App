package com.apartmentslt.apartments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public abstract class GenericAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LinkedList<T> dataList;
    private LayoutInflater mInflater;
    protected Context mContext;

    protected GenericAdapter(Context context) {
        this.dataList = new LinkedList<>();
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    /**
     * Inflates item layout before adding to recycler view
     *
     * @param parent   Parent view group
     * @param viewType ViewType
     * @return Item view holder object
     */
    @NonNull
    @Override
    public GenericAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemVIew = mInflater.inflate(getLayoutId(), parent, false);
        return new ItemViewHolder(mItemVIew);
    }

    /**
     * Binds item's data to inflated layout components and creates click listener
     *
     * @param holder   Inflated recycler view item layout
     * @param position Itm position
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        onBindData(dataList.get(position), position, ((ItemViewHolder) holder));

        ((ItemViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenericAdapter.this.onClick(dataList.get(position), position);
            }
        });
    }

    /**
     * Gets items list size
     *
     * @return Items list size
     */
    @Override
    public int getItemCount() {
        return this.dataList.size();
    }

    /**
     * Adds item at the back and notifies adapter to refresh recycler view list
     *
     * @param item New item
     */
    public void addItem(T item) {
        this.dataList.addLast(item);
        this.notifyItemInserted(this.dataList.size());
    }

    /**
     * Adds items array at the back
     *
     * @param items Items array
     */
    public void addAll(T[] items) {
        for (T item : items) {
            this.dataList.addLast(item);
        }

        this.notifyDataSetChanged();
    }

    /**
     * Gets recycler view item layout id
     *
     * @return Item layout id
     */
    public abstract int getLayoutId();

    /**
     * Expects data binding implementation
     *
     * @param model      Current item
     * @param position   Current item's position
     * @param viewHolder Inflated layout view
     */
    public abstract void onBindData(T model, int position, ItemViewHolder viewHolder);

    /**
     * Expects item on click behaviour implementation
     *
     * @param item     Clicked item
     * @param position Clicked item position in list
     */
    public abstract void onClick(T item, int position);

    /**
     * Clears all items from data array
     */
    public void clear() {
        dataList.clear();
        this.notifyDataSetChanged();
    }

    /**
     * Class for holding inflated layout
     */
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private View itemView;

        ItemViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        /**
         * Returns inflated layout components (TextView, EditText, etc.)
         *
         * @param id Component id
         * @return Found component
         */
        public View getComponent(int id) {
            return this.itemView.findViewById(id);
        }
    }
}
