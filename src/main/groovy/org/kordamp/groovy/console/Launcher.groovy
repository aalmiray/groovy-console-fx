package org.kordamp.groovy.console

//import groovy.cli.picocli.CliBuilder
//import groovy.cli.picocli.OptionAccessor
import griffon.javafx.JavaFXGriffonApplication
//import groovy.ui.GroovyMain
//import org.codehaus.groovy.control.CompilerConfiguration
//import org.codehaus.groovy.runtime.StackTraceUtils
//import org.codehaus.groovy.runtime.StringGroovyMethods
//import org.codehaus.groovy.tools.shell.util.MessageSource

class Launcher {
     static void main(String[] args) throws Exception {
/*
        MessageSource messages = new MessageSource(Console)
        CliBuilder cli = new CliBuilder(usage: 'groovyConsole [options] [filename]', stopAtNonOption: false,
            header: messages['cli.option.header'])
        cli.with {
            _(names: ['-cp', '-classpath', '--classpath'], messages['cli.option.classpath.description'])
            h(longOpt: 'help', messages['cli.option.help.description'])
            V(longOpt: 'version', messages['cli.option.version.description'])
            pa(longOpt: 'parameters', messages['cli.option.parameters.description'])
            i(longOpt: 'indy', messages['cli.option.indy.description'])
            D(longOpt: 'define', type: Map, argName: 'name=value', messages['cli.option.define.description'])
            _(longOpt: 'configscript', args: 1, messages['cli.option.configscript.description'])
        }
        OptionAccessor options = cli.parse(args)

        if (options == null) {
            // CliBuilder prints error, but does not exit
            System.exit(22) // Invalid Args
        }

        if (options.h) {
            cli.usage()
            System.exit(0)
        }

        if (options.V) {
            System.out.println(messages.format('cli.info.version', GroovySystem.version))
            System.exit(0)
        }

        if (options.hasOption('D')) {
            options.Ds.each { k, v -> System.setProperty(k, v) }
        }

        // full stack trace should not be logged to the output window - GROOVY-4663
        java.util.logging.Logger.getLogger(StackTraceUtils.STACK_LOG_NAME).useParentHandlers = false


        def baseConfig = new CompilerConfiguration()
        String starterConfigScripts = System.getProperty("groovy.starter.configscripts", null)
        if (options.configscript || (starterConfigScripts != null && !starterConfigScripts.isEmpty())) {
            List<String> configScripts = new ArrayList<String>()
            if (options.configscript) {
                configScripts.add(options.configscript)
            }
            if (starterConfigScripts != null) {
                configScripts.addAll(StringGroovyMethods.tokenize((CharSequence) starterConfigScripts, ','))
            }
            GroovyMain.processConfigScripts(configScripts, baseConfig)
        }

        baseConfig.setParameters(options.hasOption("pa"))

        if (options.i) {
            enableIndy(baseConfig)
        }
*/        
        JavaFXGriffonApplication.main(args)
    }
/*
    private static void enableIndy(CompilerConfiguration cc) {
        cc.getOptimizationOptions().put(CompilerConfiguration.INVOKEDYNAMIC, true)
    }

    private static void disableIndy(CompilerConfiguration cc) {
        cc.getOptimizationOptions().remove(CompilerConfiguration.INVOKEDYNAMIC)
    }

    private static boolean isIndyEnabled(CompilerConfiguration cc) {
        cc.getOptimizationOptions().get(CompilerConfiguration.INVOKEDYNAMIC)
    }
*/
}