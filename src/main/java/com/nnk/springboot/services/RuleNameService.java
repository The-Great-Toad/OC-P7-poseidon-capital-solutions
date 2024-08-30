package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class RuleNameService extends AbstractEntityService<RuleName> {


    public RuleNameService(JpaRepository<RuleName, Integer> repository) {
        super("RuleNameService", repository);
    }
}
