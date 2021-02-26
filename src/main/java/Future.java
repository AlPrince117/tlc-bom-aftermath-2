public class Future extends Product {
    private String exchange, contractCode;
    private int month, year;

    private final ProductPricingService productPricingService;

    public Future(String id, String exchange, String contractCode, int month, int year, ProductPricingService productPricingService) {
        super(id);
        this.exchange = exchange;
        this.contractCode = contractCode;
        this.month = month;
        this.year = year;
        this.productPricingService = productPricingService;
    }

    @Override
    public double getPrice() {
        return this.productPricingService.price(this.exchange, this.contractCode, this.month, this.year);
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
