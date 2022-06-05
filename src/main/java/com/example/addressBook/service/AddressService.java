package com.example.addressBook.service;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.example.addressBook.model.Address;
import com.example.addressBook.repository.AddressRepository;

@Service
public class AddressService {

	@Autowired
	AddressRepository addressRepo;

	//ユーザに関係する住所をすべて取得
	public List<Address> selectAll(String userId){
		return addressRepo.selectUsersAddresses(userId);
	}
	
	//住所IDに対応する住所情報を1件取得する
	public Address selectOne(int addressId) {
		return addressRepo.selectOne(addressId);
	}
	
	//住所を1件更新する
	public boolean updateOne(Address address) {
		boolean result = false;
		
		int rowNumber = addressRepo.updateOne(address);
		
		if(rowNumber > 0) {
			result = true;
		}
		
		return result;
	}
	
	//住所を1件削除する
	public boolean deleteOne(int addressId) {
		boolean result = false;

		int rowNumber = addressRepo.deleteOne(addressId);

		if(rowNumber > 0) {
			result = true;
		}

		return result;
	}
	
	//住所を1件追加する
	public boolean insertOne(Address address) {
		boolean result = false;
		
		int rowNumber = addressRepo.insertOne(address);

		if(rowNumber > 0) {
			result = true;
		}

		return result;
	}
	
	//DBデータをCSV形式で出力
	public void addressCsvOut() throws DataAccessException {
		addressRepo.addressCsvOut();
	}
	
	//CSVファイルを出力
	public byte[] getFile(String fileName) throws IOException {

		FileSystem fs = FileSystems.getDefault();
		Path p = fs.getPath(fileName);
		byte[] bytes = Files.readAllBytes(p);

		return bytes;
    }
}
