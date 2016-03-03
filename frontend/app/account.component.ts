import {Component, View} from 'angular2/core';
import 'rxjs/add/operator/map';
import {AccountService, Account} from "./account.service";
import {OnInit} from "angular2/core";
import {Json} from "angular2/src/facade/lang";
import {ControlGroup, FormBuilder} from "angular2/common";
import {User} from "./account.service";

@Component({
    selector: 'account',
})

@View({
    templateUrl: 'app/account.html',
})

export class AccountComponent implements OnInit{
    private updateForm: ControlGroup;
    private account: Account = {
        email: "",
        user:{
            firstName: "",
            lastName: ""
        }
    }

    constructor(private _formBuilder: FormBuilder, private _accountService: AccountService) {
        //this.updateForm = this._formBuilder.group({
        //    firstName: [this.account.user.firstName],
        //    lastName:[this.account.user.lastName],
        //    email: [this.account.email],
        //});
    }

    ngOnInit(){
        this._accountService.currentActiveAccount().then(account => this.setUp(account));
    }

    private setUp(account : Account){
        this.account = account;
    }

    public updateAccount(event){
        event.preventDefault();
        this._accountService.changeUser(this.account.email, this.account.user).subscribe(response => console.log(response.status));
    }
}