import java.util.ArrayList;

class Sequent {

    private ArrayList<Formula> antecedent;
    private ArrayList<Formula> sucedent;

    Sequent(ArrayList<Formula> antecedent, ArrayList<Formula> sucedent) {
        this.antecedent = antecedent;
        this.sucedent = sucedent;
    }

    ArrayList<Formula> getAntecedent() {
        return antecedent;
    }

    ArrayList<Formula> getSucedent() {
        return sucedent;
    }

    String print() {
        String s = "";
        for (int i = 0; i < antecedent.size() - 1; i++) {
            s += antecedent.get(i).print() + ", ";
        }
        if (antecedent.size() > 0) {
            s += antecedent.get(antecedent.size() - 1).print();
        }
        s += " |- ";
        for (int i = 0; i < sucedent.size() - 1; i++) {
            s += sucedent.get(i).print() + ", ";
        }
        if (sucedent.size() > 0) {
            s += sucedent.get(sucedent.size() - 1).print();
        }
        return s;
    }

    Pair<Sequent, Sequent> useRule() {
        boolean stepMadeFlag = false;
        Sequent leftSequent = this;
        Sequent rightSequent = null;
        for (int i = 0; i < antecedent.size(); i++) {
            if (stepMadeFlag) {
                break;
            }
            switch(antecedent.get(i).getFormulaType()) {
                case NEGATION:
                    leftSequent.sucedent.add(((Operation)(antecedent.get(i))).getLeftArg());
                    leftSequent.antecedent.remove(i);
                    stepMadeFlag = true;
                    break;
                case CONJUNCTION:
                    leftSequent.antecedent.add(((Operation)(antecedent.get(i))).getLeftArg());
                    leftSequent.antecedent.add(((Operation)(antecedent.get(i))).getRightArg());
                    leftSequent.antecedent.remove(i);
                    stepMadeFlag = true;
                    break;
                case DISJUNCTION:
                    rightSequent = new Sequent((ArrayList<Formula>)new ArrayList<Formula>(antecedent), (ArrayList<Formula>) new ArrayList<>(sucedent));
                    leftSequent.antecedent.add(((Operation)(antecedent.get(i))).getLeftArg());
                    rightSequent.antecedent.add(((Operation)(antecedent.get(i))).getRightArg());
                    leftSequent.antecedent.remove(leftSequent.antecedent.get(i));
                    rightSequent.antecedent.remove(rightSequent.antecedent.get(i));
                    stepMadeFlag = true;
                    break;
                case IMPLICATION:
                    rightSequent = new Sequent((ArrayList<Formula>)new ArrayList<Formula>(antecedent), (ArrayList<Formula>) sucedent.clone());
                    leftSequent.sucedent.add(((Operation)(antecedent.get(i))).getLeftArg());
                    rightSequent.antecedent.add(((Operation)(antecedent.get(i))).getRightArg());
                    leftSequent.antecedent.remove(leftSequent.antecedent.get(i));
                    rightSequent.antecedent.remove(rightSequent.antecedent.get(i));
                    stepMadeFlag = true;
                    break;
            }
        }

        if (stepMadeFlag) {
            return new Pair<>(leftSequent, rightSequent);
        }

        for (int i = 0; i < sucedent.size(); i++) {
            if (stepMadeFlag) {
                break;
            }
            switch(sucedent.get(i).getFormulaType()) {
                case NEGATION:
                    leftSequent.antecedent.add(((Operation)(sucedent.get(i))).getLeftArg());
                    leftSequent.sucedent.remove(leftSequent.sucedent.get(i));
                    stepMadeFlag = true;
                    break;
                case CONJUNCTION:
                    rightSequent = new Sequent((ArrayList<Formula>) new ArrayList<>(antecedent), (ArrayList<Formula>) new ArrayList<>(sucedent));
                    leftSequent.sucedent.add(((Operation)(sucedent.get(i))).getLeftArg());
                    rightSequent.sucedent.add(((Operation)(sucedent.get(i))).getRightArg());
                    leftSequent.sucedent.remove(leftSequent.sucedent.get(i));
                    rightSequent.sucedent.remove(rightSequent.sucedent.get(i));
                    stepMadeFlag = true;
                    break;
                case DISJUNCTION:
                    leftSequent.sucedent.add(((Operation)(sucedent.get(i))).getLeftArg());
                    leftSequent.sucedent.add(((Operation)(sucedent.get(i))).getRightArg());
                    leftSequent.sucedent.remove(leftSequent.sucedent.get(i));
                    stepMadeFlag = true;
                    break;
                case IMPLICATION:
                    leftSequent.antecedent.add(((Operation)(sucedent.get(i))).getLeftArg());
                    leftSequent.sucedent.add(((Operation)(sucedent.get(i))).getRightArg());
                    leftSequent.sucedent.remove(leftSequent.sucedent.get(i));
                    stepMadeFlag = true;
                    break;
            }
        }

        if (stepMadeFlag) {
            return new Pair<>(leftSequent, rightSequent);
        }
        return null;
    }

    public void deleteDuplicates() {
        for (int i = 0; i < antecedent.size(); i++) {
            for (int j = i + 1; j < antecedent.size(); j++) {
                if (antecedent.get(i) == antecedent.get(j)) {
                    antecedent.remove(antecedent.get(j));
                }
            }
        }
        for (int i = 0; i < sucedent.size(); i++) {
            for (int j = i + 1; j < sucedent.size(); j++) {
                if (sucedent.get(i) == sucedent.get(j)) {
                    sucedent.remove(sucedent.get(j));
                }
            }
        }
    }
}