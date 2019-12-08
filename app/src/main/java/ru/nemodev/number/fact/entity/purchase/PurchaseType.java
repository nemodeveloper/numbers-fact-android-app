package ru.nemodev.number.fact.entity.purchase;

public enum PurchaseType {
    ADS("ads", "inapp");

    private final String sku;
    private final String itemType;

    PurchaseType(String sku, String itemType) {
        this.sku = sku;
        this.itemType = itemType;
    }

    public String getSku() {
        return sku;
    }

    public String getItemType() {
        return itemType;
    }

    public static PurchaseType getBySkuId(String skuId) {
        for (PurchaseType purchaseType : values()) {
            if (purchaseType.getSku().equals(skuId)) {
                return purchaseType;
            }
        }

        throw new IllegalArgumentException("Не корректный значение skuId = " + skuId);
    }
}
