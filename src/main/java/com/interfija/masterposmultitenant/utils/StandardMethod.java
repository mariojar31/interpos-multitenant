package com.interfija.masterposmultitenant.utils;

import com.interfija.masterposmultitenant.dto.invoice.InvoiceProductTaxDTO;
import com.interfija.masterposmultitenant.dto.other.TypeTaxDTO;
import com.interfija.masterposmultitenant.dto.other.TypeUnitDTO;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase utilitaria que contiene métodos estáticos para formatear y procesar datos relacionados con números,
 * como el formato de porcentaje, formato de moneda, conversión de valores y cálculo de impuestos.
 *
 * @author Steven Arzuza.
 */
public class StandardMethod {

    /**
     * Formatea un valor numérico como porcentaje.
     *
     * @param amount el valor numérico que se va a formatear.
     * @return el valor formateado como porcentaje en formato "es-CO".
     */
    public static String formatPercent(double amount) {
        NumberFormat percentFormat = NumberFormat.getPercentInstance(Locale.of("es", "CO"));
        percentFormat.setMaximumFractionDigits(0);
        return percentFormat.format(amount);
    }

    /**
     * Elimina el formato de porcentaje de un texto, devolviendo solo el valor numérico.
     *
     * @param text el texto que contiene el porcentaje formateado.
     * @return el valor numérico como una cadena.
     */
    public static String removeFormatPercent(String text) {
        double percent = 0;
        try {
            percent = Double.parseDouble(text.replaceAll("[^\\d.,-]", "").trim());
        } catch (NumberFormatException ignored) {
        }
        return String.valueOf(percent == 0 ? "" : percent);
    }

    /**
     * Formatea un valor numérico como moneda colombiana (COP).
     *
     * @param amount el valor numérico que se va a formatear.
     * @return el valor formateado como moneda colombiana.
     */
    public static String formatMoneyColombian(BigDecimal amount) {
        if (amount == null) {
            return "$0.00";
        }

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.of("es", "CO"));

        currencyFormat.setMinimumFractionDigits(2);
        currencyFormat.setMaximumFractionDigits(2);
        String formattedAmount = currencyFormat.format(amount.setScale(2, RoundingMode.HALF_UP));

        formattedAmount = formattedAmount.substring(0, formattedAmount.length() - 3);

