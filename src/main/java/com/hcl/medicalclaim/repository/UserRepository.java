package com.hcl.medicalclaim.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.hcl.medicalclaim.entity.User;

@Repository
@Transactional
public interface UserRepository extends CrudRepository<User, Long>{
	
  @Override
  List<User> findAll();

  User findByUserIdAndPassword(String userId, String password);
  
  @Query("Select u from User u where u.approvalLevel= :approvalLevel")
  List<User> approvalLevelUsers(@Param("approvalLevel") long approvalLevel);
  
  User findByUserId(String userId);

  User findById(long id);
}
