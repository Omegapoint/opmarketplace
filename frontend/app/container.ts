import {Component} from 'angular2/core';
import { RouteConfig, ROUTER_DIRECTIVES, ROUTER_PROVIDERS } from 'angular2/router';
import {HTTP_PROVIDERS, Http, Response, Headers} from "angular2/http";
import {RegisterComponent} from "./register.component";
import {AccountComponent} from "./account.component";
import {AccountService} from './account.service';

@Component({
    selector: 'container',
    directives: [ROUTER_DIRECTIVES],
    templateUrl: '/app/container.html',
    providers: [HTTP_PROVIDERS, ROUTER_PROVIDERS, AccountService],
})

@RouteConfig([
    {
        path: '/register',
        name: 'RegisterComponent',
        component: RegisterComponent
    },
    {
        path: '/account',
        name: 'AccountComponent',
        component: AccountComponent
    }
])
export class Container {
    constructor(private _accountService: AccountService) { }
}
