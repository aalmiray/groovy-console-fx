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

import groovy.transform.CompileStatic

import javax.annotation.Nonnull

@CompileStatic
interface Finder<T> {
    boolean find(
        @Nonnull T control,
        @Nonnull String toFind,
        boolean forward, boolean matchCase, boolean wrapAround, boolean regex)

    boolean replace(
        @Nonnull T control,
        @Nonnull String toFind,
        @Nonnull String replaceWith,
        boolean forward, boolean matchCase, boolean wrapAround, boolean regex)

    int replaceAll(
        @Nonnull T control,
        @Nonnull String toFind,
        @Nonnull String replaceWith,
        boolean matchCase, boolean wrapAround, boolean regex)
}
