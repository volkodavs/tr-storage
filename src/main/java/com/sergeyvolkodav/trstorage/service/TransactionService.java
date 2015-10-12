package com.sergeyvolkodav.trstorage.service;


import com.sergeyvolkodav.trstorage.model.TransactionModel;
import com.sergeyvolkodav.trstorage.repository.TrRepository;
import com.sergeyvolkodav.trstorage.rest.data.TransactionData;
import com.sergeyvolkodav.trstorage.rest.exception.AppTRStoreException;
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
        log.info("Save new transaction");
        TransactionModel newTransaction = mapTransactionDataToEntity(transactionData);

        String parentPath = getParentPath(transactionData);
        PathBuilder pathBuilder = new PathBuilder(newTransaction.getId().toString(), parentPath);
        newTransaction.setMpath(pathBuilder.getNewPath());
        transactionRepository.save(newTransaction);
    }

    @Override
    public TransactionData getTransactionDataById(Long id) {
        TransactionModel transactionModel = transactionRepository.findById(id);
        if (transactionModel != null) {
            return mapEntityToTransactionData(transactionModel);
        }
        throw new AppTRStoreException(String.format("Transaction with id %d not found", id));
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
        if (transactionModel != null) {
            return transactionRepository.sumParentByPath(transactionModel.getMpath());
        }
        throw new AppTRStoreException(String.format("Transaction with id %d not found", id));
    }

    /**
     * Find for transaction parent path
     *
     * @param transactionData Input transaction
     * @return Return parent path
     */
    private String getParentPath(TransactionData transactionData) {
        if (transactionData.getParentId() != null) {
            TransactionModel parentTransaction = transactionRepository.findById(transactionData.getParentId());
            if (parentTransaction != null) {
                return parentTransaction.getMpath();
            }
            throw new AppTRStoreException(String.format("Transaction with id %d not found", transactionData.getId()));
        }
        return "";
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

    /**
     * Builder Path class
     */
    public class PathBuilder {

        private String curNode;
        private String parentPath;
        private String newPath;

        public PathBuilder(String curNode, String parentPath) {
            this.curNode = curNode;
            this.parentPath = parentPath;
            checkForCycling();
            setNewPath();
        }

        private void setNewPath() {
            newPath = parentPath + "/" + curNode;
        }

        public String getNewPath() {
            return newPath;
        }

        public void checkForCycling() {
            if (parentPath.contains(curNode)) {
                throw new AppTRStoreException("Detect cycling graph");
            }
        }
    }
}
