package dev.dankom.script.type.method;

import dev.dankom.script.exception.ScriptRuntimeException;
import dev.dankom.script.lexer.Lexeme;
import dev.dankom.script.lexer.Token;
import dev.dankom.script.interfaces.MemoryBoundStructure;
import dev.dankom.script.util.ScriptHelper;
import dev.dankom.util.general.ExceptionUtil;
import dev.dankom.util.general.Validation;

import java.util.ArrayList;
import java.util.List;

public class ScriptMethodCall implements MemoryBoundStructure<ScriptMethodCall> {

    private List<Integer> openBracketsPointers = new ArrayList<>();
    private List<Integer> closeBracketPointers = new ArrayList<>();
    private List<Lexeme> lexemes;

    private String method;
    private List<ScriptMethodCallParameter> currentPars = new ArrayList<>();

    private boolean isReturn = false;
    private ScriptMethod parent;

    public ScriptMethodCall(ScriptMethod parent) {
        this.parent = parent;
    }

    @Override
    public ScriptMethodCall loadToMemory(List<Lexeme> lexemes) {
        this.lexemes = lexemes;
        boolean lookingForPars = false;
        List<Lexeme> pars = new ArrayList<>();

        if (!lexemes.isEmpty()) {
            for (int i = 0; i < lexemes.size(); i++) {
                Lexeme l = lexemes.get(i);

                if (l.getToken() == Token.RETURN) {
                    isReturn = true;
                    lookingForPars = true;
                }

                if (l.getToken() == Token.IDENTIFIER && lexemes.get(i + 1).getToken() == Token.OPEN) {
                    method = l.getLexeme();
                }

                if (l.getToken() == Token.OPEN) {
                    openBracketsPointers.add(i);

                    if (openBracketsPointers.size() == 1) {
                        lookingForPars = true;
                        continue;
                    }
                }

                if (l.getToken() == Token.CLOSE) {
                    closeBracketPointers.add(i);

                    if (i == lexemes.size() - 2) {
                        lookingForPars = false;
                        addPar(new ScriptMethodCallParameter(pars));
                        pars.removeAll(pars);
                        continue;
                    }
                }

                if (l.getToken() == Token.END_LINE) {
                    lookingForPars = false;
                }

                if (l.getToken() == Token.COMMA) {
                    addPar(new ScriptMethodCallParameter(pars));
                    pars.removeAll(pars);
                    continue;
                }

                if (lookingForPars) {
                    pars.add(l);
                }
            }

            if (method == null && isReturn()) {
                method = "return";
            }
            return this;
        } else {
            return null;
        }
    }

    public void call() {
        try {
            List<String> pars = new ArrayList<>();
            for (ScriptMethodCallParameter ls : this.currentPars) {
                pars.add(ScriptHelper.getFullValue(parent.getScript(), ls.getLexemes()));
            }

            try {
                if (method.equalsIgnoreCase("return")) {
                    for (String par : pars) {
                        System.out.println(par);
                    }
                }
                if (method.equalsIgnoreCase("info")) {
                    parent.getScript().logger().info(pars.get(0), pars.get(1));
                } else if (method.equalsIgnoreCase("error")) {
                    parent.getScript().logger().error(pars.get(0), pars.get(1));
                } else if (method.equalsIgnoreCase("warning")) {
                    parent.getScript().logger().warning(pars.get(0), pars.get(1));
                } else if (method.equalsIgnoreCase("fatal")) {
                    parent.getScript().logger().fatal(pars.get(0), pars.get(1));
                } else if (method.equalsIgnoreCase("test")) {
                    parent.getScript().logger().test(pars.get(0), pars.get(1));
                } else if (method.equalsIgnoreCase("print")) {
                    System.out.println(pars.get(0));
                }
            } catch (IndexOutOfBoundsException e) {
                ExceptionUtil.throwCompactException(new ScriptRuntimeException("Not all pars are present for method call of " + method + "!", parent.getScript()));
                return;
            }

            ScriptMethod method = parent.getScript().getMethod(this.method);
            if (method != null) {
                method.call(pars);
            }
        } catch (NullPointerException e) {}
    }

    public void addPar(ScriptMethodCallParameter par) {
        if (!currentPars.contains(par)) {
            currentPars.add(par);
        }
    }

    public boolean isReturn() {
        return isReturn;
    }

    @Override
    public String toString() {
        return "ScriptMethodCall{" +
                "openBracketsPointers=" + openBracketsPointers +
                ", closeBracketPointers=" + closeBracketPointers +
                ", lexemes=" + lexemes +
                ", method='" + method + '\'' +
                ", pars=" + currentPars +
                ", isReturn=" + isReturn +
                ", parent=" + parent +
                '}';
    }

    class ScriptMethodCallParameter {
        private final List<Lexeme> lexemes = new ArrayList<>();

        public ScriptMethodCallParameter(List<Lexeme> lexemes) {
            for (Lexeme l : lexemes) {
                this.lexemes.add(l);
            }
        }

        public List<Lexeme> getLexemes() {
            return lexemes;
        }

        @Override
        public String toString() {
            return "ScriptMethodCallParameter{" +
                    "lexemes=" + lexemes +
                    '}';
        }
    }
}
