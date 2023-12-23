package utils;

import org.junit.Before;
import org.junit.Test;

import pojo.Expression;

import static org.junit.jupiter.api.Assertions.*;

public class ExpressionUtilTest {
    Expression E1;
    Expression E2;
    Expression E3;
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
        ExpressionUtil.WriteExpr(E1);
        ExpressionUtil.WriteExpr(E2);
        ExpressionUtil.WriteExpr(E3);
    }
}