package com.example.list.stact;

import java.util.LinkedList;

/**
 * Created by Administrator on 2017/11/12 0012.
 * <p>
 * 逆波兰表达式
 */

public class Anti_Polish_Expression {


    /**
     * 转换器
     * 作用是将中则表达式转换成后则表达式
     */
    public class Converter {

        /**
         * @param nomal 中则表达式
         * @return 后则表达式的队列
         */
        public LinkedList<String> conver(String nomal) {
            //将String 类型的中则表达式转换成LinkedList
            LinkedList<String> nomalList = nomalConver(nomal);
            return null;
        }

        /**
         *
         *
         * @return 中则表达式队列
         */
        private LinkedList<String> nomalConver(String nomal) {
            ComparatorForSymbol comparatorForSymbol = ComparatorForSymbol.getNesInstance();

            LinkedList<String> linkedList = new LinkedList<>();
            StringBuilder temp = new StringBuilder();
            for (int i = 0; i < nomal.length(); i++) {
                char c = nomal.charAt(i);
                String s = String.valueOf(c);
               if (isNum(s)) {
                    //如果是数字
                    temp.append(s);
                } else if (comparatorForSymbol.isContain(s)) {
                    //如果是符号
                    if (temp.length()!=0) {
                        //先把原来的数字加入到集合
                        linkedList.addLast(temp.toString());
                        temp.delete(0,temp.length()-1);
                    }
                    //把符号加入到集合
                    linkedList.addLast(s);
                }else if(s.equals("")) {
                    //如果是空格
                    if (temp.length()!=0) {
                        linkedList.add(temp.toString());
                        temp.delete(0,temp.length()-1);
                    }
                }
            }

            return linkedList;
        }

        /**
         * 比较字符串是否是数字
         *
         * @return
         */
        private boolean isNum(String s) {
            return s.equals("0") ||
                    s.equals("1") ||
                    s.equals("2") ||
                    s.equals("3") ||
                    s.equals("4") ||
                    s.equals("5") ||
                    s.equals("6") ||
                    s.equals("7") ||
                    s.equals("8") ||
                    s.equals("9");
        }

    }


}
