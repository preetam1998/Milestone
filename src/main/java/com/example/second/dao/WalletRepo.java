package com.example.second.dao;

import com.example.second.models.UserWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepo extends JpaRepository<UserWallet,Integer>
{
	@Query("SELECT w from UserWallet w JOIN w.user u WHERE u.mobileNumber = :mobileNumber")
	UserWallet findByUserMobileNumber(@Param("mobileNumber") String mobile);
	Boolean deleteByUserMobileNumber(@Param("mobileNumber")String mobile);


	
}
