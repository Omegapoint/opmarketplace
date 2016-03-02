import {HTTP_PROVIDERS, Http, Response, Headers} from "angular2/http";
import {LoginComponent} from "./login.component";
import {RouteConfig, ROUTER_DIRECTIVES} from "angular2/router";
import {Component} from "angular2/core";
import {RegisterComponent} from "./register.component";

@Component({
    selector: 'start',
    directives: [ROUTER_DIRECTIVES],
    templateUrl: '/app/start.html',
})

@RouteConfig([
    {
        path: '/login',
        name: 'LoginComponent',
        component: LoginComponent,
        useAsDefault: true
    },
    {
        path: '/register',
        name: 'RegisterComponent',
        component: RegisterComponent
    }
])
export class StartComponent {
}