package utils;

import pojo.Expression;
import pojo.MutableInteger;

import java.util.Scanner;

public class ExpressionUtil {
    /**
     * 以字符序列的形式输入语法正确的前缀表示式并构造表达式E，对外暴露的接口
     * @param E 表达式
     */
    public static void ReadExpr(Expression E) {
        Scanner scanner = new Scanner(System.in);
        MutableInteger index = new MutableInteger(-1); // 用于在字符序列中移动的索引
        char[] input; // 存储输入的字符序列
        System.out.println("Enter an arithmetic expression:");
        String inputString = scanner.nextLine();
        input = inputString.toCharArray();
        ReadExpression(E, input, index);
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
        if (Character.isDigit(currentChar) || currentChar == '-') {
            // 数字或负号，构造常量表达式
            E.value = Integer.parseInt(Character.toString(currentChar));
        } else if (Character.isAlphabetic(currentChar)) {
            // 变量，构造变量表达式
            E.value = null; // 变量的初值为null
            E.op = currentChar;
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
     * 用带括弧的中缀表示式输出表达式E。
     * @param E 表达式
     */
    private static void WriteExpr(Expression E) {
        if (E != null) {
            if (E.op == ' ') {
                // 常量或变量
                System.out.print(E.value);
            } else {
                // 复合表达式
                System.out.print("(");
                WriteExpr(E.left);
                System.out.print(" " + E.op + " ");
                WriteExpr(E.right);
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
}
