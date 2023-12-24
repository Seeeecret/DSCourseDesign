package utils;

import DataStructure.MyHashMap;
import pojo.Expression;
import pojo.ExpressionTree;
import pojo.MutableInteger;
import java.util.Scanner;

public class ExpressionUtil {
//    TODO:注意必须要用一个变量接住ReadExpr的返回值，会无法正常读取
    /**
     * 以字符序列的形式输入语法正确的前缀表示式并构造表达式E，对外暴露的接口
     *
     * @param E 表达式
     * @return {@link ExpressionTree}
     */
    public static ExpressionTree ReadExpr(Expression E) {
        Scanner scanner = new Scanner(System.in);
        MutableInteger index = new MutableInteger(-1); // 用于在字符序列中移动的索引
        System.out.println("Enter an arithmetic expression:");
        String inputString = scanner.nextLine();
        if (inputString.isEmpty()) {
            System.out.println("Invalid input");
            return null;
        }
        char[] input = inputString.toCharArray();// 存储输入的字符序列
        ExpressionTree expressionTree = ExpressionTree.buildExpressionTree(E, inputString);
        ReadExpression(expressionTree, input, index);
        return expressionTree;
    }

    public static ExpressionTree testReadExpr(Expression E,String inputString) {
        MutableInteger index = new MutableInteger(-1); // 用于在字符序列中移动的索引
        if (inputString.isEmpty()) {
            System.out.println("Invalid input");
            return null;
        }
        char[] input = inputString.toCharArray();// 存储输入的字符序列
        ExpressionTree expressionTree = ExpressionTree.buildExpressionTree(E, inputString);
        ReadExpression(expressionTree, input, index);
        return expressionTree;
    }

    /**
     * 以字符序列的形式输入语法正确的前缀表示式并构造表达式E，内部使用的方法
     * @param E 表达式
     * @param input 输入的字符序列
     * @param index 用于在字符序列中移动的索引
     */
    private static void ReadExpression(Expression E, char[] input, MutableInteger index) {
        if (index.getValue() >= input.length) {
            return;
        }
        char currentChar = input[index.increment().getValue()];
        if (Character.isDigit(currentChar)) {
            // 正数，构造常量表达式
            E.op = '#';
            E.value = Integer.parseInt(Character.toString(currentChar));
        } else if (currentChar == '-' && input.length == 2) {
            // 负数，构造常量表达式
            E.op = '#';
            E.value = -Integer.parseInt(Character.toString(input[index.increment().getValue()]));

        } else if (Character.isAlphabetic(currentChar)) {
            // 变量，构造变量表达式
            E.value = 0; // 变量的初值为0
            E.setOp(currentChar);
        } else if (isOperator(currentChar)) {
            // 运算符，构造复合表达式
            E.op = currentChar;
            E.left = new Expression();
            E.right = new Expression();
            ReadExpression(E.left, input, index);
            ReadExpression(E.right, input, index);
        }
    }

    /**
     * 用带括弧的中缀表示式输出表达式E的外部接口
     * @param E 表达式
     */
    public static void WriteExpr(Expression E) {
        WriteExpression(E);
        System.out.println();
    }

    /**
     * 用带括弧的中缀表示式输出表达式E的内部方法
     * @param E 表达式
     */
    private static void WriteExpression(Expression E) {
        if (E != null) {
            if (E.op == '#') {
                // 常量
                System.out.print(E.value);
            } else if (Character.isAlphabetic(E.op)) {
                // 变量
                System.out.print(E.op);
            } else {
                // 复合表达式
                System.out.print("(");
                WriteExpression(E.left);
                System.out.print(" " + E.op + " ");
                WriteExpression(E.right);
                System.out.print(")");
            }
        }
    }
    /**
     * 判断字符是否是运算符
     * @param c
     * @return boolean
     */
    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    /**
     * 实现对变量V的赋值（V = c）
     * TODO: 异常情况可用异常处理机制处理
     * @param V 变量
     * @param c 将要赋给变量的值
     */
    public static boolean Assign(char V, int c, ExpressionTree E) {
        if (E == null) {
            return false;
        }
        MyHashMap<String, Integer> variableCountMap = E.getVariableCountMap();
        if (variableCountMap.containsKey(Character.toString(V))) {
            variableCountMap.put(Character.toString(V), c);
            return true;
        }
        return false;
    }

    /**
     * 实现对变量V的赋值（V = c）
     * TODO: 异常情况可用异常处理机制处理
     * @param V 变量
     * @param c 将要赋给变量的值
     */
    public static boolean Assign(String V, int c, ExpressionTree E) {
        if (E == null) {
            return false;
        }
        MyHashMap<String, Integer> variableCountMap = E.getVariableCountMap();
        if (variableCountMap.containsKey(V)) {
            variableCountMap.put(V, c);
            return true;
        }
        return false;
    }


    /**
     * 求值表达式E的值的外部接口
     *
     * @param E e
     * @return {@link Double}
     */
    public static Double Value(ExpressionTree E) {
        if (E == null) {
            return null;
        }
        try {
            return Evaluate(E, E.getVariableCountMap());
        } catch (ArithmeticException e) {
            System.out.println("Error, Divided by zero");
            return null;
        }
    }


    /**
     * 计算表达式E的值的内部方法
     * @param E 表达式
     * @return int
     */
    private static double Evaluate(Expression E, MyHashMap<String, Integer> variableCountMap) {
        if (E == null) {
            return 0;
        }
        if (E.op == '#') {
            // 常量
            return E.value;
        } else if (Character.isAlphabetic(E.op)) {
            // 变量
            return variableCountMap.get(Character.toString(E.op).toLowerCase());
        } else {
            // 复合表达式
            double leftValue = Evaluate(E.left,variableCountMap);
            double rightValue = Evaluate(E.right,variableCountMap);
            switch (E.op) {
                case '+':
                    return leftValue + rightValue;
                case '-':
                    return leftValue - rightValue;
                case '*':
                    return leftValue * rightValue;
                case '/':
                    return leftValue / rightValue;
                case '^':
                    return (int) Math.pow(leftValue, rightValue);
                default:
                    return 0;
            }
        }
    }

    public static ExpressionTree CompoundExpr(char P, ExpressionTree E1, ExpressionTree E2) {
        if (E1 == null || E2 == null) {
            return null;
        }
        MyHashMap<String, Integer> newCountMap = MyHashMap.merge(E1.getVariableCountMap(), E2.getVariableCountMap());
        ExpressionTree expressionTree = new ExpressionTree(newCountMap);
        expressionTree.setOp(P);
        expressionTree.left = E1;
        expressionTree.right = E2;
        return expressionTree;
    }
}
