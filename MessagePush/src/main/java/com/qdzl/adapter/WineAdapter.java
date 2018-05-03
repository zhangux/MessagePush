package com.qdzl.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.qdzl.R;
import com.qdzl.WineActivity;
import com.qdzl.socket.ClientManager;
import com.qdzl.utils.ToastUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by QDZL on 2018/3/26.
 */

public class WineAdapter extends RecyclerView.Adapter<WineAdapter.WineViewHolder> {
    private List<String> pdStrLis;
    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;

    private Map<Integer, Integer> mapState;

    public RelativeLayout.LayoutParams lp;
    SharedPreferences sp;
    private String containerLayer;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public WineAdapter(List<String> pdStrLis, Context context,String containerLayer) {
        Log.e("aaaa","-----------------"+containerLayer);
        this.pdStrLis = pdStrLis;
        this.context = context;
        if(containerLayer==null){
            containerLayer="";
        }
        this.containerLayer=containerLayer;
        inflater = LayoutInflater.from(context);
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        String json = sp.getString("mapState"+containerLayer, null);
        Log.e("aaaa","----"+json+"----"+"mapState"+containerLayer);
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

    public void clearAll() {
        mapState = new HashMap<>();
        this.pdStrLis.clear();
//        this.pdStrLis=pdStrLis;
        notifyDataSetChanged();
    }
    public void setData(List<String> pdStrLis,String containerLayer) {
        if(containerLayer==null){
            containerLayer="";
        }
        this.containerLayer=containerLayer;
//        mapState = new HashMap<>();
//        this.pdStrLis.clear();
        String json = sp.getString("mapState"+containerLayer, null);
        Log.e("aaaa","----"+json+"----"+"mapState"+containerLayer);
        if (json == null) {
            mapState = new HashMap<>();
        } else {
            mapState = JSON.parseObject(json, Map.class);
        }
        this.pdStrLis=pdStrLis;
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
        holder.bt4.setTag(position);
        holder.bt5.setTag(position);
        if(pdStrLis.size()==24){
            if(position==0){
                holder.txtTitle.setText("一排");
                holder.txtTitle.setVisibility(View.VISIBLE);
            }else if(position==12){
                holder.txtTitle.setText("二排");
                holder.txtTitle.setVisibility(View.VISIBLE);
            }
        }else if(pdStrLis.size()==48){
            if(position==0){
                holder.txtTitle.setText("一排");
                holder.txtTitle.setVisibility(View.VISIBLE);
            }else if(position==12){
                holder.txtTitle.setText("二排");
                holder.txtTitle.setVisibility(View.VISIBLE);
            }else if(position==24){
                holder.txtTitle.setText("三排");
                holder.txtTitle.setVisibility(View.VISIBLE);
            }else if(position==36){
                holder.txtTitle.setText("四排");
                holder.txtTitle.setVisibility(View.VISIBLE);
            }
        }

        if (mapState.get(position) != null) {
            if (mapState.get(position) == 1) {
                holder.txtPdState.setText("拿起");
                holder.txtPdState.setTextColor(context.getResources().getColor(R.color.colorAccent));
            } else if (mapState.get(position) == 0) {
//                holder.txtPdState.setText("放下");
                holder.txtPdState.setText("在售");
                holder.txtPdState.setTextColor(context.getResources().getColor(R.color.qhs));
            } else if (mapState.get(position) == 2) {
                holder.txtPdState.setText("已售");
                holder.txtPdState.setTextColor(context.getResources().getColor(R.color.colorRed));
            }else if (mapState.get(position) == 4) {
                holder.txtPdState.setText("下架");
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
        private Button bt4;
        private Button bt5;
        private TextView txtTitle;
//        private CheckBox cbPdCheck;

        public WineViewHolder(final View view) {
            super(view);
            this.txtPdIndex = view.findViewById(R.id.txt_pd_index);
            this.txtPdState = view.findViewById(R.id.txt_pd_state);
            this.bt1 = view.findViewById(R.id.bt_1);
            this.bt2 = view.findViewById(R.id.bt_2);
            this.bt3 = view.findViewById(R.id.bt_3);
            this.bt4 = view.findViewById(R.id.bt_4);
            this.bt5 = view.findViewById(R.id.bt_5);
            this.txtTitle = view.findViewById(R.id.txt_title);
//            this.cbPdCheck = view.findViewById(R.id.cb_pd_check);
            bt1.setOnClickListener(onClickListener);
            bt2.setOnClickListener(onClickListener);
            bt3.setOnClickListener(onClickListener);
            bt4.setOnClickListener(onClickListener);
            bt5.setOnClickListener(onClickListener);
        }

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer position = (Integer) view.getTag();
                Integer isH = 0;
//                if (ClientManager.isCon) {
                switch (view.getId()) {
                    case R.id.bt_1:
                        if (mapState.get(position) != null && (mapState.get(position) == 2||mapState.get(position) == 4)) {
                            ToastUtil.show("当前商品已售出或下架！");
                        } else {
                            isH = 1;
                            txtPdState.setText("拿起");
                            txtPdState.setTextColor(context.getResources().getColor(R.color.colorAccent));
                            sendClick(view, position, isH);
                        }
                        break;
                    case R.id.bt_2:
                        if (mapState.get(position) != null && (mapState.get(position) == 2||mapState.get(position) == 4)) {
                            ToastUtil.show("当前商品已售出或下架！");
                        } else {
                            isH = 0;
                            txtPdState.setText("在售");
                            txtPdState.setTextColor(context.getResources().getColor(R.color.qhs));
                            sendClick(view, position, isH);
                        }
                        break;
                    case R.id.bt_3:
                        if (mapState.get(position) != null && (mapState.get(position) == 2||mapState.get(position) == 4)) {
                            ToastUtil.show("当前商品已售出或下架！");
                        } else {
                            isH = 2;
                            txtPdState.setText("已售");
                            txtPdState.setTextColor(context.getResources().getColor(R.color.colorRed));
                            sendClick(view, position, isH);
                        }
                        break;
                    case R.id.bt_4:
                        if (mapState.get(position) != null && (mapState.get(position) == 2||mapState.get(position) == 4)) {
                            ToastUtil.show("当前商品已售出或下架！");
                        } else {
                            isH = 4;
                            txtPdState.setText("下架");
                            txtPdState.setTextColor(context.getResources().getColor(R.color.colorRed));
                            sendClick(view, position, isH);
                        }
                        break;
                    case R.id.bt_5:
                        if (mapState.get(position) == null||mapState.get(position) == 1||mapState.get(position) == 0||mapState.get(position) == 3) {
                            ToastUtil.show("当前商品有货！");
                        } else {
                            isH = 3;
                            txtPdState.setText("在售");
                            txtPdState.setTextColor(context.getResources().getColor(R.color.qhs));
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
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("mapState"+containerLayer, json);
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
