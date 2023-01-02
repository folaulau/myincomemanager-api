package com.incomemanager.api.entity.income;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class IncomeDAOImp implements IncomeDAO {
    
    @Autowired
    private IncomeRepository incomeRepository;

    @Override
    public Income save(Income income) {
        // TODO Auto-generated method stub
        return incomeRepository.saveAndFlush(income);
    }

    @Override
    public Optional<Income> findByUuid(String uuid) {
        // TODO Auto-generated method stub
        return incomeRepository.findByUuid(uuid);
    }
}
