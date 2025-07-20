CREATE DATABASE agencia_viajes2;
USE agencia_viajes2;
-- SELECT * FROM Usuarios;

CREATE TABLE Clientes (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    correo VARCHAR(100) UNIQUE,
    cedula VARCHAR(20) UNIQUE
);

CREATE TABLE Usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) UNIQUE NOT NULL,
    contrasena VARCHAR(255) NOT NULL, -- Contraseña encriptada
    fecha_registro DATE NOT NULL DEFAULT (CURDATE()) -- Cambiado a CURDATE() con paréntesis
);

CREATE TABLE Rutas (
    id_ruta INT AUTO_INCREMENT PRIMARY KEY,
    origen VARCHAR(50) NOT NULL,
    destino VARCHAR(50) NOT NULL,
    duracion TIME NOT NULL -- Duración estimada del viaje
);

CREATE TABLE Pasajes (
    id_pasaje INT AUTO_INCREMENT PRIMARY KEY,
    id_ruta INT NOT NULL,
    tipo_transporte VARCHAR(20) NOT NULL, -- Tipo de transporte (avion, tren, autobus)
    fecha_salida DATE NOT NULL,
    hora_salida TIME NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    disponibilidad INT NOT NULL,
    FOREIGN KEY (id_ruta) REFERENCES Rutas(id_ruta)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Reservas (
    id_reserva INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    id_pasaje INT NOT NULL,
    estado VARCHAR(20) NOT NULL, -- Estado de la reserva (pendiente, confirmada, cancelada)
    fecha_reserva DATE NOT NULL DEFAULT (CURDATE()),
    FOREIGN KEY (id_cliente) REFERENCES Clientes(id_cliente)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_pasaje) REFERENCES Pasajes(id_pasaje)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Pagos (
    id_pago INT AUTO_INCREMENT PRIMARY KEY,
    id_reserva INT NOT NULL,
    monto DECIMAL(10, 2) NOT NULL,
    metodo_pago VARCHAR(50) NOT NULL, -- Método de pago (tarjeta_credito, paypal, efectivo)
    estado VARCHAR(20) NOT NULL, -- Estado del pago (exitoso, fallido, pendiente)
    fecha_pago DATE NOT NULL DEFAULT (CURDATE()),
    FOREIGN KEY (id_reserva) REFERENCES Reservas(id_reserva)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Inventario (
    id_inventario INT AUTO_INCREMENT PRIMARY KEY,
    id_ruta INT NOT NULL,
    id_pasaje INT NOT NULL,
    cantidad_disponible INT NOT NULL,
    FOREIGN KEY (id_ruta) REFERENCES Rutas(id_ruta)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_pasaje) REFERENCES Pasajes(id_pasaje)
        ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO Usuarios (nombre, correo, contrasena)
VALUES 
('Juan Pérez', 'juan.perez@example.com', 'password1');


INSERT INTO Usuarios (nombre, correo, contrasena, fecha_registro)
VALUES ('Admin', 'admin@example.com', '1234', CURDATE());

ALTER TABLE Usuarios MODIFY COLUMN contrasena VARCHAR(255) NOT NULL;

CREATE TABLE servicio (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(100),
    detalle VARCHAR(100),
    precio DOUBLE
);

CREATE TABLE productos (
    id_producto VARCHAR(10) PRIMARY KEY,
    nombre VARCHAR(100),
    categoria VARCHAR(50),
    stock INT,
    precio DECIMAL(10,2)
);

CREATE TABLE facturas (
    id_factura INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT,
    fecha_hora DATETIME,
    total DECIMAL(10,2),
    FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente)
);

CREATE TABLE detalle_factura (
    id_detalle INT AUTO_INCREMENT PRIMARY KEY,
    id_factura INT,
    tipo VARCHAR(20), -- Producto o Servicio
    descripcion VARCHAR(255),
    precio_unitario DECIMAL(10,2),
    cantidad INT,
    subtotal DECIMAL(10,2),
    FOREIGN KEY (id_factura) REFERENCES facturas(id_factura)
);





