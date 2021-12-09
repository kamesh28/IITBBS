package com.smart.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.entities.Module;
import com.smart.entities.User;

public interface ModuleRepository extends JpaRepository<Module, Integer> {
	//pagination.....
		@Query("from Module as d where d.user.id =:userId") // from c to d changed on nov 2021
			//public List<Contact> findContactsByUser(@Param("userId") int userId); // It is used for show contacts below change the code for pagination
	    //current page - page
		//contact per page - 5 [n]
		
		public Page<Module> findModuleByUser(@Param("userId") int userId, Pageable pePageable);
		
		public List<Module> findByNameContainingAndUser(String name, User user);
		
		public Module getByUserId(int UserId);
		
		public User getUserByUserName(@Param("email") String email);
		
		/*
		 * @Query("select d from Module d where d.mainauthor =:mainauthor") // change
		 * public Module getModuleByMainAuthor(@Param("mainauthor") String mainauthor);
		 * //change
		 */
		



}
