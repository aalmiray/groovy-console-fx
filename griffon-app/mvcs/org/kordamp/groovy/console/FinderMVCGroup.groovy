package org.kordamp.groovy.console

import griffon.core.mvc.MVCGroup
import org.codehaus.griffon.runtime.core.mvc.AbstractTypedMVCGroup

import javax.annotation.Nonnull
import javax.inject.Named

@Named('finder')
class FinderMVCGroup extends AbstractTypedMVCGroup<FinderModel, FinderView, FinderController> {
    FinderMVCGroup(@Nonnull MVCGroup delegate) {
        super(delegate)
    }
}