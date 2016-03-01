System.register(['angular2/core', "angular2/core", "angular2/common", 'rxjs/add/operator/map', "./account.service"], function(exports_1) {
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var core_1, core_2, common_1, common_2, account_service_1, account_service_2, account_service_3;
    var RegisterComponent;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (core_2_1) {
                core_2 = core_2_1;
            },
            function (common_1_1) {
                common_1 = common_1_1;
                common_2 = common_1_1;
            },
            function (_1) {},
            function (account_service_1_1) {
                account_service_1 = account_service_1_1;
                account_service_2 = account_service_1_1;
                account_service_3 = account_service_1_1;
            }],
        execute: function() {
            RegisterComponent = (function () {
                function RegisterComponent(fb, _accountService) {
                    this._accountService = _accountService;
                    this.registerForm = fb.group({
                        firstName: ["", common_2.Validators.required],
                        lastName: ["", common_2.Validators.required],
                        email: ["", common_2.Validators.required],
                        password: ["", common_2.Validators.required]
                    });
                }
                RegisterComponent.prototype.registerAccount = function (event) {
                    event.preventDefault();
                    var newAccount = new account_service_2.Account(this.registerForm.value.email, new account_service_3.User(this.registerForm.value.firstName, this.registerForm.value.lastName));
                    this._accountService.registerAccount(newAccount);
                };
                RegisterComponent = __decorate([
                    core_1.Component({
                        selector: 'register',
                    }),
                    core_2.View({
                        templateUrl: 'app/register.html',
                    }), 
                    __metadata('design:paramtypes', [common_1.FormBuilder, account_service_1.AccountService])
                ], RegisterComponent);
                return RegisterComponent;
            })();
            exports_1("RegisterComponent", RegisterComponent);
        }
    }
});
//# sourceMappingURL=register.component.js.map