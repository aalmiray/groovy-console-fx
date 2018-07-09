package org.kordamp.groovy.console

import griffon.core.artifact.GriffonModel
import griffon.metadata.ArtifactProviderFor
import griffon.transform.FXObservable
import org.codehaus.griffon.runtime.core.artifact.AbstractGriffonModel

@ArtifactProviderFor(GriffonModel)
class ConsoleModel extends AbstractGriffonModel {
    @FXObservable String clickCount = "0"
}