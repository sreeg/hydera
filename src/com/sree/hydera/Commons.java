package com.sree.hydera;

import java.util.LinkedHashMap;

public class Commons
{
  public static LinkedHashMap<String, Integer> monthDaysMap;
  public static LinkedHashMap<String, String> monthMap;

  static
  {
    monthMap = new LinkedHashMap<String, String>();
    monthMap.put("January", "January");
    monthMap.put("February", "February");
    monthMap.put("March", "March");
    monthMap.put("April", "April");
    monthMap.put("May", "May");
    monthMap.put("June", "June");
    monthMap.put("July", "July");
    monthMap.put("August", "August");
    monthMap.put("September", "September");
    monthMap.put("October", "October");
    monthMap.put("November", "November");
    monthMap.put("December", "December");
  }

  static
  {
    monthDaysMap = new LinkedHashMap<String, Integer>();
    monthDaysMap.put("January", 31);
    monthDaysMap.put("February", 28);
    monthDaysMap.put("March", 31);
    monthDaysMap.put("April", 30);
    monthDaysMap.put("May", 31);
    monthDaysMap.put("June", 30);
    monthDaysMap.put("July", 31);
    monthDaysMap.put("August", 31);
    monthDaysMap.put("September", 30);
    monthDaysMap.put("October", 31);
    monthDaysMap.put("November", 30);
    monthDaysMap.put("December", 31);
  }

}
