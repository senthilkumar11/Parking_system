package com.zoho.parking_system;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.zoho.parking_system.model.ERole;
import com.zoho.parking_system.model.Fee;
import com.zoho.parking_system.model.Role;
import com.zoho.parking_system.model.User;
import com.zoho.parking_system.model.VehicleType;
import com.zoho.parking_system.repo.FeeRepository;
import com.zoho.parking_system.repo.RoleRepository;
import com.zoho.parking_system.repo.UserRepository;

@SpringBootApplication
public class ParkingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkingSystemApplication.class, args);
	}
	@Bean
    public CommandLineRunner data( RoleRepository roleRepository,
                                         UserRepository  userRepository,PasswordEncoder passwordEncoder,FeeRepository feeRepository) {
        return args -> {

            // create a new book
        	Role admin=null,user=null;
        	
        	List<Role> adminList= roleRepository.findByName(ERole.ROLE_ADMIN);
			if(adminList==null||adminList.isEmpty()) {
				admin=roleRepository.save(new Role(ERole.ROLE_ADMIN));
				
			}
			else
			{
				admin=adminList.get(0);
			}
			List<Role> userList= roleRepository.findByName(ERole.ROLE_USER);
			if(userList==null||userList.isEmpty()) {
				user=roleRepository.save(new Role(ERole.ROLE_USER));
			}else {
				user=userList.get(0);
			}
			System.out.println("Role save completed");
			Set<Role> roles=new HashSet<>();
			roles.add(user);
			Optional<User> users=userRepository.findByUsername("user");
			if(users==null||users.isEmpty()) {
				userRepository.save(new User("user","user@gmail.com", passwordEncoder.encode("user123"), "user", "user", "123456789",roles));
			}
			roles.add(admin);
			System.out.println(user.getId());
			System.out.println(admin.getId());
			users=userRepository.findByUsername("admin");
			if(users==null||users.isEmpty()) {
				userRepository.save(new User("admin","admin@gmail.com", passwordEncoder.encode("admin123"), "admin", "admin", "123456789",roles));
			}
			
			
			List<Fee> feeList=new ArrayList<>();
			feeList.add(new Fee(VehicleType.BIKE, 40, 20));
			feeList.add(new Fee(VehicleType.BUS, 100, 50));
			feeList.add(new Fee(VehicleType.CAR, 60, 30));
			feeRepository.saveAll(feeList);
        };
    }
}
