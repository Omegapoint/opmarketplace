import {Injectable} from 'angular2/core';
import {HTTP_PROVIDERS, Http, Response, Headers} from "angular2/http";
import {Component} from "angular2/core";

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

    public currentActiveAccount() : Account {
        return new Account(this.account.email, this.account.user);
    }

    public registerAccount(newAccount: Account){
        var headers = new Headers();
        headers.append('Content-Type', 'application/json');
        this._http.post("http://localhost:8001/accounts", JSON.stringify(newAccount), {headers:headers})
            .subscribe(response => this.handleRegistrationResponse(response, newAccount));
    }

    private handleRegistrationResponse(response: Response, account: Account){
        console.log(response);
        if (response.status == 201){
            this.account = account;
            console.log("ok");
        }
    }

    public login(email: string, password: string){
        var headers = new Headers();
        headers.append('Content-Type', 'application/json');
        this._http.get("http://localhost:8001/accounts?email=" + email, {headers:headers})
            .subscribe(response => this.handleGetAccountResponse(response));
    }

    private handleGetAccountResponse(response: Response){
        console.log(response);
        if (response.status == 200){
            this.account = new Account(response.json().email.address, response.json().user);
            console.log("ok");
        }
    }
}