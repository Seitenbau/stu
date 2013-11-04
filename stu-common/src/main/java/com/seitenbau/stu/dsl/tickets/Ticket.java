package com.seitenbau.stu.dsl.tickets;

/**
 * Helper methods for the ticket dsl.
 */
public class Ticket {

    public static boolean solved(String ticketDSL)
    {
        if (ticketDSL == null) {
            throw new IllegalArgumentException();
        }
        if (ticketDSL.startsWith("fixed:")) {
            return true;
        }
        return false;
    }
    
    public static boolean notSolved(String ticketDSL)
    {
      return !solved(ticketDSL);
    }

}
