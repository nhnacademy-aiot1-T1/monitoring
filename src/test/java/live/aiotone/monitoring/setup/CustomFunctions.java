package live.aiotone.monitoring.setup;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CustomFunctions {

  public static String date_format(Date date, String mysqlFormatPattern) {
    if (date == null) {
      return null;
    }
    System.out.println(mysqlFormatPattern);
    String dateFormatPattern = mysqlFormatPattern
        .replace("%Y", "yyyy")
        .replace("%m", "MM")
        .replace("%d", "dd")
        .replace("%H", "HH")
        .replace("%i", "mm")
        .replace("%s", "ss");
    System.out.println(dateFormatPattern);
    return date.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime().format(DateTimeFormatter.ofPattern(dateFormatPattern));
  }
}