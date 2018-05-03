package com.qdzl.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.qdzl.MainActivity;
import com.qdzl.R;

/**
 * Created by QDZL on 2018/1/19.
 */

public class FragmentMap1 extends Fragment {
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;

    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;

    private Button btn9;
    private Button btn10;
    private Button btn11;
    private Button btn12;

    private Button btn13;
    private Button btn14;
    private Button btn15;
    private Button btn16;

    private Button btn17;
    private Button btn18;
    private Button btn19;
    private Button btn20;

    private Button btn21;
    private Button btn22;
    private Button btn23;
    private Button btn24;

    private Button btn25;
    private Button btn26;
    private Button btn27;
    private Button btn28;

    private Button btn29;
    private Button btn30;
    private Button btn31;

    private View v;

    private String light1;

    @Nullable
    @Override //创建视图
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (v == null) {
            v = inflater.inflate(R.layout.fragment_map1, null);
        }
        bind();
        return v;
    }

    private void bind() {
        light1=MainActivity.light1;
        
        btn1 = v.findViewById(R.id.btn1_1);
        btn2 = v.findViewById(R.id.btn1_2);
        btn3 = v.findViewById(R.id.btn1_3);
        btn4 = v.findViewById(R.id.btn1_4);

        btn5 = v.findViewById(R.id.btn1_5);
        btn6 = v.findViewById(R.id.btn1_6);
        btn7 = v.findViewById(R.id.btn1_7);
        btn8 = v.findViewById(R.id.btn1_8);

        btn9 = v.findViewById(R.id.btn1_9);
        btn10 = v.findViewById(R.id.btn1_10);
        btn11 = v.findViewById(R.id.btn1_11);
        btn12 = v.findViewById(R.id.btn1_12);

        btn13 = v.findViewById(R.id.btn1_13);
        btn14 = v.findViewById(R.id.btn1_14);
        btn15 = v.findViewById(R.id.btn1_15);
        btn16 = v.findViewById(R.id.btn1_16);

        btn17 = v.findViewById(R.id.btn1_17);
        btn18 = v.findViewById(R.id.btn1_18);
        btn19 = v.findViewById(R.id.btn1_19);
        btn20 = v.findViewById(R.id.btn1_20);

        btn21 = v.findViewById(R.id.btn1_21);
        btn22 = v.findViewById(R.id.btn1_22);
        btn23 = v.findViewById(R.id.btn1_23);
        btn24 = v.findViewById(R.id.btn1_24);

        btn25 = v.findViewById(R.id.btn1_25);
        btn26 = v.findViewById(R.id.btn1_26);
        btn27 = v.findViewById(R.id.btn1_27);
        btn28 = v.findViewById(R.id.btn1_28);

        btn29 = v.findViewById(R.id.btn1_29);
        btn30 = v.findViewById(R.id.btn1_30);
        btn31 = v.findViewById(R.id.btn1_31);

        btn1.setOnClickListener(onClickLis);
        btn2.setOnClickListener(onClickLis);
        btn3.setOnClickListener(onClickLis);
        btn4.setOnClickListener(onClickLis);

        btn5.setOnClickListener(onClickLis);
        btn6.setOnClickListener(onClickLis);
        btn7.setOnClickListener(onClickLis);
        btn8.setOnClickListener(onClickLis);

        btn9.setOnClickListener(onClickLis);
        btn10.setOnClickListener(onClickLis);
        btn11.setOnClickListener(onClickLis);
        btn12.setOnClickListener(onClickLis);

        btn13.setOnClickListener(onClickLis);
        btn14.setOnClickListener(onClickLis);
        btn15.setOnClickListener(onClickLis);
        btn16.setOnClickListener(onClickLis);

        btn17.setOnClickListener(onClickLis);
        btn18.setOnClickListener(onClickLis);
        btn19.setOnClickListener(onClickLis);
        btn20.setOnClickListener(onClickLis);

        btn21.setOnClickListener(onClickLis);
        btn22.setOnClickListener(onClickLis);
        btn23.setOnClickListener(onClickLis);
        btn24.setOnClickListener(onClickLis);

        btn25.setOnClickListener(onClickLis);
        btn26.setOnClickListener(onClickLis);
        btn27.setOnClickListener(onClickLis);
        btn28.setOnClickListener(onClickLis);

        btn29.setOnClickListener(onClickLis);
        btn30.setOnClickListener(onClickLis);
        btn31.setOnClickListener(onClickLis);
    }

    View.OnClickListener onClickLis = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn1_1:
                    MainActivity.on(light1+"01");
                    break;
                case R.id.btn1_2:
                    MainActivity.on(light1+"02");
                    break;
                case R.id.btn1_3:
                    MainActivity.on(light1+"03");
                    break;
                case R.id.btn1_4:
                    MainActivity.on(light1+"04");
                    break;
                case R.id.btn1_5:
                    MainActivity.on(light1+"05");
                    break;
                case R.id.btn1_6:
                    MainActivity.on(light1+"06");
                    break;
                case R.id.btn1_7:
                    MainActivity.on(light1+"07");
                    break;
                case R.id.btn1_8:
                    MainActivity.on(light1+"08");
                    break;
                case R.id.btn1_9:
                    MainActivity.on(light1+"09");
                    break;
                case R.id.btn1_10:
                    MainActivity.on(light1+"10");
                    break;
                case R.id.btn1_11:
                    MainActivity.on(light1+"11");
                    break;
                case R.id.btn1_12:
                    MainActivity.on(light1+"12");
                    break;
                case R.id.btn1_13:
                    MainActivity.on(light1+"13");
                    break;
                case R.id.btn1_14:
                    MainActivity.on(light1+"14");
                    break;
                case R.id.btn1_15:
                    MainActivity.on(light1+"15");
                    break;
                case R.id.btn1_16:
                    MainActivity.on(light1+"16");
                    break;
                case R.id.btn1_17:
                    MainActivity.on(light1+"17");
                    break;
                case R.id.btn1_18:
                    MainActivity.on(light1+"18");
                    break;
                case R.id.btn1_19:
                    MainActivity.on(light1+"19");
                    break;
                case R.id.btn1_20:
                    MainActivity.on(light1+"20");
                    break;
                case R.id.btn1_21:
                    MainActivity.on(light1+"21");
                    break;
                case R.id.btn1_22:
                    MainActivity.on(light1+"22");
                    break;
                case R.id.btn1_23:
                    MainActivity.on(light1+"23");
                    break;
                case R.id.btn1_24:
                    MainActivity.on(light1+"24");
                    break;
                case R.id.btn1_25:
                    MainActivity.on(light1+"25");
                    break;
                case R.id.btn1_26:
                    MainActivity.on(light1+"26");
                    break;
                case R.id.btn1_27:
                    MainActivity.on(light1+"27");
                    break;
                case R.id.btn1_28:
                    MainActivity.on(light1+"28");
                    break;
                case R.id.btn1_29:
                    MainActivity.on(light1+"29");
                    break;
                case R.id.btn1_30:
                    MainActivity.on(light1+"30");
                    break;
                case R.id.btn1_31:
                    MainActivity.on(light1+"31");
                    break;
            }
        }
    };
}
