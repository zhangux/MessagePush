package com.qdzl.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.qdzl.R;
import com.qdzl.WineActivity;
import com.qdzl.utils.ToastUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by QDZL on 2018/3/26.
 */

public class WineAdapter2 extends RecyclerView.Adapter<WineAdapter2.WineViewHolder> {
    private List<String> pdStrLis;
    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;

    private Map<Integer, Integer> mapState;

    public RelativeLayout.LayoutParams lp;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public WineAdapter2(List<String> pdStrLis, Context context) {
        this.pdStrLis = pdStrLis;
        this.context = context;
        inflater = LayoutInflater.from(context);
        String json = WineActivity.sp.getString("mapState", null);
//        Log.e("aaaa","----"+json);
        if (json == null) {
            mapState = new HashMap<>();
        } else {
            mapState = JSON.parseObject(json, Map.class);
        }
    }

    public void clear() {
        mapState = new HashMap<>();
        notifyDataSetChanged();
    }

    @Override
    public WineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.wine_item, parent, false);
        lp = new RelativeLayout.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(lp);
        WineViewHolder wineViewHolder = new WineViewHolder(v);
        return wineViewHolder;
    }

    @Override
    public void onBindViewHolder(WineViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        holder.txtPdIndex.setText(pdStrLis.get(position));
        holder.bt1.setTag(position);
        holder.bt2.setTag(position);
        holder.bt3.setTag(position);
        if(position==0){
            holder.txtTitle.setText("一层");
            holder.txtTitle.setVisibility(View.VISIBLE);
        }else if(position==10){
            holder.txtTitle.setText("二层 一排");
            holder.txtTitle.setVisibility(View.VISIBLE);
        }else if(position==20){
            holder.txtTitle.setText("二层 二排");
            holder.txtTitle.setVisibility(View.VISIBLE);
        }
        if (mapState.get(position) != null) {
            if (mapState.get(position) == 1) {
                holder.txtPdState.setText("拿起");
                holder.txtPdState.setTextColor(context.getResources().getColor(R.color.colorAccent));
            } else if (mapState.get(position) == 0) {
                holder.txtPdState.setText("放下");
//                holder.txtPdState.setText("在售");
                holder.txtPdState.setTextColor(context.getResources().getColor(R.color.qhs));
            } else if (mapState.get(position) == 2) {
                holder.txtPdState.setText("已售");
                holder.txtPdState.setTextColor(context.getResources().getColor(R.color.colorRed));
            }
        }
//        Log.e("aaaa", "-------------------" + position);
    }


    @Override
    public int getItemCount() {
        return pdStrLis != null ? pdStrLis.size() : 0;
    }

    class WineViewHolder extends RecyclerView.ViewHolder {
        private TextView txtPdIndex;
        private TextView txtPdState;
        private Button bt1;
        private Button bt2;
        private Button bt3;
        private TextView txtTitle;
//        private CheckBox cbPdCheck;

        public WineViewHolder(final View view) {
            super(view);
            this.txtPdIndex = view.findViewById(R.id.txt_pd_index);
            this.txtPdState = view.findViewById(R.id.txt_pd_state);
            this.bt1 = view.findViewById(R.id.bt_1);
            this.bt2 = view.findViewById(R.id.bt_2);
            this.bt3 = view.findViewById(R.id.bt_3);
            this.txtTitle = view.findViewById(R.id.txt_title);
//            this.cbPdCheck = view.findViewById(R.id.cb_pd_check);
            bt1.setOnClickListener(onClickListener);
            bt2.setOnClickListener(onClickListener);
            bt3.setOnClickListener(onClickListener);
        }

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer position = (Integer) view.getTag();
                Integer isH = 0;
//                if (ClientManager.isCon) {
                switch (view.getId()) {
                    case R.id.bt_1:
                        if (mapState.get(position) != null && mapState.get(position) == 2) {
                            ToastUtil.show("当前商品已售出！");
                        } else {
                            isH = 1;
                            txtPdState.setText("拿起");
                            txtPdState.setTextColor(context.getResources().getColor(R.color.colorAccent));
                            sendClick(view, position, isH);
                        }
                        break;
                    case R.id.bt_2:
                        if (mapState.get(position) != null && mapState.get(position) == 2) {
                            ToastUtil.show("当前商品已售出！");
                        } else {
                            isH = 0;
                            txtPdState.setText("放下");
                            txtPdState.setTextColor(context.getResources().getColor(R.color.qhs));
                            sendClick(view, position, isH);
                        }
                        break;
                    case R.id.bt_3:
                        if (mapState.get(position) != null && mapState.get(position) == 2) {
                            ToastUtil.show("当前商品已售出！");
                        } else {
                            isH = 2;
                            txtPdState.setText("已售");
                            txtPdState.setTextColor(context.getResources().getColor(R.color.colorRed));
                            sendClick(view, position, isH);
                        }
                        break;
                }
//                }
            }
        };
    }

    private void sendClick(View view, Integer position, Integer isH) {
        mapState.put(position, isH);
        String json = JSON.toJSONString(mapState);
        SharedPreferences.Editor editor = WineActivity.sp.edit();
        editor.putString("mapState", json);
        editor.apply();
//                Log.e("aaaa","--2--"+json);
        if (onItemClickListener != null) {
            onItemClickListener.onClick(view, position, isH);
        }
    }

    public interface OnItemClickListener {
        void onClick(View v, int position, Integer isH);
    }


}
