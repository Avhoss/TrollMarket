package com.trollmarket.RestController;

import antlr.StringUtils;
import com.trollmarket.config.ApplicationUserDetails;
import com.trollmarket.config.authentication.CustomAuthenticationProvider;
import com.trollmarket.dto.account.RegisterAdminDTO;
import com.trollmarket.dto.account.RegisterDTO;
import com.trollmarket.dto.account.RequestTokenDTO;
import com.trollmarket.dto.account.ResponseTokenDTO;
import com.trollmarket.service.AccountService;
import com.trollmarket.service.BuyerService;
import com.trollmarket.service.SellerService;
import com.trollmarket.utility.JwtToken;
import com.trollmarket.utility.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AccountRestController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private BuyerService buyerService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;


    @PostMapping("/authenticate")
    public ResponseTokenDTO post(@RequestBody RequestTokenDTO dto) {

        System.out.println(dto);

        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getUsername());

            if(!dto.getRole().equals(userDetails.getAuthorities().toArray()[0].toString())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST
                        , "Can not authenticate, Role invalid or mismatch");
            }

            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(userDetails.getUsername()
                            , dto.getPassword()
                            , userDetails.getAuthorities());

            Authentication authentication = customAuthenticationProvider.authenticate(token);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        String token = jwtToken.generateToken(
                dto.getUsername(),
                dto.getSecretKey(),
                dto.getRole()
        );

        ResponseTokenDTO responseTokenDTO = new ResponseTokenDTO(dto.getUsername(), dto.getRole(), token);

        return responseTokenDTO;
    }


    @PostMapping("/registerForm")
    public ResponseEntity<String> registerForm(
            @RequestBody RegisterDTO registerDTO
    ){
        if(registerDTO.getRole().toLowerCase().equals("buyer")){

            registerDTO.setRole(Utilities.capitalizeFirstChar(registerDTO.getRole()));

            buyerService.save(registerDTO);

            return new ResponseEntity<>("Successfully added buyer!", HttpStatus.ACCEPTED);
        }
        else if(registerDTO.getRole().toLowerCase().equals("seller")){


            registerDTO.setRole(Utilities.capitalizeFirstChar(registerDTO.getRole()));
            sellerService.save(registerDTO);

            return new ResponseEntity<>("Successfully added seller!", HttpStatus.ACCEPTED);
        }
        else {
            return new ResponseEntity<>("Something wrong just happened in register", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/registerAdmin")
    public ResponseEntity<String> registerAdmin(
            @RequestBody RegisterAdminDTO registerAdminDTO
            ){

        if(registerAdminDTO.getRole().toLowerCase().equals("administrator")){


            registerAdminDTO.setRole(Utilities.capitalizeFirstChar(registerAdminDTO.getRole()));

            accountService.registerAccount(registerAdminDTO);

            return new ResponseEntity<>("Successfully added Admin!", HttpStatus.ACCEPTED);
        }
        else {
            return new ResponseEntity<>("Something went wrong, could be typo in role", HttpStatus.BAD_REQUEST);
        }

    }

}