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
package org.kordamp.griffon.runtime.core.mvc

import griffon.core.ApplicationClassLoader
import griffon.core.GriffonApplication
import griffon.core.mvc.MVCGroup
import griffon.core.mvc.TypedMVCGroup
import griffon.exceptions.MVCGroupInstantiationException
import griffon.util.Instantiator
import org.codehaus.griffon.runtime.groovy.mvc.GroovyAwareMVCGroupManager

import javax.annotation.Nonnull
import javax.annotation.Nullable
import javax.inject.Inject
import java.lang.reflect.Constructor

//@CompileStatic
class TypedMVCGroupManager extends GroovyAwareMVCGroupManager {
    @Inject
    TypedMVCGroupManager(
        @Nonnull GriffonApplication application,
        @Nonnull ApplicationClassLoader applicationClassLoader,
        @Nonnull Instantiator instantiator) {
        super(application, applicationClassLoader, instantiator)
    }

    @Nullable
    <MVC extends TypedMVCGroup> MVC findTypedGroup(@Nonnull String mvcId) {
        return (MVC) findGroup(mvcId)
    }

    @Override
    protected void removeGroup(@Nonnull MVCGroup group) {
        super.removeGroup(group)
        if (group instanceof TypedMVCGroup) {
            groups.remove(group.delegate())
        }
    }

    @Override
    protected <MVC extends TypedMVCGroup> MVC typedMvcGroup(
        @Nonnull Class<? extends MVC> mvcType, @Nonnull MVCGroup mvcGroup) {
        try {
            Constructor<? extends MVC> constructor = mvcType.getDeclaredConstructor(MVCGroup)
            MVC group = constructor.newInstance(mvcGroup)
            addGroup(group)
            return group
        } catch (Exception e) {
            throw new MVCGroupInstantiationException('Unexpected error', mvcGroup.mvcType, mvcGroup.mvcId, e)
        }
    }
}
