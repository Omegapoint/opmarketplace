import {Component} from 'angular2/core';
import {View} from "angular2/core";
import 'rxjs/add/operator/map';
import {AccountService, Account} from "./account.service";
import {Json} from "angular2/src/facade/lang";
import {FormBuilder} from "angular2/common";
import {Validators} from "angular2/common";
import {ControlGroup} from "angular2/common";

@Component({
    selector: 'login',
})

@View({
    templateUrl: 'app/login.html',
})

export class LoginComponent{
    private loginForm: ControlGroup;

    constructor(fb: FormBuilder, private _accountService: AccountService) {
        this.loginForm = fb.group({
            email: ["", Validators.required],
            password: ["", Validators.required]
        });
    }

    public login(event) {
        event.preventDefault();
        this._accountService.login(this.loginForm.value.email, this.loginForm.value.password);
    }



}