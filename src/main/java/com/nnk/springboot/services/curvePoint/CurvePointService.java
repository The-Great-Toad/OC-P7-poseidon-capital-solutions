package com.nnk.springboot.services.curvePoint;

import com.nnk.springboot.domain.CurvePoint;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CurvePointService {

    List<CurvePoint> getCurvePoints();

    CurvePoint getCurvePoint(Integer id);

    CurvePoint save(CurvePoint curvePoint);

    CurvePoint update(CurvePoint curvePoint);

    boolean delete(CurvePoint curvePoint);
}
