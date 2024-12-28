package com.songnguyen.example05.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.songnguyen.example05.Entity.Address;

@Repository
public interface AddressRepo extends JpaRepository<Address,Long> {
    Address findByCountryAndStateAndCityAndPincodeAndStreetAndBuildingName(String country,String state, String city,String pincode,String street,String buildingName);
}
