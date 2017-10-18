<%@page import="model.mechaTable.StringDataList"%>
<%@page contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%> 

<%@page language="java" import="dbUtils.*" %>
<%@page language="java" import="model.mechaTable.*" %>
<%@page language="java" import="java.util.ArrayList" %>

<%@page language="java" import="com.google.gson.*" %>

<%

    StringDataList list = new StringDataList();

    DbConn dbc = new DbConn();
    list.dbError = dbc.getErr(); // returns "" if connection is good, else db error msg.

    if (list.dbError.length() == 0) { // got open connection 

        String mechaName = request.getParameter("q");
        String sortType = request.getParameter("s");
        mechaName = mechaName.substring(0, mechaName.indexOf('_'));
        if(sortType == null){sortType = "";}
        
        if (mechaName == null) { 
            mechaName = "";
        }

        // countryFlagList is an object with an array of country objects inside, 
        // plus a possible dbError.
        System.out.println("jsp page ready to search for mechas with " + mechaName);
        System.out.println("Sort type: " + sortType);  
        list = new StringDataList(mechaName,sortType, dbc);
    } 

    // PREVENT DB connection leaks:
    dbc.close(); // EVERY code path that opens a db connection, must also close it.

    Gson gson = new Gson();
    out.print(gson.toJson(list).trim()); 
%>