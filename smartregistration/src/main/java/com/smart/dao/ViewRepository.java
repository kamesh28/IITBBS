/*
 * package com.smart.dao;
 * 
 * import org.springframework.data.jpa.repository.JpaRepository; import
 * org.springframework.data.jpa.repository.Query;
 * 
 * 
 * import com.smart.entities.View;
 * 
 * public interface ViewRepository extends JpaRepository<View, Integer> {
 * 
 * @Query("from View as e where e.module.id =:moduleId")
 * 
 * public View getByUserId(int viewId);
 * 
 * }
 */