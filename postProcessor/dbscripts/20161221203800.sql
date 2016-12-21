
CREATE TABLE `accion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `zona` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `interaccion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `frame_fin` int(11) DEFAULT NULL,
  `frame_inicio` int(11) DEFAULT NULL,
  `horario` datetime DEFAULT NULL,
  `x` int(11) DEFAULT NULL,
  `y` int(11) DEFAULT NULL,
  `z` int(11) DEFAULT NULL,
  `zona_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKr0nfwjx828r1dttk8r50ornqt` (`zona_id`),
  CONSTRAINT `FKr0nfwjx828r1dttk8r50ornqt` FOREIGN KEY (`zona_id`) REFERENCES `zona` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `interaccion_acciones` (
  `interaccion_id` int(11) NOT NULL,
  `acciones_id` int(11) NOT NULL,
  KEY `FKjqkhvwmisdpk1cir1m0b2s7vs` (`acciones_id`),
  KEY `FK5rmvbntxvxr3aer0srl76980k` (`interaccion_id`),
  CONSTRAINT `FK5rmvbntxvxr3aer0srl76980k` FOREIGN KEY (`interaccion_id`) REFERENCES `interaccion` (`id`),
  CONSTRAINT `FKjqkhvwmisdpk1cir1m0b2s7vs` FOREIGN KEY (`acciones_id`) REFERENCES `accion` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `video` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fecha_inicio` datetime DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `ubicacion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `recorrido` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `altura` int(11) DEFAULT NULL,
  `duracion` int(11) DEFAULT NULL,
  `edad` int(11) DEFAULT NULL,
  `frame_entrada` int(11) DEFAULT NULL,
  `frame_salida` int(11) DEFAULT NULL,
  `horario_entrada` datetime DEFAULT NULL,
  `horario_salida` datetime DEFAULT NULL,
  `blob_id` int(11) DEFAULT NULL,
  `sentido_entrada` varchar(255) DEFAULT NULL,
  `sentido_salida` varchar(255) DEFAULT NULL,
  `sexo` varchar(255) DEFAULT NULL,
  `x` int(11) DEFAULT NULL,
  `y` int(11) DEFAULT NULL,
  `video_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKt9tgqsmglrcnbyy42qpwmhajc` (`video_id`),
  CONSTRAINT `FKt9tgqsmglrcnbyy42qpwmhajc` FOREIGN KEY (`video_id`) REFERENCES `video` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `video` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fecha_inicio` datetime DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `ubicacion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
