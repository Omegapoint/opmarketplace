
import {Component} from 'angular2/core';
import { RouteConfig, ROUTER_DIRECTIVES, ROUTER_PROVIDERS } from 'angular2/router';
import {HTTP_PROVIDERS, Http, Response, Headers} from "angular2/http";
import {AccountService} from "./account.service";
import {ContainerComponent} from "./container.component";
import {StartComponent} from "./start.component";

@Component({
    selector: 'app',
    directives: [ROUTER_DIRECTIVES],
    template: '<router-outlet></router-outlet>',
    providers: [HTTP_PROVIDERS, ROUTER_PROVIDERS, AccountService],
})

@RouteConfig([
    {
        path: '/app/...',
        name: 'ContainerComponent',
        component: ContainerComponent,
        useAsDefault: true
    },
    {
        path: '/start/...',
        name: 'StartComponent',
        component: StartComponent
    }
])
export class AppComponent {
    constructor(private _accountService: AccountService) { }
}
