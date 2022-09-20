<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<DOCUMENTTYPE !html>
    <html>
<%@ page language="java" pageEncoding="UTF-8" %>
    <body>

    <h2>ALL PLAYERS</h2>

    <table>

        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Title</th>
            <th>Race</th>
            <th>Profession</th>
            <th>Experience</th>
            <th>Level</th>
            <th>UntilNextLevel</th>
            <th>Birthday</th>
            <th>Banned</th>
        </tr>
        <c:forEach var="pls" items="${allPlayersAtribut}" >
            <tr>
                <td>${pls.id}</td>
                <td>${pls.name}</td>
                <td>${pls.title}</td>
                <td>${pls.race}</td>
                <td>${pls.profession}</td>
                <td>${pls.experience}</td>
                <td>${pls.level}</td>
                <td>${pls.untilNextLevel}</td>
                <td>${pls.birthday}</td>
                <td>${pls.banned}</td>
            </tr>

        </c:forEach>
    </table>
    </body>
    </html>