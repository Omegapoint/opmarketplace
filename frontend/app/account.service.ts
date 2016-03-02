import {Injectable} from 'angular2/core';
import {HTTP_PROVIDERS, Http, Response, Headers} from "angular2/http";
import {Component} from "angular2/core";
import {Observable} from "rxjs/Observable";

export class User{
    public firstName: string;
    public lastName: string;

    constructor(firstName:string, lastName:string) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}

export class Account{
    public email: string;
    public user: User;

    constructor(email:string, user: User) {
        this.email = email;
        this.user = user;
    }
}

@Injectable()
export class AccountService {
    private account: Account;


    constructor(private _http: Http) {
        this.account = new Account("hej", new User("hej", "hej"));
    }

    public currentActiveAccount() : Promise<Account> {
        return Promise.resolve(new Account(this.account.email, this.account.user));
    }

    public registerAccount(newAccount: Account) : Observable<Response>{
        var headers = new Headers();
        headers.append('Content-Type', 'application/json');
        var registrationSubscription = this._http.post("http://localhost:8001/accounts", JSON.stringify(newAccount), {headers:headers});
        registrationSubscription.subscribe(response => this.handleRegistrationResponse(response, newAccount));
        return registrationSubscription;
    }

    private handleRegistrationResponse(response: Response, account: Account){
        if (response.status == 201){
            this.account = account;
        }
    }

    public login(email: string, password: string) : Observable<Response>{
        var headers = new Headers();
        headers.append('Content-Type', 'application/json');
        var subscription = this._http.get("http://localhost:8001/accounts?email=" + email, {headers:headers});
        subscription.subscribe(response => this.handleGetAccountResponse(response));
        return subscription;
    }

    private handleGetAccountResponse(response: Response){
        if (response.status == 200){
            this.account = new Account(response.json().email.address, response.json().user);
        }
    }
}