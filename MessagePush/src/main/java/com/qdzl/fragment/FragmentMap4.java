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

public class FragmentMap4 extends Fragment {
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

    @Nullable
    @Override //创建视图
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (v == null) {
            v = inflater.inflate(R.layout.fragment_map4, null);
        }
        bind();
        return v;
    }

    private void bind() {

        btn1 = v.findViewById(R.id.btn4_1);
        btn2 = v.findViewById(R.id.btn4_2);
        btn3 = v.findViewById(R.id.btn4_3);
        btn4 = v.findViewById(R.id.btn4_4);

        btn5 = v.findViewById(R.id.btn4_5);
        btn6 = v.findViewById(R.id.btn4_6);
        btn7 = v.findViewById(R.id.btn4_7);
        btn8 = v.findViewById(R.id.btn4_8);

        btn9 = v.findViewById(R.id.btn4_9);
        btn10 = v.findViewById(R.id.btn4_10);
        btn11 = v.findViewById(R.id.btn4_11);
        btn12 = v.findViewById(R.id.btn4_12);

        btn13 = v.findViewById(R.id.btn4_13);
        btn14 = v.findViewById(R.id.btn4_14);
        btn15 = v.findViewById(R.id.btn4_15);
        btn16 = v.findViewById(R.id.btn4_16);

        btn17 = v.findViewById(R.id.btn4_17);
        btn18 = v.findViewById(R.id.btn4_18);
        btn19 = v.findViewById(R.id.btn4_19);
        btn20 = v.findViewById(R.id.btn4_20);

        btn21 = v.findViewById(R.id.btn4_21);
        btn22 = v.findViewById(R.id.btn4_22);
        btn23 = v.findViewById(R.id.btn4_23);
        btn24 = v.findViewById(R.id.btn4_24);

        btn25=v.findViewById(R.id.btn4_25);
        btn26=v.findViewById(R.id.btn4_26);
        btn27=v.findViewById(R.id.btn4_27);
        btn28=v.findViewById(R.id.btn4_28);

        btn29=v.findViewById(R.id.btn4_29);
        btn30=v.findViewById(R.id.btn4_30);
        btn31=v.findViewById(R.id.btn4_31);

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
                case R.id.btn4_1:
                    MainActivity.on("9D01");
                    break;
                case R.id.btn4_2:
                    MainActivity.on("9D02");
                    break;
                case R.id.btn4_3:
                    MainActivity.on("9D03");
                    break;
                case R.id.btn4_4:
                    MainActivity.on("9D04");
                    break;
                case R.id.btn4_5:
                    MainActivity.on("9D05");
                    break;
                case R.id.btn4_6:
                    MainActivity.on("9D06");
                    break;
                case R.id.btn4_7:
                    MainActivity.on("9D07");
                    break;
                case R.id.btn4_8:
                    MainActivity.on("9D08");
                    break;
                case R.id.btn4_9:
                    MainActivity.on("9D09");
                    break;
                case R.id.btn4_10:
                    MainActivity.on("9D10");
                    break;
                case R.id.btn4_11:
                    MainActivity.on("9D11");
                    break;
                case R.id.btn4_12:
                    MainActivity.on("9D12");
                    break;
                case R.id.btn4_13:
                    MainActivity.on("9D13");
                    break;
                case R.id.btn4_14:
                    MainActivity.on("9D14");
                    break;
                case R.id.btn4_15:
                    MainActivity.on("9D15");
                    break;
                case R.id.btn4_16:
                    MainActivity.on("9D16");
                    break;
                case R.id.btn4_17:
                    MainActivity.on("9D17");
                    break;
                case R.id.btn4_18:
                    MainActivity.on("9D18");
                    break;
                case R.id.btn4_19:
                    MainActivity.on("9D19");
                    break;
                case R.id.btn4_20:
                    MainActivity.on("9D20");
                    break;
                case R.id.btn4_21:
                    MainActivity.on("9D21");
                    break;
                case R.id.btn4_22:
                    MainActivity.on("9D22");
                    break;
                case R.id.btn4_23:
                    MainActivity.on("9D23");
                    break;
                case R.id.btn4_24:
                    MainActivity.on("9D24");
                    break;
                case R.id.btn4_25:
                    MainActivity.on("9D25");
                    break;
                case R.id.btn4_26:
                    MainActivity.on("9D26");
                    break;
                case R.id.btn4_27:
                    MainActivity.on("9D27");
                    break;
                case R.id.btn4_28:
                    MainActivity.on("9D28");
                    break;
                case R.id.btn4_29:
                    MainActivity.on("9D29");
                    break;
                case R.id.btn4_30:
                    MainActivity.on("9D30");
                    break;
                case R.id.btn4_31:
                    MainActivity.on("9D31");
                    break;
            }
        }
    };
}
