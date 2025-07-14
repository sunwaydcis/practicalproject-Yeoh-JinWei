package ch.makery.address.view
import ch.makery.address.model.Person
import ch.makery.address.MainApp
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.{Label, TableColumn, TableView}
import scalafx.Includes.*
import scalafx.beans.binding.Bindings
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType


@FXML //must have alliance FMXL, will inject the code into this class to make it a full controller for the window
class PersonOverviewController():
  @FXML
  private var personTable: TableView[Person] = null
  @FXML
  private var firstNameColumn: TableColumn[Person, String] = null
  @FXML
  private var lastNameColumn: TableColumn[Person, String] = null
  @FXML
  private var firstNameLabel: Label = null
  @FXML
  private var lastNameLabel: Label = null
  @FXML
  private var streetLabel: Label = null
  @FXML
  private var postalCodeLabel: Label = null
  @FXML
  private var cityLabel: Label = null
  @FXML
  private var birthdayLabel: Label = null
  // initialize Table View display contents model
  def initialize() =
    personTable.items = MainApp.personData
    // initialize columns's cell values
    firstNameColumn.cellValueFactory = {_.value.firstName}
    //cellValueFactory already specify x is cellDataFeatures by definition so need specifically set type
    lastNameColumn.cellValueFactory  = {_.value.lastName}
    showPersonDetails(None)
    personTable.selectionModel.value.selectedItem.onChange(
      (_, _, newValue) => showPersonDetails(Option(newValue))
    )

  private def showPersonDetails(person: Option[Person]): Unit =
    person match
      case Some(person) =>
        import ch.makery.address.util.DateUtil.*
        // only in case some all the local date string will get extended
        // Fill the labels with info from the Person object.
        firstNameLabel.text <== person.firstName
        lastNameLabel.text <== person.lastName
        streetLabel.text <== person.street
        cityLabel.text <== person.city
        postalCodeLabel.text <== person.postalCode.delegate.asString()
        //special case need delegate asString cause this originally is integer value, unlike
        //the ones above is originally string
        birthdayLabel.text <== Bindings.createStringBinding(
          () => {
            person.date.value.asString
          }, person.date
        )



      // birthdayLabel.text = TODO
      case None =>
        // Person is null, remove all the text.
        firstNameLabel.text.unbind() //need to unbind to set value cause compiler cannot set subscriber yourself
        firstNameLabel.text = ""
        lastNameLabel.text.unbind()
        lastNameLabel.text = ""
        streetLabel.text.unbind()
        streetLabel.text = ""
        cityLabel.text.unbind()
        cityLabel.text = ""
        postalCodeLabel.text.unbind()
        postalCodeLabel.text = ""
        birthdayLabel.text.unbind()
        birthdayLabel.text = ""

  @FXML
  def handleDeletePerson(action: ActionEvent) =
    val selectedIndex = personTable.selectionModel().selectedIndex.value
    if (selectedIndex >= 0) then
      personTable.items().remove(selectedIndex)
    else
      // Nothing selected.
      val alert = new Alert(AlertType.Information):
        initOwner(MainApp.stage)
        title = "No Selection"
        headerText = "No Person Selected"
        contentText = "Please select a person in the table."
      alert.showAndWait()

  @FXML
  def handleNewPerson(action: ActionEvent) =
    val person = new Person("", "")
    val okClicked = MainApp.showPersonEditDialog(person);
    if (okClicked) then
      MainApp.personData += person

  @FXML
  def handleEditPerson(action: ActionEvent) =
    val selectedPerson = personTable.selectionModel().selectedItem.value
    if (selectedPerson != null) then
      val okClicked = MainApp.showPersonEditDialog(selectedPerson)

      if (okClicked) then showPersonDetails(Some(selectedPerson))

    else
      // Nothing selected.
      val alert = new Alert(Alert.AlertType.Warning):
        initOwner(MainApp.stage)
        title = "No Selection"
        headerText = "No Person Selected"
        contentText = "Please select a person in the table."
      .showAndWait()
