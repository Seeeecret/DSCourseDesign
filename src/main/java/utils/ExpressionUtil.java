package utils;

import collections.MyHashMap;
import collections.MyStack;
import pojo.Expression;
import pojo.ExpressionTree;
import collections.MutableInteger;

import java.util.Scanner;

/**
 *
 */
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

    public static ExpressionTree testReadExpr(Expression E, String inputString) {
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
     *
     * @param E     表达式
     * @param input 输入的字符序列
     * @param index 用于在字符序列中移动的索引
     */
    private static void ReadExpression(Expression E, char[] input, MutableInteger index) {
        if (index.getValue() >= input.length || E == null) {
            return;
        }
        char currentChar = input[index.increment().getValue()];
        if (Character.isDigit(currentChar)) {
            // 正数，构造常量表达式
            E.op = "#";
            E.value = Integer.parseInt(Character.toString(currentChar));
        } else if (currentChar == '-' && input.length == 2) {
            // 负数，构造常量表达式
            E.op = "#";
            E.value = -Integer.parseInt(Character.toString(input[index.increment().getValue()]));

        } else if (Character.isAlphabetic(currentChar)) {
            // 变量，构造变量表达式
            E.value = 0; // 变量的初值为0
            E.setOp(String.valueOf(currentChar));
        } else if (isOperator(currentChar)) {
            // 运算符，构造复合表达式
            E.op = String.valueOf(currentChar);
            E.left = new Expression();
            E.right = new Expression();
            ReadExpression(E.left, input, index);
            ReadExpression(E.right, input, index);
        }
    }

    /**
     * 构造三角函数表达式的子树
     *
     * @param E           e
     * @param inputString 输入字符串
     */
    @Deprecated
    private static void ReadTrigExpr(Expression E, String inputString) {
        MutableInteger index = new MutableInteger(-1); // 用于在字符序列中移动的索引
        if (inputString.isEmpty()) {
            System.out.println("Invalid input");
            return;
        }
        char[] input = inputString.toCharArray();// 存储输入的字符序列
        ReadExpression(E.left, input, index);
        ReadExpression(E.right, input, index);
    }

    private static void searchReplaceTargetNode(String trigFuc, Expression newExpression, Expression targetTree, String targetOp) {
//        是无效输入
        if (newExpression == null || targetTree == null) {
            return;
        }
//        在整棵树中搜索目标节点，当当前结点的操作符为targetOp时，说明查找成功。
//        将目标节点的左子树设置为操作符为trigFuc的新节点对象，右子树设置为newExpression
        if (targetTree.getOp().equals(targetOp)) {
            targetTree.left = new Expression();
            targetTree.left.setOp(trigFuc);
            targetTree.right = newExpression;
        }
//        递归搜索左右子树

        searchReplaceTargetNode(trigFuc, newExpression, targetTree.left, targetOp);
        searchReplaceTargetNode(trigFuc, newExpression, targetTree.right, targetOp);


//        是数字
//        if (Character.isDigit(targetTree.getOp().charAt(0))) {
//            return;
//        } else if (targetTree.left == null && targetTree.right != null) {
//            if (targetTree.right.getOp().equals(targetOp)) {
//                targetTree.right = newExpression;
//            } else {
//                searchReplaceTargetNode(newExpression, targetTree.right, targetOp);
//            }
//        } else if (targetTree.left != null && targetTree.right == null) {
//            if (targetTree.left.getOp().equals(targetOp)) {
//                targetTree.left = newExpression;
//            } else {
//                searchReplaceTargetNode(newExpression, targetTree.left, targetOp);
//            }
//        } else {
//            if (targetTree.left.getOp().equals(targetOp)) {
//                targetTree.left = newExpression;
//            }
//            if (targetTree.right.getOp().equals(targetOp)) {
//                targetTree.right = newExpression;
//            }
//        }
//        searchReplaceTargetNode(newExpression, targetTree.left, targetOp);
//        searchReplaceTargetNode(newExpression, targetTree.right, targetOp);
    }

    /**
     * 用带括弧的中缀表示式输出表达式E的外部接口
     *
     * @param E 表达式
     */
    public static void WriteExpr(Expression E) {
        WriteExpression(E);
        System.out.println();
    }

    /**
     * 用带括弧的中缀表示式输出表达式E的内部方法
     *
     * @param E 表达式
     */
    private static void WriteExpression(Expression E) {
        if (E != null) {
            if (E.op.equals("#")) {
                // 常量
                System.out.print(E.value);
            } else if (Character.isAlphabetic(E.op.charAt(0))
                    && E.op.length() == 1) {
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
     *
     * @param c
     * @return boolean
     */
    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    /**
     * 实现对变量V的赋值（V = c）
     * TODO: 异常情况可用异常处理机制处理
     *
     * @param V 变量
     * @param c 将要赋给变量的值
     */
    public static boolean Assign(char V, int c, ExpressionTree E) {
        if (E == null) {
            return false;
        }
        MyHashMap<String, Double> variableCountMap = E.getVariableCountMap();
        if (variableCountMap.containsKey(Character.toString(V))) {
            variableCountMap.put(Character.toString(V), (double) c);
            return true;
        }
        return false;
    }

    /**
     * 实现对变量V的赋值（V = c）
     * TODO: 异常情况可用异常处理机制处理
     *
     * @param V 变量
     * @param c 将要赋给变量的值
     */
    public static boolean Assign(String V, int c, ExpressionTree E) {
        if (E == null) {
            return false;
        }
        MyHashMap<String, Double> variableCountMap = E.getVariableCountMap();
        if (variableCountMap.containsKey(V)) {
            variableCountMap.put(V, (double) c);
            return true;
        }
        return false;
    }

    /**
     * 在表达式中添加三角函数等初等函数的操作。
     * 实现原理为在对变量进行赋值时，可以选择将变量设置为一个三角函数，且函数的输入值就就为用户设置的变量的值。
     * TODO: 需要增加限制禁止变量的输入
     *
     * @param trigFunction 三角函数
     * @param inputString  输入字符串
     * @param E            e
     * @return boolean
     */
    public static boolean assignTrigFunction(String trigFunction, String V, String inputString, ExpressionTree E) {
        if (inputString.isEmpty()) {
            System.out.println("Invalid input");
            return false;
        } else if (E == null) {
            return false;
        }
//        由inputString构造三角函数的子树
        ExpressionTree newTree = testReadExpr(new Expression(), inputString);
//        计算inputString的值
        double value = evaluatePrefixExpression(inputString);
//        如果计算结果为NaN，说明输入的表达式不合法
        if (Double.isNaN(evaluateTrigFunc(trigFunction,value))) {
            System.out.println("Invalid input");
            return false;
        }
//        将哈希表中变量的值设置为inputString的值
        E.getVariableCountMap().put(V.toLowerCase(), value);
//        将变量的左子树替换为操作符为三角函数名的Expression对象，右子树为inputString的Expression对象
        searchReplaceTargetNode(trigFunction, newTree, E, V.toLowerCase());

        return true;
//        MyHashMap<String, Double> variableCountMap = E.getVariableCountMap();
//        if (!variableCountMap.containsKey(V.toLowerCase())) {
//            System.out.println("Invalid variable");
//            return false;
//        }
////        从哈希表移除原来的变量
//        variableCountMap.remove(V.toLowerCase());
////        当前变量的操作符变成三角函数名+变量名，下挂表达式
//        Expression newExpression = new Expression();
//        newExpression.setOp(trigFunction + V.toLowerCase());
////        读取三角函数的子树
//        ReadTrigExpr(newExpression, inputString);
////        在原表达式中搜索并替换原来的变量
//        searchReplaceTargetNode(newExpression, E, V.toLowerCase());
////        添加新的，当前变量下挂表达式的值添加到哈希表中
//        variableCountMap.put(trigFunction + V.toLowerCase(), evaluatePrefixExpression(inputString));
//        return true;
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
     *
     * @param E                表达式
     * @param variableCountMap 变量哈希表
     * @return int
     */
    private static double Evaluate(Expression E, MyHashMap<String, Double> variableCountMap) {
        if (E == null) {
            return 0;
        }
        if ("#".equals(E.op)) {
            // 常量
            return E.value;
        } else if (Character.isAlphabetic(E.op.charAt(0))
                && E.op.length() == 1) {
            // 变量
//            情况1:三角函数的情况，下挂表达式的计算值已存放在表达式树的哈希表中,Key仍为变量名
            if (E.left != null && E.right != null) {
                double originValue = variableCountMap.get(E.op.toLowerCase());
                String trigFunction = E.left.op;
                return evaluateTrigFunc(trigFunction, originValue);
            } else {
//                情况2:普通变量的情况，直接从哈希表中取值
                return variableCountMap. get(E.op.toLowerCase());
            }
        } else {
            // 复合表达式
            double leftValue = Evaluate(E.left, variableCountMap);
            double rightValue = Evaluate(E.right, variableCountMap);
            switch (E.op.charAt(0)) {
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

    /**
     * 求三角函数值
     *
     * @param trigFunc 三角函数
     * @param value    值
     * @return double
     */
    private static double evaluateTrigFunc(String trigFunc, double value) {
        switch (trigFunc) {
            case "sin":
                return Math.sin(value);
            case "cos":
                return Math.cos(value);
            case "tan":
                return Math.tan(value);
            case "cot":
                return 1 / Math.tan(value);
            case "sec":
                return 1 / Math.cos(value);
            case "csc":
                return 1 / Math.sin(value);
            case "asin":
                return Math.asin(value);
            case "acos":
                return Math.acos(value);
            case "atan":
                return Math.atan(value);
            default:
                return 0;
        }
    }

    /**
     * 复合两个表达式，构建一个新表达式
     *
     * @param P  p
     * @param E1 e1
     * @param E2 e2
     * @return {@link ExpressionTree}
     */
    public static ExpressionTree CompoundExpr(char P, ExpressionTree E1, ExpressionTree E2) {
        if (E1 == null || E2 == null) {
            return null;
        }
        MyHashMap<String, Double> newCountMap = MyHashMap.merge(E1.getVariableCountMap(), E2.getVariableCountMap());
        ExpressionTree expressionTree = new ExpressionTree(newCountMap);
        expressionTree.setOp(Character.toString(P));
        expressionTree.left = E1;
        expressionTree.right = E2;
        return expressionTree;
    }

    public static ExpressionTree MergeConst(ExpressionTree E) {
        MergeConstParameter(E);
        return E;
    }

    /**
     * 常数合并操作MergeConst(E) –– 合并表达式E中所有常数运算。
     * 例如，对表达式E=(2+3-a)*(b+3*4)进行合并常数的操作后，求得E=(5-a)*(b+12)。
     *
     * @param E e
     */
    public static void MergeConstParameter(Expression E) {
        if ("#".equals(E.op) || Character.isAlphabetic(E.op.charAt(0))
                && E.op.length() == 1) {
            // 常量或变量，不需要合并
            return;
        } else {
            // 复合表达式

            // 递归合并左右子表达式
            MergeConstParameter(E.left);
            MergeConstParameter(E.right);

            // 如果左右子表达式都是常数，合并常数运算
            if ("#".equals(E.left.op) && "#".equals(E.right.op)) {
                E.value = applyOperator(E.op.charAt(0), E.left.value, E.right.value);
                E.op = "#"; // 将操作符清空，表示这是一个常数节点
                E.left = E.right = null; // 清空左右子表达式
            }

        }

    }

    /**
     * 应用操作符到两个常数上
     *
     * @param op    操作符
     * @param left  左
     * @param right 右
     * @return int
     */
    private static int applyOperator(char op, int left, int right) {
        switch (op) {
            case '+':
                return left + right;
            case '-':
                return left - right;
            case '*':
                return left * right;
            case '/':
                return left / right;
            case '^':
                return (int) Math.pow(left, right);
            default:
                return 0;
        }
    }

    /**
     * 应用操作符到两个常数上
     *
     * @param op    操作符
     * @param left  左
     * @param right 右
     * @return int
     */
    private static double applyOperator(char op, double left, double right) {
        switch (op) {
            case '+':
                return left + right;
            case '-':
                return left - right;
            case '*':
                return left * right;
            case '/':
                return left / right;
            case '^':
                return (int) Math.pow(left, right);
            default:
                return 0;
        }
    }

    public static double evaluatePrefixExpression(String prefixExpression) {
        if (prefixExpression == null || prefixExpression.isEmpty()) {
            throw new IllegalArgumentException("Input expression is null or empty.");
        }

        // 将表达式拆分为字符数组
        char[] chars = prefixExpression.toCharArray();

        // 创建一个栈来存储操作数
        MyStack<Double> operandStack = new MyStack<>();

        // 从右到左遍历表达式
        for (int i = chars.length - 1; i >= 0; i--) {
            char currentChar = chars[i];

            // 如果是操作数，则将其推入栈中
            if (Character.isDigit(currentChar) || Character.isLetter(currentChar)) {
                // 只有单位数字的情况，没有多位数字的情况
                operandStack.push((double) Character.getNumericValue(currentChar));

            } else if (isOperator(currentChar)) {
                // 如果是运算符，则取出栈中的两个操作数进行计算，并将结果推入栈中
                if (operandStack.size() < 2) {
                    throw new IllegalArgumentException("Invalid expression format: not enough operands for operator.");
                }
                double operand1 = operandStack.pop();
                double operand2 = operandStack.pop();
                double result = applyOperator(currentChar, operand1, operand2);
                operandStack.push(result);
            } else {
                throw new IllegalArgumentException("Invalid character in expression: " + currentChar);
            }
        }

        // 最终栈中应该只有一个元素，即表达式的计算结果
        if (operandStack.size() != 1) {
            throw new IllegalArgumentException("Invalid expression format: too many operands or operators.");
        }

        return operandStack.pop();
    }
}
