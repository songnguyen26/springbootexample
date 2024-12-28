package com.songnguyen.example05.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.songnguyen.example05.Entity.Address;
import com.songnguyen.example05.Service.AddressService;
import com.songnguyen.example05.payloads.AddressDTO;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
@SecurityRequirement(name = "E-commerce Application")
public class AddressController {
    @Autowired
    private AddressService addressService;
    @PostMapping("/address")
    public ResponseEntity<AddressDTO> createAddress(@Valid @RequestBody AddressDTO addressDTO){
        AddressDTO savedAddress=addressService.createAddress(addressDTO);
        return new ResponseEntity<AddressDTO>(savedAddress,HttpStatus.CREATED);
    }
    @GetMapping("/address")
    public ResponseEntity<List<AddressDTO>> getAddresses(){
        List<AddressDTO> addressDTO=addressService.getAddresses();
        return new ResponseEntity<List<AddressDTO>>(addressDTO,HttpStatus.FOUND);
    }
    @GetMapping("/address/{addressId}")
    public ResponseEntity<AddressDTO> getAddress(@PathVariable Long addressId){
        AddressDTO addressDTO=addressService.getAddress(addressId);
        return new ResponseEntity<AddressDTO>(addressDTO,HttpStatus.FOUND);
    }
    @PutMapping("/address/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long addressId,@RequestBody Address address){
        AddressDTO addressDTO=addressService.updateAddress(addressId, address);
        return new ResponseEntity<AddressDTO>(addressDTO,HttpStatus.OK);
    }
    @DeleteMapping("/address/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long addressId){
        String status=addressService.deleteAddress(addressId);
        return new ResponseEntity<String>(status,HttpStatus.OK);
    }
}
