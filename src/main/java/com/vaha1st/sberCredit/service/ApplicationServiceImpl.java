package com.vaha1st.sberCredit.service;

import com.vaha1st.sberCredit.dao.ApplicationRepository;
import com.vaha1st.sberCredit.dao.CreditRepository;
import com.vaha1st.sberCredit.entity.Application;
import com.vaha1st.sberCredit.entity.Credit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplicationServiceImpl implements ApplicationService{

    ApplicationRepository appRepository;

    @Autowired
    public ApplicationServiceImpl(ApplicationRepository appRepository) {
        this.appRepository = appRepository;
    }

    @Override
    public Application getFormById(int id) {
        Optional<Application> result = appRepository.findById(id);

        Application app = null;

        if (result.isPresent()) {
            app = result.get();
        } else {
            throw new RuntimeException("Заявки с id=" + id + " не найдено");
        }
        return app;
    }

    @Override
    public void saveOrUpdateForm(Application app) {
        appRepository.save(app);
    }

    @Override
    public void deleteForm(Application app) {
        appRepository.delete(app);
    }
}
