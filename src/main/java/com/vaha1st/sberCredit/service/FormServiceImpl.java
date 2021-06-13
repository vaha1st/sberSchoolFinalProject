package com.vaha1st.sberCredit.service;

import com.vaha1st.sberCredit.dao.FormRepository;
import com.vaha1st.sberCredit.entity.Form;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FormServiceImpl implements FormService{

    FormRepository formRepository;

    @Autowired
    public FormServiceImpl(FormRepository formRepository) {
        this.formRepository = formRepository;
    }

    @Override
    public Form getFormById(int id) {
        Optional<Form> result = formRepository.findById(id);

        Form form = null;

        if (result.isPresent()) {
            form = result.get();
        } else {
            throw new RuntimeException("Анкеты с id=" + id + " не найдено");
        }
        return form;
    }

    @Override
    public void saveOrUpdateForm(Form form) {
        formRepository.save(form);
    }

    @Override
    public void deleteForm(Form form) {
        formRepository.delete(form);
    }
}
