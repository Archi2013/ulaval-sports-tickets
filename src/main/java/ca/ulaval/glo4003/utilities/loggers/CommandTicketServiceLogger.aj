package ca.ulaval.glo4003.utilities.loggers;

import java.util.Set;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.ulaval.glo4003.domain.cart.SectionForCart;

public aspect CommandTicketServiceLogger {

    private static final Logger CommandTicketServiceLogger = LoggerFactory.getLogger("TransactionLogger");

    pointcut CommandTicketService_makeTicketsUnavailable() :
        execution (public void ca.ulaval.glo4003.services.CommandTicketService.makeTicketsUnavailable(..));
    
    @SuppressWarnings("unchecked")
    after() : CommandTicketService_makeTicketsUnavailable(){
        Set<SectionForCart> sections = (Set<SectionForCart>) thisJoinPoint.getArgs()[0];
        Date dateNow = new Date();
        for(SectionForCart sectionFC : sections) {
            if (sectionFC.getGeneralAdmission()) {
                CommandTicketServiceLogger.info(dateNow.toString()+" : Vente de tickets d'admission générale pour le sport "+sectionFC.getSportName()
                        + " à la date " + sectionFC.getGameDate()
                        + "\n"+sectionFC.getNumberOfTicketsToBuy() + " tickets vendus à " + sectionFC.getUnitPrice()
                        +"\n Total "+sectionFC.getSubtotal());
            } else {
                CommandTicketServiceLogger.info(dateNow.toString()+" : Vente de tickets sélectionnés pour le sport "+sectionFC.getSportName() +
                        " à la date " + sectionFC.getGameDate());
                for (String seat : sectionFC.getSelectedSeats()) {
                    CommandTicketServiceLogger.info("Ticket " + seat +" vendus à " + sectionFC.getUnitPrice());
                    }
                CommandTicketServiceLogger.info("Total : "+sectionFC.getSubtotal()+"\n");
            }
        }
    }
} 