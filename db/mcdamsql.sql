
DROP DATABASE IF EXISTS mcDam;
CREATE DATABASE mcDam;
use mcDam;
 							/* Todos los valores entre paréntesis están sujetos a cambios futuros. */

-- Declaración de la tabla `producto`

CREATE TABLE `producto` (
  `nombre` char(100) NOT NULL DEFAULT '',
  `uuid` char(45) NOT NULL,
  `descripción` char(200) DEFAULT NULL,
  `disponibilidad` boolean DEFAULT NULL,
  `coddescuento` varchar(10) DEFAULT '0',
  PRIMARY KEY (`uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


-- Declaración de la tabla `codigoDescuento`

CREATE TABLE `codigoDescuento` (
  `codigo` varchar(10),
  `porcendesc` float UNSIGNED DEFAULT '0',
  PRIMARY KEY (`codigo`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


-- Declaración de la tabla `pedido`

CREATE TABLE `pedido` (
  `metodopago` enum('efectivo', 'tarjeta'),
  `uuid` char(45) NOT NULL,
  `precio` float DEFAULT '0' CHECK(precio>=0),
  `compra` varchar(200),
  `cliente` char(100),
  `localizador` int(3),
  PRIMARY KEY (`uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Declaración de la tabla `personaRegistrada`

CREATE TABLE `personaRegistrada` (
  `nombre` char(100) NOT NULL DEFAULT '',
  `uuid` char(45) NOT NULL,
  `correo` varchar(100) NOT NULL,
  `contraseña` varchar(100) NOT NULL,
  `tipo` enum('USER', 'ADMIN'),
  PRIMARY KEY (`uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;