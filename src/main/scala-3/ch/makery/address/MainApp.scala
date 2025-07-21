package ch.makery.address

import ch.makery.address.model.Person
import ch.makery.address.view.PersonEditDialogController
import javafx.fxml.FXMLLoader
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes.*
import javafx.scene as jfxs
import scalafx.beans.property.StringProperty
import scalafx.collections.ObservableBuffer
import scalafx.stage.{Modality, Stage}
// as is making it easier to write when u refer to jfxs you're saying javafx.scene

object MainApp extends JFXApp3:

  //Window Root Pane
  //Option: Optional object set to NONE (Singleton object no value). Optional can fill or not also ok
  var roots: Option[scalafx.scene.layout.BorderPane] = None
  var cssResource = getClass.getResource("view/DarkTheme.css")
  /**
   * The data as an observable list of Persons.
   */
  val personData = new ObservableBuffer[Person]()

  /**
   * Constructor
   */
  personData += new Person("Hans", "Muster")
  personData += new Person("Ruth", "Mueller")
  personData += new Person("Heinz", "Kurz")
  personData += new Person("Cornelia", "Meier")
  personData += new Person("Werner", "Meyer")
  personData += new Person("Lydia", "Kunz")
  personData += new Person("Anna", "Best")
  personData += new Person("Stefan", "Meier")
  personData += new Person("Martin", "Mueller")

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
        stylesheets = Seq(cssResource.toExternalForm)
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

  val stringA = new StringProperty("sunway") //publisher
  val stringB = new StringProperty("monash") //subscriber
  val stringC = new StringProperty("taylor") //subscriber

  def showPersonEditDialog(person: Person): Boolean =
    val resource = getClass.getResource("view/PersonEditDialog.fxml")
    val loader = new FXMLLoader(resource)
    loader.load();
    val roots2 = loader.getRoot[jfxs.Parent] //use parent mean subtype polymorphism dont use direct anchor pane
    val control = loader.getController[PersonEditDialogController]

    val dialog = new Stage():
      initModality(Modality.ApplicationModal)
      initOwner(stage)
      scene = new Scene:
        root = roots2
        stylesheets = Seq(cssResource.toExternalForm)

    control.dialogStage = dialog
    control.person = person
    dialog.showAndWait()

    control.okClicked

  
  
  
  
  
  stringA.onChange((a, b, c) => {
    println(" a has change value " + b + " to " + c)
  })
  //You're attaching a listener (callback) to stringA.
  //Whenever stringA's value changes:
  //a refers to the signal/observable itself (stringA).
  //b is the old value.
  //c is the new value.
  //The callback will print a message saying that stringA changed from b to c.
  stringB.<==(stringA)
  //Now, stringB will always mirror the value of stringA.
  //When stringA changes, stringB will update to match.
  //But changing stringB directly won’t affect stringA.
  stringC.<==>(stringA)
  //This is a bidirectional binding.
  //Now, stringC and stringA are linked in both directions:
  //Changing stringA updates stringC.
  //Changing stringC updates stringA.
  stringA.value = "segi"
  //Sets stringA’s value to "segi".
  //This will trigger:
  //The onChange listener on stringA, printing the change.
  //stringB to update its value (due to the one-way binding).
  //stringC to update its value (due to the bidirectional binding).
  stringC.value = "utm"

  println("string a value " + stringA.value)
  println("string b value " + stringB.value)
  println("string C value " + stringC.value)

  stringA.value = "monash"

  println("string a value " + stringA.value)
  println("string b value " + stringB.value)
  println("string C value " + stringC.value)

  val add:  (Int, Int) => Int = (a: Int, b: Int) => { a + b }
  println(add(1, 2))