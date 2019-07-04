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