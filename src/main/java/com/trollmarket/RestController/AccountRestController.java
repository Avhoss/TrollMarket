package com.trollmarket.RestController;

import com.trollmarket.config.ApplicationUserDetails;
import com.trollmarket.config.authentication.CustomAuthenticationProvider;
import com.trollmarket.dto.account.RequestTokenDTO;
import com.trollmarket.dto.account.ResponseTokenDTO;
import com.trollmarket.service.AccountService;
import com.trollmarket.utility.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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

}