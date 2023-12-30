package org.example.pojo;

import org.example.collections.MyHashMap;

public class ExpressionTree extends Expression {
    /**
     * 记录表达式中的变量的哈希表
     */
    private MyHashMap<String, Expression> variableCountMap;

    public ExpressionTree(String op, Double value, Expression left, Expression right, MyHashMap<String, Expression> variableCountMap) {
        super(op, value, left, right);
        this.variableCountMap = variableCountMap;
    }

    public ExpressionTree(Expression E, MyHashMap<String, Expression> variableCountMap) {
        super(E.getOp(), E.getValue(), E.getLeft(), E.getRight());
        this.variableCountMap = variableCountMap;
    }

    public ExpressionTree() {
        this.variableCountMap = new MyHashMap<>();
    }

    public ExpressionTree(MyHashMap<String, Expression> variableCountMap) {
        this.variableCountMap = variableCountMap;
    }

    public MyHashMap<String, Expression> getVariableCountMap() {
        return variableCountMap;
    }

    public void setVariableCountMap(MyHashMap<String, Expression> variableCountMap) {
        this.variableCountMap = variableCountMap;
    }

    /**
     * 通过遍历表达式的字符数组计算变量数量，并存与哈希表中
     *
     * @param E     表达式
     * @return {@link ExpressionTree}
     */
    public static ExpressionTree buildExpressionTree(Expression E) {
        if (E == null) {
            return null;
        }
        MyHashMap<String, Expression> variableCountMap = new MyHashMap<>();
        return new ExpressionTree(E, variableCountMap);
    }


}
