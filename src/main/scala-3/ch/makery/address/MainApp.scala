package ch.makery.address

import javafx.fxml.FXMLLoader
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes.* //Do auto conversion. Option is scala but root is javafx so need A.C
import javafx.scene as jfxs
// as is making it easier to write when u refer to jfxs you're saying javafx.scene

object MainApp extends JFXApp3:

  //Window Root Pane
  //Option: Optional object set to NONE (Singleton object no value). Optional can fill or not also ok
  var roots: Option[scalafx.scene.layout.BorderPane] = None

  override def start(): Unit =
    // transform path of RootLayout.fxml to URI for resource location.
    val rootResource = getClass.getResource("view/RootLayout.fxml")
    // initialize the loader object.
    val loader = new FXMLLoader(rootResource)
    // Load root layout from fxml file.
    loader.load() // loader read the fxml construct all the objects in the loader

    // retrieve the root component BorderPane from the FXML
    roots = Option(loader.getRoot[jfxs.layout.BorderPane])

    stage = new PrimaryStage():
      title = "AddressApp"
      scene = new Scene():
        root = roots.get //Scene has a root property and calling the setter

    // call to display PersonOverview when app start
    showPersonOverview() // if no call only borderPane showed
  // actions for display person overview window
  def showPersonOverview(): Unit =
    val resource = getClass.getResource("view/PersonOverview.fxml")
    val loader = new FXMLLoader(resource)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.get.center = roots