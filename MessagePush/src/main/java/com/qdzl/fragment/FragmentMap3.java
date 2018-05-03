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

public class FragmentMap3 extends Fragment {
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
            v = inflater.inflate(R.layout.fragment_map3, null);
        }
        bind();
        return v;
    }

    private void bind() {

        btn1 = v.findViewById(R.id.btn3_1);
        btn2 = v.findViewById(R.id.btn3_2);
        btn3 = v.findViewById(R.id.btn3_3);
        btn4 = v.findViewById(R.id.btn3_4);

        btn5 = v.findViewById(R.id.btn3_5);
        btn6 = v.findViewById(R.id.btn3_6);
        btn7 = v.findViewById(R.id.btn3_7);
        btn8 = v.findViewById(R.id.btn3_8);

        btn9 = v.findViewById(R.id.btn3_9);
        btn10 = v.findViewById(R.id.btn3_10);
        btn11 = v.findViewById(R.id.btn3_11);
        btn12 = v.findViewById(R.id.btn3_12);

        btn13 = v.findViewById(R.id.btn3_13);
        btn14 = v.findViewById(R.id.btn3_14);
        btn15 = v.findViewById(R.id.btn3_15);
        btn16 = v.findViewById(R.id.btn3_16);

        btn17 = v.findViewById(R.id.btn3_17);
        btn18 = v.findViewById(R.id.btn3_18);
        btn19 = v.findViewById(R.id.btn3_19);
        btn20 = v.findViewById(R.id.btn3_20);

        btn21 = v.findViewById(R.id.btn3_21);
        btn22 = v.findViewById(R.id.btn3_22);
        btn23 = v.findViewById(R.id.btn3_23);
        btn24 = v.findViewById(R.id.btn3_24);

        btn25=v.findViewById(R.id.btn3_25);
        btn26=v.findViewById(R.id.btn3_26);
        btn27=v.findViewById(R.id.btn3_27);
        btn28=v.findViewById(R.id.btn3_28);

        btn29=v.findViewById(R.id.btn3_29);
        btn30=v.findViewById(R.id.btn3_30);
        btn31=v.findViewById(R.id.btn3_31);

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
                case R.id.btn3_1:
                    MainActivity.on("9C01");
                    break;
                case R.id.btn3_2:
                    MainActivity.on("9C02");
                    break;
                case R.id.btn3_3:
                    MainActivity.on("9C03");
                    break;
                case R.id.btn3_4:
                    MainActivity.on("9C04");
                    break;
                case R.id.btn3_5:
                    MainActivity.on("9C05");
                    break;
                case R.id.btn3_6:
                    MainActivity.on("9C06");
                    break;
                case R.id.btn3_7:
                    MainActivity.on("9C07");
                    break;
                case R.id.btn3_8:
                    MainActivity.on("9C08");
                    break;
                case R.id.btn3_9:
                    MainActivity.on("9C09");
                    break;
                case R.id.btn3_10:
                    MainActivity.on("9C10");
                    break;
                case R.id.btn3_11:
                    MainActivity.on("9C11");
                    break;
                case R.id.btn3_12:
                    MainActivity.on("9C12");
                    break;
                case R.id.btn3_13:
                    MainActivity.on("9C13");
                    break;
                case R.id.btn3_14:
                    MainActivity.on("9C14");
                    break;
                case R.id.btn3_15:
                    MainActivity.on("9C15");
                    break;
                case R.id.btn3_16:
                    MainActivity.on("9C16");
                    break;
                case R.id.btn3_17:
                    MainActivity.on("9C17");
                    break;
                case R.id.btn3_18:
                    MainActivity.on("9C18");
                    break;
                case R.id.btn3_19:
                    MainActivity.on("9C19");
                    break;
                case R.id.btn3_20:
                    MainActivity.on("9C20");
                    break;
                case R.id.btn3_21:
                    MainActivity.on("9C21");
                    break;
                case R.id.btn3_22:
                    MainActivity.on("9C22");
                    break;
                case R.id.btn3_23:
                    MainActivity.on("9C23");
                    break;
                case R.id.btn3_24:
                    MainActivity.on("9C24");
                    break;
                case R.id.btn3_25:
                    MainActivity.on("9C25");
                    break;
                case R.id.btn3_26:
                    MainActivity.on("9C26");
                    break;
                case R.id.btn3_27:
                    MainActivity.on("9C27");
                    break;
                case R.id.btn3_28:
                    MainActivity.on("9C28");
                    break;
                case R.id.btn3_29:
                    MainActivity.on("9C29");
                    break;
                case R.id.btn3_30:
                    MainActivity.on("9C30");
                    break;
                case R.id.btn3_31:
                    MainActivity.on("9C31");
                    break;
            }
        }
    };
}
