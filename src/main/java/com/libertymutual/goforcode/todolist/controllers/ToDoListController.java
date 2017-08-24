package com.libertymutual.goforcode.todolist.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.libertymutual.goforcode.todolist.models.ToDoItem;
import com.libertymutual.goforcode.todolist.services.ToDoItemRepository;

@Controller
@RequestMapping("/")
public class ToDoListController {

    private ToDoItemRepository repository;

    public ToDoListController(ToDoItemRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public String redirectToApplication() {
        return "redirect:/todos";
    }

    @GetMapping("todos")
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView("todo/default");
        List<ToDoItem> items = repository.getAll();
        mv.addObject("toDoItems", items);
        mv.addObject("hasToDoItems", !items.isEmpty());
        return mv;
    }

    @PostMapping("todos")
    public String create(ToDoItem item) {
        repository.create(item);
        return "redirect:/todos";
    }

    @PostMapping("todos/{id}/delete")
    public String complete(@PathVariable int id) {
        ToDoItem item = repository.getById(id);
        item.setComplete(true);
        repository.update(item);
        return "redirect:/todos";
    }

}