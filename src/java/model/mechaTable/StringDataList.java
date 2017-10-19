package model.mechaTable;

import dbUtils.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class StringDataList {

    public String dbError = "";
    private ArrayList<StringData> recordList = new ArrayList();

    // Default constructor just leaves the 2 data members initialized as above
    public StringDataList() {
    }

    // overloaded constructor populates the list (and possibly the dbError)
    public StringDataList(String mechaNameStartsWith, String searchBy, DbConn dbc) {

        StringData sd = new StringData();
        System.out.println("Searching for mecha that start with " + mechaNameStartsWith);
        try {

            String sql = "SELECT mechaTable_ID, mechaName, mechaURL, mechaHeight,"
                    + " mechaDescriptor FROM mechaTable WHERE "+ searchBy + " LIKE ? ORDER "
                    + "BY mechaName";

            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
            stmt.setString(1, mechaNameStartsWith + "%");
            ResultSet results = stmt.executeQuery();
            System.out.println(stmt.toString());
            while (results.next()) {
                try {
                    sd = new StringData();
                    sd.mechaTable_ID = FormatUtils.formatInteger(results.getObject("mechaTable_ID"));
                    sd.mechaDescriptor = FormatUtils.formatString(results.getObject("mechaDescriptor"));
                    sd.mechaURL = FormatUtils.formatString(results.getObject("mechaURL"));
                    sd.mechaName = FormatUtils.formatString(results.getObject("mechaName"));
                    sd.mechaHeight = FormatUtils.formatInteger(results.getObject("mechaHeight"));
                    System.out.println("Returned info: " + sd.toString());
                    this.recordList.add(sd);
                } catch (Exception e) {
                    sd.errorMsg = "Something's wrong " + e.getMessage();
                    this.recordList.add(sd);
                }
            } // while
        } catch (Exception e) {
            this.dbError = "Somethign else is wrong " + e.getMessage();
        }
    } // method

} // class
