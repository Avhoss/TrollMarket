package com.trollmarket.RestController;

import com.trollmarket.dto.shipment.GetShipmentDTO;
import com.trollmarket.dto.shipment.UpsertShipmentDTO;
import com.trollmarket.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/shipment")
public class RestShipmentController {

    @Autowired
    ShipmentService shipmentService;

    @GetMapping(value = {"", "/{page}"})
    public ResponseEntity<List<GetShipmentDTO>> getAllShipment
            (@PathVariable(required = false) Integer page) {
        page = (page == null) ? 1 : page;
        return new ResponseEntity<>(shipmentService.findAllShipment(page).getContent(),
                HttpStatus.FOUND);
    }
    @PostMapping
    public ResponseEntity<String> addShipment(@Valid @RequestBody UpsertShipmentDTO upsertShipmentDTO){
        shipmentService.save(upsertShipmentDTO);
        return new ResponseEntity<>("Succesfully adding Shipment : " + upsertShipmentDTO.getName(),
                HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<String> updateShipment(@Valid @RequestBody UpsertShipmentDTO upsertShipmentDTO){
        shipmentService.save(upsertShipmentDTO);
        return new ResponseEntity<>("Succesfully Update Shipment : " + upsertShipmentDTO.getName(),
                HttpStatus.CREATED);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<String> serviceShipment(@PathVariable Long id){
        shipmentService.updateService(id);
        return new ResponseEntity<>("Succesfully Patch Shipment Service with ID : " +id,HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteShipment(@PathVariable Long id){
        shipmentService.delete(id);
        return new ResponseEntity<>("Succesfully Delete Shipment With ID : "+ id,HttpStatus.OK);
    }

}
