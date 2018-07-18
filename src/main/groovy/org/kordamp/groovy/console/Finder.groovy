package org.kordamp.groovy.console

import groovy.transform.CompileStatic

import javax.annotation.Nonnull

@CompileStatic
interface Finder<T> {
    boolean find(
        @Nonnull T control,
        @Nonnull String toFind,
        boolean forward, boolean matchCase, boolean wrapAround, boolean regex)

    boolean replace(
        @Nonnull T control,
        @Nonnull String toFind,
        @Nonnull String replaceWith,
        boolean forward, boolean matchCase, boolean wrapAround, boolean regex)

    int replaceAll(
        @Nonnull T control,
        @Nonnull String toFind,
        @Nonnull String replaceWith,
        boolean matchCase, boolean wrapAround, boolean regex)
}
