import {Component, View} from "angular2/core";
import {OnInit} from "angular2/core";

export class Item{
    constructor(private title: string, private description: string, private price: number, public expiration: Date){}
}

@Component({
    selector: 'item',
    inputs: ['item'],
})

@View({
    templateUrl: 'app/item.html',
})

export class ItemComponent implements OnInit{
    private item: Item;
    private expireString: string;

    public ngOnInit(){
        this.expireString = this.item.expiration.toDateString();
    }
}