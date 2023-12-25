package pojo;

public class Expression{
    /**
     * 操作符
     */
    public String op;

    /**
     * 操作数
     */
    public Integer value;

    /**
     * 左右子表达式
     */
    public Expression left, right;

    /**
     * 构造函数
     * @param op 操作符
     * @param value 操作数
     * @param left 左子表达式
     * @param right 右子表达式
     */
    public Expression(String op, int value, Expression left, Expression right) {
        this.op = op;
        this.value = value;
        this.left = left;
        this.right = right;
    }

    /**
     * 空构造函数
     */
    public Expression() {
        op = " ";
        value = null;
        left = null;
        right = null;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Expression getLeft() {
        return left;
    }

    public void setLeft(Expression left) {
        this.left = left;
    }

    public Expression getRight() {
        return right;
    }

    public void setRight(Expression right) {
        this.right = right;
    }
}
