import java.util.HashSet;
import java.util.Set;

class Parser {

    private Set<String> variables;

    Parser() {
        variables = new HashSet<>();
    }

    Set<String> getVariables() {
        return variables;
    }

    Formula parse(String input) {
        int parenthesesDepth = 0;
        for (int i = 0; i < input.length(); i++) {
            switch (input.charAt(i)) {
                case '(':
                    parenthesesDepth++;
                    break;
                case ')':
                    parenthesesDepth--;
                    break;
                case '^':
                    if (parenthesesDepth == 1) {
                        return new Operation(parse(input.substring(1, i - 1)), parse(input.substring(i + 2, input.length() - 1)), FormulaType.CONJUNCTION);
                    }
                    break;
                case '>':
                    if (parenthesesDepth == 1) {
                        return new Operation(parse(input.substring(1, i - 2)), parse(input.substring(i + 2, input.length() - 1)), FormulaType.IMPLICATION);
                    }
                    break;
                case 'v':
                    if (parenthesesDepth == 1) {
                        return new Operation(parse(input.substring(1, i - 1)), parse(input.substring(i + 2, input.length() - 1)), FormulaType.DISJUNCTION);
                    }
                    break;
                case '!':
                    if (parenthesesDepth == 1) {
                        return new Operation(parse(input.substring(2, input.length() - 1)));
                    }
                    break;
            }
        }
        variables.add(input);
        return new Variable(input);
    }
}
