package com.trollmarket.controller;

import com.trollmarket.dto.shipment.UpsertShipmentDTO;
import com.trollmarket.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/shipment")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;

    @GetMapping("/index")
    public String index(@RequestParam(defaultValue = "1") Integer page , Model model){
        Integer totalPage = shipmentService.findAllShipment(1).getTotalPages();

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPage);
        model.addAttribute("shipments",shipmentService.findAllShipment(page));
        model.addAttribute("upsertShipment",new UpsertShipmentDTO());
        return "shipment/shipment";
    }

    @PostMapping("/upsert")
    public String upsert(@Valid @ModelAttribute("upsertShipment")UpsertShipmentDTO upsertShipmentDTO,
                         BindingResult bindingResult,Model model){
        Integer totalPage = shipmentService.findAllShipment(1).getTotalPages();
        if(bindingResult.hasErrors()){
            model.addAttribute("hasErrors",true);
            model.addAttribute("currentPage", 1);
            model.addAttribute("totalPages", totalPage);
            model.addAttribute("shipments",shipmentService.findAllShipment(1));
            return "shipment/shipment";
        }
        shipmentService.save(upsertShipmentDTO);
        return "redirect:/shipment/index";
    }
    @GetMapping("/updateService")
    public String service(@RequestParam Long id,@RequestParam Integer page){
        shipmentService.updateService(id);
        return "redirect:/shipment/index?page="+page;
    }

    @GetMapping("delete")
    public String delete(@RequestParam Long id,@RequestParam Integer page){
        shipmentService.delete(id);
        return "redirect:/shipment/index?page="+page;
    }

    @GetMapping("/edit")
    @ResponseBody
    public UpsertShipmentDTO editForm(@RequestParam Long id){
        if(id==0){
            return new UpsertShipmentDTO();
        }
        return shipmentService.findUpsertShipmentById(id);
    }

}
