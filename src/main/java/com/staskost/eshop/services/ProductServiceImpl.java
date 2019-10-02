package com.staskost.eshop.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.staskost.eshop.model.Product;
import com.staskost.eshop.repos.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	private ProductRepository productRepository;

	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public void createProduct(Product product) {
		productRepository.save(product);
	}

	public void updateProduct(int id, Product product) {
		Optional<Product> opt = productRepository.findById(id);
		if (opt.isPresent()) {
			Product pr = opt.get();
			pr.setName(product.getName());
			pr.setPrice(product.getPrice());
			pr.setCategory(product.getCategory());
			pr.setDescription(product.getDescription());
			productRepository.save(pr);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found");
		}
	}

	public void deleteProduct(int id) {
		Optional<Product> opt = productRepository.findById(id);
		if (opt.isPresent()) {
			Product product = opt.get();
			productRepository.delete(product);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found");
		}
	}

	public Product getById(int id) {
		Optional<Product> opt = productRepository.findById(id);
		if (opt.isPresent()) {
			Product product = opt.get();
			return product;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found");
		}
	}

	public List<Product> getAllProducts() {
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
		Optional<Product> opt = productRepository.findById(id);
		if (opt.isPresent()) {
			Product product = opt.get();
			product.setPrice(price);
			productRepository.save(product);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found");
		}

	}

	public void removeItemfromProductCount(int items, int id) {
		Optional<Product> opt = productRepository.findById(id);
		if (opt.isPresent()) {
			Product product = opt.get();
			int count = product.getProductCount();
			if (items <= 0 || items > count) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid number of items");
			}
			product.setProductCount(count - items);
			productRepository.save(product);
			Optional<Product> opt2 = productRepository.findById(id);
			Product p = opt2.get();
			int countAfterRemoval = p.getProductCount();
			if (countAfterRemoval <= 0) {
				p.setIsAvailabe(0);
				productRepository.save(product);
			}
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found");
		}
	}

	public void addItemfromProductCount(int items, int id) {
		Optional<Product> opt = productRepository.findById(id);
		if (opt.isPresent()) {
			Product product = opt.get();
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
	}

	public void removeProduct(int id) {
		Optional<Product> opt = productRepository.findById(id);
		if (opt.isPresent()) {
			Product product = opt.get();
			productRepository.save(product);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found");
		}
	}

	public void addProduct(Product product) {
		if (product != null) {
			productRepository.save(product);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found");
		}
	}
}
