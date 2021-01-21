package com.springframework.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.springframework.configuration.RepositoryConfiguration;
import com.springframework.domain.Product;
import com.springframework.repositories.ProductRepository;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RepositoryConfiguration.class})
public class ProductRepositoryTest {
    private ProductRepository productRepository;
    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Test
    public void testSaveProduct(){
        //setup product
        Product product = new Product();
        product.setDescription("Spring Framework Guru Shirt");
        product.setPrice(new BigDecimal("18.95"));
        product.setProductId("1234");
        //save product, verify has ID value after save
        assertNull(product.getId()); //null before save
        productRepository.save(product);
        assertNotNull(product.getId()); //not null after save
       
        Product fetchedProduct = productRepository.findById(product.getId()).orElse(null); //fetch from DB
        
        assertNotNull(fetchedProduct);//should not be null
        
        assertEquals(product.getId(), fetchedProduct.getId());//should equal
        assertEquals(product.getDescription(), fetchedProduct.getDescription());
        
        fetchedProduct.setDescription("New Description");//update description and save
        productRepository.save(fetchedProduct);
       
        Product fetchedUpdatedProduct = productRepository.findById(fetchedProduct.getId()).orElse(null); //get from DB, should be updated
        assertEquals(fetchedProduct.getDescription(), fetchedUpdatedProduct.getDescription());
       
        long productCount = productRepository.count(); //verify count of products in DB
        assertEquals(productCount, 1);
        
        Iterable<Product> products = productRepository.findAll();//get all products, list should only have one
        int count = 0;
        for(Product p : products){
            count++;
        }
        assertEquals(count, 1);
    }
}
