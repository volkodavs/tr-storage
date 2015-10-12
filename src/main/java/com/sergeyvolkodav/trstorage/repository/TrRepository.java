package com.sergeyvolkodav.trstorage.repository;


import com.sergeyvolkodav.trstorage.model.TransactionModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TrRepository extends CrudRepository<TransactionModel, Long> {


    List<TransactionModel> findAll();

    List<TransactionModel> findByTypeIgnoreCase(String type);

    TransactionModel findById(Long id);

    @Query("select sum(t.amount) from TransactionModel t where t.mpath like ?1%")
    Double sumParentByPath(String path);
}




