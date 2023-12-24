package utils;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import pojo.Expression;
import pojo.ExpressionTree;

import static org.junit.jupiter.api.Assertions.*;

public class ExpressionUtilTest {
    ExpressionTree E1;
    ExpressionTree E2;
    ExpressionTree E3;
    @Test
    @Before
    public void testReadExpr() {
        String inputString1 = "+++*3^x3*2^x2x6";
        E1 = ExpressionUtil.testReadExpr(new Expression(),inputString1);
        String inputString2 = "++*15^x2*8x";
        E2 = ExpressionUtil.testReadExpr(new Expression(),inputString2);
        String inputString3 = "+a*bc";
        E3 = ExpressionUtil.testReadExpr(new Expression(),inputString3);
    }
    @Test
    public void testWriteExpr() {
        System.out.println("testWriteExpr");
        ExpressionUtil.WriteExpr(E1);
        ExpressionUtil.WriteExpr(E2);
        ExpressionUtil.WriteExpr(E3);
    }
    @Test
    public void testAssign() {
        System.out.println("testAssign");
        System.out.println(ExpressionUtil.Assign('x', 2, E1));
        System.out.println(ExpressionUtil.Assign('x', 2, E2));
        System.out.println(ExpressionUtil.Assign('x', 2, E3));
//        要用字符串!
        System.out.println(E1.getVariableCountMap().get("x"));
        System.out.println(E2.getVariableCountMap().get("x"));
        System.out.println(E3.getVariableCountMap().get("x"));
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
        System.out.println(ExpressionUtil.Value(E3));
    }

}