package org.kordamp.groovy.console

import groovy.transform.CompileStatic
import javafx.scene.control.IndexRange
import org.fxmisc.richtext.TextEditingArea

import javax.annotation.Nonnull
import javax.annotation.Nullable

import static griffon.util.GriffonNameUtils.requireNonBlank
import static java.util.Objects.requireNonNull

@CompileStatic
class TextEditingAreaFinder implements Finder<TextEditingArea> {
    private static final String ERROR_CONTROL_NULL = "Argument 'control' must not be null"
    private static final String ERROR_REPLACE_WITH_BLANK = "Argument 'toFind' must not be blank"
    private static final String ERROR_TO_FIND_BLANK = "Argument 'toFind' must not be blank"
    private static final String ERROR_TEXT_BLANK = "Argument 'text' must not be blank"

    @Override
    boolean find(
        @Nonnull TextEditingArea control,
        @Nonnull String toFind, boolean forward, boolean matchCase, boolean wrapAround, boolean regex) {
        requireNonNull(control, ERROR_CONTROL_NULL)
        requireNonBlank(toFind, ERROR_TEXT_BLANK)

        int caretPosition = control.getCaretPosition()
        IndexRange selection = control.getSelection()
        int start = forward ? Math.max(caretPosition, selection.getEnd()) : Math.min(caretPosition, selection.getStart())
        int length = control.getText().length()
        if (start == length) {
            start = forward ? 0 : start - 1
        }

        boolean found = doFind(control, toFind, forward, matchCase, regex, start)
        if (!found && wrapAround) {
            start = forward ? 0 : length - 1
            return doFind(control, toFind, forward, matchCase, regex, start)
        }

        return found
    }

    @Override
    boolean replace(
        @Nonnull TextEditingArea control,
        @Nonnull String toFind,
        @Nonnull String replaceWith, boolean forward, boolean matchCase, boolean wrapAround, boolean regex) {
        requireNonNull(control, ERROR_CONTROL_NULL)
        requireNonBlank(toFind, ERROR_TO_FIND_BLANK)
        requireNonBlank(replaceWith, ERROR_REPLACE_WITH_BLANK)

        return false
    }

    @Override
    int replaceAll(
        @Nonnull TextEditingArea control,
        @Nonnull String toFind, @Nonnull String replaceWith, boolean matchCase, boolean wrapAround, boolean regex) {
        requireNonNull(control, ERROR_CONTROL_NULL)
        requireNonBlank(toFind, ERROR_TO_FIND_BLANK)
        requireNonBlank(replaceWith, ERROR_REPLACE_WITH_BLANK)

        return 0
    }

    protected boolean doFind(
        @Nonnull TextEditingArea control,
        @Nonnull String toFind, boolean forward, boolean matchCase, boolean regex, int start) {
        String text = getText(control, start, forward)
        if (text == null || text.length() == 0) {
            return false
        }

        if (!regex) {
            int pos = nextMatchingPosition(toFind, text, forward, matchCase)
            if (pos != -1) {
                pos = forward ? start + pos : pos
                control.selectRange(pos, pos + toFind.length())
                return true
            }
        } else {

        }

        return false
    }

    @Nullable
    protected String getText(@Nonnull TextEditingArea control, int start, boolean forward) {
        if (forward) {
            return control.getText(start, control.getText().length())
        } else {
            return control.getText(0, start)
        }
    }

    protected final int nextMatchingPosition(@Nonnull String searchFor,
                                             @Nonnull String searchIn,
                                             boolean forward,
                                             boolean matchCase) {
        if (!matchCase) {
            return nextMatchingPosition0(searchFor.toLowerCase(), searchIn.toLowerCase(), forward)
        }

        return nextMatchingPosition0(searchFor, searchIn, forward)
    }

    protected final int nextMatchingPosition0(@Nonnull String searchFor,
                                              @Nonnull String searchIn,
                                              boolean goForward) {
        return goForward ? searchIn.indexOf(searchFor) : searchIn.lastIndexOf(searchFor)
    }
}
