<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dto.UserRecordDTO, java.util.ArrayList" %>

<%
ArrayList<UserRecordDTO> searchResults = (ArrayList<UserRecordDTO>) session.getAttribute("search_result");
if (searchResults == null) {
    searchResults = new ArrayList<UserRecordDTO>();
}
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>検索結果</title>
</head>
<body>
    <h1>検索</h1>
    
    <form action="search" method="post">
        <input type="text" name="kensaku">
        <input type="submit" value="検索">
    </form>
    <br/>

    <form action="/shohinKanri_12/Delete" method="post">
        <table border="1">
            <thead>
                <tr>
                    <td>選択</td>
                    <td>アイコン</td>
                    <td>名前</td>
                    <td>県</td>
                </tr>
            </thead>
            <tbody>
                <% if (searchResults.isEmpty()) { %>
       			 <tr>
 				 <td colspan="4">結果が見つかりませんでした。</td>
				 </tr>
				<% } else { %>
                    <% for (UserRecordDTO user : searchResults) { %>
                        <tr>
                            <td><img src="<%= user.getAccountIcon() %>" ></td>
                            <td><%= user.getAccountName() %></td>
                            <td><%= user.getAccountKen() %></td>
                        </tr>
                    <% } %>
                 <% } %>
            </tbody>
        </table>

        <a href="/shohinKanri_12/shohinAdd.jsp">追加</a>
        <input type="submit" value="削除">
    </form>
</body>
</html>
