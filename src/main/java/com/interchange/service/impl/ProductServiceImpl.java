package com.interchange.service.impl;

import com.interchange.base.BaseResponse;
import com.interchange.dto.ProductDTO.AddProductDTO;
import com.interchange.dto.ProductDTO.ListProductDTO;
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
            int materialId = materialRepository.findMaterialByMaterialName(addProductDTO.getMaterialName()).getMaterialId();
            int categoryId = categoryProductRepository.findCategoryProductByCategoryName(addProductDTO.getCategoryName()).getProCategoryId();
            CategoryMaterial categoryMaterial = categoryMaterialRepository.findCategoryMaterialByCategoryProductAndMaterial(categoryId, materialId);
            int categoryMaterialId = categoryMaterial.getCategoryMaterialId();

            if(categoryMaterialId == 0) return getResponseEntity("Error");

            Product product = new Product();
            product.setProName(addProductDTO.getProductName());
            product.setProDescription(addProductDTO.getProductDescription());
            product.setProColor(addProductDTO.getProductColor());
            product.setCategoryMaterial(categoryMaterial);
            product.setMeasureUnit(measureUnitRepository
                    .findFirstByMeasureUnitId(addProductDTO.getMeasureUnitId()));
            product.setCategoryRoom(categoryRoomRepository.findCategoryRoomByCategoryName(addProductDTO.getRoomCategoryName()));
            productRepository.save(product);

            SupplierProduct supplierProduct1 = new SupplierProduct();
            supplierProduct1.setUnitPrice(addProductDTO.getUnitPriceAnCuongSupplier());
            supplierProduct1.setProduct(product);
            supplierProduct1.setSupplier(supplierRepository.getFirstBySupId(1));
            supplierProductRepository.save(supplierProduct1);

            SupplierProduct supplierProduct2 = new SupplierProduct();
            supplierProduct2.setUnitPrice(addProductDTO.getUnitPriceThanhThuySupplier());
            supplierProduct2.setProduct(product);
            supplierProduct2.setSupplier(supplierRepository.getFirstBySupId(2));
            supplierProductRepository.save(supplierProduct2);

            SupplierProduct supplierProduct3 = new SupplierProduct();
            supplierProduct3.setUnitPrice(addProductDTO.getUnitPriceSupplerMocPhatSupplier());
            supplierProduct3.setProduct(product);
            supplierProduct3.setSupplier(supplierRepository.getFirstBySupId(3));
            supplierProductRepository.save(supplierProduct3);


            ProductDetail productDetail1 = new ProductDetail();
            productDetail1.setProLength(addProductDTO.getLength());
            productDetail1.setProHeight(addProductDTO.getHeight());
            productDetail1.setProWidth(addProductDTO.getWidth());
            if(product.getMeasureUnit().getMeasureUnitName() == "Mét Dài") {
                productDetail1.setProPrice(addProductDTO.getLength() * supplierProduct1.getUnitPrice());
            } else if (product.getMeasureUnit().getMeasureUnitName() == "Mét Vuông" &&
                    product.getMeasureUnit().isCusLength() && product.getMeasureUnit().isCusWidth()) {
                productDetail1.setProPrice(addProductDTO.getLength() * addProductDTO.getWidth() * supplierProduct1.getUnitPrice());
            } else if (product.getMeasureUnit().getMeasureUnitName() == "Mét Vuông" &&
            product.getMeasureUnit().isCusLength() && !product.getMeasureUnit().isCusWidth()) {
                productDetail1.setProPrice(addProductDTO.getLength() * supplierProduct1.getUnitPrice());
            }
            productDetail1.setSupplierProduct(supplierProduct1);
            productDetailRepository.save(productDetail1);


            ProductDetail productDetail2 = new ProductDetail();
            productDetail2.setProLength(addProductDTO.getLength());
            productDetail2.setProHeight(addProductDTO.getHeight());
            productDetail2.setProWidth(addProductDTO.getWidth());
            if(product.getMeasureUnit().getMeasureUnitName() == "Mét Dài") {
                productDetail2.setProPrice(addProductDTO.getLength() * supplierProduct1.getUnitPrice());
            } else if (product.getMeasureUnit().getMeasureUnitName() == "Mét Vuông" &&
                    product.getMeasureUnit().isCusLength() && product.getMeasureUnit().isCusWidth()) {
                productDetail2.setProPrice(addProductDTO.getLength() * addProductDTO.getWidth() * supplierProduct1.getUnitPrice());
            } else if (product.getMeasureUnit().getMeasureUnitName() == "Mét Vuông" &&
                    product.getMeasureUnit().isCusLength() && !product.getMeasureUnit().isCusWidth()) {
                productDetail2.setProPrice(addProductDTO.getLength() * supplierProduct1.getUnitPrice());
            }
            productDetail2.setSupplierProduct(supplierProduct2);
            productDetailRepository.save(productDetail2);


            ProductDetail productDetail3 = new ProductDetail();
            productDetail3.setProLength(addProductDTO.getLength());
            productDetail3.setProHeight(addProductDTO.getHeight());
            productDetail3.setProWidth(addProductDTO.getWidth());
            if(product.getMeasureUnit().getMeasureUnitName() == "Mét Dài") {
                productDetail3.setProPrice(addProductDTO.getLength() * supplierProduct1.getUnitPrice());
            } else if (product.getMeasureUnit().getMeasureUnitName() == "Mét Vuông" &&
                    product.getMeasureUnit().isCusLength() && product.getMeasureUnit().isCusWidth()) {
                productDetail3.setProPrice(addProductDTO.getLength() * addProductDTO.getWidth() * supplierProduct1.getUnitPrice());
            } else if (product.getMeasureUnit().getMeasureUnitName() == "Mét Vuông" &&
                    product.getMeasureUnit().isCusLength() && !product.getMeasureUnit().isCusWidth()) {
                productDetail3.setProPrice(addProductDTO.getLength() * supplierProduct1.getUnitPrice());
            }
            productDetail3.setSupplierProduct(supplierProduct3);
            productDetailRepository.save(productDetail3);

            return getResponseEntity("Add Successfully!");

        }
        catch (Exception e) {
            return getResponseEntity(e.getMessage());
        }

    }

}
