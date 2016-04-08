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
import java.util.UUID;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class TransactionService {

    public static DomainEvent creditTransaction(ItemOrdered request, AccountRepository accountRepository) {
        notNull(request);
        Order order = request.order();

        Optional<Account> maybeBuyer = accountRepository.account(order.buyerId());
        DomainEvent chargeEvent = maybeBuyer
                .map(buyer -> DomainObjectResult.of(b -> b.charge(request), buyer)
                        .map(n -> (DomainEvent)n)
                        .orElseReason(reason -> new ItemPaymentNotCompleted(order.id(), reason)))
                .orElse(new ItemPaymentNotCompleted(order.id(), "Buyer does not have an account registered."));

        if (chargeEvent instanceof PersistableEvent){
            Optional<Account> maybeSeller = accountRepository.account(order.sellerId());
            DomainEvent depositEvent = maybeSeller
                    .map(seller -> DomainObjectResult.of(s -> s.depositCredits(request), seller)
                            .map(n -> (DomainEvent)n)
                            .orElseReason(reason -> new ItemPaymentNotCompleted(order.id(), reason)))
                    .orElse(new ItemPaymentNotCompleted(order.id(), "Seller does not have an account registered."));
            if (depositEvent instanceof PersistableEvent){
                accountRepository.append((PersistableEvent) chargeEvent);
                accountRepository.append((PersistableEvent) depositEvent);
                return new ItemPaymentCompleted(order.id(), order.sellerId(), order.sum(), order.buyerId());
            }
            return depositEvent;
        }
        return chargeEvent;
    }

}
