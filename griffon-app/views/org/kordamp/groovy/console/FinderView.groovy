package org.kordamp.groovy.console

import griffon.core.artifact.GriffonView
import griffon.inject.MVCMember
import griffon.metadata.ArtifactProviderFor
import griffon.util.GriffonNameUtils
import javafx.beans.binding.BooleanBinding
import javafx.beans.property.BooleanProperty
import javafx.beans.property.StringProperty
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.CheckBox
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.paint.Color
import javafx.stage.Stage
import org.codehaus.griffon.runtime.javafx.artifact.AbstractJavaFXGriffonView

import javax.annotation.Nonnull

import static griffon.javafx.beans.binding.MappingBindings.mapAsBoolean
import static griffon.javafx.beans.binding.UIThreadAwareBindings.uiThreadAwareBooleanProperty
import static griffon.javafx.beans.binding.UIThreadAwareBindings.uiThreadAwareStringProperty
import static griffon.javafx.collections.GriffonFXCollections.uiThreadAwareObservableList
import static griffon.util.GriffonNameUtils.isNotBlank

@ArtifactProviderFor(GriffonView)
class FinderView extends AbstractJavaFXGriffonView {
    @MVCMember @Nonnull
    FinderController controller
    @MVCMember @Nonnull
    FinderModel model

    @FXML private ComboBox<String> finder_findComboBox
    @FXML private ComboBox<String> finder_replaceComboBox
    @FXML private Label finder_matchesLabel
    @FXML private CheckBox finder_regexCheckBox
    @FXML private CheckBox finder_matchCaseCheckBox
    @FXML private CheckBox finder_wrapAroundCheckBox

    private ObservableList<String> uiFindItems
    private ObservableList<String> uiReplaceItems
    private StringProperty uiFind
    private StringProperty uiReplace
    private StringProperty uiMatches
    private BooleanProperty uiRegex
    private BooleanProperty uiMatchCase
    private BooleanProperty uiWrapAround

    @Override
    void initUI() {
        Stage stage = (Stage) getApplication()
            .createApplicationContainer(Collections.<String, Object> emptyMap())
        stage.setTitle('Find')
        stage.setScene(init())
        stage.sizeToScene()
        stage.setMinWidth(610)
        stage.setMinHeight(170)
        getApplication().getWindowManager().attach('finder', stage)
    }

    // build the UI
    private Scene init() {
        Parent node = loadFromFXML()

        connectActions(node, controller)
        connectMessageSource(node)
        setupBindings()

        Scene scene = new Scene(node)
        scene.setFill(Color.WHITE)
        scene.getStylesheets().add('bootstrapfx.css')
        scene.getStylesheets().add('/org/kordamp/groovy/console/console.css')

        return scene
    }

    protected void setupBindings() {
        uiFindItems = uiThreadAwareObservableList(model.getFindItems())
        uiReplaceItems = uiThreadAwareObservableList(model.getReplaceItems())
        uiFind = uiThreadAwareStringProperty(model.findProperty())
        uiReplace = uiThreadAwareStringProperty(model.replaceProperty())
        uiMatches = uiThreadAwareStringProperty(model.matchesProperty())
        uiRegex = uiThreadAwareBooleanProperty(model.regexProperty())
        uiMatchCase = uiThreadAwareBooleanProperty(model.matchCaseProperty())
        uiWrapAround = uiThreadAwareBooleanProperty(model.wrapAroundProperty())

        finder_findComboBox.setItems(uiFindItems)
        finder_replaceComboBox.setItems(uiReplaceItems)
        finder_findComboBox.valueProperty().bindBidirectional(uiFind)
        finder_replaceComboBox.valueProperty().bindBidirectional(uiReplace)
        finder_matchesLabel.textProperty().bindBidirectional(uiMatches)
        finder_regexCheckBox.selectedProperty().bindBidirectional(uiRegex)
        finder_matchCaseCheckBox.selectedProperty().bindBidirectional(uiMatchCase)
        finder_wrapAroundCheckBox.selectedProperty().bindBidirectional(uiWrapAround)

        final BooleanBinding findIsNotBlank = mapAsBoolean(uiFind, { s -> isNotBlank(s)})
        application.actionManager.actionsFor(controller).each { name, action ->
            action.toolkitAction.enabledProperty().bind(findIsNotBlank)
        }

        /*
        final BooleanBinding findIsNotBlank = mapAsBoolean(uiFind, Gri::isNotBlank)
        getApplication().getActionManager().actionsFor(controller).forEach((s, action) -> {
            JavaFXAction toolkitAction = (JavaFXAction) action.getToolkitAction()
            toolkitAction.enabledProperty().bind(findIsNotBlank)
        })
        */
    }
}
