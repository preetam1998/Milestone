package com.example.second.dao;

import com.example.second.models.UserWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepo extends JpaRepository<UserWallet,String>
{


    UserWallet findByMobileNumber(String mobile);
}
