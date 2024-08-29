package com.nnk.springboot.controllers;

import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;

public interface ControllerInterface<T> {

    String home(Model model);

    String addEntityForm(T entity);

    String validate(
            Model model,
            @Valid T bid,
            BindingResult result,
            RedirectAttributes redirectAttributes) throws SQLException;

    String showUpdateForm(@PathVariable("id") Integer id, Model model);

    String updateEntity(
            @PathVariable("id") Integer id,
            @Valid T entity,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes);

    String deleteEntity(
            @PathVariable("id") Integer id,
            Model model,
            RedirectAttributes redirectAttributes);
}
