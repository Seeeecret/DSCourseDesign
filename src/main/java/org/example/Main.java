package org.example;

import org.example.pojo.Expression;
import org.example.utils.ExpressionUtil;

/*题目9  表达式类型的实现（难度系数：1.2）
[问题描述]
    一个表达式和一棵二叉树之间，存在着自然的对应关系。写一个程序，实现基于二叉树表示的算术表达式Expression的操作。
[基本要求]
假设算术表达式Expression内可以含有变量（a~z）、常量（0~9）和二元运算符（+,-,*,/,^(乘幂)）。实现以下操作：
    （1）ReadExpr(E) -- 以字符序列的形式输入语法正确的前缀表示式并构造表达式E。
    （2）WriteExpr(E) -- 用带括弧的中缀表示式输出表达式E。
    （3）Assign(V,c) -- 实现对变量V的赋值（V = c），变量的初值为0。
（4）Value(E) –– 对算术表达式E求值。
 (5) CompoundExpr(P, E1, E2)  构造一个新的复合表达式 (E1)P(E2)。
[测试数据]
（1）	分别输入0;  a;  -91;  +a*bc;  +*15^x2*8x;  +++*3^x3*2^x2x6 并输出。
（2）	每当输入一个表达式后，对其中的变量赋值，然后对表达式求值。
[实现提示]
    （1）在读入表达式的字符序列的同时，完成运算符和运算数（整数）的识别处理，以及相应的运算。
（2）在识别出运算数的同时，要将其字符形式转换成整数形式。
（3）	用后根遍历的次序对表达式求值。
（4）	用中缀表示输出表达式E时，适当添加括号，以正确反映运算的优先次序。
[扩展要求]
（1）增加求偏导数运算Diff(E,V) –– 求表达式E对变量V的导数。
（2）在表达式中添加三角函数等初等函数的操作。
（3）增加常数合并操作MergeConst(E) –– 合并表达式E中所有常数运算。例如，对表达式E=(2+3-a)*(b+3*4)进行合并常数的操作后，求得E=(5-a)*(b+12)。


*/
public class Main {
    public static void main(String[] args) {

        while (true) {
            Expression E = ExpressionUtil.ReadExpr(new Expression());
            ExpressionUtil.WriteExpr(E);
        }
    }
}