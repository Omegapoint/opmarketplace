System.register(['angular2/core', "angular2/core", 'rxjs/add/operator/map', "./account.service", "angular2/common"], function(exports_1) {
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var core_1, core_2, account_service_1, common_1, common_2;
    var LoginComponent;
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
            },
            function (common_1_1) {
                common_1 = common_1_1;
                common_2 = common_1_1;
            }],
        execute: function() {
            LoginComponent = (function () {
                function LoginComponent(fb, _accountService) {
                    this._accountService = _accountService;
                    this.loginForm = fb.group({
                        email: ["", common_2.Validators.required],
                        password: ["", common_2.Validators.required]
                    });
                }
                LoginComponent.prototype.login = function (event) {
                    event.preventDefault();
                    this._accountService.login(this.loginForm.value.email, this.loginForm.value.password);
                };
                LoginComponent = __decorate([
                    core_1.Component({
                        selector: 'login',
                    }),
                    core_2.View({
                        templateUrl: 'app/login.html',
                    }), 
                    __metadata('design:paramtypes', [common_1.FormBuilder, account_service_1.AccountService])
                ], LoginComponent);
                return LoginComponent;
            })();
            exports_1("LoginComponent", LoginComponent);
        }
    }
});
//# sourceMappingURL=login.component.js.map