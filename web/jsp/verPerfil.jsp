<%-- 
    Document   : verPerfil
    Created on : Jun 19, 2021, 10:14:21 AM
    Author     : USUARIO
--%>

<%@page import="DTO.Estudiante"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="../css/libro.css">
    <link rel="stylesheet" href="../css/prestamos.css">
    <link rel="stylesheet" href="../css/estilos.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous">
    </script>
</head>

<body >
    <div class="container-fluid">

        <header>
            
               <!-- headerrrrrrrrrrrrrr -->
        <header class="shadow">
            <div class="col-12">
                <div class="collapse" style="color: antiquewhite;" id="navbarToggleExternalContent">
                    <div class=" p-4" style="background-color: #93291E">
                        <% String nom = request.getSession().getAttribute("nombreUser").toString(); %>
                        <h5><%=nom%></h5>
                        <ul class="list-group">
                            
                            <a href="../IrInicio.do"> <li class="list-group-item" style="color: antiquewhite; background-color: #93291E">
                                Inicio</li></a>
                            
                            <a href="../VerHistorial.do"> <li class="list-group-item" style="color: antiquewhite; background-color: #93291E">
                                Ver Historial</li></a>
                        </ul>
                    </div>
                </div>
                <nav class=" navbar-light" style="color: antiquewhite; background-color: #f3efef33;">

                    <div class="container">
                        <div class="row">
                            <div class="col-2 ">
                                <button class="navbar navbar-toggler bg-light m-2" style="color: black" type="button"
                                        data-bs-toggle="collapse" data-bs-target="#navbarToggleExternalContent"
                                        aria-controls="navbarToggleExternalContent" aria-expanded="false"
                                        aria-label="Toggle navigation">
                                    <span class="navbar-toggler-icon"></span>
                                     <% String img = request.getSession().getAttribute("urlImg").toString(); %>
                                    <img src="https://i.postimg.cc/pLs6K1NP/UFPS-Logo.png" alt="" style="width: 30px;">
                                </button>
                            </div>
                            <div class="col-6 d-flex justify-content-end align-items-center text-end">
                            </div>
                            <div class="col-2 d-flex justify-content-end align-items-center">
                                <button type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasRight"
                                        aria-controls="offcanvasRight" class="btn btn-light rounded float-end ">
                                    <img src="https://i.postimg.cc/C19ykrqR/bell-1.png" style="width: 20px;" alt="">
                                    <span class="badge bg-danger">
                                        <%
                                            int cant = Integer.parseInt(request.getSession().getAttribute("cantNoti").toString());
                                        %>
                                        <%=cant%> 
                                    </span>
                                </button>
                                <!-- CANVAS NOTIFICACIONES -->
                                <div style="color: black;" class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasRight"
                                     aria-labelledby="offcanvasRightLabel">
                                    <div class="offcanvas-header"> 
                                        <h5 id="offcanvasRightLabel">Notificaciones</h5>
                                        <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas"
                                                aria-label="Close"></button>

                                    </div>
                                    <div class="offcanvas-body">
                                        <%
                                            String noti = request.getSession().getAttribute("notificacionesEstudiante").toString();
                                        %>
                                        <%=noti%>
                                        <button class="btn btn-danger">Marcar como leido</button>
                                    </div>
                                </div>
                            </div>
                            <div class="col-1 d-flex justify-content-end align-items-center">
                                <img src="<%=img%>" class="rounded-circle float-end"
                                     style="width: 50px;">
                            </div>
                            <div class="col-1 d-flex justify-content-center align-items-center">
                                <form action="../CerrarSesion.do">
                                <button type="submit" class="btn"><img style="width: 30px;" src="data:image/svg+xml;base64,PHN2ZyBoZWlnaHQ9IjUxMnB0IiB2aWV3Qm94PSIwIDAgNTEyLjAxNiA1MTIiIHdpZHRoPSI1MTJwdCIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj48cGF0aCBkPSJtNDk2IDI0MC4wMDc4MTJoLTIwMi42Njc5NjljLTguODMyMDMxIDAtMTYtNy4xNjc5NjgtMTYtMTYgMC04LjgzMjAzMSA3LjE2Nzk2OS0xNiAxNi0xNmgyMDIuNjY3OTY5YzguODMyMDMxIDAgMTYgNy4xNjc5NjkgMTYgMTYgMCA4LjgzMjAzMi03LjE2Nzk2OSAxNi0xNiAxNnptMCAwIi8+PHBhdGggZD0ibTQxNiAzMjAuMDA3ODEyYy00LjA5NzY1NiAwLTguMTkxNDA2LTEuNTU4NTkzLTExLjMwODU5NC00LjY5MTQwNi02LjI1LTYuMjUzOTA2LTYuMjUtMTYuMzg2NzE4IDAtMjIuNjM2NzE4bDY4LjY5NTMxMy02OC42OTE0MDctNjguNjk1MzEzLTY4LjY5NTMxMmMtNi4yNS02LjI1LTYuMjUtMTYuMzgyODEzIDAtMjIuNjMyODEzIDYuMjUzOTA2LTYuMjUzOTA2IDE2LjM4NjcxOS02LjI1MzkwNiAyMi42MzY3MTkgMGw4MCA4MGM2LjI1IDYuMjUgNi4yNSAxNi4zODI4MTMgMCAyMi42MzI4MTNsLTgwIDgwYy0zLjEzNjcxOSAzLjE1NjI1LTcuMjMwNDY5IDQuNzE0ODQzLTExLjMyODEyNSA0LjcxNDg0M3ptMCAwIi8+PHBhdGggZD0ibTE3MC42Njc5NjkgNTEyLjAwNzgxMmMtNC41NjY0MDcgMC04Ljg5ODQzOC0uNjQwNjI0LTEzLjIyNjU2My0xLjk4NDM3NGwtMTI4LjM4NjcxOC00Mi43NzM0MzhjLTE3LjQ2ODc1LTYuMTAxNTYyLTI5LjA1NDY4OC0yMi4zNzg5MDYtMjkuMDU0Njg4LTQwLjU3NDIxOXYtMzg0YzAtMjMuNTMxMjUgMTkuMTM2NzE5LTQyLjY2Nzk2ODUgNDIuNjY3OTY5LTQyLjY2Nzk2ODUgNC41NjI1IDAgOC44OTQ1MzEuNjQwNjI1NSAxMy4yMjY1NjIgMS45ODQzNzU1bDEyOC4zODI4MTMgNDIuNzczNDM3YzE3LjQ3MjY1NiA2LjEwMTU2MyAyOS4wNTQ2ODcgMjIuMzc4OTA2IDI5LjA1NDY4NyA0MC41NzQyMTl2Mzg0YzAgMjMuNTMxMjUtMTkuMTMyODEyIDQyLjY2Nzk2OC00Mi42NjQwNjIgNDIuNjY3OTY4em0tMTI4LTQ4MGMtNS44NjcxODggMC0xMC42Njc5NjkgNC44MDA3ODItMTAuNjY3OTY5IDEwLjY2Nzk2OXYzODRjMCA0LjU0Mjk2OSAzLjA1MDc4MSA4Ljc2NTYyNSA3LjQwMjM0NCAxMC4yODEyNWwxMjcuNzg1MTU2IDQyLjU4MjAzMWMuOTE3OTY5LjI5Njg3NiAyLjExMzI4MS40Njg3NSAzLjQ4MDQ2OS40Njg3NSA1Ljg2NzE4NyAwIDEwLjY2NDA2Mi00LjgwMDc4MSAxMC42NjQwNjItMTAuNjY3OTY4di0zODRjMC00LjU0Mjk2OS0zLjA1MDc4MS04Ljc2NTYyNS03LjQwMjM0My0xMC4yODEyNWwtMTI3Ljc4NTE1Ny00Mi41ODIwMzJjLS45MTc5NjktLjI5Njg3NC0yLjExMzI4MS0uNDY4NzUtMy40NzY1NjItLjQ2ODc1em0wIDAiLz48cGF0aCBkPSJtMzI1LjMzMjAzMSAxNzAuNjc1NzgxYy04LjgzMjAzMSAwLTE2LTcuMTY3OTY5LTE2LTE2di05NmMwLTE0LjY5OTIxOS0xMS45NjQ4NDMtMjYuNjY3OTY5LTI2LjY2NDA2Mi0yNi42Njc5NjloLTI0MGMtOC44MzIwMzEgMC0xNi03LjE2Nzk2OC0xNi0xNiAwLTguODMyMDMxIDcuMTY3OTY5LTE1Ljk5OTk5OTUgMTYtMTUuOTk5OTk5NWgyNDBjMzIuMzYzMjgxIDAgNTguNjY0MDYyIDI2LjMwNDY4NzUgNTguNjY0MDYyIDU4LjY2Nzk2ODV2OTZjMCA4LjgzMjAzMS03LjE2Nzk2OSAxNi0xNiAxNnptMCAwIi8+PHBhdGggZD0ibTI4Mi42Njc5NjkgNDQ4LjAwNzgxMmgtODUuMzM1OTM4Yy04LjgzMjAzMSAwLTE2LTcuMTY3OTY4LTE2LTE2IDAtOC44MzIwMzEgNy4xNjc5NjktMTYgMTYtMTZoODUuMzM1OTM4YzE0LjY5OTIxOSAwIDI2LjY2NDA2Mi0xMS45Njg3NSAyNi42NjQwNjItMjYuNjY3OTY4di05NmMwLTguODMyMDMyIDcuMTY3OTY5LTE2IDE2LTE2czE2IDcuMTY3OTY4IDE2IDE2djk2YzAgMzIuMzYzMjgxLTI2LjMwMDc4MSA1OC42Njc5NjgtNTguNjY0MDYyIDU4LjY2Nzk2OHptMCAwIi8+PC9zdmc+" /></button>
                                </form>
                            </div>
                        </div>
                    </div>
                </nav>
            </div>
        </header>
        <br><br><br>

        <main>
            
            <%
                        Estudiante e = (Estudiante)(request.getSession().getAttribute("usuario"));
                    %>

            <div class="container d-flex justify-content-center my-5">

                <section class="row mt-5 mb-1 shadow-lg p-3  bg-white w-50 d-flex ">


                    <div class="col-5 d-flex justify-content-end">
                        <img class="" id="fotoPerfil"
                            src=<%= e.getUrlFoto() %> alt="Foto Perfil">
                    </div>
                    
                    



                    <div class="col-7 ">

                        <div class="row">


                            <p><b>Nombre :</b> <%= e.getNombre() %></p>
                        </div>

                        <div class="row">

                            <p><b>Codigo: </b> <%= e.getEstudiantePK().getCodigo() %></p>
                        </div>
                        <div class="row">
                            
                            
                        </div>

                    </div>

                    <h3 class="text-center my-4">Datos Personales</h3>
                    <p><b>Correo: </b> <%= e.getEstudiantePK().getEmail() %></p>
                    <p><b>Telefono: </b> <%= e.getTelefono() %></p>

                </section>




            </div>

        </main>

        <footer>

        </footer>
    </div>

</body>

</html>