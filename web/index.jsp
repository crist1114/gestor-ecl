<%-- 
    Document   : index
    Created on : 19/06/2021, 03:46:16 PM
    Author     : Cristian
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login</title>
        <link rel="stylesheet" href="./css/index.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous">
        </script>
    </head>

    <body">
        <div class="container-fluid ">



            <main>

                <div class="row">

                    <div class="col-11  d-flex justify-content-center ">

                        <div class=" div-container shadow p-2 mb-5 bg-white  mt-5 pt-5 w-25"   > 
                            <form action="ValidarUsuario.do">


                                <div class="row text-center mb-2">
                                    <h1>Gestor ECL</h1>
                                </div>

                                <div class="row d-flex justify-content-center">

                                    <div class="col-10 my-2">
                                        <!-- <label for="correo">Email</label> -->
                                        <input type="email" class="form-control" id="correo" name="correo" placeholder="Email"
                                               required>
                                    </div>
                                </div>

                                <div class="row d-flex justify-content-center">
                                    <div class="col-10 my-2">
                                        <!-- <label for="password">Password</label> -->
                                        <input type="password" class="form-control" id="password" name="password"
                                               placeholder="Contraseña" required>
                                    </div>
                                </div>




                                <div class="row d-flex justify-content-center ">
                                    <div class="col-10 d-block justify-content-center my-2">
                                        <%
                                            String rta = request.getSession().getAttribute("error") != null
                                                    ? request.getSession().getAttribute("error").toString() : "";

                                        %>
                                        <%=rta%>

                                        <button type="submit" class="botones botonlogin">Iniciar Sesion</button>

                                    </div>
                                </div>
                                <div class="row d-flex justify-content-center ">
                                    <div class="col-10 d-block justify-content-center my-2">
                                        <button type="button" class="botones botonlogin"   > <a href="./html/loginAdmin.jsp" style="text-decoration: none; color: white;" >Soy Administrador </a> </button>
                                    </div>
                                </div>


                                <div class="row">
                                    <div class="col d-flex justify-content-center">
                                        <a href="https://ww2.ufps.edu.co/" target="_blank"><img
                                                src="https://i.postimg.cc/WpXbwy4F/logoufps.png" id="logoufps"
                                                alt="Logo UFPS"></a>
                                    </div>
                                </div>




                            </form>
                        </div>

                    </div>


                </div>


            </main>


        </div>

    </body>

</html>
