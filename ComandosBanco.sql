CREATE SCHEMA `MorseCristian`;
CREATE TABLE `MorseCristian`.`Paciente` (
  `codigo` INT(11) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(100) NULL,
  `data_nascimento` DATE NULL,
  PRIMARY KEY (`codigo`)
);
CREATE TABLE `MorseCristian`.`Formulario` (
  `codigo` INT(11) NOT NULL AUTO_INCREMENT,
  `data` DATE NULL,
  `resultado1` INT NULL,
  `resultado2` INT NULL,
  `resultado3` INT NULL,
  `resultado4` INT NULL,
  `resultado5` INT NULL,
  `resultadofinal` INT NULL,
  `codigo_paciente` INT NULL,
  PRIMARY KEY (`codigo`),
  INDEX `codigo_paciente_idx` (`codigo_paciente` ASC),
  CONSTRAINT `codigo_paciente` FOREIGN KEY (`codigo_paciente`) REFERENCES `MorseCristian`.`Paciente` (`codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION
);