<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Página de Inicio</title>
</head>
<body>
<h1>Bienvenido al Sistema de Ventas</h1>
<p><a href="Inicio">Ver productos</a></p>
</body>
</html>

<form action="Inicio" method="post">
    <label>Nombre:</label>
    <input type="text" name="nombre" required>
    <label>Precio:</label>
    <input type="number" name="precio" required>
    <button type="submit">Guardar</button>
</form>
