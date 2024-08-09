package com.nnk.springboot.services.ruleName;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RuleNameServiceImpl implements RuleNameService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleNameServiceImpl.class);
    private static final String LOG_ID = "[RuleNameService]";

    private final RuleNameRepository ruleNameRepository;

    public RuleNameServiceImpl(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    @Override
    public List<RuleName> getRuleNames() {
        return ruleNameRepository.findAll();
    }

    @Override
    public RuleName getRuleName(Integer id) {
        Optional<RuleName> optionalRuleName = ruleNameRepository.findById(id);

        if (optionalRuleName.isPresent()) {
            LOGGER.info("{} - Retrieved ruleName: {}", LOG_ID, optionalRuleName.get());
            return optionalRuleName.get();
        }

        LOGGER.error("{} - RuleName with ID: {}, not found", LOG_ID, id);
        return null;
    }

    @Override
    public RuleName save(RuleName ruleName) {
        LOGGER.info("{} - Creating ruleName: {}", LOG_ID, ruleName);
        return ruleNameRepository.save(ruleName);
    }

    @Override
    public RuleName update(RuleName ruleName) {
        LOGGER.info("{} - Updating ruleName: {}", LOG_ID, ruleName);
        return ruleNameRepository.save(ruleName);
    }

    @Override
    public boolean delete(RuleName ruleName) {
        ruleNameRepository.delete(ruleName);
        LOGGER.info("{} - Deleted ruleName: {}", LOG_ID, ruleName);
        return true;
    }
}
