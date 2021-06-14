package com.vaha1st.sberCredit.service;

import com.vaha1st.sberCredit.dao.CreditSchemeRepository;
import com.vaha1st.sberCredit.entity.CreditScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreditSchemeServiceImpl implements CreditSchemeService{

    CreditSchemeRepository creditSchemeRepository;

    @Autowired
    public CreditSchemeServiceImpl(CreditSchemeRepository creditSchemeRepository) {
        this.creditSchemeRepository = creditSchemeRepository;
    }

    @Override
    public List<CreditScheme> getSchemes() {
        return creditSchemeRepository.findAll();
    }

    @Override
    public CreditScheme getSchemeById(Integer id) {
        return creditSchemeRepository.getById(id);
    }

    @Override
    public List<CreditScheme> getSchemesBySumAndPeriod(Integer sum, Integer period) {
        List<CreditScheme> tmp = creditSchemeRepository.findAll();
        if (tmp.isEmpty()) {
            return tmp;
        }
        return tmp.stream()
                .filter(a -> a.getMaxSum() >= sum && a.getMinSum() <= sum && a.getPeriod() >= period)
                .collect(Collectors.toList());
    }
}
