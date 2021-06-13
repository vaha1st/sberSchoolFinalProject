package com.vaha1st.sberCredit.service;

import com.vaha1st.sberCredit.entity.Form;

public interface FormService {

    public Form getFormById(int id);
    public void saveOrUpdateForm(Form form);
    public void deleteForm(Form form);
}
