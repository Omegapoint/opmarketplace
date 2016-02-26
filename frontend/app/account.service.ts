import {Injectable} from 'angular2/core';
import {HTTP_PROVIDERS, Http, Response, Headers} from "angular2/http";
import {Component} from "angular2/core";

export interface Account{
    email: string;
    user:{
        firstName: string;
        lastName: string;
    };
}

@Injectable()
export class AccountService {
    private account: Account = {
        email: "hej",
        user:{
            firstName: "",
            lastName: ""
        }
    }

    constructor(private _http: Http) { }

    public currentActiveAccount() : Account {
        return {
            email: this.account.email,
            user:{
                firstName: this.account.user.firstName,
                lastName: this.account.user.lastName
            },
        }
    }

    public registerAccount(newAccount: Account){
        var headers = new Headers();
        headers.append('Content-Type', 'application/json');
        this._http.post("http://localhost:8001/accounts", JSON.stringify(newAccount), {headers:headers})
            .subscribe(response => this.handleResponse(response, newAccount));
    }

    private handleResponse(response: Response, account: Account){
        console.log(response);
        if (response.status == 201){
            this.account = account;
            console.log("ok");
        }
    }
}