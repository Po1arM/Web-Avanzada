public class Mensaje {
    String fechaGeneracion;
    int idDispositivo;
    float temperatura;
    float humedad;

    public Mensaje(String fechaGeneracion, int idDispositivo, float temperatura, float humedad) {
        this.fechaGeneracion = fechaGeneracion;
        this.idDispositivo = idDispositivo;
        this.temperatura = temperatura;
        this.humedad = humedad;
    }

    public String getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(String fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public int getIdDispositivo() {
        return idDispositivo;
    }

    public void setIdDispositivo(int idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public float getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(float temperatura) {
        this.temperatura = temperatura;
    }

    public float getHumedad() {
        return humedad;
    }

    public void setHumedad(float humedad) {
        this.humedad = humedad;
    }
}
