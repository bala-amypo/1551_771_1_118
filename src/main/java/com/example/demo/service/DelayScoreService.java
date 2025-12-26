package com.example.demo.service;

import com.example.demo.model.DelayScoreRecord;
import java.util.List;

public interface DelayScoreService {

    List<DelayScoreRecord> getScoresBySupplier(long supplierId);
}
