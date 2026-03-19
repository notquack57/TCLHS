//Temporary storage of public data using ConcurrentHashMap for easy data retrieval in the backend. 
//Method to retrive public profile data from database and give it to a sorting algorithm in service.
//Method to retrive chats if we're doing that?
//The cache updates when data is commited to actual DB


package com.LHSprojects.TCLHS.Repository;

import org.springframework.stereotype.Component;

import com.LHSprojects.TCLHS.model.Account;

import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Component
public class Repository {

    private final ConcurrentHashMap<String, Account> cache = new ConcurrentHashMap<>();

    
public List<Account> getAllAccounts() {
    return Collections.unmodifiableList(new ArrayList<>(cache.values()));
    }

    
    public Account getAccount(String id) {
        return cache.get(id);
    }

   
    public void saveAccount(Account account) {
        if (account == null || account.getId() == null) return;
        cache.put(account.getId(), account);
        }

    
    public void deleteAccount(String id) {
            cache.remove(id);
    }

    
    public void setAllAccounts(List<Account> accounts) {
        cache.clear();
        if (accounts != null) {
            for (Account acc : accounts) {
                if (acc != null && acc.getId() != null) {
                    cache.put(acc.getId(), acc);
                }
            }
        }
    }

    
    public boolean updateIfExists(Account account) {
        if (account == null || account.getId() == null) return false;

        return cache.replace(account.getId(), account) != null;
    }

    public int size() {
        return cache.size();
    }
}
