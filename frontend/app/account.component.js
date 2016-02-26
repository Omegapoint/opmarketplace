System.register(['angular2/core', "angular2/core", 'rxjs/add/operator/map', "./account.service"], function(exports_1) {
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var core_1, core_2, account_service_1;
    var AccountComponent;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (core_2_1) {
                core_2 = core_2_1;
            },
            function (_1) {},
            function (account_service_1_1) {
                account_service_1 = account_service_1_1;
            }],
        execute: function() {
            AccountComponent = (function () {
                function AccountComponent(_accountService) {
                    this._accountService = _accountService;
                    this.account = {
                        email: "",
                        user: {
                            firstName: "",
                            lastName: ""
                        }
                    };
                }
                AccountComponent.prototype.ngOnInit = function () {
                    this.account = this._accountService.currentActiveAccount();
                };
                AccountComponent = __decorate([
                    core_1.Component({
                        selector: 'account',
                    }),
                    core_2.View({
                        templateUrl: 'app/account.html',
                    }), 
                    __metadata('design:paramtypes', [account_service_1.AccountService])
                ], AccountComponent);
                return AccountComponent;
            })();
            exports_1("AccountComponent", AccountComponent);
        }
    }
});
//# sourceMappingURL=account.component.js.map