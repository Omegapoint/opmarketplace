package se.omegapoint.academy.opmarketplace.customer.infrastructure;

import java.util.Optional;

//TODO [dd] Consider moving Result to domain. May make things easier...

public class Result<T> {

    //TODO [dd]: consider changing API to Result<Optional<T>, Failure>

    //TODO [dd]: make immutable
    private Optional<T> value;
    private Optional<String> errorMessage;

    //TODO [dd] consider invoking one common constructor with an invariant. Cannot be error and success at the same time!

    private Result(T value){
        //TODO [dd] add notNull contracts
        this.value = Optional.of(value);
        this.errorMessage = Optional.empty();
    }

    private Result(String errorMessage){
        //TODO [dd] add notNull contracts
        this.errorMessage = Optional.of(errorMessage);
        this.value = Optional.empty();
    }

    public boolean isSuccess(){
        return this.value.isPresent();
    }

    public T value(){
        return this.value.get();
    }

    public String error(){
        return this.errorMessage.get();
    }

    public static <T> Result<T> success(T value){
        return new Result<>(value);
    }

    public static <T> Result<T> error(String errorMessage){
        return new Result<>(errorMessage);
    }

}
