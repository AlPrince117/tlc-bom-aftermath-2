import java.util.*;

public class MontrealTradedProductsImpl implements MontrealTradedProducts {
    // new Stock(AAPL), MST, GOOGLE
    private List<Product> registeredProductsList = new ArrayList<>();

    private Map<Product, Integer> tradedProductsMap = new HashMap<>();
    // Google stock -> 4,
    // Apple stock -> 6,


    @Override
    public void addNewProduct(Product product) throws ProductAlreadyRegisteredException {
        // product does not already exist, else throw an exception
        // java streams throughout - id 34
        Optional<Product> optionalProduct = this.registeredProductsList
                .stream()
                .filter(p -> p.getId().equals(product.getId()))
                .findFirst();
        if (optionalProduct.isPresent()) {
            throw new ProductAlreadyRegisteredException("Product with id " + product.getId() + " already exists!");
        }
        this.registeredProductsList.add(product);
    }

    @Override
    public void trade(Product product, int quantity) {
        if (!registeredProductsList.contains(product)) {
            return;
        }
        // new Stock("12", "AAPL", "EXCH" ...), 4
        // apple, 2 == 6
        //data = {"age": 12, "name" : "Angel"} --- data["age"], data.get("age") - 12
        if (tradedProductsMap.containsKey(product)) {
            int previousQuantity = tradedProductsMap.get(product);
            tradedProductsMap.put(product, previousQuantity + quantity);
        } else {
            tradedProductsMap.put(product, quantity);
        }
    }

    @Override
    public int totalTradeQuantityForDay() {
        //google, 4
        // microsoft, 5
        //apple, 2
        // 4 + 5 + 2 = 11
        //key - product, value - quantity -- we want the map to just give us only the values, then we sum it.

        return this.tradedProductsMap.values().stream().mapToInt(q -> q).sum();
    }

    @Override
    public double totalValueOfDaysTradedProducts() {

//        double totalValue = 0.0;
//        for (Map.Entry<Product, Integer> productEntry: tradedProductsMap.entrySet()) {
//            double price = productEntry.getKey().getPrice() * productEntry.getValue();
//            totalValue += price;
//        }

        //return totalValue;
//        return this.tradedProductsMap.entrySet()
//                .stream()
//                .mapToDouble(productEntry -> productEntry.getKey().getPrice() * productEntry.getValue())
//                .sum();

        //[ (apple, 4), (google, 5)  ]
        // (apple, 4) - key - aaple, value - 4

        //(apple), (google)
        // (4), (5)
       return this.tradedProductsMap.keySet().stream()
                .mapToDouble(p -> p.getPrice() * tradedProductsMap.get(p))
                .sum();
    }

}
