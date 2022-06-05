package com.example.addressBook.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.addressBook.model.Address;

@Repository
public class AddressRepository {

	@Autowired
	private JdbcTemplate jdbc;
	
	//引数のユーザIDを外部キーに持つ住所をすべて取得
	public List<Address> selectUsersAddresses(String userId){
		String sql = 
				"SELECT address_id, fullname, furigana, address, tel, mail, note, user_id FROM address WHERE user_id=?";
		List<Map<String,Object>> getList;
		List<Address> addressList = new ArrayList<Address>();
		
		try {
			getList = jdbc.queryForList(sql, userId);
			
		} catch (EmptyResultDataAccessException e) {
			return addressList;
		}
		
        for (Map<String, Object> map : getList) {
            Address address = new Address();

            address.setAddressId((int) map.get("address_id"));
            address.setFullname((String) map.get("fullname"));
            address.setFurigana((String) map.get("furigana"));
            address.setAddress((String) map.get("address"));
            address.setTel((String) map.get("tel"));
            address.setMail((String) map.get("mail"));
            address.setNote((String) map.get("note"));
            address.setUserId((String) map.get("user_id"));

            addressList.add(address);
        }

        return addressList;
	}
	
	//引数の住所IDを持つ住所レコードを1件取得する
	public Address selectOne(int addressId){
		String sql = 
				"SELECT address_id, fullname, furigana, address, tel, mail, note, user_id FROM address WHERE address_id=?";
		Map<String, Object> map;
		Address address = new Address();
		
		try {
			map = jdbc.queryForMap(sql, addressId);
			
		} catch (EmptyResultDataAccessException e) {
			return address;
		}
		
		address.setAddressId((int) map.get("address_id"));
		address.setFullname((String) map.get("fullname"));
		address.setFurigana((String) map.get("furigana"));
		address.setAddress((String) map.get("address"));
		address.setTel((String) map.get("tel"));
		address.setMail((String) map.get("mail"));
		address.setNote((String) map.get("note"));
		address.setUserId((String) map.get("user_id"));

        return address;
	}
	
	//住所IDをもとに住所の情報を更新する
	public int updateOne(Address address) throws DataAccessException{
		String sql = "UPDATE address SET "
					+ "fullname = ?, furigana = ?, address = ?, tel = ?, "
					+ "mail = ?, note = ? WHERE address_id = ?";
		
		int rowNumber = jdbc.update(sql, 
				address.getFullname(), address.getFurigana(), address.getAddress(), address.getTel(), 
				address.getMail(), address.getNote(), address.getAddressId());
		
		return rowNumber;
	}
	
	//引数の住所IDを持つ住所を削除する
	public int deleteOne(int addressId) throws DataAccessException{
		String sql = "DELETE FROM address WHERE address_id = ?";
		
		int rowNumber = jdbc.update(sql, addressId);
		
		return rowNumber;
	}
	
	//新たに住所を1件追加する
	public int insertOne(Address address) throws DataAccessException{
		String sql = "INSERT INTO "
				+ "address(fullname, furigana, address, tel, mail, note, user_id) "
				+ "VALUES(?, ?, ?, ?, ?, ?, ?)";

		int rowNumber = jdbc.update(sql, 
				address.getFullname(), address.getFurigana(), address.getAddress(), address.getTel(), 
				address.getMail(), address.getNote(), address.getUserId());

		return rowNumber;
	}
	
	//住所を全件取得してCSV形式にする
	public void addressCsvOut(){
		String sql = "SELECT address_id, fullname, furigana, address, tel, mail, note, user_id FROM address";
		
        AddressRowCallbackHandler handler = new AddressRowCallbackHandler();

        jdbc.query(sql, handler);
	}
}
