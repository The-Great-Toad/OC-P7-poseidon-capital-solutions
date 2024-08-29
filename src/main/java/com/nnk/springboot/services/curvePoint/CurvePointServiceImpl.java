package com.nnk.springboot.services.curvePoint;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.BidListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurvePointServiceImpl implements CurvePointService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BidListService.class);
    private static final String LOG_ID = "[BidListService]";

    private final CurvePointRepository curvePointRepository;

    public CurvePointServiceImpl(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    @Override
    public List<CurvePoint> getCurvePoints() {
        return curvePointRepository.findAll();
    }

    @Override
    public CurvePoint getCurvePoint(Integer id) {
        Optional<CurvePoint> optionalCurvePoint = curvePointRepository.findById(id);

        if (optionalCurvePoint.isPresent()) {
            LOGGER.info("{} - Retrieved curvePoint: {}", LOG_ID, optionalCurvePoint.get());
            return optionalCurvePoint.get();
        }

        LOGGER.error("{} - CurvePoint with ID: {}, not found", LOG_ID, id);
        return null;
    }

    @Override
    public CurvePoint save(CurvePoint curvePoint) {
        LOGGER.info("{} - Creating curvePoint: {}", LOG_ID, curvePoint);
        return curvePointRepository.save(curvePoint);
    }

    @Override
    public CurvePoint update(CurvePoint curvePoint) {
        LOGGER.info("{} - Updating curvePoint: {}", LOG_ID, curvePoint);
        return curvePointRepository.save(curvePoint);
    }

    @Override
    public boolean delete(CurvePoint curvePoint) {
        curvePointRepository.delete(curvePoint);
        LOGGER.info("{} - Deleted curvePoint: {}", LOG_ID, curvePoint);
        return true;
    }
}
