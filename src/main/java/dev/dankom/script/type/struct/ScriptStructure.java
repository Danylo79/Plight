package dev.dankom.script.type.struct;

import dev.dankom.lexer.Token;
import dev.dankom.script.ScriptLoader;

import java.util.ArrayList;
import java.util.List;

public class ScriptStructure {
    private ScriptLoader parent;
    private final String name;
    private final List<Token> bodyTokens;
    private final List<String> bodyLexemes;

    public ScriptStructure(ScriptLoader parent, String name, List<Token> bodyTokens, List<String> bodyLexemes) {
        this.parent = parent;
        this.name = name;
        this.bodyTokens = bodyTokens;
        this.bodyLexemes = bodyLexemes;
    }

    public void run() {
        List<Token> ttrack = new ArrayList<>();
        List<String> ltrack = new ArrayList<>();
        for (int i = 0; i < bodyTokens.size(); i++) {
            if (bodyTokens.get(i) == Token.END_LINE) {
                runInside(ttrack, ltrack);
                ttrack.clear();
                ltrack.clear();
            }

            ttrack.add(bodyTokens.get(i));
            ltrack.add(bodyLexemes.get(i));
        }
    }

    public void runInside(List<Token> tokens, List<String> lexemes) {
        String methodName = null;
        List<String> pars = null;

        boolean trackingPars = false;

        for (int i = 0; i < tokens.size(); i++) {
            Token ct = tokens.get(i);
            String cl = lexemes.get(i);

            if (ct == Token.IDENTIFIER && tokens.get(i + 1) == Token.OPEN) {
                methodName = cl;
                trackingPars = true;
                pars = new ArrayList<>();
                continue;
            }

            if (ct == Token.STRING) {
                pars.add(cl.replace("\"", ""));
            }

            if (ct == Token.IDENTIFIER && trackingPars) {
                pars.add(parent.getVariableValue(cl));
            }
        }

        try {
            if (methodName.equalsIgnoreCase("print")) {
                System.out.println(pars.get(0));
            } else if (methodName.equalsIgnoreCase("info")) {
                parent.log().info(pars.get(0), pars.get(1));
            } else if (methodName.equalsIgnoreCase("error")) {
                parent.log().error(pars.get(0), pars.get(1));
            } else if (methodName.equalsIgnoreCase("test")) {
                parent.log().test(pars.get(0), pars.get(1));
            } else if (methodName.equalsIgnoreCase("fatal")) {
                parent.log().fatal(pars.get(0), pars.get(1));
            }
        } catch (IndexOutOfBoundsException e) {}
    }

    public String getName() {
        return name;
    }

    public List<Token> getBodyTokens() {
        return bodyTokens;
    }

    public List<String> getBodyLexemes() {
        return bodyLexemes;
    }

    @Override
    public String toString() {
        return "ScriptStructure{" +
                "name='" + name + '\'' +
                ", body=" + bodyTokens +
                ", bodyLexemes=" + bodyLexemes +
                '}';
    }
}
