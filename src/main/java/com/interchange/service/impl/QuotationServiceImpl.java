package com.interchange.service.impl;

import com.interchange.base.BaseResponse;
import com.interchange.entities.*;
import com.interchange.entities.DTO.MainProjectDTO.MainProjectDTO;
import com.interchange.entities.DTO.MainProjectDTO.ProductDetailDTO;
import com.interchange.entities.DTO.MainProjectDTO.ProjectDTO;
import com.interchange.entities.DTO.MainProjectDTO.RoomDTO;
import com.interchange.repository.*;
import com.interchange.service.QuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class QuotationServiceImpl extends BaseResponse implements QuotationService {

    @Autowired
    private QuotationRepository quotationRepository;
    @Autowired
    private SupplierProductRepository supplierProductRepository;
    @Autowired
    private CategoryProjectRepository categoryProjectRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private CategoryRoomRepository categoryRoomRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ProductDetailRepository productDetailRepository;
    @Autowired
    private RoomProductRepository roomProductRepository;

    @Override
    public ResponseEntity<?> findAllPendingQuotationByTime(LocalDate requestTime) {
        return getResponseEntity(quotationRepository.findAllPendingQuotationByTime(requestTime));
    }

    @Override
    public ResponseEntity<?> findQuotationById(int quotationId) {
        return getResponseEntity(quotationRepository.findQuotationById(quotationId));
    }

    @Override
    public ResponseEntity<?> findQuotationEagerById(int quotationId) {
        return getResponseEntity(quotationRepository.findById(quotationId));
    }

    @Override
    public ResponseEntity<?> saveQuotation(MainProjectDTO mainProjectDTO) {
        quotationRepository.findById(mainProjectDTO.getQuotations().get(0).getQuotationId()).get()
                .setStatus(mainProjectDTO.getQuotations().get(0).getStatus());
        Project project = saveProject(mainProjectDTO);
        int supId = mainProjectDTO.getQuotations().get(0).getProject().getSupplierId();
        for (RoomDTO roomDTO : mainProjectDTO.getQuotations().get(0).getProject().getRooms()) {
            Room room = saveRoom(project, roomDTO);
            for (ProductDetailDTO productDetailDTO: roomDTO.getProductDetailList()) {
                ProductDetail productDetail = saveProductDetail(supId, productDetailDTO);
                saveRoomProduct(room, productDetail, productDetailDTO.getQuantity(), productDetailDTO.getTotalPrice());
            }
        }
        return getResponseEntity("Save new quotation successfully");
    }

    public Project saveProject(MainProjectDTO mainProjectDTO) {
        ProjectDTO projectDTO = mainProjectDTO.getQuotations().get(0).getProject();
        int projId = projectDTO.getProjId();
        String projName = projectDTO.getProjName();
        int projectCategoryId = projectDTO.getProjectCategoryId();
        int quotationId = projectDTO.getQuotationId();

        Project project = new Project();
        if(projId!=0) {
            project.setProjId(projId);
        }
        project.setProjName(projName);
        project.setCategoryProject(categoryProjectRepository.findById(projectCategoryId).get());
        project.setQuotation(quotationRepository.findById(quotationId).get());
        return projectRepository.save(project);
    }

    public Room saveRoom(Project project, RoomDTO roomDTO) {
        Room room = new Room();
        if(roomDTO.getRoomId()!=0) room.setRoomId(roomDTO.getRoomId());
        room.setRoomName(roomDTO.getRoomName());
        room.setCategoryRoom(categoryRoomRepository.findById(roomDTO.getRoomCategoryId()).get());
        room.setProject(project);
        return roomRepository.save(room);
    }

    public ProductDetail saveProductDetail(int supId, ProductDetailDTO productDetailDTO) {
        int quantity = productDetailDTO.getQuantity();
        double totalPrice = productDetailDTO.getTotalPrice();
        ProductDetail productDetail = new ProductDetail();
        productDetail.setProLength(productDetailDTO.getProLength());
        productDetail.setProWidth(productDetailDTO.getProWidth());
        productDetail.setProHeight(productDetailDTO.getProHeight());
        productDetail.setProPrice(totalPrice/quantity);
        productDetail.setSupplierProduct(supplierProductRepository.findBySupplier_SupIdAndProduct_ProId(supId, productDetailDTO.getProId()));
        return productDetailRepository.save(productDetail);
    }

    public void saveRoomProduct(Room room, ProductDetail productDetail, int quantity, double totalPrice) {
        RoomProduct roomProduct = new RoomProduct();
        roomProduct.setRoom(room);
        roomProduct.setProductDetail(productDetail);
        roomProduct.setQuantity(quantity);
        roomProduct.setTotalPrice(totalPrice);
        roomProductRepository.save(roomProduct);
    }

}
