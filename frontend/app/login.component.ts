import {Component, View} from 'angular2/core';
import 'rxjs/add/operator/map';
import {AccountService, Account} from "./account.service";
import {Json} from "angular2/src/facade/lang";
import {Validators, FormBuilder, ControlGroup} from "angular2/common";
import {Response} from "angular2/http";
import {Router} from "angular2/router";

@Component({
    selector: 'login',
})

@View({
    templateUrl: 'app/login.html',
})

export class LoginComponent{
    private loginForm: ControlGroup;

    constructor(fb: FormBuilder, private _accountService: AccountService, private _router: Router) {
        this.loginForm = fb.group({
            email: ["", Validators.required],
            password: ["", Validators.required]
        });
    }

    public login(event) {
        event.preventDefault();
        this._accountService.login(this.loginForm.value.email, this.loginForm.value.password).subscribe(response => this.handleLogin(response));
    }

    private handleLogin(response: Response){
        if (response.status == 200){
            console.log("login success");
            this._router.navigateByUrl("/app/account");
        }
    }



}