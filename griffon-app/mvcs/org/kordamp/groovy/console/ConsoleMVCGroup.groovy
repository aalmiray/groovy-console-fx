package org.kordamp.groovy.console

import griffon.core.mvc.MVCGroup
import org.codehaus.griffon.runtime.core.mvc.AbstractTypedMVCGroup

import javax.annotation.Nonnull
import javax.inject.Named

@Named('console')
class ConsoleMVCGroup extends AbstractTypedMVCGroup<ConsoleModel, ConsoleView, ConsoleController> {
    ConsoleMVCGroup(@Nonnull MVCGroup delegate) {
        super(delegate)
    }
}