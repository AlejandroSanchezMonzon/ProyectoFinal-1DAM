
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


-- Declaración de la tabla `menu`

CREATE TABLE `menu` (
  `nombre` char(100) NOT NULL DEFAULT '',
  `uuid` char(45) NOT NULL,
  `precio` float DEFAULT NULL CHECK(precio>=0),
  `productos` varchar(200) DEFAULT '',
  `coddescuento` varchar(10) DEFAULT NULL,
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
  `id` int NOT NULL,
  `venta` varchar(200),
  `cliente` char(100),
  `localizador` int(3),
  PRIMARY KEY (`uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


-- Declaración de la tabla `factura`

CREATE TABLE `factura` (
  `pedido` char(100) NOT NULL DEFAULT '',
  `titulo` char(100) DEFAULT '',
  `fecha` datetime,
  PRIMARY KEY (`pedido`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


-- Declaración de la tabla `localizador`

CREATE TABLE `localizador` (
  `estado` enum('libre', 'ocupado'),
  `numero` int(3),
  PRIMARY KEY (`numero`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


-- Declaración de la tabla `cliente`

CREATE TABLE `cliente` (
  `puntos` int DEFAULT '0',
  `porcendesc` float UNSIGNED DEFAULT '0',
  PRIMARY KEY (`puntos`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


-- Declaración de la tabla `personaRegistrada`

CREATE TABLE `personaRegistrada` (
  `nombre` char(100) NOT NULL DEFAULT '',
  `uuid` char(45) NOT NULL,
  `correo` varchar(100) NOT NULL,
  `contraseña` varchar(100) NOT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;