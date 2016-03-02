import {Component, View} from 'angular2/core';
import 'rxjs/add/operator/map';
import {AccountService, Account, User} from "./account.service";
import {Json} from "angular2/src/facade/lang";
import {Validators, ControlGroup, FormBuilder} from "angular2/common";
import {Response} from "angular2/http";
import {Router} from "angular2/router";

@Component({
    selector: 'register',
})

@View({
    templateUrl: 'app/register.html',
})

export class RegisterComponent {
    private registerForm: ControlGroup;

    constructor(fb: FormBuilder, private _accountService: AccountService, private _router: Router) {
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
        this._accountService.registerAccount(newAccount).subscribe(response => this.handleRegistration(response));
    }

    private handleRegistration(response: Response){
        if (response.status == 201){
            console.log("registration success");
            this._router.navigateByUrl("/app/account");
        }
    }
}
