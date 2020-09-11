package com.example.addressBook.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.addressBook.model.Address;
import com.example.addressBook.model.User;
import com.example.addressBook.service.AddressService;
import com.example.addressBook.service.UserService;

@Controller
public class HomeController {
	
	@Autowired
	UserService userService; 
	
	@Autowired
	AddressService addressService;

	@GetMapping("/home")
	public String getHome(Model model) {
		//debug
		System.out.println("in getHome()");
		model.asMap().entrySet().stream().forEach(s -> System.out.println(s));
		System.out.println();
		
		return "home";
	}
	
	@PostMapping("/home")
	public String postHome(
			@ModelAttribute @Validated User form,
			BindingResult bindingResult,
			Model model
	) {
		if(bindingResult.hasErrors()) {
			IndexController index = new IndexController();
			return index.getIndex(form, model);
		}
		
		boolean match = userService.verifyUser(form.getUserId(), form.getPw());
		
		if(!match) {
			model.addAttribute("retry", true);
			return new IndexController().getIndex(form, model);
		}
		
		List<Address> addressList = addressService.selectAll(form.getUserId());
		model.addAttribute("addressList", addressList);
		
		Address address = new Address();
		address.setUserId(form.getUserId());
		model.addAttribute("address", address);
		
		//debug
		System.out.println("in postHome()");
		model.asMap().entrySet().stream().forEach(s -> System.out.println(s));
		System.out.println();
		
		return "home";
	}
	
	@GetMapping("/addressList/csv")
	public ResponseEntity<byte[]> getAddressListCsv(Model model) {

    	addressService.addressCsvOut();

        byte[] bytes = null;

        try {
        	bytes = addressService.getFile("addressList.csv");

        } catch (IOException e) {
        	e.printStackTrace();
        }

        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "text/csv; charset=UTF-8");
        header.setContentDispositionFormData("filename", "addressList.csv");

        return new ResponseEntity<>(bytes, header, HttpStatus.OK);
    }
}
