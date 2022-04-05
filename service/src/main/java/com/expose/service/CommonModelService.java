package com.expose.service;

import com.expose.service.modal.CommonModal;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommonModelService {

    public Optional<List<CommonModal>> getCommonModalList() {
        List<CommonModal> list = new ArrayList<CommonModal>(Set.of(new CommonModal(1, "dc"),
                new CommonModal(2, "mn")));

        return Optional.of(list);
    }

    public Optional<CommonModal> saveCommonModal(final CommonModal commonModal){
        return Optional.of(new CommonModal(1, "dc"));
    }
}
