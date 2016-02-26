System.register(['angular2/platform/browser', "./container"], function(exports_1) {
    var browser_1, container_1;
    return {
        setters:[
            function (browser_1_1) {
                browser_1 = browser_1_1;
            },
            function (container_1_1) {
                container_1 = container_1_1;
            }],
        execute: function() {
            browser_1.bootstrap(container_1.Container);
        }
    }
});
//# sourceMappingURL=main.js.map