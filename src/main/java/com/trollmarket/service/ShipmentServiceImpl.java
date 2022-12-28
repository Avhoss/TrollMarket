package com.trollmarket.service;
import com.trollmarket.dao.CartRepository;
import com.trollmarket.dao.ShipmentRepository;
import com.trollmarket.dto.shipment.GetShipmentDTO;
import com.trollmarket.dto.shipment.UpsertShipmentDTO;
import com.trollmarket.entity.Shipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipmentServiceImpl implements ShipmentService{

    @Autowired
    ShipmentRepository shipmentRepository;

    @Autowired
    CartRepository cartRepository;

    private final int rowsInPage = 5;


    public Page<GetShipmentDTO> findAllShipment(Integer page) {
        Pageable pagination = PageRequest.of(page - 1, rowsInPage, Sort.by("s.id"));
        return shipmentRepository.findAllShipment(pagination);
    }

    @Override
    public void save(UpsertShipmentDTO upsertShipmentDTO) {
        Shipment shipment = new Shipment(
                upsertShipmentDTO.getId(),
                upsertShipmentDTO.getName(),
                upsertShipmentDTO.getPrice(),
                upsertShipmentDTO.getService()
        );
        if(upsertShipmentDTO.getId()!=null){
            shipment.setService(shipmentRepository.findById(upsertShipmentDTO.getId()).get().getService());
        }
        shipmentRepository.save(shipment);
    }

    @Override
    public void delete(Long id) {
        if(cartRepository.findCartByShipment(id).size() > 0){
            cartRepository.findCartByShipment(id).forEach(cart -> {
                    cartRepository.deleteById(cart.getId());
            });
        }
        shipmentRepository.deleteById(id);
    }

    @Override
    public UpsertShipmentDTO findUpsertShipmentById(Long id) {
        Shipment shipment = shipmentRepository.findById(id).get();
        UpsertShipmentDTO upsertShipmentDTO = new UpsertShipmentDTO(
                shipment.getId(),
                shipment.getName(),
                shipment.getPrice(),
                shipment.getService()
        );
        return upsertShipmentDTO;
    }

    @Override
    public void updateService(Long id) {
        Shipment shipment = shipmentRepository.findById(id).get();
        shipment.setService(false);
        shipmentRepository.save(shipment);
    }

    @Override
    public List<Shipment> findAllShipmentService() {
        return shipmentRepository.findAllShipmentService();
    }

}
