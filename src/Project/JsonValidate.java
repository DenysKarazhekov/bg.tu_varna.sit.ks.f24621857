package Project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonValidate extends BaseCase {

    private String json;
    private int pos;
    private int line;
    private int col;

    public JsonValidate(CommandProcessor cp) {
        super(cp);
    }

    @Override
    public boolean execute(String input, String[] parts) {

        if (parts.length > 1) {
            System.out.println("Error: validate command does not take arguments");
            return true;
        }

        try {
            json = Files.readString(Path.of(cp.getCurrentFilePath()));
            pos = 0;
            line = 1;
            col = 1;

            skipWhitespace();
            validateValue();
            skipWhitespace();

            if (pos < json.length()) {
                throw new JsonException("Unexpected character '" + json.charAt(pos) + "' at line " + line + ", col " + col);
            }

            System.out.println("JSON is valid");

        } catch (JsonException e) {
            System.out.println("Invalid JSON: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return true;
    }

    private void validateValue() {
        if (pos >= json.length()) {
            throw new JsonException("Unexpected end at line " + line);
        }
        char c = json.charAt(pos);
        if (c == '{') {
            validateObject();
        }
        else if (c == '[') {
            validateArray();
        }
        else if (c == '"') {
            validateString();
        }
        else if (c == 't') {
            expect("true");
        }
        else if (c == 'f') {
            expect("false");
        }
        else if (c == 'n') {
            expect("null");
        }
        else if (c == '-' || Character.isDigit(c)) {
            validateNumber();
        }
        else {
            throw new JsonException("Unexpected character '" + c + "' at line " + line + ", col " + col);
        }
    }

    private void validateObject() {
        int startLine = line;
        advance();
        skipWhitespace();
        if (pos < json.length() && json.charAt(pos) == '}') {
            advance();
            return;
        }
        while (true) {
            skipWhitespace();
            if (pos >= json.length()) {
                throw new JsonException("Unclosed object started at line " + startLine + ". Fix: add '}'");
            }
            if (json.charAt(pos) != '"') {
                throw new JsonException("Unclosed object started at line " + startLine + ". Fix: add '}' at end of line " + startLine);
            }
            validateString();
            skipWhitespace();
            if (pos >= json.length() || json.charAt(pos) != ':') {
                throw new JsonException("Missing ':' at line " + line + ", col " + col + ". Fix: add ':' after key");
            }
            advance();
            skipWhitespace();
            validateValue();
            skipWhitespace();
            if (pos >= json.length()) {
                throw new JsonException("Unclosed object started at line " + startLine + ". Fix: add '}'");
            }
            char c = json.charAt(pos);
            if (c == '}') {
                advance();
                return;
            }
            if (c != ',') {
                throw new JsonException("Unclosed object started at line " + startLine + ". Fix: add '}' at end of line " + (line - 1));
            }
            advance();
            skipWhitespace();
        }
    }

    private void validateArray() {
        advance();
        skipWhitespace();
        if (pos < json.length() && json.charAt(pos) == ']') {
            advance();
            return;
        }
        while (true) {
            skipWhitespace();
            validateValue();
            skipWhitespace();
            if (pos >= json.length()) {
                throw new JsonException("Unclosed array, missing ']' at line " + line);
            }
            char c = json.charAt(pos);
            if (c == ']') {
                advance();
                return;
            }
            if (c != ',') {
                throw new JsonException("Expected ',' or ']' at line " + line + ", col " + col + ". Fix: add ',' between elements");
            }
            advance();
            skipWhitespace();
        }
    }



    private void validateString() {
        advance();
        while (pos < json.length()) {
            char c = json.charAt(pos);
            if (c == '\\') {
                advance();
                advance();
                continue;
            }
            if (c == '"') {
                advance();
                return;
            }
            advance();
        }
        throw new JsonException("Unclosed string at line " + line + ". Fix: add closing '\"'");
    }

    private void validateNumber() {
        int start = pos;
        if (json.charAt(pos) == '-') {
            advance();
        }
        while (pos < json.length() && (Character.isDigit(json.charAt(pos)) || "eE+-.".indexOf(json.charAt(pos)) >= 0)) advance();
        try {
            Double.parseDouble(json.substring(start, pos));
        }
        catch (NumberFormatException e) {
            throw new JsonException("Invalid number at line " + line);
        }
    }

    private void expect(String word) {
        if (!json.startsWith(word, pos)) {
            throw new JsonException("Expected '" + word + "' at line " + line + ", col " + col);
        }
        for (int i = 0; i < word.length(); i++) {
            advance();
        }
    }

    private void advance() {
        if (pos < json.length() && json.charAt(pos) == '\n') {
            line++;
            col = 1;
        }
        else {
            col++;
        }
        pos++;
    }

    private void skipWhitespace() {
        while (pos < json.length() && Character.isWhitespace(json.charAt(pos))) {
            advance();
        }
    }
}