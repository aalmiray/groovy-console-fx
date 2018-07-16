package org.kordamp.groovy.console

import de.codecentric.centerdevice.MenuToolkit
import griffon.core.artifact.GriffonView
import griffon.inject.MVCMember
import griffon.metadata.ArtifactProviderFor
import griffon.util.GriffonApplicationUtils
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.ContentDisplay
import javafx.scene.control.MenuBar
import javafx.scene.control.ToolBar
import javafx.scene.layout.Priority
import org.codehaus.griffon.runtime.javafx.artifact.AbstractJavaFXGriffonView

import javax.annotation.Nonnull
import javax.inject.Inject

@ArtifactProviderFor(GriffonView)
class ConsoleView extends AbstractJavaFXGriffonView {
    @MVCMember @Nonnull
    private FactoryBuilderSupport builder

    @MVCMember @Nonnull
    private ConsoleController controller

    @MVCMember @Nonnull
    private ConsoleModel model

    @Inject
    private CodeEditor codeEditor

    void initUI() {
        MenuBar theMenuBar = buildMenuBar()
        ToolBar theToolBar = buildToolBar()

        if (GriffonApplicationUtils.isMacOSX) {
            MenuToolkit tk = MenuToolkit.toolkit()
            theMenuBar.getMenus().add(0, tk.createDefaultApplicationMenu(application.configuration['application.title']))
            tk.setGlobalMenuBar(theMenuBar)
        }

        builder.application(title: application.configuration['application.title'],
            sizeToScene: true, centerOnScreen: true, name: 'mainWindow') {
            scene(fill: WHITE, width: 800, height: 600, stylesheets: '/org/kordamp/groovy/console/console.css') {
                b = borderPane() {
                    top {
                        vbox {
                            if (!GriffonApplicationUtils.isMacOSX) node(theMenuBar)
                            node(theToolBar)
                        }
                    }
                    center {
                        splitPane(dividerPositions: [0.5], orientation: Orientation.VERTICAL) {
                            node(codeEditor)
                            textArea(stylesheets: 'output-area', editable: false)
                        }
                    }
                    bottom {
                        anchorPane {
                            label(id: 'statusMessage', hgrow: Priority.ALWAYS, leftAnchor: 5, topAnchor: 5,
                                text: bind(model.statusMessageProperty()))
                            separator(orientation: Orientation.VERTICAL, rightAnchor: 105, topAnchor: 5)
                            label(id: 'colRow', hgrow: Priority.NEVER, prefWidth: 100, rightAnchor: 5, topAnchor: 5,
                                text: bind(model.colRowProperty()), alignment: Pos.CENTER_RIGHT)
                        }
                    }
                }

                connectActions(b, controller)
                connectMessageSource(b)
            }
        }
    }

    @Nonnull
    private MenuBar buildMenuBar() {
        builder.menuBar {
            menu(msg('org.kordamp.groovy.console.ConsoleView.menu.File.label')) {
                menuItem(newFileAction)
                menuItem(newWindowAction)
                menuItem(openAction)
                separatorMenuItem()
                menuItem(saveAction)
                menuItem(saveAsAction)
                separatorMenuItem()
                menuItem(printAction)
                separatorMenuItem()
                menuItem(quitAction)
            }

            menu(msg('org.kordamp.groovy.console.ConsoleView.menu.Edit.label')) {
                menuItem(undoAction)
                menuItem(redoAction)
                separatorMenuItem()
                menuItem(cutAction)
                menuItem(copyAction)
                menuItem(pasteAction)
                separatorMenuItem()
                menuItem(findAction)
                menuItem(findNextAction)
                menuItem(findPreviousAction)
                menuItem(replaceAction)
                separatorMenuItem()
                menuItem(selectAllAction)
                separatorMenuItem()
                menuItem(commentAction)
                menuItem(selectBlockAction)
            }

            menu(msg('org.kordamp.groovy.console.ConsoleView.menu.View.label')) {
                menuItem(clearOutputAction)
                separatorMenuItem()
                menuItem(largerFontAction)
                menuItem(smallerFontAction)
                separatorMenuItem()
                checkMenuItem(captureStdOutAction)
                checkMenuItem(captureStdErrAction)
                checkMenuItem(fullStackTracesAction)
                checkMenuItem(showScriptInOutputAction)
                checkMenuItem(visualizeScriptResultsAction)
                checkMenuItem(showToolbarAction)
                checkMenuItem(detachedOutputAction)
                checkMenuItem(autoClearOutputAction)
            }

            menu(msg('org.kordamp.groovy.console.ConsoleView.menu.History.label')) {
                menuItem(historyPrevAction)
                menuItem(historyNextAction)
            }

            menu(msg('org.kordamp.groovy.console.ConsoleView.menu.Script.label')) {
                menuItem(runAction)
                checkMenuItem(saveOnRunAction)
                menuItem(runSelectionAction)
                checkMenuItem(threadInterruptAction)
                menuItem(interruptAction)
                menuItem(compileAction)
                separatorMenuItem()
                menuItem(addClasspathJarAction)
                menuItem(addClasspathDirAction)
                menuItem(showClasspathAction)
                menuItem(clearClassloaderAction)
                separatorMenuItem()
                menuItem(inspectLastAction)
                menuItem(inspectVariablesAction)
                menuItem(inspectAstAction)
                menuItem(inspectTokensAction)
            }

            menu(msg('org.kordamp.groovy.console.ConsoleView.menu.Help.label')) {
                menuItem(aboutAction)
                menuItem(preferencesAction)
            }
        }
    }

    @Nonnull
    private ToolBar buildToolBar() {
        builder.toolBar(orientation: Orientation.HORIZONTAL) {
            button(newFileAction, contentDisplay: ContentDisplay.GRAPHIC_ONLY)
            button(openAction, contentDisplay: ContentDisplay.GRAPHIC_ONLY)
            button(saveAction, contentDisplay: ContentDisplay.GRAPHIC_ONLY)
            separator(orientation: Orientation.VERTICAL)
            button(undoAction, contentDisplay: ContentDisplay.GRAPHIC_ONLY)
            button(redoAction, contentDisplay: ContentDisplay.GRAPHIC_ONLY)
            separator(orientation: Orientation.VERTICAL)
            button(cutAction, contentDisplay: ContentDisplay.GRAPHIC_ONLY)
            button(copyAction, contentDisplay: ContentDisplay.GRAPHIC_ONLY)
            button(pasteAction, contentDisplay: ContentDisplay.GRAPHIC_ONLY)
            separator(orientation: Orientation.VERTICAL)
            button(findAction, contentDisplay: ContentDisplay.GRAPHIC_ONLY)
            button(replaceAction, contentDisplay: ContentDisplay.GRAPHIC_ONLY)
            separator(orientation: Orientation.VERTICAL)
            button(historyPrevAction, contentDisplay: ContentDisplay.GRAPHIC_ONLY)
            button(historyNextAction, contentDisplay: ContentDisplay.GRAPHIC_ONLY)
            separator(orientation: Orientation.VERTICAL)
            button(runAction, contentDisplay: ContentDisplay.GRAPHIC_ONLY)
            button(interruptAction, contentDisplay: ContentDisplay.GRAPHIC_ONLY)
            separator(orientation: Orientation.VERTICAL)
            button(clearOutputAction, contentDisplay: ContentDisplay.GRAPHIC_ONLY)
        }
    }
}