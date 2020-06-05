package classes;

import java.util.Date;

public class Paciente {
    private String Codigo;
    private String Nome;
    private Date DataNasc;

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String Codigo) {
        this.Codigo = Codigo;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public Date getDataNasc() {
        return DataNasc;
    }

    public void setDataNasc(Date DataNasc) {
        this.DataNasc = DataNasc;
    }
    
    
    
}
