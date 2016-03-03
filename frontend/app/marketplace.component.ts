import {Component} from 'angular2/core';
import {View} from "angular2/core";
import 'rxjs/add/operator/map';

@Component({
    selector: 'marketplace',
})

@View({
    templateUrl: '/app/marketplace.html',
})

export class MarketplaceComponent {
    private query: string;
}
