package com.example.addressBook.repository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowCallbackHandler;

public class AddressRowCallbackHandler implements RowCallbackHandler {

    @Override
    public void processRow(ResultSet rs) throws SQLException {

        try {
            File file = new File("addressList.csv");
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            do {
                String str = rs.getInt("address_id") + ","
                        	+ rs.getString("fullname") + ","
                        	+ rs.getString("furigana") + ","
                        	+ rs.getString("address") + ","
                        	+ rs.getString("tel") + ","
                        	+ rs.getString("mail") + ","
                        	+ rs.getString("note") + ","
                        	+ rs.getString("user_id");

                bw.write(str);
                bw.newLine();

            } while(rs.next());

            bw.flush();
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
            throw new SQLException(e);
        }
    }
}
