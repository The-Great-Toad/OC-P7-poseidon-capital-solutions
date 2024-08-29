package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.data.jpa.repository.JpaRepository;

class CurvePointServiceTest extends AbstractEntityServiceTest<CurvePoint> {

    @BeforeEach
    public void setUp() {
        this.setService(createService(repository));
    }

    @Override
    protected AbstractEntityService<CurvePoint> createService(JpaRepository<CurvePoint, Integer> repository) {
        return new CurvePointService(repository);
    }

    @Override
    protected CurvePoint createEntity() {
        return CurvePoint.builder()
                .curveId(1)
                .term(1d)
                .value(1d)
                .build();
    }
}