package com.sergeyvolkodav.trstorage.service;


import com.sergeyvolkodav.trstorage.model.TransactionModel;
import com.sergeyvolkodav.trstorage.repository.TrRepository;
import com.sergeyvolkodav.trstorage.rest.data.TransactionData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService implements ITransactionService {

    Logger log = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    TrRepository transactionRepository;

    @Override
    public void saveTransaction(TransactionData transactionData) {
        log.info("Save new transaction, id: {}", transactionData.getId());
        TransactionModel newTransaction = mapTransactionDataToEntity(transactionData);

        String parentPath = "";
        if (transactionData.getParentId() != null) {
            TransactionModel parentTransaction = transactionRepository.findById(transactionData.getParentId());
            parentPath = parentTransaction.getMpath();
        }
        newTransaction.setMpath(buildPath(parentPath, transactionData.getId()));
        transactionRepository.save(newTransaction);
    }

    @Override
    public TransactionData getTransactionDataById(Long id) {
        TransactionModel transactionModel = transactionRepository.findById(id);
        return mapEntityToTransactionData(transactionModel);
    }

    @Override
    public List<Long> getTransactionListByType(String type) {
        List<TransactionModel> transactionEntityList = transactionRepository.findByTypeIgnoreCase(type);
        List<Long> transactions = new ArrayList<>();
        transactionEntityList.stream().forEach(e -> transactions.add(e.getId()));
        return transactions;
    }

    @Override
    public Double getTransactionSum(Long id) {
        TransactionModel transactionModel = transactionRepository.findById(id);
        return transactionRepository.sumParentByPath(transactionModel.getMpath());
    }

    /**
     * Build path
     *
     * @param parentPath parent Path
     * @param id         new id
     * @return Path for new id
     */
    private String buildPath(String parentPath, Long id) {
        return parentPath + "/" + id.toString();
    }

    /**
     * Mapper from DTO to Entity
     *
     * @param transactionData Transaction DTO
     * @return Entity
     */
    private TransactionModel mapTransactionDataToEntity(TransactionData transactionData) {
        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setAmount(new BigDecimal(transactionData.getAmount()));
        transactionModel.setId(transactionData.getId());
        transactionModel.setType(transactionData.getType());
        transactionModel.setParentId(transactionData.getParentId());
        return transactionModel;
    }

    /**
     * Mapper from Entity to DTO
     *
     * @param transactionModel Entity
     * @return DTO
     */
    private TransactionData mapEntityToTransactionData(TransactionModel transactionModel) {
        TransactionData transactionData = new TransactionData();
        transactionData.setAmount(transactionModel.getAmount().doubleValue());
        transactionData.setId(transactionModel.getId());
        transactionData.setParentId(transactionModel.getParentId());
        transactionData.setType(transactionModel.getType());
        return transactionData;
    }
}
