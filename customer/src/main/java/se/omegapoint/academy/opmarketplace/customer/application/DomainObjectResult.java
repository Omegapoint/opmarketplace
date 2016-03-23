package se.omegapoint.academy.opmarketplace.customer.application;

import se.sawano.java.commons.lang.validate.IllegalArgumentValidationException;

import java.util.Optional;
import java.util.function.Function;

public class DomainObjectResult<T>{
    private Optional<T> value;
    private String error;

    private DomainObjectResult(T value){
        this.value = Optional.of(value);
        this.error = "";
    }


    private DomainObjectResult(String error){
        this.value = Optional.empty();
        this.error = error;
    }

    public<U> DomainObjectResult<U> map(Function<? super T, ? extends U> mapper) {
        if (value.isPresent()) {
            return new DomainObjectResult<>(value.map(mapper).get());
        }
        return new DomainObjectResult<>(error);
    }

    public T orElseError(Function<String, ? extends T> other) {
        return value.isPresent() ? value.get() : other.apply(error);
    }

    public static <U, V> DomainObjectResult<U> of(Function<V, U> mapper, V input){
        try {
            return new DomainObjectResult<>(mapper.apply(input));
        } catch (IllegalArgumentValidationException e){
            return new DomainObjectResult<>(e.getMessage());
        }
    }
}
