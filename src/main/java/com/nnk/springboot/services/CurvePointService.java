package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CurvePointService extends AbstractEntityService<CurvePoint> {

    public CurvePointService(JpaRepository<CurvePoint, Integer> abstractEntityRepository) {
        super("CurvePointService", abstractEntityRepository);
    }
}
