package com.example.addressBook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.addressBook.model.Address;
import com.example.addressBook.service.AddressService;

@Controller
public class AddressEditController {
	
	@Autowired
	AddressService addressService;

	@GetMapping("/addressEdit/{id:.+}")
	public String getAddressEdit(
			Model model,
			@PathVariable("id") String pathVariable
	) {
		//debug
		System.out.println("in getAddressEdit()");
		model.asMap().entrySet().stream().forEach(s -> System.out.println(s));
		System.out.println();
		
		if(pathVariable != null && pathVariable.length() > 0) {
			int addressId = Integer.parseInt(pathVariable);
			
			Address address = addressService.selectOne(addressId);
			
			if(address.getAddressId() > 0) {
				model.addAttribute("editTargetAddress", address);
			}else {
				return "redirect:/";
			}
		}
		
		return "addressEdit";
	}
	
	@PostMapping(value = "/addressEdit", params = "update")
	public String postUpdate(
			@ModelAttribute @Validated Address form,
			BindingResult bindingResult,
			Model model
	) {
		if(bindingResult.hasErrors()) {
			//debug
			System.out.println("in postAddressEditUpdate()");
			model.asMap().entrySet().stream().forEach(s -> System.out.println(s));
			System.out.println();
			
			return getAddressEdit(model, String.valueOf(form.getAddressId()));
		}
		
		Address address = new Address();
		address.setAddressId(form.getAddressId());
		address.setFullname(form.getFullname());
		address.setFurigana(form.getFurigana());
		address.setAddress(form.getAddress());
		address.setTel(form.getTel());
		address.setMail(form.getMail());
		address.setNote(form.getNote());
		
		try {
			boolean result = addressService.updateOne(address);
			
			if(result) {
				model.addAttribute("result", "更新成功");
			}else {
				model.addAttribute("result", "更新失敗");
			}
			
		} catch (DataAccessException e) {
			model.addAttribute("result", "更新失敗");
		}
		
		List<Address> addressList = addressService.selectAll(form.getUserId());
		model.addAttribute("addressList", addressList);
		
		return new HomeController().getHome(model);
	}
	
	@PostMapping(value = "/addressEdit", params = "delete")
	public String postDelete(
			@ModelAttribute @Validated Address form,
			BindingResult bindingResult,
			Model model
	) {
		if(bindingResult.hasErrors()) {
			return "home";
		}
		
		try {
			boolean result = addressService.deleteOne(form.getAddressId());
			
			if(result) {
				model.addAttribute("result", "削除成功");
			}else {
				model.addAttribute("result", "削除失敗");
			}
			
		} catch (DataAccessException e) {
			model.addAttribute("result", "削除失敗");
		}
		
		List<Address> addressList = addressService.selectAll(form.getUserId());
		model.addAttribute("addressList", addressList);
		
		return new HomeController().getHome(model);
	}
	
	@PostMapping("/addressEdit/register")
	public String postRegister(
			@ModelAttribute Address address,
			Model model
	) {
		//debug
		System.out.println("in postRegister()");
		model.asMap().entrySet().stream().forEach(s -> System.out.println(s));
		System.out.println();
		
		return "register";
	}
	
	@PostMapping("/addressEdit/register/confirm")
	public String postRegisterConfirm(
			@ModelAttribute @Validated Address form,
			BindingResult bindingResult,
			Model model
	) {
		if(bindingResult.hasErrors()) {
			return postRegister(form, model);
		}
		
		Address address = new Address();
		address.setFullname(form.getFullname());
		address.setFurigana(form.getFurigana());
		address.setAddress(form.getAddress());
		address.setTel(form.getTel());
		address.setMail(form.getMail());
		address.setNote(form.getNote());
		address.setUserId(form.getUserId());
		
		//debug
		System.out.println("in postRegisterCofirm()");
		model.asMap().entrySet().stream().forEach(s -> System.out.println(s));
		System.out.println();
		
		try {
			boolean result = addressService.insertOne(address);
			
			if(result) {
				model.addAttribute("result", "登録成功");
			}else {
				model.addAttribute("result", "登録失敗");
			}
			
		} catch (DataAccessException e) {
			model.addAttribute("result", "登録失敗");
		}
		
		List<Address> addressList = addressService.selectAll(form.getUserId());
		model.addAttribute("addressList", addressList);
		
		return new HomeController().getHome(model);
	}
	
	@PostMapping("/addressEdit/search")
	public String postSearch(
			@RequestParam("column") String column,
			@RequestParam("keyword") String keyword,
			Model model
	) {
		//debug
		System.out.println("in postSearch()");
		model.asMap().entrySet().stream().forEach(s -> System.out.println(s));
		System.out.println();
		
		return new HomeController().getHome(model);
	}
}
