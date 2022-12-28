package com.trollmarket.service;

import com.trollmarket.dto.shipment.GetShipmentDTO;
import com.trollmarket.dto.shipment.UpsertShipmentDTO;
import com.trollmarket.entity.Shipment;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ShipmentService {

    Page<GetShipmentDTO>findAllShipment(Integer page);

    void save(UpsertShipmentDTO upsertShipmentDTO);

    void delete(Long id);

    UpsertShipmentDTO findUpsertShipmentById(Long id);

    void updateService(Long id);

    List<Shipment> findAllShipmentService();

    Shipment findById(Long id);
}
