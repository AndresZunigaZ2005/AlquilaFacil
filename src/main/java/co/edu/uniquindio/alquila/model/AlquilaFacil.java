package co.edu.uniquindio.alquila.model;

import co.edu.uniquindio.alquila.exceptions.ClienteException;
import co.edu.uniquindio.alquila.exceptions.FacturaException;
import co.edu.uniquindio.alquila.exceptions.VehiculoException;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@Getter
@Setter
public class AlquilaFacil /*implements Serializable*/ {

    //private final String RUTA = "alquilaFacil.data";

    private ArrayList<Cliente> listaClientes;
    private ArrayList<Vehiculo> listaVehiculos;
    private ArrayList<Factura> listaFactura;

    private static final Logger LOGGER =Logger.getLogger(AlquilaFacil.class.getName());

    private static AlquilaFacil alquilaFacil;
    private AlquilaFacil(){
        try {
            FileHandler fh = new FileHandler("logs.log", true);
            fh.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fh);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }

        LOGGER.log(Level.INFO, "Se crea una nueva instanica de Alquilafacil");
        this.listaClientes = new ArrayList<>();
        this.listaVehiculos = new ArrayList<>();
        this.listaFactura = new ArrayList<>();
        //Deserialización
        leerClientes();
        leerVehiculos();
        leerFactura();
    }

    public static AlquilaFacil getInstance(){
        if (alquilaFacil == null){
            alquilaFacil = new AlquilaFacil();
        }
        return alquilaFacil;
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    /**
     * Metodos para añadir un cliente
     */

    /**
     * Metodo para verificar si ya existe un cliente mediante la cedula
     * @param cedula
     * @return
     */
    public boolean isClienteRegistradoCedula(String cedula){
        for (Cliente cliente:listaClientes) {
            if(cliente.getCedula().equals(cedula)){
                return false;
            }
        }
        return true;
    }

    /**
     * Metodo para verificar si ya existe un cliente mediante el email
     * @param email
     * @return
     */

    public boolean isClienteRegistradoEmail(String email){
        for (Cliente cliente:listaClientes) {
            if(cliente.getEmail().equals(email)){
                return false;
            }
        }
        return true;
    }

    /**
     * Metodo para crear un cliente
     * @param cedula
     * @param nombre
     * @param telefono
     * @param email
     * @param ciudad
     * @param direccion
     * @return
     * @throws ClienteException
     */
    public Cliente crearCliente(String cedula, String nombre, String telefono,
                                String email, String ciudad, String direccion)throws ClienteException {
        if (isClienteRegistradoCedula(cedula) && isClienteRegistradoEmail(email)){
            if (nombre == null || cedula == null || ciudad == null || direccion == null || telefono == null || email == null ||
                    nombre.isBlank() || cedula.isBlank() || ciudad.isBlank() || direccion.isBlank() || telefono.isBlank() || email.isBlank()) {
                LOGGER.log(Level.WARNING, "Todos los campos deben de ser llenados correctamente");
                throw new ClienteException("Los campos obligatorios no están bien escritos, por favor revise nuevamente");
            }else{
                LOGGER.log(Level.INFO, "El cliente de cedula "+ cedula+" se registro a la base de datos correctamente");
                Cliente cliente = new Cliente.ClienteBuilder()
                        .cedula(cedula)
                        .nombre(nombre)
                        .telefono(telefono)
                        .email(email)
                        .ciudad(ciudad)
                        .direccion(direccion)
                        .build();

                try{
                    FileWriter fw = new FileWriter("src/main/resources/persistencia/clientes.txt", true);
                    Formatter f = new Formatter(fw);
                    f.format(cliente.getCedula()+";"+
                            cliente.getNombre()+";"+
                            cliente.getTelefono()+";"+
                            cliente.getEmail()+";"+
                            cliente.getCiudad()+";"+
                            cliente.getDireccion()+"%n");
                    fw.close();
                } catch (FileNotFoundException e) {
                    LOGGER.log(Level.SEVERE,e.getMessage(), e);
                } catch (IOException e) {
                    LOGGER.log(Level.WARNING,e.getMessage());
                }

                listaClientes.add(cliente);
                /*try {
                    ObjectOutputStream flujoSalida = new ObjectOutputStream(new FileOutputStream(RUTA));
                    for (int i = 0; i < listaClientes.size(); i++) {
                        flujoSalida.writeObject(listaClientes.get(i));
                    }
                    flujoSalida.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }*/
                return cliente;
            }
        }
        LOGGER.log(Level.WARNING, "El cliente ya existe");
        throw new ClienteException("El cliente "+nombre+" ya existe");
    }

    public Cliente obtenerCliente(String cedula) throws ClienteException {
        for (Cliente cliente : listaClientes) {
            if(cliente.getCedula().equals(cedula)){
                return cliente;
            }
        }
        throw new ClienteException("El cliente no existe, registrese primero");
    }

    ///////////////////////////////////////////////////////////////////////////////////
    /**
     * Estos son los metodos para añadir un vehiculo
     */

    /**
     * verifica si el vehiculo ya existe mediante la placa
     * @param placa
     * @return
     */
    public boolean isVehiculoRegistradoPlaca(String placa){
        for (Vehiculo vechiculo:listaVehiculos) {
            if(vechiculo.getPlaca().equals(placa)){
                return false;
            }
        }
        return true;
    }

    /**
     * Crea el objeto de vehiculo
     * @param placa
     * @param referencia
     * @param marca
     * @param modelo
     * @param imagen
     * @param kilometraje
     * @param precioDia
     * @param isAutomatico
     * @param numeroSillas
     * @return
     * @throws VehiculoException
     */
    public Vehiculo crearVehiculos(String placa, String referencia, String marca, int modelo,
                               String imagen, double kilometraje, double precioDia, boolean isAutomatico,
                               int numeroSillas) throws VehiculoException {
        if(isVehiculoRegistradoPlaca(placa)){
            if (placa == null || referencia == null || marca == null || modelo == 0 || kilometraje < 0 || precioDia <= 0 || numeroSillas <= 0
            || placa.isBlank() || referencia.isBlank() || marca.isBlank()) {
                LOGGER.log(Level.WARNING, "Todos los campos deben de ser llenados correctamente");
                throw new VehiculoException("Los campos obligatorios no están bien escritos, por favor revise nuevamente");
            }else{
                LOGGER.log(Level.INFO, "El vehiculo de placa "+ placa+" se registro a la base de datos correctamente");
                Vehiculo vehiculo = new Vehiculo.VehiculoBuilder()
                        .placa(placa)
                        .referencia(referencia)
                        .marca(marca)
                        .modelo(modelo)
                        .imagen(imagen)
                        .kilometraje(kilometraje)
                        .precioDia(precioDia)
                        .isAutomatico(isAutomatico)
                        .numeroSillas(numeroSillas)
                        .isDisponible(true)
                        .build();
                listaVehiculos.add(vehiculo);

                try {
                    serializarObjeto("src/main/resources/persistencia/vehiculo.ser", listaVehiculos);
                }catch(IOException e){
                    LOGGER.log(Level.SEVERE, e.getMessage());
                }
                return vehiculo;
            }
        }
        LOGGER.log(Level.WARNING, "El vehículo de placa "+placa+" no se añadio, debido a que ya existe");
        throw new VehiculoException("El vehiculo de placa "+placa+" ya existe");
    }


    /**
     * Se obtiene el vehiculo por la placa
     * @param placa
     * @return
     * @throws VehiculoException
     */
    public Vehiculo obtenerVehiculo(String placa) throws VehiculoException {
        for (Vehiculo vehiculo : listaVehiculos) {
            if(vehiculo.getPlaca().equals(placa)){
                return vehiculo;
            }
        }
        throw new VehiculoException("El vehiculo que desea alquilar no existe");
    }

    ///////////////////////////////////////////////////////////////////////////////////

    /**
     * Metodo relacionados a la factura
     */

    /**
     * Metodo para crear una factura
     * @param cedulaCliente
     * @param placaVehiculo
     * @param pagoFactura
     * @param fechaInicio
     * @param fechaFinal
     * @return
     * @throws FacturaException
     * @throws ClienteException
     * @throws VehiculoException
     */
    public Factura crearFactura(String cedulaCliente, String placaVehiculo,
                             LocalDate pagoFactura, LocalDateTime fechaInicio, LocalDateTime fechaFinal) throws FacturaException, ClienteException, VehiculoException {
        if(fechaInicio.isAfter(fechaFinal)){
            throw new FacturaException("La fecha de inicio no puede ser después a la fecha final");
        }
        if(pagoFactura != null || fechaFinal != null || obtenerCliente(cedulaCliente) != null || obtenerVehiculo(placaVehiculo) != null){
            Vehiculo vehiculo=obtenerVehiculo(placaVehiculo);
            Factura factura = new Factura.FacturaBuilder()
                    .cliente(obtenerCliente(cedulaCliente))
                    .pagoFactura(pagoFactura)
                    .fechaAlquiler(fechaInicio)
                    .fechaRegreso(fechaFinal)
                    .vehiculo(vehiculo)
                    .precioTotal((vehiculo.getPrecioDia()*ChronoUnit.DAYS.between(fechaInicio, fechaFinal)))
                    .build();
            try {
                serializarObjeto("src/main/resources/persistencia/factura.ser", listaFactura);
            }catch(IOException e){
                LOGGER.log(Level.SEVERE, e.getMessage());
            }
            listaFactura.add(factura);
            LOGGER.log(Level.WARNING, "Se creo una nueva factura");
            return factura;
        }else{
            LOGGER.log(Level.WARNING, "Error al crear la factura");
            throw new FacturaException("Los campos obligatorios no están bien escritos, por favor revise nuevamente");
        }
    }


    /**
     * /////////////////////////////////////////////////////////////////////////////////
     */
    public void leerClientes(){
        try(Scanner scanner = new Scanner(new File("src/main/resources/persistencia/clientes.txt"))) {
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                String[] datos = linea.split(";");
                this.listaClientes.add(new Cliente.ClienteBuilder()
                        .cedula(datos[0])
                        .nombre(datos[1])
                        .telefono(datos[2])
                        .email(datos[3])
                        .ciudad(datos[4])
                        .direccion(datos[5])
                        .build());
            }
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    public static void serializarObjeto(String ruta, Object objeto) throws IOException{
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta));
        oos.writeObject(objeto);
        oos.close();
    }

    public static Object deserializarObjeto(String ruta) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta));
        Object objeto = ois.readObject();
        ois.close();
        return objeto;
    }

    private void leerVehiculos(){
        try{
            this.listaVehiculos = (ArrayList<Vehiculo>)deserializarObjeto("src/main/resources/persistencia/vehiculo.ser");
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }


    private void leerFactura(){
        try{
            this.listaFactura = (ArrayList<Factura>)deserializarObjeto("src/main/resources/persistencia/factura.ser");
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }
}
