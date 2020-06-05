
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utilidades {

    static SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

    public static String DateToString(Date data) {
        return formato.format(data);
    }

    public static Date StringToDate(String data) {
        try {
            return formato.parse(data);
        } catch (ParseException ex) {
            Logger.getLogger(List_Paciente.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static boolean DataValida(String data) {
        try {
            formato.parse(data);
            return true;
        } catch (ParseException ex) {
            return false;
        }
    }
}
