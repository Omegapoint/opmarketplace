package se.omegapoint.academy.opmarketplace.customer.application;

import se.sawano.java.commons.lang.validate.IllegalArgumentValidationException;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

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

    public static <U, V> DomainObjectResult<U> of(Function<V, U> conversion, V input){
        try {
            return new DomainObjectResult<>(conversion.apply(input));
        } catch (IllegalArgumentValidationException e){
            return new DomainObjectResult<>(e.getMessage());
        }
    }

    public static <U> DomainObjectResult<U> of(Supplier<U> supplier){
        try {
            return new DomainObjectResult<>(supplier.get());
        } catch (IllegalArgumentValidationException e){
            return new DomainObjectResult<>(e.getMessage());
        }
    }
}
