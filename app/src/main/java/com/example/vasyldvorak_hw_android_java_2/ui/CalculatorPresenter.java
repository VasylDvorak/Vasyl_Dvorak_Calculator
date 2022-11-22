package com.example.vasyldvorak_hw_android_java_2.ui;


import android.os.Parcel;
import android.os.Parcelable;

import com.example.vasyldvorak_hw_android_java_2.model.Calculator;
import com.example.vasyldvorak_hw_android_java_2.model.CalculatorImpl;
import com.example.vasyldvorak_hw_android_java_2.model.Operator;

import java.io.Serializable;
import java.text.DecimalFormat;


public class CalculatorPresenter implements Parcelable {


    public DecimalFormat formater = new DecimalFormat("#.##");
    public CalculatorView view;

    public Calculator calculator;
    public float argOne;
    public float argTwo;
    public Operator selectedOperator;
    public boolean dot = true;
    public boolean dotn = true;
    public boolean next = true;
    public String res;

    public CalculatorPresenter(CalculatorView view, Calculator calculator) {
        this.view = view;

        this.calculator = calculator;
    }


    protected CalculatorPresenter(Parcel in) {
        res = in.readString();
        argOne = in.readFloat();
        argTwo = in.readFloat();
        dot = in.readByte() != 0;
        dotn = in.readByte() != 0;
        next = in.readByte() != 0;
        int d = in.readInt();
        if (d == 1)
            selectedOperator = Operator.ADD;
        if (d == 2)
            selectedOperator = Operator.SUB;
        if (d == 3)
            selectedOperator = Operator.MULT;
        if (d == 4)
            selectedOperator = Operator.DIV;


    }

    public String get_res() {
        return res;
    }

    public static final Creator<CalculatorPresenter> CREATOR = new Creator<CalculatorPresenter>() {
        @Override
        public CalculatorPresenter createFromParcel(Parcel in) {
            return new CalculatorPresenter(in);
        }

        @Override
        public CalculatorPresenter[] newArray(int size) {
            return new CalculatorPresenter[size];
        }
    };

    public void onDigitPressed(int digit) {
        int sign;


        if (next) {
            sign = (argOne == 0) ? 1 : (int) Math.signum(argOne);
            if (dot) {

                argOne = sign * Math.abs(argOne * 10 + digit);
            } else
                argOne = Dot_funct(argOne, digit);
            res = String.valueOf(argOne);
            view.showResult(res);


        } else {
            sign = (argTwo == 0) ? 1 : (int) Math.signum(argTwo);
            if (dot)
                argTwo = sign * Math.abs(argTwo * 10 + digit);
            else
                argTwo = Dot_funct(argTwo, digit);
            res = String.valueOf(argTwo);
            view.showResult(res);

        }


    }

    public void onOperatorPressed(Operator operator) {
        selectedOperator = operator;
        next = false;
        dot = true;
        dotn = true;


    }

    public void onDotPressed() {

        dot = false;


    }


    public void onSignPressed() {
        if (next) {
            argOne *= -1;
            res = String.valueOf(argOne);
            view.showResult(res);
        } else {
            argTwo *= -1;
            res = String.valueOf(argTwo);
            view.showResult(res);
        }

    }

    public void onDelPressed() {

        if (next) {

            String first_argument = argOne - (int) argOne != 0 ? String.valueOf(argOne) : String.valueOf((int) argOne);
            if (argOne - (int) argOne == 0) {
                dot = true;
                dotn = true;
            }

            if (!(first_argument == null || first_argument.length() == 0 || first_argument.equals("0") || first_argument.equals(""))) {
                first_argument = first_argument.substring(0, first_argument.length() - 1);

            } else {
                first_argument = "0";
            }
            if (!(first_argument == null || first_argument.length() == 0 || first_argument.equals("0") || first_argument.equals("-") || first_argument.equals("")))
                argOne = Float.valueOf(first_argument);
            else
                argOne = 0;
            res = String.valueOf(argOne);
            view.showResult(res);
        } else {
            String second_argument = argTwo - (int) argTwo != 0 ? String.valueOf(argTwo) : String.valueOf((int) argTwo);
            if (argTwo - (int) argTwo == 0) {
                dot = true;
                dotn = true;
            }
            if (!(second_argument == null || second_argument.length() == 0 || second_argument.equals("0") || second_argument.equals("-") || second_argument.equals(""))) {
                second_argument = second_argument.substring(0, second_argument.length() - 1);

            } else {
                second_argument = "0";
            }
            if (!(second_argument == null || second_argument.length() == 0 || second_argument.equals("0") || second_argument.equals("")))
                argTwo = Float.valueOf(second_argument);
            else
                argTwo = 0;
            res = String.valueOf(argTwo);
            view.showResult(res);
        }


    }

    public void onEqualPressed() {
        if (selectedOperator != null) {
            argOne = calculator.perform(argOne, argTwo, selectedOperator);


        }
        argTwo = 0f;
        String str;
        if (String.valueOf(argOne).equals("Infinity")) {
            str = "Infinity/дел. на ноль";
            argOne = 0;
            argTwo = 0;
            next = true;
            dot = true;
            dotn = true;
            selectedOperator = null;
        } else
            str = String.valueOf(argOne);
        res = str;
        view.showResult(str);

    }

    float Dot_funct(float fdigit, int digit4) {
        String arg = String.valueOf(fdigit);
        if (dotn) {
            arg = String.valueOf((int) fdigit) + ".";
            dotn = false;
        }

        arg = arg + digit4;
        if (!(arg.equals("") || arg.equals(null) || arg.equals("0")))
            return Float.valueOf(arg);// Float.parseFloat(str);// String str=Float.toString(argum);
        else return 0;
    }

    public void onCPressed() {
        if (next) {
            argOne = 0;
            res = String.valueOf(argOne);
            view.showResult(res);
        } else {
            argTwo = 0;
            res = String.valueOf(argTwo);
            view.showResult(res);
        }
        dot = true;
        dotn = true;
    }

    public void onCEPressed() {
        argOne = 0;
        argTwo = 0;
        res = "0";
        view.showResult("0");
        next = true;
        dot = true;
        dotn = true;
        selectedOperator = null;

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        int n = 0;
        parcel.writeString(res);
        parcel.writeFloat(argOne);
        parcel.writeFloat(argTwo);
        parcel.writeByte((byte) (dot ? 1 : 0));
        parcel.writeByte((byte) (dotn ? 1 : 0));
        parcel.writeByte((byte) (next ? 1 : 0));

        if (selectedOperator.equals(Operator.ADD))
            n = 1;
        if (selectedOperator.equals(Operator.SUB))
            n = 2;
        if (selectedOperator.equals(Operator.MULT))
            n = 3;
        if (selectedOperator.equals(Operator.DIV))
            n = 4;
        parcel.writeInt(n);
    }
}
