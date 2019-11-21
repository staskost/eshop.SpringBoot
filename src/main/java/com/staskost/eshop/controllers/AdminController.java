package com.staskost.eshop.controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.staskost.eshop.model.Product;
import com.staskost.eshop.model.User;
import com.staskost.eshop.payload.UploadFileResponse;
import com.staskost.eshop.services.FileStorageService;
import com.staskost.eshop.services.ProductService;
import com.staskost.eshop.services.UserService;

@RestController
@RequestMapping("secured/admin")
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	private FileStorageService fileStorageService;

	private UserService userService;

	private ProductService productService;

	public AdminController(UserService userService, ProductService productService,
			FileStorageService fileStorageService) {
		super();
		this.userService = userService;
		this.productService = productService;
		this.fileStorageService = fileStorageService;
	}

	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.getAll();
		return new ResponseEntity<>(users, HttpStatus.OK);

	}

	@PostMapping("create/product")
	public ResponseEntity<String> createProduct(@RequestBody Product product) {
		if (product.getPrice() < 0) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid Price");
		}
		productService.saveProduct(product);
		return ResponseEntity.status(HttpStatus.OK)
				.body("Product  " + product.getName() + " was successfully created.");
	}

	@PutMapping("update/product/{id}")
	public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestBody Product product) {
		if (product.getPrice() < 0) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid Price");
		}
		productService.updateProduct(id, product);
		return ResponseEntity.status(HttpStatus.OK)
				.body("Product  " + product.getName() + " was successfully updated.");
	}

	@PostMapping("/set/price/{id}/{price}")
	public ResponseEntity<String> setProductPrice(@PathVariable int id, @PathVariable double price) {
		if (price < 0) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid Price");
		}
		productService.setProductPrice(price, id);
		return ResponseEntity.status(HttpStatus.OK).body("Price was set to " + price);
	}

	@PostMapping("/add/product")
	public ResponseEntity<String> addNewProduct(@RequestBody Product product) {
		productService.addProduct(product);
		return ResponseEntity.status(HttpStatus.OK).body("Product " + product.getName() + " was successfully added.");
	}

	@DeleteMapping("/remove/product/{id}")
	public ResponseEntity<String> removeProduct(@PathVariable int id) {
		productService.removeProduct(id);
		return ResponseEntity.status(HttpStatus.OK).body("Product was removed successfully.");
	}

	@PostMapping("/add/items/{items}/{id}")
	public ResponseEntity<String> addItems(@PathVariable int items, @PathVariable int id) {
		productService.addItemToProductCount(items, id);
		return ResponseEntity.status(HttpStatus.OK).body("Items added successfully.");
	}

	@PostMapping("/remove/items/{items}/{id}")
	public ResponseEntity<String> removeItems(@PathVariable int items, @PathVariable int id) {
		productService.removeItemfromProductCount(items, id);
		return ResponseEntity.status(HttpStatus.OK).body("Items removed successfully.");
	}

	@PostMapping("/uploadFile")
	public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
		String fileName = fileStorageService.storeFile(file);

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/downloadFile/")
				.path(fileName).toUriString();

		return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
	}

	@PostMapping("/uploadMultipleFiles")
	public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
		return Arrays.asList(files).stream().map(file -> uploadFile(file)).collect(Collectors.toList());
	}

	@GetMapping("/downloadFile/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
		Resource resource = fileStorageService.loadFileAsResource(fileName);
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			logger.info("Could not determine file type.");
		}
		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	@PostMapping("/savePhotoLink/{productId}")
	public void savePhotoLink(@PathVariable int productId, @RequestBody String photoLink) {
		Product product = productService.getById(productId);
		product.setPhotLink(photoLink);
		productService.saveProduct(product);
	}
}
