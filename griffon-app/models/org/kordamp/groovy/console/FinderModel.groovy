package org.kordamp.groovy.console

import griffon.core.artifact.GriffonModel
import griffon.metadata.ArtifactProviderFor
import griffon.transform.FXObservable
import javafx.collections.ObservableList
import org.codehaus.griffon.runtime.core.artifact.AbstractGriffonModel

import static javafx.collections.FXCollections.observableArrayList

@ArtifactProviderFor(GriffonModel)
class FinderModel extends AbstractGriffonModel {
    @FXObservable String find
    @FXObservable String replace
    @FXObservable String matches
    @FXObservable boolean regex
    @FXObservable boolean matchCase
    @FXObservable boolean wrapAround

    final ObservableList<String> findItems = observableArrayList()
    final ObservableList<String> replaceItems = observableArrayList()

    void pushFind() {
        String find = getFind()
        if (!findItems.contains(find)) {
            findItems.add(find)
        }
    }

    void pushReplace() {
        String replace = getReplace()
        if (!replaceItems.contains(replace)) {
            replaceItems.add(replace)
        }
    }
}