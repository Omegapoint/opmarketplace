package se.omegapoint.academy.opmarketplace.customer.domain.services;

import se.omegapoint.academy.opmarketplace.customer.application.DomainObjectResult;
import se.omegapoint.academy.opmarketplace.customer.domain.entities.Account;
import se.omegapoint.academy.opmarketplace.customer.domain.events.DomainEvent;
import se.omegapoint.academy.opmarketplace.customer.domain.events.ItemOrdered;
import se.omegapoint.academy.opmarketplace.customer.domain.events.ItemPaymentCompleted;
import se.omegapoint.academy.opmarketplace.customer.domain.events.ItemPaymentNotCompleted;
import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.PersistableEvent;

import java.util.Optional;
import java.util.UUID;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class TransactionService {

    public static DomainEvent creditTransaction(ItemOrdered request, AccountRepository accountRepository) {
        notNull(request);

        Optional<Account> maybeBuyer = accountRepository.account(request.buyerId());
        DomainEvent chargeEvent = maybeBuyer
                .map(buyer -> DomainObjectResult.of(b -> b.charge(request), buyer)
                        .map(n -> (DomainEvent)n)
                        .orElseReason(reason -> new ItemPaymentNotCompleted(request.orderId(), reason)))
                .orElse(new ItemPaymentNotCompleted(request.orderId(), "Buyer does not have an account registered."));

        if (chargeEvent instanceof PersistableEvent){
            Optional<Account> maybeSeller = accountRepository.account(request.sellerId());
            DomainEvent depositEvent = maybeSeller
                    .map(seller -> DomainObjectResult.of(s -> s.depositCredits(request), seller)
                            .map(n -> (DomainEvent)n)
                            .orElseReason(reason -> new ItemPaymentNotCompleted(request.orderId(), reason)))
                    .orElse(new ItemPaymentNotCompleted(request.orderId(), "Seller does not have an account registered."));
            if (depositEvent instanceof PersistableEvent){
                accountRepository.append((PersistableEvent) chargeEvent);
                accountRepository.append((PersistableEvent) depositEvent);
                return new ItemPaymentCompleted(request.orderId(), request.sellerId(), request.price(), request.buyerId());
            }
            return depositEvent;
        }
        return chargeEvent;
    }

}
