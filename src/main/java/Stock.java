public class Stock extends Product  {
    private String ticker, exchange;
    private final ProductPricingService productPricingService;

    public Stock(String id, String ticker, String exchange, ProductPricingService productPricingService) {
        super(id);
        this.ticker = ticker;
        this.exchange = exchange;
        this.productPricingService = productPricingService;
    }

    @Override
    public double getPrice() {
        return this.productPricingService.price(this.exchange, this.ticker);
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }
}
