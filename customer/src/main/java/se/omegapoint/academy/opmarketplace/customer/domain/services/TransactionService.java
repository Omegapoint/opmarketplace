package se.omegapoint.academy.opmarketplace.customer.domain.services;

import se.omegapoint.academy.opmarketplace.customer.application.DomainObjectResult;
import se.omegapoint.academy.opmarketplace.customer.domain.entities.Account;
import se.omegapoint.academy.opmarketplace.customer.domain.events.DomainEvent;
import se.omegapoint.academy.opmarketplace.customer.domain.events.ItemOrdered;
import se.omegapoint.academy.opmarketplace.customer.domain.events.ItemPaymentCompleted;
import se.omegapoint.academy.opmarketplace.customer.domain.events.ItemPaymentNotCompleted;
import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.PersistableEvent;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Order;

import java.util.Optional;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class TransactionService {

    public static DomainEvent creditTransaction(ItemOrdered request, AccountRepository accountRepository) {
        notNull(request);
        Order order = request.order();

        DomainEvent chargeEvent = requestCharge(request, accountRepository);
        DomainEvent depositEvent = requestDeposit(request, accountRepository);
        if (chargeEvent instanceof PersistableEvent && depositEvent instanceof PersistableEvent){
                accountRepository.append((PersistableEvent) chargeEvent);
                accountRepository.append((PersistableEvent) depositEvent);
                return new ItemPaymentCompleted(order.id(), order.sellerId(), order.sum(), order.buyerId());
        } else if (chargeEvent instanceof ItemPaymentNotCompleted){
            return chargeEvent;
        } else if (depositEvent instanceof  ItemPaymentNotCompleted){
            return depositEvent;
        } else {
            return new ItemPaymentNotCompleted(order.id(), "Unknown failure.");
        }
    }

    private static DomainEvent requestDeposit(ItemOrdered request, AccountRepository accountRepository) {
        Optional<Account> maybeSeller = accountRepository.account(request.order().sellerId());
        return maybeSeller
                .map(seller -> DomainObjectResult.of(() -> (DomainEvent)seller.depositCredits(request))
                        .orElseReason(reason -> new ItemPaymentNotCompleted(request.order().id(), reason)))
                .orElse(new ItemPaymentNotCompleted(request.order().id(), "Seller does not have an account registered."));
    }

    private static DomainEvent requestCharge(ItemOrdered request, AccountRepository accountRepository) {
        Optional<Account> maybeBuyer = accountRepository.account(request.order().buyerId());
        return maybeBuyer
                .map(buyer -> DomainObjectResult.of(() -> (DomainEvent)buyer.charge(request))
                        .orElseReason(reason -> new ItemPaymentNotCompleted(request.order().id(), reason)))
                .orElse(new ItemPaymentNotCompleted(request.order().id(), "Buyer does not have an account registered."));
    }



}
