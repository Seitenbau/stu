package com.seitenbau.testing.dsl.tickets;

/**
 * Hilfsmethoden fuer ticket-dsl
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
