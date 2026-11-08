package cl.elprofecarreno.app.controller.validator;

import java.util.regex.Pattern;

import javafx.scene.control.TextField;

public class FormValidator {
    
    private static FormValidator instancia;

    private static final Pattern RUT_PATTERN = Pattern.compile("^(\\d{1,3}(?:\\.\\d{3}){2}|\\d{7,8})-?[\\dkK]$");

    // 2. Constructor privado para evitar la instanciación externa
    private FormValidator() {
        // Inicialización (si es necesaria)
    }
    // 3. Método público para obtener la instancia única
    public static FormValidator getInstancia() {
        if (instancia == null) {
            instancia = new FormValidator();
        }
        return instancia;
    }

    public void isEmpty(TextField textField, String fieldName) throws ValidationException {

        if (textField.getText() == null || textField.getText().trim().isEmpty()) {
            throw new ValidationException("El campo " + fieldName + " no puede estar vacío");
        }
    }

    public void isMaxSize(TextField textField, String fieldName, int maxSize) throws ValidationException{
        if (textField.getText().trim().length() > maxSize) {
            throw new ValidationException("El campo " + fieldName + " no puede exceder " + maxSize + " caracteres");
        }
    }

    public void isEmptyOrMaxSize(TextField textField, String fieldName, int maxSize) throws ValidationException {
        isEmpty(textField, fieldName);
        isMaxSize(textField, fieldName, maxSize);
    }

    public void isRutValid(TextField textField) throws ValidationException {

        isEmpty(textField, "RUT");
        String rutInput = textField.getText();

        String rutLimpiar = rutInput.trim().toUpperCase();
        if (!RUT_PATTERN.matcher(rutLimpiar).matches()) {
            throw new ValidationException("El campo RUT no tiene un formato válido");
        }

        try {
            // 2. Limpiar puntos y guion
            String rutSinPuntosNiGuion = rutLimpiar.replace(".", "").replace("-", "");
            String cuerpo = rutSinPuntosNiGuion.substring(0, rutSinPuntosNiGuion.length() - 1);
            char dvIngresado = rutSinPuntosNiGuion.charAt(rutSinPuntosNiGuion.length() - 1);

            // 3. Calcular dígito verificador por Módulo 11
            int rutAux = Integer.parseInt(cuerpo);
            int m = 0, s = 1;
            for (; rutAux != 0; rutAux /= 10) {
                s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
            }
            char dvEsperado = (char) (s != 0 ? s + 47 : 75); // ASCII 'K' es 75

            if (dvIngresado != dvEsperado) {
                throw new ValidationException("Dígito verificador del RUT no es válido");
            }
        } catch (NumberFormatException e) {
            throw new ValidationException("El campo RUT no tiene un formato válido");
        }
    }


}
