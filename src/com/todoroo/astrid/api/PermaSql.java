/**
 * See the file "LICENSE" for the full license governing this code.
 */
package com.todoroo.astrid.api;

import java.util.Date;

import com.todoroo.andlib.utility.DateUtilities;

/**
 * PermaSql allows for creating SQL statements that can be saved and used
 * later without dates getting stale. It also allows these values to be
 * used in
 *
 * @author Tim Su <tim@todoroo.com>
 *
 */
public final class PermaSql {

    // --- placeholder strings

    /** value to be replaced with the current time as long */
    public static final String VALUE_NOW = "NOW()"; //$NON-NLS-1$

    /** value to be replaced by end of day as long */
    public static final String VALUE_EOD = "EOD()"; //$NON-NLS-1$

    /** value to be replaced by end of day yesterday as long */
    public static final String VALUE_EOD_YESTERDAY = "EODY()"; //$NON-NLS-1$

    /** value to be replaced by end of day tomorrow as long */
    public static final String VALUE_EOD_TOMORROW = "EODT()"; //$NON-NLS-1$

    /** value to be replaced by end of day day after tomorrow as long */
    public static final String VALUE_EOD_DAY_AFTER = "EODTT()"; //$NON-NLS-1$

    /** value to be replaced by end of day next week as long */
    public static final String VALUE_EOD_NEXT_WEEK = "EODW()"; //$NON-NLS-1$

    /** value to be replaced by approximate end of day next month as long */
    public static final String VALUE_EOD_NEXT_MONTH = "EODM()"; //$NON-NLS-1$

    /** Replace placeholder strings with actual */
    public static String replacePlaceholders(String value) {
        if(value.contains(VALUE_NOW))
            value = value.replace(VALUE_NOW, Long.toString(DateUtilities.now()));
        if(value.contains(VALUE_EOD) || value.contains(VALUE_EOD_DAY_AFTER) ||
                value.contains(VALUE_EOD_NEXT_WEEK) || value.contains(VALUE_EOD_TOMORROW) ||
                value.contains(VALUE_EOD_YESTERDAY) || value.contains(VALUE_EOD_NEXT_MONTH)) {
            Date date = new Date();
            date.setHours(23);
            date.setMinutes(59);
            date.setSeconds(59);
            long time = date.getTime();
            value = value.replace(VALUE_EOD_YESTERDAY, Long.toString(time - DateUtilities.ONE_DAY));
            value = value.replace(VALUE_EOD, Long.toString(time));
            value = value.replace(VALUE_EOD_TOMORROW, Long.toString(time + DateUtilities.ONE_DAY));
            value = value.replace(VALUE_EOD_DAY_AFTER, Long.toString(time + 2 * DateUtilities.ONE_DAY));
            value = value.replace(VALUE_EOD_NEXT_WEEK, Long.toString(time + 7 * DateUtilities.ONE_DAY));
            value = value.replace(VALUE_EOD_NEXT_MONTH, Long.toString(time + 30 * DateUtilities.ONE_DAY));
        }
        return value;
    }

}
