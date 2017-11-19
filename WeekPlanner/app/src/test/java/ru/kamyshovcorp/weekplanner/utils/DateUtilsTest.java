package ru.kamyshovcorp.weekplanner.utils;

import org.junit.Test;

import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

public class DateUtilsTest {

    @Test
    public void getWeekStartDate_whenMondayTest() throws Exception {
        Date mondayDate = new GregorianCalendar(2017, 10, 12, 10, 30).getTime();
        Date expectedDateTime = new GregorianCalendar(2017, 10, 12).getTime();

        Date weekStartDate = DateUtils.getWeekStartDate(mondayDate);

        assertEquals(expectedDateTime, weekStartDate);
    }

    @Test
    public void getWeekStartDate_whenWednesdayTest() throws Exception {
        Date wednesdayDate = new GregorianCalendar(2017, 10, 15, 10, 30).getTime();
        Date expectedDateTime = new GregorianCalendar(2017, 10, 12).getTime();

        Date weekStartDate = DateUtils.getWeekStartDate(wednesdayDate);

        assertEquals(expectedDateTime, weekStartDate);
    }

    @Test
    public void getWeekStartDate_whenSaturdayTest() throws Exception {
        Date saturdayDate = new GregorianCalendar(2017, 10, 18, 10, 30).getTime();
        Date expectedDateTime = new GregorianCalendar(2017, 10, 12).getTime();

        Date weekStartDate = DateUtils.getWeekStartDate(saturdayDate);

        assertEquals(expectedDateTime, weekStartDate);
    }

    @Test
    public void getWeekEndDate_whenMondayTest() throws Exception {
        Date mondayDate = new GregorianCalendar(2017, 10, 12, 10, 30).getTime();
        Date expectedDateTime = new GregorianCalendar(2017, 10, 18, 23,59,59).getTime();

        Date weekEndDate = DateUtils.getWeekEndDate(mondayDate);

        assertEquals(expectedDateTime, weekEndDate);
    }

    @Test
    public void getWeekEndDate_whenWednesdayTest() throws Exception {
        Date wednesdayDate = new GregorianCalendar(2017, 10, 15, 10, 30).getTime();
        Date expectedDateTime = new GregorianCalendar(2017, 10, 18, 23,59,59).getTime();

        Date weekEndDate = DateUtils.getWeekEndDate(wednesdayDate);

        assertEquals(expectedDateTime, weekEndDate);
    }

    @Test
    public void getWeekEndDate_whenSaturdayTest() throws Exception {
        Date saturdayDate = new GregorianCalendar(2017, 10, 18, 10, 30).getTime();
        Date expectedDateTime = new GregorianCalendar(2017, 10, 18, 23,59,59).getTime();

        Date weekEndDate = DateUtils.getWeekEndDate(saturdayDate);

        assertEquals(expectedDateTime, weekEndDate);
    }

}