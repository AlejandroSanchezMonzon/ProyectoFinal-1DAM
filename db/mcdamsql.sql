
 							/* Todos los valores entre paréntesis están sujetos a cambios futuros. */

-- Declaración de la tabla `producto`

CREATE TABLE `producto` (
  `nombre` text,
  `uuid` text primary key,
  `descripcion` text,
  `disponibilidad` text,
  `coddescuento` integer
);


-- Declaración de la tabla `codigoDescuento`

CREATE TABLE `codigoDescuento` (
  `codigo` text primary key ,
  `porcendesc` integer
);


-- Declaración de la tabla `pedido`

CREATE TABLE `pedido` (
  `metodopago` text,
  `uuid` text primary key,
  `precio` text,
  `compra` text,
  `cliente` text,
  `localizador` integer
);

-- Declaración de la tabla `personaRegistrada`

CREATE TABLE `personaRegistrada` (
  `nombre`text ,
  `uuid` text primary key,
  `correo` text,
  `contraseña` text,
  `tipo` text
);