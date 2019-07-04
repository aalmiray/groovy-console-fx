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