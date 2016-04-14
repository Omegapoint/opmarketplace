package json.marketplace;

public class MarketplaceLog {

    public final CreateItemRequest createItemRequest;
    public final ChangeItemRequest changeItemRequest;
    public final GetItemRequest getItemRequest;
    public final PurchaseItemRequest purchaseItemRequest;
    public final SearchItemRequest searchItemRequest;

    public MarketplaceLog(CreateItemRequest createItemRequest,
                          ChangeItemRequest changeItemRequest,
                          GetItemRequest getItemRequest,
                          PurchaseItemRequest purchaseItemRequest,
                          SearchItemRequest searchItemRequest) {
        this.createItemRequest = createItemRequest;
        this.changeItemRequest = changeItemRequest;
        this.getItemRequest = getItemRequest;
        this.purchaseItemRequest = purchaseItemRequest;
        this.searchItemRequest = searchItemRequest;
    }
}
