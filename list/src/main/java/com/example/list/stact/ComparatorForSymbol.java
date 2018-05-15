package com.example.list.stact;

/**
 * Created by Administrator on 2017/11/12 0012.
 * 运算符比较器
 * 用于比较两个运算符的运算优先级
 */

public class ComparatorForSymbol {
    private String[] symbols = {"+", "-", "*", "/", "(", ")", "#"};
    /**
     * 优先级低
     */
    public static final int LOWER = -1;
    /**
     * 优先级相等
     */
    public static final int EQUAL = 0;
    /**
     * 优先级高
     */
    public static final int HEIGHTER = 1;
    /**
     * 优先级:无关
     */
    public static final int NONE = -2;

    private ComparatorForSymbol() {
    }

    /**
     * 此处不是单例,
     * 只是让外界得到比较器后,
     * 可以直接链式调用比较器的方法,
     * 避免使用静态
     *
     * @return
     */
    public static ComparatorForSymbol getNesInstance() {
        return new ComparatorForSymbol();
    }

    /**
     * @param a 第一个符号
     * @param b 第二个符号
     * @return 高=1 ,低=-1, 相等 = 0,没关系= -2
     */
    public int compare(String a, String b) {

        if (!isContain(a, symbols) || !isContain(b, symbols)) {
            try {
                throw new CompareSymbolException("存在不能识别的运算符");
            } catch (CompareSymbolException e) {
                e.printStackTrace();
            }
        }

        //优先级 -1 是小,0 是相等,1是高,-2是没关系
        int x = NONE;
        switch (a) {
            case "+":
            case "-":
                if (isContain(b, "+", "-", ")", "#")) {
                    x = HEIGHTER;
                } else {
                    x = LOWER;
                }
                break;
            case "*":
            case "/":
                if (isContain(b, "+", "-", "*", "/", ")", "#")) {
                    x = HEIGHTER;
                } else {
                    x = LOWER;
                }
                break;
            case "(":
                if (b.equals(")")) {
                    x = EQUAL;
                } else if (b.equals("#")) {
                    x = NONE;
                } else {
                    x = LOWER;
                }
                break;
            case ")":
                if (b.equals("(")) {
                    x = NONE;
                } else {
                    x = HEIGHTER;
                }
                break;
            case "#":
                if (b.equals(")")) {
                    x = NONE;
                } else if (b.equals("#")) {
                    x = EQUAL;
                } else {
                    x = LOWER;
                }
                break;
        }
        return x;
    }

    /**
     * 列表是否含有给定元素
     *
     * @param symbol       给定元素
     * @param heighSymbols 列表
     * @return
     */
    private boolean isContain(String symbol, String... heighSymbols) {
        for (int i = 0; i < heighSymbols.length; i++) {
            if (heighSymbols[i].equals(symbol)) {
                return true;
            }
        }
        return false;
    }

    public boolean isContain(String symbol){
        for (int i = 0; i < symbols.length; i++) {
            if (symbols[i].equals(symbol)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 异常
     */
    class CompareSymbolException extends Exception {
        public CompareSymbolException(String s) {
            super(s);
        }
    }

}
