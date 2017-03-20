package com.ngautocode.service;

import org.springframework.stereotype.Service;

@Service
public class CounterService {
	
    private int index;

    public CounterService() {
        this.index = 0;
    }


    public int getIndex() {
        return index;
    }

    public void setIndex(int ind) {
        index = ind;
    }
    
}
