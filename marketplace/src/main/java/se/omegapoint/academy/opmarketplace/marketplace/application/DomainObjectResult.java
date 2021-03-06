package se.omegapoint.academy.opmarketplace.marketplace.application;

import se.sawano.java.commons.lang.validate.IllegalArgumentValidationException;

import java.util.Optional;
import java.util.function.Function;

public class DomainObjectResult<T>{
    private Optional<T> value;
    private String reason;

    private DomainObjectResult(T value){
        this.value = Optional.of(value);
        this.reason = "";
    }


    private DomainObjectResult(String reason){
        this.value = Optional.empty();
        this.reason = reason;
    }

    public<U> DomainObjectResult<U> map(Function<? super T, ? extends U> mapper) {
        if (value.isPresent()) {
            return new DomainObjectResult<>(value.map(mapper).get());
        }
        return new DomainObjectResult<>(reason);
    }

    public T orElseReason(Function<String, ? extends T> other) {
        return value.isPresent() ? value.get() : other.apply(reason);
    }

    public static <U, V> DomainObjectResult<U> of(Function<V, U> conversion, V ownerOfFunction){
        try {
            return new DomainObjectResult<>(conversion.apply(ownerOfFunction));
        } catch (IllegalArgumentValidationException | IllegalArgumentException e){
            return new DomainObjectResult<>(e.getMessage());
        }
    }
}
