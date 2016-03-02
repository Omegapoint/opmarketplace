import {Component} from 'angular2/core';
import { RouteConfig, ROUTER_DIRECTIVES } from 'angular2/router';
import {HTTP_PROVIDERS, Http, Response, Headers} from "angular2/http";
import {AccountComponent} from "./account.component";
import {MarketplaceComponent} from "./marketplace.component";

@Component({
    selector: 'container',
    directives: [ROUTER_DIRECTIVES],
    templateUrl: '/app/container.html',
})

@RouteConfig([
    {
        path: '/account',
        name: 'AccountComponent',
        component: AccountComponent
    },
    {
        path: '/marketplace',
        name: 'MarketplaceComponent',
        component: MarketplaceComponent,
        useAsDefault: true
    }
])
export class ContainerComponent {}
