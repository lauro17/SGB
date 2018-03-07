package Util;

public class UtilCriptografiaDescriptografia {

    public String Converter(String valor_a) {
        char array[] = valor_a.toCharArray();
        for (int i = 0; i < array.length; i++) {
            array[i] = (char) (array[i] + (char) 5);
        }
        String encriptado = String.valueOf(array);
        System.out.println("Criptografia: " + encriptado);
        return encriptado;
    }

    public String Reverter(String valor_b) {
        char arrayD[] = valor_b.toCharArray();
        for (int i = 0; i < arrayD.length; i++) {
            arrayD[i] = (char) (arrayD[i] - (char) 5);
        }
        String desencriptado = String.valueOf(arrayD);
        System.out.println("Descriptografia: " + desencriptado);
        return desencriptado;
    }

}
