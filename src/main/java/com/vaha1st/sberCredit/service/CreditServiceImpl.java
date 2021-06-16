package com.vaha1st.sberCredit.service;

import com.vaha1st.sberCredit.dao.ApplicationRepository;
import com.vaha1st.sberCredit.dao.ClientRepository;
import com.vaha1st.sberCredit.dao.CreditRepository;
import com.vaha1st.sberCredit.dao.FormRepository;
import com.vaha1st.sberCredit.entity.Client;
import com.vaha1st.sberCredit.entity.Credit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreditServiceImpl implements CreditService{

    private CreditRepository creditRepository;
    private FormRepository formRepository;
    private ApplicationRepository appRepository;
    private ClientRepository clientRepository;

    @Autowired
    public CreditServiceImpl(CreditRepository creditRepository, FormRepository formRepository,
                             ApplicationRepository appRepository, ClientRepository clientRepository) {
        this.creditRepository = creditRepository;
        this.formRepository = formRepository;
        this.appRepository = appRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public Credit getCreditById(int id) {
        Optional<Credit> result = creditRepository.findById(id);

        Credit credit = null;

        if (result.isPresent()) {
            credit = result.get();
        } else {
            throw new RuntimeException("Кредита с id=" + id + " не найдено");
        }
        return credit;
    }

    @Override
    public Credit getCreditByClient(Client client) {
        Optional<Credit> result = creditRepository.findByClientAndFinishedIsFalseOrFinishedIsNull(client);

        Credit credit = null;

        if (result.isPresent()) {
            credit = result.get();
        } else {
            credit = new Credit();
            credit.setClient(client);
            credit.setFinished(false);
            credit.setStatus("");
            saveOrUpdateCredit(credit);
        }
        return credit;
    }

    @Override
    public Credit getCreditByClientWithoutNew(Client client) {
        Optional<Credit> result = creditRepository.findByClientAndFinishedIsFalseOrFinishedIsNull(client);

        Credit credit = null;

        if (result.isPresent()) {
            credit = result.get();
        }
        return credit;
    }

    @Override
    public void finalizeCredit(int id) {
        Optional<Credit> result = creditRepository.findById(id);

        Credit credit = null;

        if (result.isPresent()) {
            credit = result.get();
            credit.setFinished(true);
            creditRepository.save(credit);
        }
    }

    @Override
    public void saveOrUpdateCredit(Credit credit) {
        creditRepository.save(credit);
    }

    @Override
    public void deleteForm(Credit credit) {
        creditRepository.delete(credit);
    }
}
