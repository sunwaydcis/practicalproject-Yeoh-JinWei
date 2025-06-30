package ch.makery.address.model

import scalafx.beans.property.{StringProperty, IntegerProperty, ObjectProperty}
import java.time.LocalDate;

class Person ( firstNameS : String, lastNameS : String ):
  var firstName  = new StringProperty(firstNameS)
  var lastName   = new StringProperty(lastNameS)
  var street     = new StringProperty("some Street")
  //var postalCode = IntegerProperty(1234)
  var postalCode = ObjectProperty[Int](1234) //calling the object property apply method with initial value of 1234
  // only obJectProperty special cause got type parameter T non-variance, require a bean object we don't have
  
  var city       = new StringProperty("some city")
  var date       = ObjectProperty[LocalDate](LocalDate.of(1999, 2, 21))
