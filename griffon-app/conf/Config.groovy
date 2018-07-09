application {
    title = 'GroovyConsole'
    startupGroups = ['console']
    autoShutdown = true
}
mvcGroups {
    // MVC Group for "console"
    'console' {
        model      = 'org.kordamp.groovy.console.ConsoleModel'
        view       = 'org.kordamp.groovy.console.ConsoleView'
        controller = 'org.kordamp.groovy.console.ConsoleController'
    }
}