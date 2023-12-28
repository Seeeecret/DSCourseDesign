package pojo;

public class Expression{
    /**
     * 操作符
     */
    public String op;

    /**
     * 操作数
     */
    public Double value;

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
    public Expression(String op, Double value, Expression left, Expression right) {
        this.op = op;
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public Expression(Expression E) {
        this.op = E.op;
        this.value = E.value;
        this.left = E.left;
        this.right = E.right;
    }

    /**
     * 左右子树置空的构造函数
     * @param op 操作符
     * @param value 操作数
     */
    public Expression(String op, Double value) {
        this.op = op;
        this.value = value;
        this.left = null;
        this.right = null;
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

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
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

    public String containsConstantsSon() {
//        左边有常数,右边没有
        if (this.left != null && "#".equals(this.left.op) && this.right != null && !"#".equals(this.right.op)) {
            return "left";
        }
//        右边有常数,左边没有
        else if (this.right != null && "#".equals(this.right.op) && this.left != null && !"#".equals(this.left.op)) {
            return "right";
        }
        return null;
    }

    public boolean isRightSonOf(Expression E) {
        return this == E.right;
    }
    public boolean isLeftSonOf(Expression E) {
        return this == E.left;
    }
    @Override
    public String toString() {
        return "Expression:" +
                "op='" + op + '\'' +
                ", value=" + value;
    }
}
