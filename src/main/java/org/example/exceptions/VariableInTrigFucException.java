package org.example.exceptions;

/**
 * 变量出现在三角函数中的异常
 *
 * @author Secret
 * @date 2023/12/28
 */
public class VariableInTrigFucException extends RuntimeException{
    public VariableInTrigFucException() {
        super("Variable can't be in trigonometric function!");
    }
}
