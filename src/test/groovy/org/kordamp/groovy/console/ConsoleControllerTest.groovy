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

import griffon.core.artifact.ArtifactManager
import griffon.core.test.GriffonUnitRule
import griffon.core.test.TestFor
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

import javax.inject.Inject

import static org.awaitility.Awaitility.await

@TestFor(ConsoleController)
class ConsoleControllerTest {
    static {
        System.setProperty('org.slf4j.simpleLogger.defaultLogLevel', 'trace')
        // force initialization JavaFX Toolkit
        new javafx.embed.swing.JFXPanel()
    }

    @Inject
    private ArtifactManager artifactManager

    private ConsoleController controller

    @Rule
    public final GriffonUnitRule griffon = new GriffonUnitRule()

    @Ignore
    @Test
    void executeClickAction() {
        // given:
        controller.model = artifactManager.newInstance(ConsoleModel)

        // when:
        controller.invokeAction('click')
        await().until { controller.model.clickCount != "0" }

        // then:
        assert "1" == controller.model.clickCount
    }
}
