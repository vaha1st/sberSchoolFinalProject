package com.vaha1st.sberCredit.service;

import com.vaha1st.sberCredit.entity.Application;

public interface ApplicationService {
    public Application getFormById(int id);
    public void saveOrUpdateForm(Application app);
    public void deleteForm(Application app);
}
