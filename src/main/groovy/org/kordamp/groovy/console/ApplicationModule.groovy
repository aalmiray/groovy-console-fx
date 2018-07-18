package org.kordamp.groovy.console

import griffon.core.event.EventHandler
import griffon.core.injection.Module
import griffon.core.mvc.MVCGroupManager
import griffon.inject.DependsOn
import org.codehaus.griffon.runtime.core.injection.AbstractModule
import org.kordamp.griffon.runtime.core.mvc.TypedMVCGroupManager
import org.kordamp.jipsy.ServiceProviderFor

@ServiceProviderFor(Module)
@DependsOn('groovy')
class ApplicationModule extends AbstractModule {
    @Override
    protected void doConfigure() {
        bind(EventHandler)
            .to(ApplicationEventHandler)
            .asSingleton()

        bind(CodeEditor)
            .asSingleton()

        bind(MVCGroupManager)
            .to(TypedMVCGroupManager)
            .asSingleton()
    }
}