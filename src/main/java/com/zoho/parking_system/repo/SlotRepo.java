package com.zoho.parking_system.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zoho.parking_system.model.Slot;
import com.zoho.parking_system.model.VehicleType;

public interface SlotRepo extends JpaRepository<Slot, Integer> {

	@Query(value="Select top 1 * from slot s where s.vehichle_type=?1 and s.availablity='A' order by s.floor_id , s.slot_number asc",nativeQuery=true)
	Slot findNextAvailableSlot(String type);


}
