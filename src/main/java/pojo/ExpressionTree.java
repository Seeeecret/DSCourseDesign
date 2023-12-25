package pojo;

import collections.MyHashMap;

import static utils.Consts.*;

public class ExpressionTree extends Expression {
    /**
     * 记录表达式中的变量的哈希表
     */
    private MyHashMap<String, Double> variableCountMap;

    public ExpressionTree(String op, int value, Expression left, Expression right, MyHashMap<String, Double> variableCountMap) {
        super(op, value, left, right);
        this.variableCountMap = variableCountMap;
    }



    public ExpressionTree(MyHashMap<String, Double> variableCountMap) {
        this.variableCountMap = variableCountMap;
    }

    public MyHashMap<String, Double> getVariableCountMap() {
        return variableCountMap;
    }

    public void setVariableCountMap(MyHashMap<String, Double> variableCountMap) {
        this.variableCountMap = variableCountMap;
    }

    /**
     * 通过遍历表达式的字符数组计算变量数量，并存与哈希表中
     *
     * @param E     表达式
     * @param input 表达式的字符数组
     * @return {@link ExpressionTree}
     */
    public static ExpressionTree buildExpressionTree(Expression E, String input) {
        if (E == null) {
            return null;
        }
        MyHashMap<String, Double> variableCountMap = new MyHashMap<>();
        char[] charArray = input.toLowerCase().toCharArray();
        for (char c : charArray) {
            if (Character.isAlphabetic(c) && !variableCountMap.containsKey(Character.toString(c))) {
                variableCountMap.put(Character.toString(c), VARIABLE_DEFAULT_VALUE);
            }
        }
        return new ExpressionTree(variableCountMap);
    }


}
