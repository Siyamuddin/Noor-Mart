package com.example.noormart;

import com.example.noormart.Configuration.AppConstants;
import com.example.noormart.Model.Role;
import com.example.noormart.Repository.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class NoorMartApplication implements CommandLineRunner {
	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(NoorMartApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}

	public void run(String... args) throws Exception {
		try{
			Role role=new Role();
			role.setId(Math.toIntExact(AppConstants.ADMIN_USER));
			role.setName("ROLE_ADMIN");

			Role role1=new Role();
			role1.setId(Math.toIntExact(AppConstants.NORMAL_USER));
			role1.setName("ROLE_NORMAL");

			List<Role> roles=List.of(role,role1);
			List<Role> result=this.roleRepo.saveAll(roles);
			result.forEach(r->{
				System.out.println(r.getName());
			});
		}catch (Exception e){
			e.printStackTrace();
		}

}}
