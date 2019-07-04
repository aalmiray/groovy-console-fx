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

@ArtifactProviderFor(GriffonController)
class ConsoleController extends AbstractGriffonController {
    @MVCMember @Nonnull
    private ConsoleModel model

    // -- File

    @ControllerAction
    void newFile() {}

    @ControllerAction
    void newWindow() {}

    @ControllerAction
    void open() {}

    @ControllerAction
    void save() {}

    @ControllerAction
    void saveAs() {}

    @ControllerAction
    void print() {}

    @ControllerAction
    void quit() {}

    // -- Edit

    @ControllerAction
    void undo() {}

    @ControllerAction
    void redo() {}

    @ControllerAction
    void cut() {}

    @ControllerAction
    void copy() {}

    @ControllerAction
    void paste() {}

    @ControllerAction
    void find() {
        if (application.mvcGroupManager.findTypedGroup('finder') == null) {
            createMVCGroup(FinderMVCGroup)
        }
        application.windowManager.show('finder')
    }

    @ControllerAction
    void findNext() {}

    @ControllerAction
    void findPrevious() {}

    @ControllerAction
    void replace() {}

    @ControllerAction
    void selectAll() {}

    @ControllerAction
    void comment() {}

    @ControllerAction
    void selectBlock() {}

    // -- View

    @ControllerAction
    void clearOutput() {}

    @ControllerAction
    void largerFont() {}

    @ControllerAction
    void smallerFont() {}

    @ControllerAction
    void captureStdOut() {}

    @ControllerAction
    void captureStdErr() {}

    @ControllerAction
    void fullStackTraces() {}

    @ControllerAction
    void showScriptInOutput() {}

    @ControllerAction
    void visualizeScriptResults() {}

    @ControllerAction
    void showToolbar() {}

    @ControllerAction
    void detachedOutput() {}

    @ControllerAction
    void autoClearOutput() {}

    // -- History

    @ControllerAction
    void historyPrev() {}

    @ControllerAction
    void historyNext() {}

    // -- Script

    @ControllerAction
    void run() {}

    @ControllerAction
    void saveOnRun() {}

    @ControllerAction
    void runSelection() {}

    @ControllerAction
    void threadInterrupt() {}

    @ControllerAction
    void interrupt() {}

    @ControllerAction
    void compile() {}

    @ControllerAction
    void addClasspathJar() {}

    @ControllerAction
    void addClasspathDir() {}

    @ControllerAction
    void showClasspath() {}

    @ControllerAction
    void clearClassloader() {}

    @ControllerAction
    void inspectLast() {}

    @ControllerAction
    void inspectVariables() {}

    @ControllerAction
    void inspectAst() {}

    @ControllerAction
    void inspectTokens() {}

    // -- Help

    @ControllerAction
    void about() {}

    @ControllerAction
    void preferences() {}
}