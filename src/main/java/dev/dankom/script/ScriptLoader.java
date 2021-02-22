package dev.dankom.script;

import dev.dankom.lexer.Lexer;
import dev.dankom.lexer.Token;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ScriptLoader {

    private Lexer lexer;

    private final List<Token> tokens = new ArrayList<>();
    private final List<String> lexemes = new ArrayList<>();

    private final List<ScriptVariable> variables = new ArrayList<>();

    public void loadFromResource(String pathToFile) {
        if (!pathToFile.contains(".plight")) {
            pathToFile += ".plight";
        }
        loadFile(new File(ScriptLoader.class.getClassLoader().getResource("scripts/" + pathToFile).getPath()));
    }

    public void loadFile(File file) {
        lexer = new Lexer(file);

        while (!lexer.isExhausted()) {
            tokens.add(lexer.currentToken());
            lexemes.add(lexer.currentLexeme());
            lexer.next();
        }

        findVars();

        for (ScriptVariable s : variables) {
            System.out.println(s.toString());
        }
    }

    public void findVars() {
        List<Token> importantTokens = null;
        List<String> importantLexemes = null;
        for (int i = 0; i < tokens.size(); i++) {
            Token t = tokens.get(i);
            String l = lexemes.get(i);
            if (importantTokens == null && t == Token.DEFINE) {
                importantTokens = new ArrayList<>();
                importantLexemes = new ArrayList<>();
                importantTokens.add(t);
                importantLexemes.add(l);
            } else if (importantTokens != null && t == Token.END_LINE) {
                importantTokens.add(t);

                String name = "";
                String type = "";
                String value = "";
                for (int j = 0; j < importantTokens.size(); j++) {
                    try {
                        Token it = importantTokens.get(j);
                        if (it == Token.IDENTIFIER && importantTokens.get(j - 1) == Token.OPEN) {
                            name = importantLexemes.get(j);
                        } else if (it == Token.IDENTIFIER && importantTokens.get(j - 1) == Token.COMMA && importantTokens.get(j + 1) == Token.COMMA) {
                            type = lexemes.get(j);
                        } else if (it == Token.COMMA && importantTokens.get(j + 1) == Token.IDENTIFIER && importantTokens.get(j + 2) == Token.CLOSE) {
                            value = importantLexemes.get(j + 1);
                            variables.add(new ScriptVariable(name, type, value));
                            break;
                        }
                    } catch (IndexOutOfBoundsException e) {
                        variables.add(new ScriptVariable(name, type, value));
                        break;
                    }
                }

                importantTokens = null;
                continue;
            } else if (importantTokens != null) {
                importantTokens.add(t);
                importantLexemes.add(l);
            }
        }
    }

    public static void main(String[] args) {
        ScriptLoader sl = new ScriptLoader();
        sl.loadFromResource("test");
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public List<String> getLexemes() {
        return lexemes;
    }
}
