<%-- 
    Document   : TTT
    Created on : 29/05/2019, 11:11:27 PM
    Author     : Mitch
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" import="java.io.*,java.util.*"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>TTT</title>
        <script
        src="https://code.jquery.com/jquery-3.4.1.min.js"
        integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
        crossorigin="anonymous"></script>
        <style>
            body {
                background-color: lightgray;
            }
            
            h1 {
                text-align: center;
                font-size: 4em;
            }

            table {
                margin: auto;
            }

            table tr td{
                height: 100px;
                width: 100px;
                text-align: center;
                font-size: 5em;
            }
            
            button {
                background: #00B4DB;  /* fallback for old browsers */
                background: -webkit-linear-gradient(to right, #0083B0, #00B4DB);  /* Chrome 10-25, Safari 5.1-6 */
                background: linear-gradient(to right, #0083B0, #00B4DB); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
                width: 10em;
                height: 5em;
                border-radius: 40px;
                border-color: rgba(255, 255, 255, 0);
                margin: 10px 10px;
            }
            
            button:hover {
                background: #006080;
            }
            
            #winner {
                text-align: center;
                font-size: 3em;
                border: none;
            }
            
            .clickable:hover {
                background-color: #006080;
            }
            
            .hidden {
                display: none;
            }

            .topdown {
                border-top: 1px solid black;
                border-bottom: 1px solid black;
            }

            .leftright {
                border-left: 1px solid black;
                border-right: 1px solid black;
            }
            
            .submit {
                display: inline-block;
            }
            
            #container {
                display: flex;
                justify-content: center;
            }
            
            #forms, #reset {
                text-align: center;
            }
            
            #reset {
                margin-top: 15px;
            }
            
            #board {
                background: #00B4DB;  /* fallback for old browsers */
                background: -webkit-linear-gradient(to right, #0083B0, #00B4DB);  /* Chrome 10-25, Safari 5.1-6 */
                background: linear-gradient(to right, #0083B0, #00B4DB); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
                width: fit-content;
            }
        </style>
    </head>
    <body>
        <%
            String gameState[][] = new String[3][3];
            for (int i = 0; i<gameState.length;i++) {
                for (int j = 0; j<gameState[i].length; j++) {
                    gameState[i][j] = "_";
                }
            }
            if (session.isNew()){
                session.setAttribute("Game", gameState);
            } else {
                gameState = (String[][])session.getAttribute("Game");
            }
        %>
        <h1>Tic Tac Toe</h1>
        <div id="forms">
            <h2>Create Game</h2>
            <button type="submit" id="user-starts" class="submit">User Starts</button>
            <button type="submit" id="computer-starts" class="submit">Computer Starts</button>
        </div>
        <div id="container" class="hidden">
            <div id="board">
            </div>
        </div>
        <div  id="winner" class="hidden">
            <h3></h3>
        </div>
        <div class="hidden" id="reset">
            <button id="resetUser">Reset User Starts</button>
            <button id="resetComputer">Reset Computer Starts</button>
        </div>
        <script>
            $(document).ready(function(){
                if (<%=session.getAttribute("gameStarted")%> === true) {
                    $("#forms").addClass("hidden");
                    $("#container").removeClass("hidden");
                    $("#reset").removeClass("hidden");
                    $.get("<%=response.encodeURL("state?format=png")%>", function(data){
                        $("#board").html(data);
                        $("#board td").addClass("clickable");
                        $("#board td").each(function(){
                            if ($(this).html() === "_") {
                                $(this).html("");
                            }
                        });
                    });
                    $.get("<%=response.encodeURL("won")%>", function(data){
                        $("#winner h3").html("Winner: "+ data);
                        if (!data.match("none")){
                           $("#winner").removeClass("hidden");
                        }
                    });
                }
            });
            
            $("#resetUser").on("click", function(){
                $.post("<%=response.encodeURL("istart")%>");
                $("#winner").addClass("hidden");
                setTimeout(function(){
                    $.get("<%=response.encodeURL("state?format=png")%>", function(data){
                        $("#board").html(data);
                        $("#board td").addClass("clickable");
                        $("#board td").each(function(){
                            if ($(this).html() === "_") {
                                $(this).html("");
                            }
                        });
                    });
                }, 100); 
            });
            
            $("#resetComputer").on("click", function(){
                $.post("<%=response.encodeURL("ustart")%>");
                $("#winner").addClass("hidden");
                setTimeout(function(){
                    $.get("<%=response.encodeURL("state?format=png")%>", function(data){
                        $("#board").html(data);
                        $("#board td").addClass("clickable");
                        $("#board td").each(function(){
                            if ($(this).html() === "_") {
                                $(this).html("");
                            }
                        });
                    });
                }, 100); 
            });
            
            var box1 = function(){
                $.post("<%=response.encodeURL("move/x1y1")%>");
                finishMove();
            };
            
            var box2 = function(){
                $.post("<%=response.encodeURL("move/x2y1")%>");
                finishMove();
            };
            
            var box3 = function(){
                $.post("<%=response.encodeURL("move/x3y1")%>");
                finishMove();
            };
            
            var box4 = function(){
                $.post("<%=response.encodeURL("move/x1y2")%>");
                finishMove();
            };
            
            var box5 = function(){
                $.post("<%=response.encodeURL("move/x2y2")%>");
                finishMove();
            };
            
            var box6 = function(){
                $.post("<%=response.encodeURL("move/x3y2")%>");
                finishMove();
            };
            
            var box7 = function(){
                $.post("<%=response.encodeURL("move/x1y3")%>");
                finishMove();
            };
            
            var box8 = function(){
                $.post("<%=response.encodeURL("move/x2y3")%>");
                finishMove();
            };
            
            var box9 = function(){
                $.post("<%=response.encodeURL("move/x3y3")%>");
                finishMove();
            };
           
            
            var finishMove = function() {
                setTimeout(function(){
                    $.get("<%=response.encodeURL("state?format=png")%>", function(data){
                        $("#board").html(data);
                        $("#board td").addClass("clickable");
                        $("#board td").each(function(){
                            if ($(this).html() === "_") {
                                $(this).html("");
                            }
                        });
                    });
                    $.get("<%=response.encodeURL("won")%>", function(data){
                        $("#winner h3").html("Winner: "+ data);
                        if (!data.match("none")){
                           $("#winner").removeClass("hidden");
                        }
                    });
                }, 100); 
            };
            
            $("#user-starts").on("click", function(){
                $.post("<%=response.encodeURL("istart")%>");
                setTimeout(function(){
                    $.get("<%=response.encodeURL("state?format=png")%>", function(data){
                        $("#board").html(data);
                        $("#board td").addClass("clickable");
                        $("#board td").each(function(){
                            if ($(this).html() === "_") {
                                $(this).html("");
                            }
                        });
                    });
                }, 100); 
            });
            
            $("#computer-starts").on("click", function(){
                $.post("<%=response.encodeURL("ustart")%>");
                setTimeout(function(){
                    $.get("<%=response.encodeURL("state?format=png")%>", function(data){
                        $("#board").html(data);
                        $("#board td").addClass("clickable");
                        $("#board td").each(function(){
                            if ($(this).html() === "_") {
                                $(this).html("");
                            }
                        });
                    });
                }, 100); 
            });
            
            $(".submit").on("click", function(){
                $("#forms").addClass("hidden");
                $("#container").removeClass("hidden");
                $("#reset").removeClass("hidden");
            });
            
            
        </script>
    </body>
</html>
