package com.example.vasyldvorak_hw_android_java_2.model;

public class CalculatorImpl implements Calculator {

    @Override
    public float perform(float arg1, float arg2, Operator operator) {
        switch (operator) {
            case ADD:
                return arg1 + arg2;

            case SUB:
                return arg1 - arg2;

            case MULT:
                return arg1 * arg2;
            case DIV:
                return arg1 / arg2;
        }
        return 0;
    }
}
