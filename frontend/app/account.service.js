System.register(['angular2/core', "angular2/http"], function(exports_1) {
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var core_1, http_1;
    var User, Account, AccountService;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (http_1_1) {
                http_1 = http_1_1;
            }],
        execute: function() {
            User = (function () {
                function User(firstName, lastName) {
                    this.firstName = firstName;
                    this.lastName = lastName;
                }
                return User;
            })();
            exports_1("User", User);
            Account = (function () {
                function Account(email, user) {
                    this.email = email;
                    this.user = user;
                }
                return Account;
            })();
            exports_1("Account", Account);
            AccountService = (function () {
                function AccountService(_http) {
                    this._http = _http;
                    this.account = new Account("hej", new User("hej", "hej"));
                }
                AccountService.prototype.currentActiveAccount = function () {
                    return new Account(this.account.email, this.account.user);
                };
                AccountService.prototype.registerAccount = function (newAccount) {
                    var _this = this;
                    var headers = new http_1.Headers();
                    headers.append('Content-Type', 'application/json');
                    this._http.post("http://localhost:8001/accounts", JSON.stringify(newAccount), { headers: headers })
                        .subscribe(function (response) { return _this.handleRegistrationResponse(response, newAccount); });
                };
                AccountService.prototype.handleRegistrationResponse = function (response, account) {
                    console.log(response);
                    if (response.status == 201) {
                        this.account = account;
                        console.log("ok");
                    }
                };
                AccountService.prototype.login = function (email, password) {
                    var _this = this;
                    var headers = new http_1.Headers();
                    headers.append('Content-Type', 'application/json');
                    this._http.get("http://localhost:8001/accounts?email=" + email, { headers: headers })
                        .subscribe(function (response) { return _this.handleGetAccountResponse(response); });
                };
                AccountService.prototype.handleGetAccountResponse = function (response) {
                    console.log(response);
                    if (response.status == 200) {
                        this.account = new Account(response.json().email.address, response.json().user);
                        console.log("ok");
                    }
                };
                AccountService = __decorate([
                    core_1.Injectable(), 
                    __metadata('design:paramtypes', [http_1.Http])
                ], AccountService);
                return AccountService;
            })();
            exports_1("AccountService", AccountService);
        }
    }
});
//# sourceMappingURL=account.service.js.map