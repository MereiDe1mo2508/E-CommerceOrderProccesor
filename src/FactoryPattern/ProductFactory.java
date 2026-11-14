package FactoryPattern;

import model.BookProduct;
import model.ClothingProduct;
import model.ElectronicsProduct;
import model.Product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public final class ProductFactory {
    private final Map<String, ProductCreator> creators = new HashMap<>();

    public ProductFactory() {
        register("electronics", ElectronicsProduct::new);
        register("clothing", ClothingProduct::new);
        register("book", BookProduct::new);
    }

    public void register(String type, ProductCreator creator) {
        creators.put(type.toLowerCase(), creator);
    }

    public Product createProduct(String type, String id, String name, BigDecimal price) {
        ProductCreator creator = creators.get(type.toLowerCase());
        if (creator == null) {
            throw new IllegalArgumentException("Unknown product type: " + type);
        }
        return creator.create(id, name, price);
    }
}
