package com.interchange.service.impl;

import com.interchange.base.BaseResponse;
import com.interchange.dto.ProductDTO.AddProductDTO;
import com.interchange.entities.*;
import com.interchange.repository.*;
import com.interchange.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl extends BaseResponse implements ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRoomRepository categoryRoomRepository;
    @Autowired
    CategoryProductRepository categoryProductRepository;
    @Autowired
    MaterialRepository materialRepository;
    @Autowired
    CategoryMaterialRepository categoryMaterialRepository;
    @Autowired
    MeasureUnitRepository measureUnitRepository;
    @Autowired
    SupplierRepository supplierRepository;
    @Autowired
    SupplierProductRepository supplierProductRepository;
    @Autowired
    ProductDetailRepository productDetailRepository;

    @Override
    public ResponseEntity<?> findAll() {
        return getResponseEntity(productRepository.findAll());
    }

    @Override
    public ResponseEntity<?> findAllProductDetailByRoomId(int roomId) {
        return getResponseEntity(productRepository.findAllProductDetailByRoomId(roomId));
    }

    @Override
    public ResponseEntity<?> inputSupplierProduct(int proId, int supId) {
        return getResponseEntity(productRepository.inputSupplierProduct(proId, supId));
    }

    @Override
    public ResponseEntity<?> findAllProductByRoomCategoryId(int roomCategoryId) {
        return getResponseEntity(categoryRoomRepository.findAllProductByRoomCategoryId(roomCategoryId));
    }

    @Override
    public ResponseEntity<?> listProduct() {
        return getResponseEntity(productRepository.listProduct());
    }

    @Override
    @Transactional
    public ResponseEntity<?> AddProduct(AddProductDTO addProductDTO) {
        try {
            List<SupplierProduct> supplierProducts = new ArrayList<>();
            CategoryMaterial categoryMaterial = getCategoryMaterial(addProductDTO);
            if(categoryMaterial == null) return getResponseEntity("Error");

            Product product = createProduct(addProductDTO, categoryMaterial);
            productRepository.save(product);

            createAndSaveSupplierProducts(addProductDTO, product, supplierProducts);
            createAndSaveProductDetails(addProductDTO, product, supplierProducts);
            return getResponseEntity("Add Successfully!");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    private CategoryMaterial getCategoryMaterial(AddProductDTO addProductDTO) {
        int materialId = materialRepository.findMaterialByMaterialName(addProductDTO.getMaterialName()).getMaterialId();
        int categoryId = categoryProductRepository.findCategoryProductByCategoryName(addProductDTO.getCategoryName()).getProCategoryId();
        return categoryMaterialRepository.findCategoryMaterialByCategoryProductAndMaterial(categoryId, materialId);
    }
    private Product createProduct(AddProductDTO addProductDTO, CategoryMaterial categoryMaterial) {
        Product product = new Product();
        product.setProName(addProductDTO.getProductName());
        product.setProDescription(addProductDTO.getProductDescription());
        product.setProColor(addProductDTO.getProductColor());
        product.setCategoryMaterial(categoryMaterial);
        product.setMeasureUnit(measureUnitRepository
                .findFirstByMeasureUnitId(addProductDTO.getMeasureUnitId()));
        product.setCategoryRoom(categoryRoomRepository.findCategoryRoomByCategoryName(addProductDTO.getRoomCategoryName()));
        return product;
    }

    private void createAndSaveSupplierProducts(AddProductDTO addProductDTO, Product product,List<SupplierProduct> supplierProducts) {
        for(Map.Entry<Integer, Double> entry : addProductDTO.getUnitPrices().entrySet()) {
            SupplierProduct supplierProduct = new SupplierProduct();
            supplierProduct.setUnitPrice(entry.getValue());
            supplierProduct.setProduct(product);
            supplierProduct.setSupplier(supplierRepository.getFirstBySupId(entry.getKey()));
            supplierProductRepository.save(supplierProduct);
            supplierProducts.add(supplierProduct);
        }
    }
    private void createAndSaveProductDetails(AddProductDTO addProductDTO, Product product, List<SupplierProduct> supplierProducts) {
        int i = 0;
        for (Map.Entry<Integer, Double> entry : addProductDTO.getUnitPrices().entrySet()) {
            ProductDetail productDetail = new ProductDetail();
            productDetail.setProLength(addProductDTO.getLength());
            productDetail.setProHeight(addProductDTO.getHeight());
            productDetail.setProWidth(addProductDTO.getWidth());
            productDetail.setProPrice(calculatePrice(addProductDTO, product, entry.getKey()));
            productDetail.setSupplierProduct(supplierProducts.get(i));
            productDetailRepository.save(productDetail);
            i++;
        }
    }

    private double calculatePrice(AddProductDTO addProductDTO, Product product, int supplierId) {
        double unitPrice = addProductDTO.getUnitPrices().get(supplierId);
        if(product.getMeasureUnit().getMeasureUnitName().equals("Mét Dài")) {
            return addProductDTO.getLength() * unitPrice;
        } else if (product.getMeasureUnit().getMeasureUnitName().equals("Mét Vuông")) {
            if (product.getMeasureUnit().isCusLength() && product.getMeasureUnit().isCusWidth()) {
                return addProductDTO.getLength() * addProductDTO.getWidth() * unitPrice;
            } else if (product.getMeasureUnit().isCusLength() && !product.getMeasureUnit().isCusWidth()) {
                return addProductDTO.getLength() * unitPrice;
            }
        }
        return 0;
    }


}
