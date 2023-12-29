package org.example.utils;

import org.example.collections.MyHashMap;
import org.junit.Before;
import org.junit.Test;

import org.example.pojo.Expression;
import org.example.pojo.ExpressionTree;

import java.util.Iterator;

public class ExpressionUtilTest {
    ExpressionTree E1;
    ExpressionTree E2;
    ExpressionTree E3;
    ExpressionTree E4;
    ExpressionTree E5;
    ExpressionTree E6;
    ExpressionTree E7;

    ExpressionTree E8;


    @Test
    @Before
    public void testReadExpr() {
        String inputString1 = "+++*3^x3*2^x2x6";
        E1 = ExpressionUtil.testReadExpr(new Expression(), inputString1);
        String inputString2 = "++*15^x2*8x";
        E2 = ExpressionUtil.testReadExpr(new Expression(), inputString2);
        String inputString3 = "+a*bc";
        E3 = ExpressionUtil.testReadExpr(new Expression(), inputString3);
        String inputString4 = "-91";
        E4 = ExpressionUtil.testReadExpr(new Expression(), inputString4);
        String inputString5 = "a";
        E5 = ExpressionUtil.testReadExpr(new Expression(), inputString5);
        String inputString6 = "-1";
        E6 = ExpressionUtil.testReadExpr(new Expression(), inputString6);
        String inputString7 = "+^x31";
        E7 = ExpressionUtil.testReadExpr(new Expression(), inputString7);
        String inputString8 = "+*3*3^x21";
        E8 = ExpressionUtil.testReadExpr(new Expression(), inputString8);

    }

    @Test
    public void testWriteExpr() {
        System.out.println("testWriteExpr:");
        ExpressionUtil.WriteExpr(E1);
        ExpressionUtil.WriteExpr(E2);
        ExpressionUtil.WriteExpr(E3);
        ExpressionUtil.WriteExpr(E7);
        System.out.println();
    }

    @Test
    public void testAssign() {
        System.out.println("testAssign:");
        System.out.println(ExpressionUtil.Assign('x', 2, E1));
        System.out.println(ExpressionUtil.Assign('x', 2, E2));
        System.out.println(ExpressionUtil.Assign('x', 2, E3));
//        要用字符串!
        System.out.println(E1.getVariableCountMap().get("x"));
        System.out.println(E2.getVariableCountMap().get("x"));
        System.out.println(E3.getVariableCountMap().get("x") + "\n");
    }

    @Test
    public void testValue() {
        System.out.println("testValue:");
        System.out.println(ExpressionUtil.Value(E1));
        System.out.println(ExpressionUtil.Value(E2));
        System.out.println(ExpressionUtil.Value(E3));
        ExpressionUtil.Assign('x', 2, E1);
        ExpressionUtil.Assign('x', 2, E2);
        ExpressionUtil.Assign('x', 2, E3);
        ExpressionUtil.Assign('b', 2, E3);
        System.out.println(ExpressionUtil.Value(E1));
        System.out.println(ExpressionUtil.Value(E2));
        System.out.println(ExpressionUtil.Value(E3) + "\n");
    }

    @Test
    public void testCompoundExpr() {
        System.out.println("testCompoundExpr:");
        ExpressionTree E4 = ExpressionUtil.CompoundExpr('+', E1, E2);
        ExpressionUtil.WriteExpr(E4);
        System.out.println(ExpressionUtil.Value(E4));
        ExpressionTree E5 = ExpressionUtil.CompoundExpr('*', E1, E2);
        ExpressionUtil.WriteExpr(E5);
        System.out.println(ExpressionUtil.Value(E5));
        ExpressionTree E6 = ExpressionUtil.CompoundExpr('-', E1, E2);
        ExpressionUtil.WriteExpr(E6);
        System.out.println(ExpressionUtil.Value(E6));
        ExpressionTree E7 = ExpressionUtil.CompoundExpr('/', E1, E2);
        ExpressionUtil.WriteExpr(E7);
        System.out.println(ExpressionUtil.Value(E7));
        ExpressionTree E8 = ExpressionUtil.CompoundExpr('^', E1, E3);
        ExpressionUtil.WriteExpr(E8);
        System.out.println(ExpressionUtil.Value(E8) + "\n");
    }

    @Test
    public void testMergeConst() {
        System.out.println("testMergeConst:");
        ExpressionTree E9 = ExpressionUtil.MergeConst(E1);
        ExpressionUtil.WriteExpr(E9);
        System.out.println(ExpressionUtil.Value(E9));

        ExpressionTree E10 = ExpressionUtil.MergeConst(E2);
        ExpressionUtil.WriteExpr(E10);
        System.out.println(ExpressionUtil.Value(E10));

        ExpressionTree E11 = ExpressionUtil.MergeConst(E3);
        ExpressionUtil.WriteExpr(E11);
        System.out.println(ExpressionUtil.Value(E11));

        ExpressionTree E12 = ExpressionUtil.MergeConst(E4);
        ExpressionUtil.WriteExpr(E12);
        System.out.println(ExpressionUtil.Value(E12));

        ExpressionTree E13 = ExpressionUtil.MergeConst(E5);
        ExpressionUtil.WriteExpr(E13);
        System.out.println(ExpressionUtil.Value(E13));

        ExpressionTree E14 = ExpressionUtil.MergeConst(E6);
        ExpressionUtil.WriteExpr(E14);
        System.out.println(ExpressionUtil.Value(E14));

        ExpressionTree E15 = ExpressionUtil.MergeConst(E8);
        ExpressionUtil.WriteExpr(E15);
        System.out.println(ExpressionUtil.Value(E15));

        System.out.println();
    }

