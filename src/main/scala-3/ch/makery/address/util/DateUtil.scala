package ch.makery.address.util
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
object DateUtil:
  val DATE_PATTERN = "dd.MM.yyyy"
  val DATE_FORMATTER =  DateTimeFormatter.ofPattern(DATE_PATTERN)



  extension (date: LocalDate)
    /**
     * Returns the given date as a well formatted String.
     * The above defined {@link DateUtil#DATE_PATTERN} is used.
     *
     * @param date the date to be returned as a string
     * @return formatted string
     */
    def asString: String =
      if (date == null)
        return null
      return DATE_FORMATTER.format(date)
  extension (data: String)
    /**
     * Converts a String in the format of the defined
     * {@link DateUtil#DATE_PATTERN} to a {@link LocalDate} object.
     *
     * Returns null if the String could not be converted.
     *
     * @param dateString the date as String
     * @return the date object (or null if conversion impossible)
     */

    /* BAD quality code cause got NULL
    def parseLocalDate: LocalDate =
      try
        LocalDate.parse(data, DATE_FORMATTER)
      catch
        case e: DateTimeParseException => null
    def isValid: Boolean =
      data.parseLocalDate != null
    */

    // Good quality code, but extra work, need check if option is defined (got value) what to do
    // if no value what to do. Troublesome
    def parseLocalDate: Option[LocalDate] =
      try
        Option(LocalDate.parse(data, DATE_FORMATTER))
      catch
        case e: DateTimeParseException => None
    def isValid: Boolean =
      data.parseLocalDate != None