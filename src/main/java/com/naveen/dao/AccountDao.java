package com.naveen.dao;

import com.naveen.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountDao extends CrudRepository<Account, Long> {
    Account findByPassword(String password);
}
