package com.staskost.eshop.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.staskost.eshop.jms.SendTextMessage;
import com.staskost.eshop.model.Product;
import com.staskost.eshop.repos.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	private ProductRepository productRepository;
	
	private SendTextMessage sendTextMessageService;

	public ProductServiceImpl(ProductRepository productRepository, SendTextMessage sendTextMessageService) {
		this.productRepository = productRepository;
		this.sendTextMessageService = sendTextMessageService;
	}

	private Product returnProductOrException(int id) {
		Optional<Product> opt = productRepository.findById(id);
		if (opt.isPresent()) {
			Product pr = opt.get();
			return pr;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found");
		}
	}

	public void saveProduct(Product product) {
		productRepository.save(product);
	}

	public void updateProduct(int id, Product product) {
		Product pr = returnProductOrException(id);
		pr.setName(product.getName());
		pr.setPrice(product.getPrice());
		pr.setCategory(product.getCategory());
		pr.setDescription(product.getDescription());
		productRepository.save(pr);
	}

	public Product getById(int id) {
		sendTextMessageService.sendTextMessage("Requested product with id : "+id);
		Product product = returnProductOrException(id);
		return product;
	}

	public List<Product> getAllProducts() {
		sendTextMessageService.sendTextMessage("Listing all products");
		return productRepository.findAll();
	}

	public Product findByName(String name) {
		Product product = productRepository.findByName(name);
		if (product != null) {
			return product;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found");
		}

	}

	public List<Product> findByNameLike(String name) {
		return productRepository.findByNameContainingIgnoreCase(name);

	}

	public List<Product> findByNameStartsWith(String name) {
		return productRepository.findByNameStartsWithIgnoreCase(name);
	}

	public List<Product> findByPrice(double price) {
		return productRepository.findByPrice(price);
	}

	public List<Product> findByPriceBetween(double minPrice, double maxPrice) {
		return productRepository.findByPriceBetween(minPrice, maxPrice);
	}

	public void setProductPrice(double price, int id) {
		Product product = returnProductOrException(id);
		product.setPrice(price);
		productRepository.save(product);
	}

	public void removeItemfromProductCount(int items, int id) {
		Product product = returnProductOrException(id);
		int count = product.getProductCount();
		if (items <= 0 || items > count) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid number of items");
		}
		product.setProductCount(count - items);
		productRepository.save(product);
		Product p = returnProductOrException(id);
		int countAfterRemoval = p.getProductCount();
		if (countAfterRemoval <= 0) {
			p.setIsAvailabe(0);
			productRepository.save(product);
		}
	}

	public void addItemToProductCount(int items, int id) {
		Product product = returnProductOrException(id);
		int count = product.getProductCount();
		if (items <= 0) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid number of items");
		}
		product.setProductCount(count + 1);
		productRepository.save(product);
		product.setProductCount(count - items);
		productRepository.save(product);
		Optional<Product> opt2 = productRepository.findById(id);
		Product p = opt2.get();
		int countAfterAddition = p.getProductCount();
		if (countAfterAddition > 0) {
			p.setIsAvailabe(1);
			productRepository.save(product);
		}
	}

	public void removeProduct(int id) {
		Product product = returnProductOrException(id);
		productRepository.delete(product);
	}

	public void addProduct(Product product) {
		if (product != null) {
			productRepository.save(product);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found");
		}
	}

	@Override
	public List<Product> findByCategory(String category) {
		List<Product> products = productRepository.findByCategory(category);
		return products;
	}
}
