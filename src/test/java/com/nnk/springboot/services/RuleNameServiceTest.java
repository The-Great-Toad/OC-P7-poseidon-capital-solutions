package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.data.jpa.repository.JpaRepository;

class RuleNameServiceTest extends AbstractEntityServiceTest<RuleName> {

    @BeforeEach
    public void setUp() {
        this.setService(createService(repository));
    }

    @Override
    protected AbstractEntityService<RuleName> createService(JpaRepository<RuleName, Integer> repository) {
        return new RuleNameService(repository);
    }

    @Override
    protected RuleName createEntity() {
        return RuleName.builder()
                .name("name")
                .description("description")
                .json("json")
                .template("template")
                .sqlStr("SQL string")
                .sqlPart("SQL part")
                .build();
    }
}