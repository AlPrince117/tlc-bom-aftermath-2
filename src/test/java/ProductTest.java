import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductTest {

    MontrealTradedProductsImpl montrealTradedProducts;
    Product product;
    ProductPricingService productPricingService;

    @BeforeEach
    void setup() {
        montrealTradedProducts = new MontrealTradedProductsImpl();
        // create a product - mock
        product = mock(Product.class); //new Stock(12, aapl, exh,...
        when(product.getId()).thenReturn("12");

         productPricingService = mock(ProductPricingService.class);
        //stock
        when(productPricingService.price(anyString(), anyString())).thenReturn(1.0);
        // future
        when(productPricingService.price(anyString(), anyString(), anyInt(), anyInt())).thenReturn(2.0);
    }

    @Test
    public void productCanBeAddedAndDuplicateProductThrowsProductAlreadyExistedException() throws ProductAlreadyRegisteredException {
        //add a product
        montrealTradedProducts.addNewProduct(product);
        //add the product again - exception
        assertThrows(ProductAlreadyRegisteredException.class, () -> montrealTradedProducts.addNewProduct(product), "Duplicate products does not throw exception");
    }


    @Test
    public void productCanBeTraded() throws ProductAlreadyRegisteredException {
        //add a product
        montrealTradedProducts.addNewProduct(product);
        //trade the product
        montrealTradedProducts.trade(product, 4);
    }

    @Test
    public void productTradedQuantityIsValid() throws ProductAlreadyRegisteredException {
        //add a product
        montrealTradedProducts.addNewProduct(product);
        //trade the product
        montrealTradedProducts.trade(product, 4);

        Product product1 = mock(Product.class);
        when(product1.getId()).thenReturn("13");
        montrealTradedProducts.addNewProduct(product1);
        montrealTradedProducts.trade(product1, 5);
        //Albert and Joy - 5,
        assertEquals(9, montrealTradedProducts.totalTradeQuantityForDay(), "Product traded quantity is not valid");
    }

    @Test
    public void totalValueOfDaysTradedProductsIsValid() throws ProductAlreadyRegisteredException {
        //a - 1, 4 = 4
        Product stock = new Stock("12", "AAPL", "EXCH1", productPricingService);
        montrealTradedProducts.addNewProduct(stock);
        montrealTradedProducts.trade(stock, 4);

        //b - price - 2, 5 = 10
        Product future = new Future("13", "excg2", "ddfa", 4, 6, productPricingService);
        montrealTradedProducts.addNewProduct(future);
        montrealTradedProducts.trade(future, 5);
        //14
        assertEquals(14, montrealTradedProducts.totalValueOfDaysTradedProducts(), "Total value of days traded products is not valid");
    }

}