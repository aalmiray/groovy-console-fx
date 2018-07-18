package org.kordamp.groovy.console

import griffon.core.artifact.GriffonController
import griffon.core.controller.ControllerAction
import griffon.inject.MVCMember
import griffon.metadata.ArtifactProviderFor
import org.codehaus.griffon.runtime.core.artifact.AbstractGriffonController

import javax.annotation.Nonnull
import javax.inject.Inject

@ArtifactProviderFor(GriffonController)
class FinderController extends AbstractGriffonController {
    @MVCMember @Nonnull
    FinderModel model

    @Inject
    private CodeEditor codeEditor

    private TextEditingAreaFinder finder = new TextEditingAreaFinder()

    @ControllerAction
    void replaceAll() {
    }

    @ControllerAction
    void replace() {
    }

    @ControllerAction
    void replaceAndFind() {
    }

    @ControllerAction
    void findPrevious() {
        model.pushFind()
        model.matches = ''
        if (!finder.find(codeEditor,
            model.find,
            false,
            model.matchCase,
            model.wrapAround,
            model.regex)) {
            model.matches = msg(getClass().getName() + '.nomatch')
        }
    }

    @ControllerAction
    void findNext() {
        model.pushFind()
        model.setMatches('')
        if (!finder.find(codeEditor,
            model.find,
            true,
            model.matchCase,
            model.wrapAround,
            model.regex)) {
            model.matches = msg(getClass().getName() + '.nomatch')
        }
    }
}