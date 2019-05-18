public class Operation implements Formula {

    private Formula leftArg;
    private Formula rightArg;
    private FormulaType formulaType;

    Operation(Formula leftArg) {
        this.leftArg = leftArg;
        this.formulaType = FormulaType.NEGATION;
    }

    public FormulaType getFormulaType() {
        return formulaType;
    }

    Formula getLeftArg() {
        return leftArg;
    }

    Formula getRightArg() {
        return rightArg;
    }

    Operation(Formula leftArg, Formula rightArg, FormulaType formulaType) {
        this.leftArg = leftArg;
        this.rightArg = rightArg;
        this.formulaType = formulaType;
    }

    @Override
    public String print() {
        switch (formulaType) {
            case CONJUNCTION:
                return "(" + leftArg.print() + " ^ " + rightArg.print() + ")";
            case DISJUNCTION:
                return "(" + leftArg.print() + " v " + rightArg.print() + ")";
            case IMPLICATION:
                return "(" + leftArg.print() + " -> " + rightArg.print() + ")";
            case NEGATION:
                return "!" + leftArg.print();
        }
        return "";
    }
}

