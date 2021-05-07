package dev.dankom.script.interfaces;

import dev.dankom.script.exception.ScriptRuntimeException;
import dev.dankom.lexer.Lexeme;

import java.util.List;

public interface MemoryBoundStructure<T> {
    T loadToMemory(List<Lexeme> lexemes) throws ScriptRuntimeException;
    void unload();
}
