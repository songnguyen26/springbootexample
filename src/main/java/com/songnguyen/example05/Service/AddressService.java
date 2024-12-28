package com.songnguyen.example05.Service;

import java.util.List;

import com.songnguyen.example05.Entity.Address;
import com.songnguyen.example05.payloads.AddressDTO;

public interface AddressService {
    AddressDTO createAddress(AddressDTO addressDTO);
    List<AddressDTO> getAddresses();
    AddressDTO getAddress(Long addressId);
    AddressDTO updateAddress(Long addressId,Address address);
    String deleteAddress(Long addressId);
}
