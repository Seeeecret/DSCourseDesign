package utils;

import org.junit.jupiter.api.Test;

import pojo.Expression;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionUtilTest {
    Expression E = new Expression();
    @Test
    void testReadExpr() {
        ExpressionUtil.ReadExpr(E);
    }
    @Test
    void testWriteExpr() {
        ExpressionUtil.WriteExpr(E);
    }
}