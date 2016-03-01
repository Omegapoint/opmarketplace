import {Component} from 'angular2/core';
import {View} from "angular2/core";
import {FormBuilder} from "angular2/common";
import {Validators} from "angular2/common";
import {Form} from "angular2/common";
import {ControlGroup} from "angular2/common";
import 'rxjs/add/operator/map';
import {Router} from 'angular2/router';
import {AccountService} from "./account.service";
import {Account} from "./account.service";
import {User} from "./account.service";

@Component({
    selector: 'register',
})

@View({
    templateUrl: 'app/register.html',
})
export class RegisterComponent {

    private registerForm: ControlGroup;

    constructor(fb: FormBuilder, private _accountService: AccountService) {
        this.registerForm = fb.group({
            firstName: ["", Validators.required],
            lastName:["", Validators.required],
            email: ["", Validators.required],
            password: ["", Validators.required]
        });
    }

    public registerAccount(event) {
        event.preventDefault();
        var newAccount = new Account(this.registerForm.value.email, new User(this.registerForm.value.firstName, this.registerForm.value.lastName));
        this._accountService.registerAccount(newAccount)
    }


}
