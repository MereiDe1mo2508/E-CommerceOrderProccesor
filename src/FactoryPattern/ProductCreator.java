package FactoryPattern;

import model.Product;

import java.math.BigDecimal;

@FunctionalInterface
public interface ProductCreator {
    Product create(String id, String name, BigDecimal price);

}
