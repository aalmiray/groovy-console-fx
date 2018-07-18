application {
    title = 'GroovyConsole'
    startupGroups = ['console']
    autoShutdown = true
}
mvcGroups {
    'console' {
        model      = 'org.kordamp.groovy.console.ConsoleModel'
        view       = 'org.kordamp.groovy.console.ConsoleView'
        controller = 'org.kordamp.groovy.console.ConsoleController'
    }
    'finder' {
        model      = 'org.kordamp.groovy.console.FinderModel'
        view       = 'org.kordamp.groovy.console.FinderView'
        controller = 'org.kordamp.groovy.console.FinderController'
    }
}