        return formattedAmount;
    }

    /**
     * Elimina el formato de moneda colombiana de un texto, devolviendo solo el valor numérico.
     *
     * @param text el texto que contiene el valor de moneda formateado.
     * @return el valor numérico.
     */
    public static BigDecimal removeFormatMoneyColombian(String text) {
        // Eliminar símbolos no numéricos, incluyendo el signo de moneda
        String sanitizedText = text != null ? text.replaceAll("[^\\d,.]", "") : "";

        // Reemplazar la coma por punto solo si está en el último lugar (como separador decimal en formato colombiano)
        int index = sanitizedText.lastIndexOf(",");
        if (index != -1) {
            // Tratamos la última coma como separador decimal
            sanitizedText = sanitizedText.substring(0, index) + "." + sanitizedText.substring(index + 1);
        }

        // Eliminar los puntos que son separadores de miles
        sanitizedText = sanitizedText.replaceAll("\\.", "");

        // Convertir el texto limpio a BigDecimal
        try {
            return new BigDecimal(sanitizedText);
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;  // Si el formato no es correcto, devolver 0
        }
    }

    /**
     * Formatea y redondea una cantidad en moneda colombiana.
     * Convierte el valor redondeado a un String con formato de moneda.
     *
     * @param amount El monto a redondear y formatear.
     * @return El monto redondeado y formateado como moneda colombiana.
     */
    public static String formatRoundingColombianCurrency(BigDecimal amount) {
        return formatMoneyColombian(roundingColombianCurrency(amount));
    }

    /**
     * Redondea una cantidad en moneda colombiana a las centenas más cercanas.
     * <ul>
     *   <li>Si los dos últimos dígitos son menores a 50, redondea hacia abajo.</li>
     *   <li>Si los dos últimos dígitos son mayores a 50, redondea hacia arriba.</li>
     *   <li>Si son exactamente 50, mantiene el valor actual.</li>
     * </ul>
     *
     * @param amount El monto a redondear.
     * @return El monto redondeado a las centenas más cercanas.
     */
    public static BigDecimal roundingColombianCurrency(BigDecimal amount) {
        BigDecimal amountInCents = amount.multiply(BigDecimal.valueOf(100));
        int cents = amountInCents.remainder(BigDecimal.valueOf(100)).intValue();
        BigDecimal rounded;

        if (cents < 50) {
            // Redondear hacia abajo (eliminar los centavos)
            rounded = amountInCents.subtract(BigDecimal.valueOf(cents));
        } else if (cents > 50) {
            // Redondear hacia arriba
            rounded = amountInCents.add(BigDecimal.valueOf(100 - cents));
        } else {
            // Mantener el valor actual
            rounded = amountInCents;
        }

        // Convertimos de nuevo a unidades monetarias
        return rounded.divide(BigDecimal.valueOf(100), 6, RoundingMode.UNNECESSARY);
    }

    public static String visualRoundingToNearest50(BigDecimal amount) {
        if (amount == null) {
            return "$ 0.00";
        }

//        // Redondeamos al múltiplo de 50 más cercano
//        BigDecimal nearest50 = BigDecimal.valueOf(50);
//
//        // Convertimos a pesos (sin centavos) antes de redondear
//        BigDecimal value = amount.setScale(0, RoundingMode.DOWN);
//
//        // Calculamos el residuo respecto a 50
//        BigDecimal remainder = value.remainder(nearest50);
//
//        if (remainder.compareTo(BigDecimal.ZERO) == 0) {
//            return formatMoneyColombian(value);
//        }
//
//        BigDecimal total;
//        if (remainder.compareTo(BigDecimal.valueOf(25)) < 0) {
//            total = value.subtract(remainder); // redondea hacia abajo
//        } else {
//            total = value.add(nearest50.subtract(remainder)); // redondea hacia arriba
//        }

        return formatMoneyColombian(amount);
    }

    /**
     * Obtiene la ventana (Frame o Dialog) asociada a un componente.
     *
     * @param parent el componente cuya ventana asociada se desea obtener.
     * @return la ventana asociada al componente proporcionado.
     */
    public static Window getWindow(Component parent) {
        if (parent == null) {
            return new JFrame();
        } else if (parent instanceof Frame || parent instanceof Dialog) {
            return (Window) parent;
        } else {
            return getWindow(parent.getParent());
        }
    }

    /**
     * Intenta analizar un valor numérico de tipo {@code double} a partir de una cadena de texto.
     * Si el valor no es un número válido, se devuelve 0.
     *
     * @param string la cadena que se intentará convertir a un valor {@code double}.
     * @return el valor numérico como {@code double}, o 0 si la cadena no es válida.
     */
    public static double validateParseDouble(String string) {
        try {
            return Double.parseDouble(string);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Intenta analizar un valor numérico de tipo {@code long} a partir de una cadena de texto.
     * Si la cadena no representa un número válido, se devuelve 0.
     *
     * @param string la cadena que se intentará convertir a un valor {@code long}.
     * @return el valor numérico como {@code long}, o 0 si la cadena no es válida.
     */
    public static long validateParseLong(String string) {
        try {
            return Long.parseLong(string);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Intenta analizar un valor numérico de tipo {@code int} a partir de una cadena de texto.
     * Si la cadena no representa un número válido, se devuelve 0.
     *
     * @param string la cadena que se intentará convertir a un valor {@code int}.
     * @return el valor numérico como {@code int}, o 0 si la cadena no es válida.
     */
    public static int validateParseInt(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Intenta analizar un valor numérico de tipo {@code BigDecimal} a partir de una cadena de texto.
     * Si el valor no es un número válido, se devuelve 0.
     *
     * @param string la cadena que se intentará convertir a un valor {@code BigDecimal}.
     * @return el valor numérico como {@code BigDecimal}, o 0 si la cadena no es válida.
     */
    public static BigDecimal validateParseBigDecimal(String string) {
        if (string == null || string.isBlank()) {
            return BigDecimal.ZERO;
        }

        if (string.startsWith("$")) {
            return removeFormatMoneyColombian(string);
        }

        try {
            return new BigDecimal(string);
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }

    /**
     * Valida un valor de tipo {@code BigDecimal}, retornando {@code BigDecimal.ZERO} si es {@code null}.
     *
     * @param bigDecimal el valor que se desea validar.
     * @return el mismo valor si no es {@code null}, o {@code BigDecimal.ZERO} en caso contrario.
     */
    public static BigDecimal validateParseBigDecimal(BigDecimal bigDecimal) {
        return bigDecimal == null ? BigDecimal.ZERO : bigDecimal;
    }

    /**
     * Calcula el precio de un producto con impuestos incluidos.
     *
     * @param salePrice el precio de venta sin impuestos.
     * @param taxesList la lista de impuestos aplicables al producto.
     * @return el precio con los impuestos incluidos.
     */
    public static BigDecimal calculatePriceWithTax(BigDecimal salePrice, List<TypeTaxDTO> taxesList) {
        return salePrice.add(calculateTaxProduct(salePrice, taxesList));
    }

    /**
     * Calcula el total de impuestos aplicables a un precio de venta.
     *
     * @param salePrice el precio de venta del producto.
     * @param taxesList la lista de impuestos aplicables al producto.
     * @return el total de impuestos.
     */
    public static BigDecimal calculateTaxProduct(BigDecimal salePrice, List<TypeTaxDTO> taxesList) {
        if (taxesList == null || taxesList.isEmpty()) {
            return BigDecimal.ZERO;  // Retorna BigDecimal.ZERO en lugar de 0.0
        }

        return taxesList.stream()
                .map(tax -> salePrice.multiply(tax.getRate()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP))
                .reduce(BigDecimal.ZERO, BigDecimal::add);  // Suma todos los impuestos
    }

    /**
     * Calcula el monto total de impuestos incluidos en un precio con impuestos.
     *
     * @param priceWithTax el precio que ya incluye los impuestos.
     * @param taxesList    la lista de impuestos aplicables, cada uno con su tasa correspondiente.
     * @return el monto total de impuestos contenidos en {@code priceWithTax}.
     * Si la lista de impuestos es {@code null} o está vacía, retorna {@code 0.0}.
     */
    public static BigDecimal extractTaxFromPriceWithTax(BigDecimal priceWithTax, List<TypeTaxDTO> taxesList) {
        if (taxesList == null || taxesList.isEmpty()) {
            return BigDecimal.ZERO;  // Retorna BigDecimal.ZERO en lugar de 0.0
        }

        // Sumar las tasas de impuestos de la lista
        BigDecimal totalRate = taxesList.stream()
                .map(TypeTaxDTO::getRate)
                .reduce(BigDecimal.ZERO, BigDecimal::add);  // Sumar todas las tasas

        // Calcular el impuesto a partir del precio con impuesto
        // Redondeo y precisión
        return priceWithTax.multiply(totalRate)
                .divide(BigDecimal.valueOf(100).add(totalRate), 6, RoundingMode.HALF_UP);
    }

    /**
     * Calcula el total de impuestos aplicables a un precio de venta.
     *
     * @param salePrice el precio de venta de la línea de la factura.
     * @param taxesList la lista de impuestos aplicables a la línea de la factura.
     * @return el total de impuestos.
     */
    public static BigDecimal calculateTaxInvoiceLine(BigDecimal salePrice, List<InvoiceProductTaxDTO> taxesList) {
        if (taxesList == null || taxesList.isEmpty()) {
            return BigDecimal.ZERO;
        }

        return taxesList.stream()
                .map(tax -> {
                    BigDecimal rate = tax.getTypeTaxDTO().getRate();
                    if (rate == null) {
                        return BigDecimal.ZERO;
                    }
                    return salePrice.multiply(
                            rate.divide(BigDecimal.valueOf(100), 6, RoundingMode.HALF_UP)
                    );
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(6, RoundingMode.HALF_UP);
    }

    /**
     * Calcula el monto total de impuestos incluidos en un precio con impuestos.
     *
     * @param priceWithTax el precio que ya incluye los impuestos.
     * @param taxesList    la lista de impuestos aplicables, cada uno con su tasa correspondiente.
     * @return el monto total de impuestos contenidos en {@code priceWithTax}.
     * Si la lista de impuestos es {@code null} o está vacía, retorna {@code 0.0}.
     */
    public static BigDecimal extractTaxFromPriceWithTax2(BigDecimal priceWithTax, List<InvoiceProductTaxDTO> taxesList) {
        if (taxesList == null || taxesList.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal totalRate = taxesList.stream()
                .map(tax -> tax.getTypeTaxDTO().getRate())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal divisor = BigDecimal.valueOf(100).add(totalRate);
        BigDecimal factor = totalRate.divide(divisor, 6, RoundingMode.HALF_UP);

        return priceWithTax.multiply(factor).setScale(6, RoundingMode.HALF_UP);
    }

    /**
     * Obtiene la fecha y hora actual del sistema operativo
     *
     * @return la fecha y hora
     */
    public static String dateTimeNow() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        return localDateTime.format(formatter);
    }

    /**
     * Obtiene la fecha actual del sistema operativo.
     *
     * @return la fecha.
     */
    public static String dateNow() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDateTime.format(formatter);
    }

    /**
     * Valida si una dirección de correo electrónico tiene el formato correcto.
     * El método verifica que la cadena proporcionada cumpla con los estándares
     * de formato de correo electrónico, según una expresión regular.
     *
     * @param mail La dirección de correo electrónico que se desea validar.
     * @return {@code true} si el correo electrónico tiene un formato válido,
     * {@code false} si el formato es inválido o la cadena está vacía.
     */
    public static boolean validateFormatMail(String mail) {
        if (mail.trim().isEmpty()) {
            return false;
        }

        Pattern pat = Pattern.compile(
                "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"" +
                        "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\[\\x01-\\x09\\x0b\\x0c\\x0e-" +
                        "\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:" +
                        "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*" +
                        "[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c" +
                        "\\x0e-\\x7f])+)])");

        Matcher mat = pat.matcher(mail.trim());
        return mat.find();
    }

    /**
     * Calcula el dígito de verificación para un número de identificación según un algoritmo específico.
     * El número de identificación se completa a 12 dígitos si es más corto, y luego se calcula el dígito
     * de verificación utilizando una serie de multiplicaciones y sumas con los primeros 12 dígitos del número.
     *
     * @param identificationNumber El número de identificación sin el dígito de verificación (puede ser de hasta 12 dígitos).
     * @return El dígito de verificación calculado como un String.
     */
    public static String calculateVerificationDigit(String identificationNumber) {
        if (identificationNumber == null || identificationNumber.isEmpty()) {
            return "0";
        }

        String newNumberIdentification = "";
        if (identificationNumber.length() <= 12) {
            for (int i = 0; i < 12 - identificationNumber.length(); i++) {
                newNumberIdentification += "0";
            }
        }
        newNumberIdentification += String.valueOf(Long.parseLong(identificationNumber));
        int newDigit = 0;
        newDigit += Integer.parseInt(newNumberIdentification.substring(0, 1)) * 0;
        newDigit += Integer.parseInt(newNumberIdentification.substring(1, 2)) * 47;
        newDigit += Integer.parseInt(newNumberIdentification.substring(2, 3)) * 43;
        newDigit += Integer.parseInt(newNumberIdentification.substring(3, 4)) * 41;
        newDigit += Integer.parseInt(newNumberIdentification.substring(4, 5)) * 37;
        newDigit += Integer.parseInt(newNumberIdentification.substring(5, 6)) * 29;
        newDigit += Integer.parseInt(newNumberIdentification.substring(6, 7)) * 23;
        newDigit += Integer.parseInt(newNumberIdentification.substring(7, 8)) * 19;
        newDigit += Integer.parseInt(newNumberIdentification.substring(8, 9)) * 17;
        newDigit += Integer.parseInt(newNumberIdentification.substring(9, 10)) * 13;
        newDigit += Integer.parseInt(newNumberIdentification.substring(10, 11)) * 7;
        newDigit += Integer.parseInt(newNumberIdentification.substring(11, 12)) * 3;
        newDigit = newDigit % 11;
        if (newDigit != 0 && newDigit != 1) {
            newDigit = 11 - newDigit;
        }
        return String.valueOf(newDigit);
    }

    public static boolean isEmptyString(String string) {
        return string.isEmpty();
    }

    /**
     * Convierte una cantidad a una unidad de medida dada (por ejemplo, de gramos a kilogramos).
     *
     * @param amount La cantidad a convertir.
     * @param extent La unidad de medida (por ejemplo, "UNIDAD" o "KG").
     * @return La cantidad convertida con la unidad correspondiente.
     */
    public static String convertAmountByExtent(double amount, String extent) {
        if (!extent.equalsIgnoreCase("Unidad")) {
            return String.format("%.3f", amount / 1000).concat(" ").concat("KG").replace(",", ".");
        }
        return String.format("%.0f", amount).concat(" ").concat("UNI");
    }

    /**
     * Formatea el valor del descuento para su correcta representación.
     *
     * @param discountType  Tipo de descuento.
     * @param valueDiscount Valor del descuento a formatear.
     * @return Cadena formateada representando el descuento como dinero o porcentaje.
     */
    public static String formatValueDiscount(String discountType, BigDecimal valueDiscount) {
        if (valueDiscount == null) {
            return "0.0%";
        }

        if ("$".equals(discountType)) {
            return formatMoneyColombian(valueDiscount);
        } else {
            return String.format("%.1f", valueDiscount.setScale(1, RoundingMode.HALF_UP)) + "%";
        }
    }

    /**
     * Formatea el valor de la comisión para su correcta representación.
     *
     * @param commissionType  Tipo de comisión.
     * @param commissionValue Valor de la comisión a formatear.
     * @return Cadena formateada representando la comisión como dinero o porcentaje.
     */
    public static String formatValueCommission(String commissionType, BigDecimal commissionValue) {
        return formatValueDiscount(commissionType, commissionValue);
    }

    /**
     * Formatea la cantidad en función de su unidad de medida y base de conversión.
     *
     * @param quantity    La cantidad registrada en la base de datos.
     * @param typeUnitDTO La unidad de medida.
     * @return Una cadena formateada con la cantidad ajustada a su unidad de medida.
     */
    public static String formatQuantityVisual(BigDecimal quantity, TypeUnitDTO typeUnitDTO) {
        if (typeUnitDTO == null) {
            return "";
        }

        String format;
        if ("Unidad".equalsIgnoreCase(typeUnitDTO.getName())) {
            format = String.format("%.0f", quantity);
        } else if ("Kilogramo".equalsIgnoreCase(typeUnitDTO.getName())) {
            format = String.format("%.3f", quantity);
        } else if ("Litro".equalsIgnoreCase(typeUnitDTO.getName())) {
            format = String.format("%.1f", quantity);
        } else {
            format = String.format("%.2f", quantity);
        }

        return format.concat(" ").concat(typeUnitDTO.getAbbreviation());
    }


    /**
     * Formatea un objeto {@link LocalDateTime} a una cadena representando la fecha y hora en el formato
     * "dd/MM/yyyy, HH:mm:ss" según la zona horaria del sistema.
     *
     * @param dateTime El {@link LocalDateTime} que se desea formatear.
     * @return Una cadena representando la fecha y hora formateada en el formato "dd/MM/yyyy, HH:mm:ss".
     * Si el parámetro {@code dateTime} es {@code null}, se retorna una cadena vacía.
     */
    public static String formatToSystemTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }

        ZoneId systemZone = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = dateTime.atZone(systemZone);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm:ss");
        return zonedDateTime.format(formatter);
    }

    /**
     * Convierte una cadena de fecha y hora en formato "yyyy-MM-dd HH:mm:ss" a un objeto {@link LocalDateTime}.
     *
     * @param dateTimeString La cadena de fecha y hora que se desea convertir.
     * @return Un objeto {@link LocalDateTime} correspondiente a la fecha y hora proporcionada.
     * Si la cadena es {@code null} o tiene un formato inválido, se retorna {@code null}.
     */
    public static LocalDateTime parseToLocalDateTime(String dateTimeString) {
        if (dateTimeString == null || dateTimeString.isBlank()) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            return LocalDateTime.parse(dateTimeString, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Convierte una cadena de fecha en formato "yyyy-MM-dd" a un objeto {@link LocalDate}.
     *
     * @param dateString La cadena de fecha que se desea convertir.
     * @return Un objeto {@link LocalDate} correspondiente a la fecha proporcionada.
     * Si la cadena es {@code null} o tiene un formato inválido, se retorna {@code null}.
     */
    public static LocalDate parseToLocalDate(String dateString) {
        if (dateString == null || dateString.isBlank()) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            return LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Calcula la cantidad de días entre una fecha base y una fecha dada.
     * <p>
     * Si la fecha objetivo está en el futuro respecto a la fecha base, el resultado será positivo;
     * si está en el pasado, será negativo. En caso de error de formato o entrada nula,
     * el método retorna 0.
     *
     * @param baseDate   Fecha base desde la cual calcular la diferencia (no nula).
     * @param targetDate Fecha objetivo en formato "yyyy-MM-dd".
     * @return Número de días entre la fecha base y la fecha objetivo.
     */
    public static long calculateDaysBetween(LocalDate baseDate, String targetDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate parsedTargetDate = LocalDate.parse(targetDate, formatter);
            return ChronoUnit.DAYS.between(baseDate, parsedTargetDate);
        } catch (IllegalArgumentException | DateTimeParseException e) {
            return 0L;
        }
    }

    /**
     * Calcula la fecha resultante a partir de hoy sumando o restando una cantidad de días.
     *
     * @param days Número de días a sumar (positivo) o restar (negativo).
     * @return Fecha en formato "yyyy-MM-dd".
     */
    public static String calculateDateFromDays(long days) {
        try {
            LocalDate resultDate = LocalDate.now().plusDays(days);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return resultDate.format(formatter);
        } catch (Exception e) {
            return String.valueOf(LocalDate.now());
        }
    }

    /**
     * Une una lista de cadenas en una sola cadena, separando los valores con el carácter "|".
     *
     * @param stringList la lista de cadenas a unir.
     * @return una cadena con los valores concatenados separados por "|", o una cadena vacía si la lista está vacía.
     */
    public static String getDataJoiner(List<String> stringList) {
        if (stringList == null || stringList.isEmpty()) {
            return null;
        }

        StringJoiner stringJoiner = new StringJoiner("|");
        stringList.forEach(stringJoiner::add);
        String result = stringJoiner.toString();

        if (result.replace("|", "").isBlank()) {
            return null;
        }

        return result;
    }

    /**
     * Redimensiona un {@link ImageIcon} manteniendo su proporción original para que encaje
     * dentro de las dimensiones especificadas. Si el tamaño original ya es menor o igual
     * que el tamaño objetivo, se devuelve la imagen original.
     *
     * @param originalIcon El {@code ImageIcon} original a redimensionar.
     * @param targetWidth  El ancho máximo deseado.
     * @param targetHeight La altura máxima deseada.
     * @return Un nuevo {@code ImageIcon} redimensionado o el original si no requiere cambios.
     */
    public static ImageIcon resizeImageIcon(ImageIcon originalIcon, int targetWidth, int targetHeight) {
        if (originalIcon == null || originalIcon.getIconWidth() <= 0 || originalIcon.getIconHeight() <= 0) {
            return null;
        }

        int originalWidth = originalIcon.getIconWidth();
        int originalHeight = originalIcon.getIconHeight();

        if (originalWidth <= targetWidth && originalHeight <= targetHeight) {
            return originalIcon;
        }

        double widthRatio = targetWidth / (double) originalWidth;
        double heightRatio = targetHeight / (double) originalHeight;
        double scale = Math.min(widthRatio, heightRatio);

        int newWidth = (int) (originalWidth * scale);
        int newHeight = (int) (originalHeight * scale);

        Image scaledImage = originalIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    /**
     * Redimensiona una imagen {@link BufferedImage} manteniendo su proporción original para que encaje
     * dentro de las dimensiones especificadas. Si el tamaño original ya es menor o igual que
     * el tamaño objetivo, se devuelve la imagen original.
     *
     * @param originalImage La imagen original a redimensionar.
     * @param targetWidth   El ancho máximo deseado.
     * @param targetHeight  La altura máxima deseada.
     * @return Un nuevo {@code BufferedImage} redimensionado o el original si no requiere cambios.
     */
    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        if (originalImage == null) {
            return null;
        }

        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();

        if (originalWidth <= targetWidth && originalHeight <= targetHeight) {
            return originalImage;
        }

        double widthRatio = targetWidth / (double) originalWidth;
        double heightRatio = targetHeight / (double) originalHeight;
        double scale = Math.min(widthRatio, heightRatio);

        int newWidth = (int) (originalWidth * scale);
        int newHeight = (int) (originalHeight * scale);

        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g2d.dispose();

        return resizedImage;
    }

    public static ImageIcon resizeImage(File file, int targetWidth, int targetHeight) throws IOException {
        if (file == null) {
            return null;
        }

        BufferedImage originalImage = ImageIO.read(file);

        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();

        if (originalWidth <= targetWidth && originalHeight <= targetHeight) {
            return new ImageIcon(originalImage);
        }

        double widthRatio = targetWidth / (double) originalWidth;
        double heightRatio = targetHeight / (double) originalHeight;
        double scale = Math.min(widthRatio, heightRatio);

        int newWidth = (int) (originalWidth * scale);
        int newHeight = (int) (originalHeight * scale);

        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g2d.dispose();

        return new ImageIcon(resizedImage);
    }

    public static byte[] imageToBytes(BufferedImage image, String format) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, format, baos);
        return baos.toByteArray();
    }

    public static String getImageFormat(File file) throws IOException {
        try (ImageInputStream iis = ImageIO.createImageInputStream(file)) {
            Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
            if (readers.hasNext()) {
                return readers.next().getFormatName().toLowerCase(); // "png" o "jpeg"
            }
        }
        throw new IOException("No se pudo detectar el formato de la imagen");
    }

    public static LocalDateTime nowUtc() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }

    /**
     * Calcula la utilidad neta de una venta considerando precio de compra, precio de venta,
     * descuentos, retención y comisión.
     *
     * @param purchasePrice   Precio de compra unitario.
     * @param salePrice       Precio de venta unitario.
     * @param discountType    Tipo de descuento: "$" para valor fijo, "%" para porcentaje.
     * @param discountValue   Valor del descuento.
     * @param reteSourceRate  Retención a la fuente aplicada (% si es > 0, puede ser null).
     * @param reteIcaRate     Retención de industria y comercio aplicada (% si es > 0, puede ser null).
     * @param quantity        Cantidad de productos vendidos.
     * @param commissionType  Tipo de comisión: "$" para valor fijo, "%" para porcentaje.
     * @param commissionValue Valor de la comisión.
     * @return Utilidad neta como BigDecimal con escala 2.
     */
    public static BigDecimal calculateUtility(BigDecimal purchasePrice, BigDecimal salePrice, String discountType,
                                              BigDecimal discountValue, BigDecimal reteSourceRate, BigDecimal reteIcaRate,
                                              BigDecimal quantity, String commissionType, BigDecimal commissionValue) throws IllegalArgumentException {

        if (purchasePrice == null || salePrice == null || discountValue == null ||
                quantity == null || commissionValue == null) {
            throw new IllegalArgumentException("Ningún parámetro monetario, ni cantidad puede ser null.");
        }

        // Evitar valores negativos
        purchasePrice = purchasePrice.max(BigDecimal.ZERO);
        salePrice = salePrice.max(BigDecimal.ZERO);
        discountValue = discountValue.max(BigDecimal.ZERO);
        quantity = quantity.max(BigDecimal.ZERO);
        commissionValue = commissionValue.max(BigDecimal.ZERO);
        reteSourceRate = reteSourceRate == null ? BigDecimal.ZERO : reteSourceRate.max(BigDecimal.ZERO);
        reteIcaRate = reteIcaRate == null ? BigDecimal.ZERO : reteIcaRate.max(BigDecimal.ZERO);

        // Precio de venta total
        BigDecimal totalSalePrice = salePrice.multiply(quantity);

        // Aplicar descuento
        BigDecimal totalDiscount = BigDecimal.ZERO;
        if ("%".equals(discountType)) {
            totalDiscount = totalSalePrice.multiply(discountValue.divide(BigDecimal.valueOf(100), 6, RoundingMode.HALF_UP));
        } else if ("$".equals(discountType)) {
            totalDiscount = discountValue.multiply(quantity);
        }

        BigDecimal netSalePrice = totalSalePrice.subtract(totalDiscount);

        // Aplicar comisión
        BigDecimal totalCommission = calculateTotalCommission(commissionType, commissionValue, netSalePrice, quantity);

        // Calcular retenciones (solo reteFuente y reteICA afectan la utilidad)
        BigDecimal reteFuente = netSalePrice.multiply(reteSourceRate.divide(BigDecimal.valueOf(100), 6, RoundingMode.HALF_UP));
        BigDecimal reteIca = netSalePrice.multiply(reteIcaRate.divide(BigDecimal.valueOf(1000), 6, RoundingMode.HALF_UP));
        BigDecimal totalRetention = reteFuente.add(reteIca);

        // Precio de compra total
        BigDecimal totalPurchasePrice = purchasePrice.multiply(quantity);

        // Calcular utilidad
        BigDecimal utility = netSalePrice
                .subtract(totalPurchasePrice)
                .subtract(totalCommission)
                .subtract(totalRetention);

        return utility.setScale(6, RoundingMode.HALF_UP);
    }

    public static BigDecimal calculateTotalCommission(String commissionType, BigDecimal commissionValue, BigDecimal netSalePrice, BigDecimal quantity) {
        if (commissionValue == null || netSalePrice == null || quantity == null) {
            return BigDecimal.ZERO;
        }

        commissionValue = commissionValue.max(BigDecimal.ZERO);
        quantity = quantity.max(BigDecimal.ZERO);
        netSalePrice = netSalePrice.max(BigDecimal.ZERO);

        if ("%".equals(commissionType)) {
            return netSalePrice.multiply(commissionValue.divide(BigDecimal.valueOf(100), 6, RoundingMode.HALF_UP))
                    .multiply(quantity);
        } else if ("$".equals(commissionType)) {
            return commissionValue.multiply(quantity);
        } else {
            return BigDecimal.ZERO;
        }
    }

    public static BigDecimal calculateTotalCommission(String commissionType, BigDecimal commissionValue, BigDecimal netSalePrice) {
        if (commissionValue == null || netSalePrice == null) {
            return BigDecimal.ZERO;
        }

        commissionValue = commissionValue.max(BigDecimal.ZERO);
        netSalePrice = netSalePrice.max(BigDecimal.ZERO);

        if ("%".equals(commissionType)) {
            return netSalePrice.multiply(commissionValue.divide(BigDecimal.valueOf(100), 6, RoundingMode.HALF_UP));
        } else if ("$".equals(commissionType)) {
            return commissionValue;
        } else {
            return BigDecimal.ZERO;
        }
    }

    public static BigDecimal parseWeight(String raw) {
        if (raw == null || raw.length() != 5) {
            throw new IllegalArgumentException("El valor de peso debe tener exactamente 5 caracteres.");
        }

        if (!raw.matches("\\d{5}")) {
            throw new IllegalArgumentException("El valor de peso debe contener solo dígitos (0-9).");
        }

        BigDecimal bigDecimal = new BigDecimal(raw);
        if (bigDecimal.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El valor de peso no puede ser 0");
        }

        return bigDecimal.movePointLeft(3);
    }

    /**
     * Formatea un objeto LocalDateTime a una cadena con solo la fecha en formato yyyy-MM-dd.
     *
     * @param dateTime el objeto LocalDateTime a formatear
     * @return una cadena con la fecha en formato yyyy-MM-dd, o una cadena vacía si ocurre un error
     */
    public static String formatDate(LocalDateTime dateTime) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return dateTime.format(formatter);
        } catch (DateTimeParseException | NullPointerException e) {
            return "";
        }
    }

    /**
     * Formatea un objeto LocalDateTime a una cadena con solo la hora en formato HH:mm:ss.
     *
     * @param dateTime el objeto LocalDateTime a formatear
     * @return una cadena con la hora en formato HH:mm:ss, o una cadena vacía si ocurre un error
     */
    public static String formatTime(LocalDateTime dateTime) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            return dateTime.format(formatter);
        } catch (DateTimeParseException | NullPointerException e) {
            return "";
        }
    }

}
