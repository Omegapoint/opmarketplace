import {Component} from 'angular2/core';
import {View} from "angular2/core";
import 'rxjs/add/operator/map';
import {AccountService, Account} from "./account.service";
import {OnInit} from "angular2/core";
import {Json} from "angular2/src/facade/lang";

@Component({
    selector: 'account',
})

@View({
    templateUrl: 'app/account.html',
})

export class AccountComponent implements OnInit{
    private account: Account = {
        email: "",
        user:{
            firstName: "",
            lastName: ""
        }
    }

    constructor(private _accountService: AccountService) {}

    ngOnInit(){
        this.account = this._accountService.currentActiveAccount();
    }
}