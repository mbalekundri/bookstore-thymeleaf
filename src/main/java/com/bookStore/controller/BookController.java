package com.bookStore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.bookStore.entity.Book;
import com.bookStore.entity.MyBookList;
import com.bookStore.service.BookService;
import com.bookStore.service.MyBookListService;

import java.util.*;

@Controller
public class BookController {
	
	@Autowired
	private BookService service;
	
	@Autowired
	private MyBookListService myBookService;
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	@GetMapping("/book_register")
	public String bookRegister() {
		return "bookRegister";
	}
	
	@GetMapping("/available_books")
	public ModelAndView getAllBook(ModelAndView modelAndView) {
		List<Book>list=service.getAllBook();
		modelAndView.getModelMap().addAttribute("book",list);
		modelAndView.setViewName("bookList");
		return modelAndView;
	}
	
	@PostMapping("/save")
	public String addBook(@ModelAttribute Book b) {
		service.save(b);
		return "redirect:/available_books";
	}
	@GetMapping("/my_books")
	public ModelAndView getMyBooks(ModelAndView modelAndView)
	{
		List<MyBookList>list=myBookService.getAllMyBooks();
		modelAndView.getModelMap().addAttribute("book",list);
		modelAndView.setViewName("myBooks");
		return modelAndView;
	}
	@GetMapping("/mylist/{id}")
	public String getMyList(@PathVariable("id") int id) {
		Book b=service.getBookById(id);
		MyBookList mb=new MyBookList(b.getId(),b.getName(),b.getAuthor(),b.getPrice());
		myBookService.saveMyBooks(mb);
		return "redirect:/my_books";
	}
	
	@GetMapping("/editBook/{id}")
	public ModelAndView editBook(@PathVariable("id") int id,ModelAndView modelAndView) {
		Book b=service.getBookById(id);
		modelAndView.getModelMap().addAttribute("book",b);
		modelAndView.setViewName("bookEdit");
		return modelAndView;
	}
	@RequestMapping("/deleteBook/{id}")
	public String deleteBook(@PathVariable("id")int id) {
		service.deleteById(id);
		return "redirect:/available_books";
	}
	
}
