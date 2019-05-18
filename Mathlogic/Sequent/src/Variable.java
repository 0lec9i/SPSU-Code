public class Variable implements Formula {

    private String variable;
    private FormulaType formulaType = FormulaType.VARIABLE;

    Variable(String data) {
        this.variable = data;
    }

    public boolean equals(Object var) {
        if (var instanceof Variable) {
            return variable.equals(((Variable) var).variable);
        } else {
            return ((Object) this).equals(var);
        }

    }

    public String print() {
        return variable;
    }

    public FormulaType getFormulaType() {
        return formulaType;
    }
}
