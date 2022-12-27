package com.trollmarket.dao;

import com.trollmarket.dto.shipment.GetShipmentDTO;
import com.trollmarket.entity.Shipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

    @Query(
            """
            SELECT s 
            FROM Shipment AS s
            WHERE s.service = 1
            """
    )
    List<Shipment> findAllShipmentService();

    @Query("""
            SELECT new com.trollmarket.dto.shipment.GetShipmentDTO(
            s.id,s.name,s.price,s.service,COUNT(od.id))
            FROM OrderDetail AS od
                FULL JOIN od.shipment as s
            GROUP BY s.id,s.name,s.price,s.service
            """)
    Page<GetShipmentDTO> findAllShipment(Pageable pageable);

}

