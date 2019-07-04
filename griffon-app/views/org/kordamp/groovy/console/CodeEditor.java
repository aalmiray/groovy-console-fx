/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * Copyright 2018-2019 Andres Almiray.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kordamp.groovy.console;

import javafx.concurrent.Task;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeEditor extends CodeArea {
    private static final String[] KEYWORDS = new String[] {
            "abstract", "assert", "boolean", "break", "byte",
            "case", "catch", "char", "class", "const",
            "continue", "default", "do", "double", "else",
            "enum", "extends", "final", "finally", "float",
            "for", "goto", "if", "implements", "import",
            "instanceof", "int", "interface", "long", "native",
            "new", "package", "private", "protected", "public",
            "return", "short", "static", "strictfp", "super",
            "switch", "synchronized", "this", "throw", "throws",
            "transient", "try", "void", "volatile", "while",
            "def", "in", "var", "as", "null", "trait"
    };

    private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
    private static final String PAREN_PATTERN = "\\(|\\)";
    private static final String BRACE_PATTERN = "\\{|\\}";
    private static final String BRACKET_PATTERN = "\\[|\\]";
    private static final String SEMICOLON_PATTERN = "\\;";
    //private static final String STRING_PATTERN_1 = "\"([^\"\\\\]|\\\\.)*\"";
    //private static final String STRING_PATTERN_2 = "\'([^\"\\\\]|\\\\.)*\'";
    //private static final String STRING_PATTERN_3 = "\"\"\"([^\"\\\\]|\\\\.)*\"\"\"";
    //private static final String STRING_PATTERN_4 = "\'\'\'([^\"\\\\]|\\\\.)*\'\'\'";
    private static final String QUOTES = "(?ms:\"{3}.*?(?:\"{3}|\\z))|(?:\"{1}.*?(?:\"|\\Z))";
    private static final String SINGLE_QUOTES = "(?ms:'{3}(?!'{1,3}).*?(?:'{3}|\\z))|(?:'{1}.*?(?:'|\\z))";
    private static final String SLASHY_QUOTES = "(?:/[^/*].*?(?<!\\\\)/|(?ms:\\$/.*?(?:/\\$|\\z)))";
    private static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";
    private static final String ARROW_PATTERN = "(\\->)";
    private static final String ANNOTATION_PATTERN = "(@[A-Z][A-Za-z0-9_]*)";
    private static final String DECIMAL_INTEGER_LITERAL = "(?:0|[1-9](?:[_0-9]*[0-9])?)[lL]?";
    private static final String HEX_INTEGER_LITERAL = "0[xX][0-9a-fA-F](?:[0-9a-fA-F_]*[0-9a-fA-F])?";
    private static final String OCTAL_INTEGER_LITERAL = "0[0-7](?:[_0-7]*[0-7])?";
    private static final String BINARY_INTEGER_LITERAL = "0[bB][01](?:[_01]*[01])?";
    private static final String DECIMAL_FLOATING_POINT_LITERAL = "(?:0|[1-9](?:[_0-9]*[0-9])?)?\\.?[0-9](?:[_0-9]*[0-9])?(?:[eE][+-]?[0-9]+(?:[_0-9]*[0-9])?)?[fFdD]?";
    private static final String HEXADECIMAL_FLOATING_POINT_LITERAL = "0[xX](?:[0-9a-fA-F](?:[0-9a-fA-F_]*[0-9a-fA-F])?)?\\.?(?:[0-9a-fA-F_]*[0-9a-fA-F])?(?:[pP][+-]?[0-9]+(?:[_0-9]*[0-9])?)?[fFdD]?";

    private static final Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
            + "|(?<PAREN>" + PAREN_PATTERN + ")"
            + "|(?<BRACE>" + BRACE_PATTERN + ")"
            + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
            + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
            + "|(?<QUOTES>" + QUOTES + ")"
            + "|(?<SINGLEQUOTES>" + SINGLE_QUOTES + ")"
            + "|(?<SLASHYQUOTES>" + SLASHY_QUOTES + ")"
            + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
            + "|(?<ARROW>" + ARROW_PATTERN + ")"
            + "|(?<ANNOTATION>" + ANNOTATION_PATTERN + ")"
            + "|(?<NUMBER1>" + DECIMAL_INTEGER_LITERAL + ")"
            + "|(?<NUMBER2>" + HEX_INTEGER_LITERAL + ")"
            + "|(?<NUMBER3>" + OCTAL_INTEGER_LITERAL + ")"
            + "|(?<NUMBER4>" + BINARY_INTEGER_LITERAL + ")"
            + "|(?<NUMBER5>" + DECIMAL_FLOATING_POINT_LITERAL + ")"
            + "|(?<NUMBER6>" + HEXADECIMAL_FLOATING_POINT_LITERAL + ")"
    );

    @Inject @Named("defaultExecutorService")
    private ExecutorService executor;

    @PostConstruct
    private void postConstruct() {
        getStylesheets().add("code-editor");
        setParagraphGraphicFactory(LineNumberFactory.get(this));
        multiPlainChanges()
                .successionEnds(Duration.ofMillis(500))
                .supplyTask(this::computeHighlightingAsync)
                .awaitLatest(multiPlainChanges())
                .filterMap(t -> {
                    if(t.isSuccess()) {
                        return Optional.of(t.get());
                    } else {
                        t.getFailure().printStackTrace();
                        return Optional.empty();
                    }
                })
                .subscribe(this::applyHighlighting);
    }

    private Task<StyleSpans<Collection<String>>> computeHighlightingAsync() {
        String text = getText();
        Task<StyleSpans<Collection<String>>> task = new Task<StyleSpans<Collection<String>>>() {
            @Override
            protected StyleSpans<Collection<String>> call() throws Exception {
                return computeHighlighting(text);
            }
        };
        executor.execute(task);
        return task;
    }

    private void applyHighlighting(StyleSpans<Collection<String>> highlighting) {
        setStyleSpans(0, highlighting);
    }

    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        while(matcher.find()) {
            String styleClass =
                    matcher.group("KEYWORD") != null ? "keyword" :
                    matcher.group("PAREN") != null ? "paren" :
                    matcher.group("BRACE") != null ? "brace" :
                    matcher.group("BRACKET") != null ? "bracket" :
                    matcher.group("SEMICOLON") != null ? "semicolon" :
                    matcher.group("QUOTES") != null ? "quotes" :
                    matcher.group("SINGLEQUOTES") != null ? "single-quotes" :
                    matcher.group("SLASHYQUOTES") != null ? "slashy-quotes" :
                    matcher.group("COMMENT") != null ? "comment" :
                    matcher.group("ARROW") != null ? "brace" :
                    matcher.group("ANNOTATION") != null ? "annotation" :
                    matcher.group("NUMBER1") != null ? "number" :
                    matcher.group("NUMBER2") != null ? "number" :
                    matcher.group("NUMBER3") != null ? "number" :
                    matcher.group("NUMBER4") != null ? "number" :
                    matcher.group("NUMBER5") != null ? "number" :
                    matcher.group("NUMBER6") != null ? "number" :
                    null; /* never happens */ assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }
}
