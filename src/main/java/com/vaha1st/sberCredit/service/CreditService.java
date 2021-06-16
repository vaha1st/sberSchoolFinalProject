package com.vaha1st.sberCredit.service;

import com.vaha1st.sberCredit.entity.Client;
import com.vaha1st.sberCredit.entity.Credit;

public interface CreditService {
    public Credit getCreditById(int id);
    public Credit getCreditByClient(Client client);
    public Credit getCreditByClientWithoutNew(Client client);
    public void saveOrUpdateCredit(Credit credit);
    public void deleteForm(Credit credit);
    public void finalizeCredit(int id);
}
