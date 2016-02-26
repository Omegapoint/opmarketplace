System.register(['angular2/core', 'angular2/router', "angular2/http", "./register.component", "./account.component", './account.service'], function(exports_1) {
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var core_1, router_1, http_1, register_component_1, account_component_1, account_service_1;
    var Container;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (router_1_1) {
                router_1 = router_1_1;
            },
            function (http_1_1) {
                http_1 = http_1_1;
            },
            function (register_component_1_1) {
                register_component_1 = register_component_1_1;
            },
            function (account_component_1_1) {
                account_component_1 = account_component_1_1;
            },
            function (account_service_1_1) {
                account_service_1 = account_service_1_1;
            }],
        execute: function() {
            Container = (function () {
                function Container(_accountService) {
                    this._accountService = _accountService;
                }
                Container = __decorate([
                    core_1.Component({
                        selector: 'container',
                        directives: [router_1.ROUTER_DIRECTIVES],
                        templateUrl: '/app/container.html',
                        providers: [http_1.HTTP_PROVIDERS, router_1.ROUTER_PROVIDERS, account_service_1.AccountService],
                    }),
                    router_1.RouteConfig([
                        {
                            path: '/register',
                            name: 'RegisterComponent',
                            component: register_component_1.RegisterComponent
                        },
                        {
                            path: '/account',
                            name: 'AccountComponent',
                            component: account_component_1.AccountComponent
                        }
                    ]), 
                    __metadata('design:paramtypes', [account_service_1.AccountService])
                ], Container);
                return Container;
            })();
            exports_1("Container", Container);
        }
    }
});
//# sourceMappingURL=container.js.map