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
    public StringDataList(String mechaNameStartsWith, DbConn dbc) {

        StringData sd = new StringData();

        System.out.println("Searching for mecha that start with " + mechaNameStartsWith);

        try {

            String sql = "SELECT mechaTable_ID, mechaName, mechaURL FROM mechaTable "
                    + "WHERE mechaName LIKE ? ORDER BY mechaTable_ID";

            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
            stmt.setString(1, mechaNameStartsWith + "%");
            ResultSet results = stmt.executeQuery();

            while (results.next()) {
                try {
                    sd = new StringData();
                    sd.mechaTable_ID = FormatUtils.formatInteger(results.getObject("mechaTable_ID"));
                    sd.mechaDescriptor = FormatUtils.formatString(results.getObject("mechaName"));
                    sd.mechaURL = FormatUtils.formatString(results.getObject("mechaURL"));
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
