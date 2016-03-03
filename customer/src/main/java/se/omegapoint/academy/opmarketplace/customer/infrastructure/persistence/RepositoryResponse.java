package se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence;

import java.util.Optional;

public class RepositoryResponse<T> {

    private Optional<T> value;
    private Optional<String> errorMessage;

    private RepositoryResponse(T value){
        this.value = Optional.of(value);
        this.errorMessage = Optional.empty();
    }

    private RepositoryResponse(String errorMessage){
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

    public static <T> RepositoryResponse<T> success(T value){
        return new RepositoryResponse<>(value);
    }

    public static <T> RepositoryResponse<T> error(String errorMessage){
        return new RepositoryResponse<>(errorMessage);
    }

}
