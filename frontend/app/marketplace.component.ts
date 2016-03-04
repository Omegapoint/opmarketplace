import {Component} from 'angular2/core';
import {View} from "angular2/core";
import 'rxjs/add/operator/map';
import {Item} from "./item.component";
import {ItemComponent} from "./item.component";

@Component({
    selector: 'marketplace',
})

@View({
    templateUrl: '/app/marketplace.html',
    directives: [ItemComponent],
})

export class MarketplaceComponent {
    private query: string;
    private items = ITEMS;
}

var ITEMS: Item[] = [
    new Item("Item1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed tempor tortor eget tincidunt ultrices. Sed. ", 100, new Date()),
    new Item("Item2", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed tempor tortor eget tincidunt ultrices. Sed. ", 200, new Date()),
    new Item("Item3", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed tempor tortor eget tincidunt ultrices. Sed. ", 300, new Date()),
    new Item("Item4", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed tempor tortor eget tincidunt ultrices. Sed. ", 4000, new Date()),
    new Item("Item5", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed tempor tortor eget tincidunt ultrices. Sed. ", 500, new Date()),
    new Item("Item6", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed tempor tortor eget tincidunt ultrices. Sed. ", 600, new Date()),
    new Item("Item7", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed tempor tortor eget tincidunt ultrices. Sed. ", 7000, new Date()),
    new Item("Item8", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed tempor tortor eget tincidunt ultrices. Sed. ", 8000, new Date()),
];