    @Test
    public void testTrigExpr() {
        System.out.println("testTrigExpr:");
//        方法测试数据1
        ExpressionUtil.WriteExpr(E3);
        ExpressionUtil.assignTrigFunction("sin", "a", "+31", E3);
        System.out.println(ExpressionUtil.Value(E3));
//        方法测试数据2
        ExpressionUtil.WriteExpr(E3);
        ExpressionUtil.assignTrigFunction("cos", "a", "+31", E3);
        System.out.println(ExpressionUtil.Value(E3));
//        方法测试数据3
        ExpressionUtil.WriteExpr(E3);
        ExpressionUtil.assignTrigFunction("tan", "a", "+31", E3);
        ExpressionUtil.assignTrigFunction("sin", "b", "-91", E3);
//        NaN
        ExpressionUtil.assignTrigFunction("acos", "c", "2", E3);
        System.out.println(ExpressionUtil.Value(E3));
        System.out.println();
    }

    @Test
    public void testDiff() {
        System.out.println("testDiff:");
        ExpressionUtil.Diff(E1, "x");
        E1 = ExpressionUtil.MergeConst(E1);
        ExpressionUtil.WriteExpr(E1);
        System.out.println(ExpressionUtil.Value(E1));
//        ExpressionUtil.printExpressionLevelOrder(E1);
//        ExpressionUtil.printExpressionTree3(E1,0);
//        ExpressionUtil.printExpressionTree2(E1,0);
//        ExpressionUtil.printExpressionTree(E1, 0);

        ExpressionUtil.Diff(E2, "x");
        E2 = ExpressionUtil.MergeConst(E2);
        ExpressionUtil.WriteExpr(E2);
        System.out.println(ExpressionUtil.Value(E2));
//        ExpressionUtil.printExpressionTree(E2, 0);

        ExpressionUtil.Diff(E3, "a");
        E3 = ExpressionUtil.MergeConst(E3);
        ExpressionUtil.WriteExpr(E3);
        System.out.println(ExpressionUtil.Value(E3));
//        ExpressionUtil.printExpressionTree(E3, 0);

        ExpressionUtil.Diff(E7, "x");
        E7 = ExpressionUtil.MergeConst(E7);
        ExpressionUtil.WriteExpr(E7);
        System.out.println(ExpressionUtil.Value(E7));
//        ExpressionUtil.printExpressionLevelOrder(E7);
//        ExpressionUtil.printExpressionTree3(E7,0);
//        ExpressionUtil.printExpressionTree2(E7,0);
//        ExpressionUtil.printExpressionTree(E7, 0);
        System.out.println();

    }

    @Test
    public void testDiff2() {
        System.out.println("testDiff2:");
        ExpressionUtil.Diff2(E1, "x");
        ExpressionUtil.WriteExpr(E1);
        System.out.println(ExpressionUtil.Value(E1));
//        ExpressionUtil.printExpressionLevelOrder(E1);
//        ExpressionUtil.printExpressionTree3(E1,0);
//        ExpressionUtil.printExpressionTree2(E1,0);
//        ExpressionUtil.printExpressionTree(E1, 0);

        ExpressionUtil.Diff2(E2, "x");
        ExpressionUtil.WriteExpr(E2);
        System.out.println(ExpressionUtil.Value(E2));
//        ExpressionUtil.printExpressionTree(E2, 0);

        ExpressionUtil.Diff2(E3, "a");
        ExpressionUtil.WriteExpr(E3);
        System.out.println(ExpressionUtil.Value(E3));
//        ExpressionUtil.printExpressionTree(E3, 0);

        ExpressionUtil.Diff2(E7, "x");
        ExpressionUtil.WriteExpr(E7);
        System.out.println(ExpressionUtil.Value(E7));
//        ExpressionUtil.printExpressionLevelOrder(E7);
//        ExpressionUtil.printExpressionTree3(E7,0);
//        ExpressionUtil.printExpressionTree2(E7,0);
//        ExpressionUtil.printExpressionTree(E7, 0);
        System.out.println();

    }
    @Test
    public void testMyHashMap(){
        MyHashMap<String, Integer> myHashMap = new MyHashMap<>();
        myHashMap.put("a",1);
        myHashMap.put("b",2);
        myHashMap.put("c",3);
        myHashMap.put("d",4);
        myHashMap.forEach((k,v)->{
            System.out.println(k+" "+v);
        });
    }

}