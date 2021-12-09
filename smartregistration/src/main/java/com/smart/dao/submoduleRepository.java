package com.smart.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smart.entities.Module;
import com.smart.entities.SubModule;
import com.smart.entities.User;

@Repository
public interface submoduleRepository extends JpaRepository<SubModule, Integer> {

	
	//pagination...
	
		
	
	  @Query("from SubModule p Join p.modules od Where od.cId = :cId") 
	  List<SubModule> findByModulecId(@Param("cId") int cId);
	  
	 
	public final static String product_ordered ="Select p from SubModule p Join p.modules od Where od.cId = :cId";

	@Query(product_ordered)
	public List<SubModule> findBycId(@Param("cId") int cId);
	
	
	/*
	 * @Query("from SubModule as e where e.module.cId =:cId") //change
	 * 
	 * public Page<SubModule> findSubModuleByModule(@Param("cId") int cId, Pageable
	 * pePageable);
	 * 
	 * public List<SubModule> findByNameContainingAndUser(String name, Module
	 * module);
	 */
	
	
	
	//public Module getByModuleId(@Param("cId") Integer cId);  //change
	
	
	 
	/*
	 * public default Page<SubModule> findPaginated(int pageNo, int pageSize) {
	 * Pageable pageable = PageRequest.of(pageNo -1, pageSize); return
	 * this.findAll(pageable); }
	 */
	  
	 
	 

	
}
