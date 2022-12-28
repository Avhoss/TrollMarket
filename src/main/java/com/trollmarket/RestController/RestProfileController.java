package com.trollmarket.RestController;

import com.trollmarket.dto.profile.GetProfilDTO;
import com.trollmarket.dto.profile.TopupDTO;
import com.trollmarket.entity.OrderDetail;
import com.trollmarket.service.BuyerService;
import com.trollmarket.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/profile")
public class RestProfileController {

    @Autowired
    private BuyerService buyerService;

    @Autowired
    private SellerService sellerService;

    @GetMapping("/index")
    public ResponseEntity<GetProfilDTO> profile(@RequestParam(defaultValue = "1") Integer page,
                                                Authentication authentication){

        String role = authentication.getAuthorities().toArray()[0].toString().toLowerCase();
        if(role.equals("buyer")){
            GetProfilDTO buyerDTO = buyerService.findProfilByUsername(authentication.getName());
            return new ResponseEntity<>(buyerDTO, HttpStatus.FOUND);
        }else {
            GetProfilDTO sellerDTO = sellerService.findProfilByUsername(authentication.getName());
            return new ResponseEntity<>(sellerDTO, HttpStatus.FOUND);
        }

    }
}
