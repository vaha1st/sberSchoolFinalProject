package com.vaha1st.sberCredit.service;

import com.vaha1st.sberCredit.entity.CreditScheme;

import java.util.List;

public interface CreditSchemeService {
    public CreditScheme getSchemeById(Integer id);
    public List<CreditScheme> getSchemes();
    public List<CreditScheme> getSchemesBySumAndPeriod(Integer sum, Integer period);
}
