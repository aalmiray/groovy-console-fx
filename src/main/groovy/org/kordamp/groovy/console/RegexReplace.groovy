package org.kordamp.groovy.console

import groovy.transform.Canonical
import groovy.transform.CompileStatic

@CompileStatic
@Canonical
class RegexReplace {
    final String matchedText
    final int startIndex
    final int endIndex
    final String replacement
}
