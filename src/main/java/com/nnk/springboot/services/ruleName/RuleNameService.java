package com.nnk.springboot.services.ruleName;

import com.nnk.springboot.domain.RuleName;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RuleNameService {

    List<RuleName> getRuleNames();

    RuleName getRuleName(Integer id);

    RuleName save(RuleName ruleName);

    RuleName update(RuleName ruleName);

    boolean delete(RuleName ruleName);
}
