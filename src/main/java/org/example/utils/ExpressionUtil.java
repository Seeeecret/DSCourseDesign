package org.example.utils;

import org.example.collections.MyHashMap;
import org.example.collections.MyStack;
import org.example.exceptions.VariableInTrigFucException;
import org.example.pojo.Expression;
import org.example.pojo.ExpressionTree;
import org.example.collections.MutableInteger;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import static org.example.utils.Consts.CONSTANT_DEFAULT_OPERATOR;
import static org.example.utils.Consts.VARIABLE_DEFAULT_VALUE;

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
        ExpressionTree expressionTree = ExpressionTree.buildExpressionTree(E);
        ReadExpression(expressionTree, input, index);
        searchPutVariableNode(expressionTree, expressionTree.getVariableCountMap());
        return expressionTree;
    }

    public static ExpressionTree testReadExpr(Expression E, String inputString) {
        MutableInteger index = new MutableInteger(-1); // 用于在字符序列中移动的索引
        if (inputString.isEmpty()) {
            System.out.println("Invalid input");
            return null;
        }
        char[] input = inputString.toCharArray();// 存储输入的字符序列
        ExpressionTree expressionTree = ExpressionTree.buildExpressionTree(E);
        ReadExpression(expressionTree, input, index);
        searchPutVariableNode(expressionTree, expressionTree.getVariableCountMap());
        return expressionTree;
    }

    public static ExpressionTree trigReadExpr(Expression E, String inputString) {
        MutableInteger index = new MutableInteger(-1); // 用于在字符序列中移动的索引
        if (inputString.isEmpty()) {
            System.out.println("Invalid input");
            return null;
        }
        char[] input = inputString.toCharArray();// 存储输入的字符序列
//        先遍历一遍检查有没有字母，即有没有变量出现
        for (char c : input) {
            if (Character.isAlphabetic(c)) {
                throw new VariableInTrigFucException();
            }
        }
        ExpressionTree expressionTree = ExpressionTree.buildExpressionTree(E);
        ReadExpression(expressionTree, input, index);
        searchPutVariableNode(expressionTree, expressionTree.getVariableCountMap());
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
            E.op = CONSTANT_DEFAULT_OPERATOR;
            E.value = Double.parseDouble(Character.toString(currentChar));
        } else if (currentChar == '-' && input.length == 2) {
            // 负数，构造常量表达式
            E.op = CONSTANT_DEFAULT_OPERATOR;
            E.value = -Double.parseDouble(Character.toString(input[index.increment().getValue()]));

        } else if (Character.isAlphabetic(currentChar)) {
            // 变量，构造变量表达式
            E.value = VARIABLE_DEFAULT_VALUE; // 变量的初值为0
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

    /**
     * 从哈希表中查找目标变量，将其左子树设置为三角函数名，右子树设置为三角函数的子树
     *
     * @param trigFuc       三角函数
     * @param newExpression 新表达式
     * @param targetTree    目标表达式树
     * @param targetOp      目标变量名
     */
    private static void replaceTargetNode(String trigFuc, Expression newExpression, ExpressionTree targetTree, String targetOp) {
        if (targetTree == null || newExpression == null) {
            return;
        }
        MyHashMap<String, Expression> variableCountMap = targetTree.getVariableCountMap();
        if (variableCountMap.containsKey(targetOp.toLowerCase())) {
            Expression expression = variableCountMap.get(targetOp);
            expression.right = newExpression;
            expression.left = new Expression();
            expression.left.setOp(trigFuc);
        }
    }

    /**
     * 搜索替换目标节点为相应的三角函数形式
     *
     * @param trigFuc       三角fuc
     * @param newExpression 新表达方式
     * @param targetTree    目标树
     * @param targetOp      目标op
     */
    @Deprecated
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
     * 对表达式中的变量V进行求导功能的外部接口
     *
     * @param E e
     * @param V v
     */
    public static void Diff(ExpressionTree E, String V) {
//        首先进行输入判断
        if (E == null || V == null || V.isEmpty()) {
            System.out.println("Invalid input");
            return;
        }
//        从表达式树对象的哈希表中搜索变量节点检查是否存在相应变量
        MyHashMap<String, Expression> variableCountMap = E.getVariableCountMap();
        if (!variableCountMap.containsKey(V.toLowerCase())) {
            System.out.println("Invalid variable");
            return;
        }
//        输入检查结束,对表达式树中的变量V进行有条件的求导
        MergeConst(E);
        differentiateConstant(E, V, variableCountMap);
        differentiateVariable(E, E, V);
//        differentiate(E, E.right, V);
    }

    public static void Diff2(ExpressionTree E, String V) {
        //        首先进行输入判断
        if (E == null || V == null || V.isEmpty()) {
            System.out.println("Invalid input");
            return;
        }
//        从表达式树对象的哈希表中搜索变量节点检查是否存在相应变量
        MyHashMap<String, Expression> variableCountMap = E.getVariableCountMap();
        if (!variableCountMap.containsKey(V.toLowerCase())) {
            System.out.println("Invalid variable");
            return;
        }
        MergeConst(E);
        differentiate(E, V);
        MergeConst(E);
    }

    /**
     * 对表达式中的常数和非求导变量进行求导的功能方法。在对表达式进行合并常数操作后执行这个方法，可以确保表达式树中不会存在全是常数节点的子树
     * 即表达式树中符号节点的左右子树不会同时为常数节点
     *
     * @param E e
     * @return boolean
     */
    private static boolean differentiateConstant(Expression E, String V, MyHashMap<String, Expression> variableCountMap) {
//        如果当前表达式的操作符为符号中的"*"或"/"或"^"号，则不做处理
/*        if(E.op.equals("*") || E.op.equals("/") || E.op.equals("^")){
            return false;
        }*/
        if (E == null) {
            return false;
        }
        V = V.toLowerCase();
//        递归遍历左右子树
        differentiateConstant(E.left, V, variableCountMap);
        differentiateConstant(E.right, V, variableCountMap);
//        如果当前表达式的操作符为符号中的"+"或"-"号，则将当前表达式的左右子树中的常数节点或非求导变量的值置为0
        if (E.op.equals("+") || E.op.equals("-")) {
            if (E.left.op.equals("#")) {
//                为常量
                E.left.value = 0.0;
            } else if (Character.isAlphabetic(E.left.op.charAt(0)) && !E.left.op.equals(V)) {
//                为非求导变量
                E.left.value = 0.0;
                E.left.op = "#";
                variableCountMap.remove(E.left.op);
            }

            if (E.right.op.equals("#")) {
//                为常量
                E.right.value = 0.0;
            } else if (Character.isAlphabetic(E.right.op.charAt(0)) && !E.right.op.equals(V)) {
//                为非求导变量
                E.right.value = 0.0;
                E.right.op = "#";
                variableCountMap.remove(E.right.op);
            }
            return true;
        }
        if (E.op.equals("*")) {
            if (Character.isAlphabetic(E.left.op.charAt(0)) && !E.left.op.equals(V)
                    && Character.isAlphabetic(E.right.op.charAt(0)) && !E.right.op.equals(V)) {
//                左右子树均为非求导变量
                variableCountMap.remove(E.left.op);
                variableCountMap.remove(E.right.op);
                E.left.setOp("#");
                E.right.setOp("#");
                E.left.setValue(0.0);
                E.right.setValue(0.0);
            }
        }
        return false;
    }

    /**
     * 对表达式中的偏导变量进行求导的功能方法
     *
     * @param fatherExpression 父亲表情
     * @param E                e
     * @param V                v
     * @return boolean
     */
    private static boolean differentiateVariable(Expression fatherExpression, Expression E, String V) {
        if (E == null) {
            return false;
        }
//        对于表达式树本身只有一个变量，即fatherExpression的操作符为变量名，且左右子树为空的情况进行特判
        if (fatherExpression.op.equals(V.toLowerCase()) && fatherExpression.left == null && fatherExpression.right == null && E.equals(fatherExpression)) {
            fatherExpression.op = CONSTANT_DEFAULT_OPERATOR;
            fatherExpression.value = 1.0;
            return true;
        }

//        如果当前表达式的操作符为变量名，即变量名与输入的变量名相同，则对当前表达式进行求导
//        后期会保证求导变量的父节点的右子节点只有常数，所以当左子节点为变量，父子节点为"^"时，便不再需要遍历右子树
        if (differentiateVariable(E, E.left, V)) {
            return false;
        } else {
            differentiateVariable(E, E.right, V);
        }
        if (E.op.equals(V.toLowerCase())) {
            switch (fatherExpression.op) {
                case "+":
                case "-":
                case "*":
                case "/":
                    E.op = CONSTANT_DEFAULT_OPERATOR;
                    E.value = 1.0;
                    break;
                case "^":
//                    将父表达式的操作符改为"*"
                    fatherExpression.op = "*";
//                    同时父表达式的左右子树互换
                    Expression temp = fatherExpression.left;
                    fatherExpression.left = fatherExpression.right;
                    fatherExpression.right = temp;
//                    原来的指数为现在父表达式的左子树的值
                    Double index = fatherExpression.left.value;
//                    将原来的需要求导的变量表达式，即父表达式的右子树E，替换为求导后的表达式树
                    E.op = "^";
                    E.left = new Expression(V, E.value);
                    E.right = new Expression(CONSTANT_DEFAULT_OPERATOR, index - 1);
                    return true;
                default:
                    break;
            }
        }
        return false;

    }

    public static Expression differentiate(Expression E, String V) {
        if (E == null) {
            return null;
        }
        V = V.toLowerCase();
//        递归遍历左右子树
//        表达式只有一个变量，且变量为求导变量的情况
        if (E.op.toLowerCase().equals(V) && E.left == null && E.right == null) {
            E.op = CONSTANT_DEFAULT_OPERATOR;
            E.value = 1.0;
            return E;
        } else if (E.op.equals("#")) {
//            表达式为常数的情况
            E.op = CONSTANT_DEFAULT_OPERATOR;
            E.value = 0.0;
            return E;
        }
//        表达式为运算符或其他变量时
        switch (E.op) {
            case "+":
//                (u + v)'= u' + v'
                E.op = "+";
                E.left = differentiate(E.left, V);
                E.right = differentiate(E.right, V);
                break;
            case "-":
//                (u - v)'= u' - v'
                E.op = "-";
                E.left = differentiate(E.left, V);
                E.right = differentiate(E.right, V);
                break;
            case "*":
//                (u * v)'= u' * v + u * v'
                E.op = "+";
                Expression left = new Expression("*", null, differentiate(E.left, V), new Expression(E.right));
                Expression right = new Expression("*", null, new Expression(E.left), differentiate(E.right, V));
                E.left = left;
                E.right = right;
                break;
            case "^":
//               (x ^ a)' = a * x ^ (a - 1)
                if (E.left.op.toLowerCase().equals(V)) {
                    double index = E.right.value;
                    if (index == 0) {
                        E.op = CONSTANT_DEFAULT_OPERATOR;
                        E.value = 0.0;
                        E.left = null;
                        E.right = null;
                        return E;
                    } else if (index == 1) {
                        E.op = CONSTANT_DEFAULT_OPERATOR;
                        E.value = 1.0;
                        E.left = null;
                        E.right = null;
                        return E;
                    } else {
                        E.op = "*";
                        E.left = new Expression(CONSTANT_DEFAULT_OPERATOR, index);
                        E.right = new Expression("^", null, new Expression(V, VARIABLE_DEFAULT_VALUE), new Expression(CONSTANT_DEFAULT_OPERATOR, index - 1));
                    }
                } else {
//                    非变量的情况
                    E.op = CONSTANT_DEFAULT_OPERATOR;
                    E.value = 0.0;
                    E.left = E.right = null;
                }
                break;
            default:
                E.op = CONSTANT_DEFAULT_OPERATOR;
                E.value = 0.0;
                E.left = E.right = null;
        }
        return E;
    }

    /**
     * 从表达式树中搜索变量节点，将其存入哈希表中
     *
     * @param expression       表达式
     * @param variableCountMap 哈希表
     */
    private static void searchPutVariableNode(Expression expression, MyHashMap<String, Expression> variableCountMap) {
        if (expression == null) {
            return;
        }
        if (Character.isAlphabetic(expression.getOp().charAt(0))
                && expression.getOp().length() == 1) {
            variableCountMap.put(expression.getOp(), expression);
        }
        searchPutVariableNode(expression.left, variableCountMap);
        searchPutVariableNode(expression.right, variableCountMap);
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

    public static String testWriteExpr(ExpressionTree E) {
        StringBuilder stringBuilder = new StringBuilder();
        testWriteExpression(E, stringBuilder);
        return stringBuilder.toString();
    }

    private static void testWriteExpression(Expression E, StringBuilder stringBuilder) {
        if (E != null) {
            if (E.op.equals("#")) {
                // 常量
//                System.out.print(E.value);
                stringBuilder.append(E.value);
            } else if (Character.isAlphabetic(E.op.charAt(0))
                    && E.op.length() == 1) {
                // 变量
//                System.out.print(E.op);
                stringBuilder.append(E.op);
            } else {
                // 复合表达式
//                System.out.print("(");
                stringBuilder.append("(");
                testWriteExpression(E.left, stringBuilder);
//                System.out.print(" " + E.op + " ");
                stringBuilder.append(" ").append(E.op).append(" ");
                testWriteExpression(E.right, stringBuilder);
//                System.out.print(")");
                stringBuilder.append(")");
            }
        }
    }

    /**
     * 判断字符是否是运算符
     *
     * @param c 操作符
     * @return boolean
     */
    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    private static boolean isMergeOperator(char c) {
        return c == '+' || c == '-' || c == '*';
    }

    private static boolean MergeExpression(Expression E) {
        if (E.left == null || E.right == null) {
            return false;
        }
        String ans = E.containsConstantsSon();
        if (null != ans) {
            String ERight = E.right.containsConstantsSon();
            String ELeft = E.left.containsConstantsSon();
//            表达式E的左子树有常数，右子树的左子树有常数
            if ("left".equals(ans) && "left".equals(ERight)) {
//                表达式E的计算符号,表达式E的右子树的计算符号
                String EOp = E.op;
                String ERightOp = E.right.op;
                if (EOp.equals(ERightOp)) {
                    switch (EOp) {
                        case "+":
                            E.left.value = E.left.value + E.right.left.value;
                            break;
                        case "-":
                            E.left.value = E.left.value - E.right.left.value;
                            E.op = "+";
                            break;
                        case "*":
                            E.left.value = E.left.value * E.right.left.value;
                            break;
                        default:
                            break;
                    }
                    E.right = E.right.right;
                    return true;
                }
                return true;
            } else if ("left".equals(ans) && "right".equals(ERight)) {
//                表达式E的计算符号,表达式的左子树的计算符号
                String EOp = E.op;
                String ERightOp = E.right.op;
                if (EOp.equals(ERightOp)) {
                    switch (EOp) {
                        case "+":
                            E.left.value = E.left.value + E.right.right.value;
                            break;
                        case "-":
                            E.left.value = E.left.value + E.right.right.value;
                            break;
                        case "*":
                            E.left.value = E.left.value * E.right.right.value;
                            break;
                        default:
                            break;
                    }
                    E.right = E.right.left;
                    return true;
                }
            } else if ("right".equals(ans) && "left".equals(ELeft)) {
                String EOp = E.op;
                String ELeftOp = E.left.op;
                if (EOp.equals(ELeftOp)) {
                    switch (EOp) {
                        case "+":
                            E.right.value = E.right.value + E.left.left.value;
                            E.left = E.left.right;
                            break;
                        case "-":
                            E.left.left.value = E.left.left.value - E.right.value;
                            E.right = E.left.right;
                            E.left = E.left.left;
                            break;
                        case "*":
                            E.right.value = E.right.value * E.left.left.value;
                            E.left = E.left.right;
                            break;
                        default:
                            break;
                    }
                    return true;

                }
            } else if ("right".equals(ans) && "right".equals(ELeft)) {
                String EOp = E.op;
                String ELeftOp = E.left.op;
                if (EOp.equals(ELeftOp)) {
                    switch (EOp) {
                        case "+":
                            E.right.value = E.right.value + E.left.right.value;
                            E.left = E.left.left;
                            break;
                        case "-":
                            E.right.value = E.left.right.value + E.right.value;
                            E.left = E.left.right;
                            break;
                        case "*":
                            E.right.value = E.right.value * E.left.right.value;
                            E.left = E.left.left;
                            break;
                        default:
                            break;
                    }
                    return true;
                }

            }


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
    public static boolean Assign(char V, int c, ExpressionTree E) {
        if (E == null) {
            return false;
        }
        MyHashMap<String, Expression> variableCountMap = E.getVariableCountMap();
        if (variableCountMap.containsKey(Character.toString(V).toLowerCase())) {
            Expression expression = variableCountMap.get(Character.toString(V).toLowerCase());
            expression.setValue((double) c);
//            空置左右子树，以抹除可能存在的三角函数子树
            expression.left = null;
            expression.right = null;
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
        MyHashMap<String, Expression> variableCountMap = E.getVariableCountMap();
        if (variableCountMap.containsKey(V.toLowerCase())) {
            Expression expression = variableCountMap.get(V.toLowerCase());
            expression.setValue((double) c);
//            空置左右子树，以抹除可能存在的三角函数子树
            expression.left = null;
            expression.right = null;
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
    public static boolean Assign(String V, Double c, ExpressionTree E) {
        if (E == null) {
            return false;
        }
        MyHashMap<String, Expression> variableCountMap = E.getVariableCountMap();
        if (variableCountMap.containsKey(V.toLowerCase())) {
            Expression expression = variableCountMap.get(V.toLowerCase());
            expression.setValue(c);
//            空置左右子树，以抹除可能存在的三角函数子树
            expression.left = null;
            expression.right = null;
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
        ExpressionTree newTree = trigReadExpr(new Expression(), inputString);
//        计算inputString的值
        double value = evaluatePrefixExpression(inputString);
//        如果计算结果为NaN，说明输入的表达式不合法
        if (Double.isNaN(evaluateTrigFunc(trigFunction, value))) {
            System.out.println("Invalid input");
            return false;
        }
//        将哈希表中变量的值设置为inputString的值
        Expression expression = E.getVariableCountMap().get(V.toLowerCase());
        expression.setValue(value);
//        将变量的左子树替换为操作符为三角函数名的Expression对象，右子树为inputString的Expression对象
//        searchReplaceTargetNode(trigFunction, newTree, E, V.toLowerCase());
        replaceTargetNode(trigFunction, newTree, E, V.toLowerCase());
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
            throw new ArithmeticException("Unevaluable expression.0 cannot be used as a divisor");
        }
    }


    /**
     * 计算表达式E的值的内部方法
     *
     * @param E                表达式
     * @param variableCountMap 变量哈希表
     * @return int
     */
    private static double Evaluate(Expression E, MyHashMap<String, Expression> variableCountMap) {
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
                double originValue = variableCountMap.get(E.op.toLowerCase()).getValue();
                String trigFunction = E.left.op;
                return evaluateTrigFunc(trigFunction, originValue);
            } else {
//                情况2:普通变量的情况，直接从哈希表中取值
                return variableCountMap.get(E.op.toLowerCase()).getValue();
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

    public static boolean isTrigFuc(String trigFuc) {
        if ("sin".equals(trigFuc) || "cos".equals(trigFuc) || "tan".equals(trigFuc)) {

        }
        return true;
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
        MyHashMap<String, Expression> newCountMap = MyHashMap.merge(E1.getVariableCountMap(), E2.getVariableCountMap());
        ExpressionTree expressionTree = new ExpressionTree(newCountMap);
        expressionTree.setOp(Character.toString(P));
        expressionTree.left = E1;
        expressionTree.right = E2;
        return expressionTree;
    }

    /**
     * 复合两个表达式，构建一个新表达式
     *
     * @param P  p
     * @param E1 e1
     * @param E2 e2
     * @return {@link ExpressionTree}
     */
    public static ExpressionTree CompoundExpr(String P, ExpressionTree E1, ExpressionTree E2) {
        if (E1 == null || E2 == null) {
            return null;
        }
        MyHashMap<String, Expression> newCountMap = MyHashMap.merge(E1.getVariableCountMap(), E2.getVariableCountMap());
        ExpressionTree expressionTree = new ExpressionTree(newCountMap);
        expressionTree.setOp(P.substring(0, 1));
        expressionTree.left = E1;
        expressionTree.right = E2;
        return expressionTree;
    }


    public static ExpressionTree MergeConst(ExpressionTree E) {
        if (E == null) {
            return null;
        }
//        判断头结点的左右子树是否有特殊可化简情况

        if (E.left != null && E.right != null && (E.left.op.equals("#") || E.right.op.equals("#"))) {
            switch (E.op.charAt(0)) {
                case '+':
                    if (E.left.op.equals("#") && E.left.value == 0) {
//                        头结点的左子树为常数且值为0
                        E = new ExpressionTree(E.right, E.getVariableCountMap());
                    } else if (E.right.op.equals("#") && E.right.value == 0) {
//                        头结点的右子树为常数且值为0
                        E = new ExpressionTree(E.left, E.getVariableCountMap());
                    }
                    break;
                case '-':
                    if (E.right.op.equals("#") && E.right.value == 0) {
//                            头结点的右子树为常数且值为0
                        E = new ExpressionTree(E.left, E.getVariableCountMap());
                    } else if (E.left.op.equals("#") && E.left.value == 0) {
//                            头结点的左子树为常数且值为0
//                            TODO:该情况待处理
                    }
                    break;
                case '*':
                    if ((E.left.op.equals("#") && E.left.value == 0) || (E.right.op.equals("#") && E.right.value == 0)) {
//                            头结点的左右子树之一为常数且值为0
                        E.getVariableCountMap().clear();
                        E.setOp("#");
                        E.setValue(0.0);
                    } else if (E.left.op.equals("#") && E.left.value == 1) {
//                            头结点的左子树为常数且值为1
                        E = new ExpressionTree(E.right, E.getVariableCountMap());
                    } else if (E.right.op.equals("#") && E.right.value == 1) {
//                            头结点的右子树为常数且值为1
                        E = new ExpressionTree(E.left, E.getVariableCountMap());
                    }
                    break;
                case '/':
                case '^':
                    if (E.left.op.equals("#") && E.left.value == 0) {
//                            头结点的左子树为常数且值为0
                        E.getVariableCountMap().clear();
                        E.setOp("#");
                        E.setValue(0.0);
                    } else if (E.right.op.equals("#") && E.right.value == 1) {
//                            头结点的右子树为常数且值为1
                        E = new ExpressionTree(E.left, E.getVariableCountMap());
                    }

            }
        }

        MergeConstParameter(E, E);
        return E;
    }

    /**
     * 常数合并操作MergeConst(E) –– 合并表达式E中所有常数运算。
     * 例如，对表达式E=(2+3-a)*(b+3*4)进行合并常数的操作后，求得E=(5-a)*(b+12)。
     *
     * @param E e
     */
    public static void MergeConstParameter(Expression fatherExpression, Expression E) {
        if (E == null) {
            return;
        }
        if (E.left == null && E.right == null) {
            return;
        }
        if ("#".equals(E.op) || Character.isAlphabetic(E.op.charAt(0))
                && E.op.length() == 1) {
            // 表达式为常量或变量，不需要合并
            return;
        } else {
            // 复合表达式

            // 递归合并左右子表达式
            MergeConstParameter(E, E.left);
            MergeConstParameter(E, E.right);

            // 如果左右子表达式都是常数，合并常数运算
            if ("#".equals(E.left.op) && "#".equals(E.right.op)) {
                E.value = applyOperator(E.op.charAt(0), E.left.value, E.right.value);
                E.op = "#"; // 将操作符清空，表示这是一个常数节点
                E.left = E.right = null; // 清空左右子表达式
            }
//            如果当前节点的运算符和左或右子节点中的运算符相同且为("*""-""+""/""^")中的一种，同时当前节点和其运算符相同的子节点的子节点中有一个为常数节点
            if (isMergeOperator(E.op.charAt(0)) && MergeExpression(E)) {
                System.out.println("深层合并成功");
            }
            if (E.left != null && E.right != null) {
                switch (E.op.charAt(0)) {
                    case '+':
                        if ("#".equals(E.left.op) && E.left.value == 0) {
                            //                    当前节点为"+"，左节点为常数且值为0
                            if (E.isLeftSonOf(fatherExpression)) {
                                //                            若当前节点为父节点的左子节点
                                fatherExpression.left = E.right;
                            } else {
                                //                            若当前节点为父节点的右子节点
                                fatherExpression.right = E.right;
                            }
                        } else if ("#".equals(E.right.op) && E.right.value == 0) {
                            //                        当前节点为"+"，右节点为常数且值为0
                            if (E.isLeftSonOf(fatherExpression)) {
                                //                            若当前节点为父节点的左子节点
                                fatherExpression.left = E.left;
                            } else {
                                //                            若当前节点为父节点的右子节点
                                fatherExpression.right = E.left;
                            }
                        }
                        break;
                    case '-':
                        if ("#".equals(E.right.op) && E.right.value == 0) {
//                            当前节点为"-"，右节点为常数且值为0
                            if (E.isLeftSonOf(fatherExpression)) {
//                            若当前节点为父节点的左子节点
                                fatherExpression.left = E.left;
                            } else {
//                            若当前节点为父节点的右子节点
                                fatherExpression.right = E.left;
                            }

                        } else if ("#".equals(E.left.op) && E.left.value == 0) {
//                            当前节点为"-"，左节点为常数且值为0
//                           TODO:该情况待处理

                        }
                        break;
                    case '*':
                        if ("#".equals(E.left.op) && E.left.value == 0) {
//                            当前节点为"*"，左节点为常数且值为0
                            E.op = "#";
                            E.value = 0.0;
                            E.left = E.right = null;
                        } else if ("#".equals(E.right.op) && E.right.value == 0) {
//                            当前节点为"*"，右节点为常数且值为0
                            E.op = "#";
                            E.value = 0.0;
                            E.left = E.right = null;
                        } else if ("#".equals(E.left.op) && E.left.value == 1) {
//                            当前节点为"*"，左节点为常数且值为1
                            E.op = E.right.op;
                            E.value = E.right.value;
                            E.left = E.right.left;
                            E.right = E.right.right;
                        } else if ("#".equals(E.right.op) && E.right.value == 1) {
//                            当前节点为"*"，右节点为常数且值为1
                            E.op = E.left.op;
                            E.value = E.left.value;
                            E.right = E.left.right;
                            E.left = E.left.left;
                        }
                        break;
                    case '/':
                    case '^':
                        if ("#".equals(E.left.op) && E.left.value == 0) {
                            E.op = "#";
                            E.value = 0.0;
                            E.left = E.right = null;
                        } else if ("#".equals(E.right.op) && E.right.value == 1) {
                            E.op = E.left.op;
                            E.value = E.left.value;
                            E.right = E.left.right;
                            E.left = E.left.left;
                        }
                        break;
                }
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

        //            先特判是否是负号和数字组合的负数
        if (chars.length == 2 && chars[0] == '-' && Character.isAlphabetic(chars[1])) {
            return -Double.parseDouble(String.valueOf(chars[1]));
        }
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

    public static boolean isPrefixExpression(String prefixExpression) {

        MyStack<Double> operandStack = null;
        try {
            if (prefixExpression == null || prefixExpression.isEmpty()) {
                throw new IllegalArgumentException("Input expression is null or empty.");
            }

            // 将表达式拆分为字符数组
            char[] chars = prefixExpression.toCharArray();

//            先特判是否是负号和数字组合的负数
            if (chars.length == 2 && chars[0] == '-' && Character.isAlphabetic(1)) {
                return true;
            }
            // 创建一个栈来存储操作数
            operandStack = new MyStack<>();

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
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }


    public static void printExpressionLevelOrder(Expression root) {
        if (root == null) {
            System.out.println("Expression is empty.");
            return;
        }

        Queue<Expression> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                Expression current = queue.poll();

                // 打印当前节点的值
                if (current.op.equals("#")) {
                    System.out.print(current.value);
                } else {
                    System.out.print(current.op);
                }

                // 将左右子节点加入队列
                if (current.left != null) {
                    queue.add(current.left);
                }
                if (current.right != null) {
                    queue.add(current.right);
                }

                // 添加逗号分隔符（除了最后一个节点）
                if (i < size - 1) {
                    System.out.print(", ");
                }
            }

            // 换行
            System.out.println();
        }
    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

}
