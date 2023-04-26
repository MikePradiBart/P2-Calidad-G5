package Controladores;
import ORIGEN.Desafio;
import ORIGEN.Usuario;
import  ORIGEN.Personaje;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class UsuarioController {

    public void verRanking() throws IOException, ClassNotFoundException {
        Appcontroller aux = new Appcontroller();
        aux.Ranking();
    }

    public Usuario menuUsuario(Usuario usuario, List<Usuario> usuarios) throws IOException, ClassNotFoundException {
        boolean salir = false;
        while (!salir){
            System.out.println(" ------MENU USUARIO------");
            System.out.println("  1.Crear personaje");
            System.out.println("  2.Eliminar personaje");
            System.out.println("  3.Cambiar equipo activo");
            System.out.println("  4.Lanzar desafio");
            System.out.println("  5.Comprobar desafío pendiente");
            System.out.println("  6.Resultados combates anteriores");
            System.out.println("  7.Ver Ranking global");
            System.out.println("  8.Darse de baja");
            System.out.println("  9.Cerrar sesion");

            int option = Pantalla.pedirenteros("Opcion");

            switch (option){
                case 1://crear personaje
                    Personaje  personaje = usuario.getPersonaje();
                    if (personaje == null){
                        PersonajeController controller = new PersonajeController();
                        controller.registrarPersonaje(usuario);
                    }
                    else {
                        System.out.println("Ya tiene un personaje creado");
                        break;
                    }
                    break;

                case 2://dar de baja personaje
                    usuario.setPersonaje(null);
                    System.out.println("Personaje eliminado con exito");
                    break;
                case 3:// cambiar equipo activo
                    personaje = usuario.getPersonaje();
                    if (personaje == null){
                        System.out.println("No tienes personaje creado");
                        break;
                    }
                    System.out.println("1.Cambiar arma activa");
                    System.out.println("2.Cambiar armadura activa");
                    System.out.println("3.Cancelar");
                    int option1 = Pantalla.pedirenteros("opcion");
                    switch (option1){
                        case 1:
                                PersonajeController contoller = new PersonajeController();
                                personaje = contoller.cambiarArma(personaje);
                                usuario.setPersonaje(personaje);
                            break;

                        case 2:
                                PersonajeController controller = new PersonajeController();
                                personaje = controller.cambiarArmadura(personaje);
                                usuario.setPersonaje(personaje);
                            break;
                        case 3:
                            break;
                    }
                case 4://lanzar desafio
                    if(usuario.getPersonaje()!=null) {
                        desafiar(usuario, usuarios);
                    }else{
                        Pantalla.imprimir("No puedes desafiar, necesitas un personaje");
                    }
                    break;
                case 5://comprobar desafio
                    if (usuario.getDesafio() != null){
                        DesafiosController dcontroller = new DesafiosController();
                        dcontroller.cargarDatos();
                        dcontroller.aceptarDesafio(usuarios, usuario);
                    }
                    else{
                        Pantalla.imprimir("No hay desafios pendientes");
                    }
                    break;
                case 6://ver combates anteriores
                    verCombate(usuario);
                    break;
                case 7://Ver ranking
                    verRanking();
                    break;
                case 8://Dar de baja usuario
                    usuario = null;
                    Pantalla.imprimir("Se elimino el usuario");
                    salir = true;
                    break;
                case 9://cerrar sesion
                    salir= true;
                    break;
            }
        }
        return usuario;
    }




    public List<Usuario> menuOperador(List<Usuario> listaUsuarios, ORIGEN.Operador usu) throws IOException, ClassNotFoundException {
        boolean salir = false;
        while (!salir){
            System.out.println(" ------MENU OPERADOR------");
            System.out.println(" 1.Modificar personaje");
            System.out.println(" 2.Validar desafio");
            System.out.println(" 3.Banear Usuario");
            System.out.println(" 4.Desbanear Ususario");
            System.out.println(" 5.Resultados combate");
            System.out.println(" 6.Darse de baja");
            System.out.println(" 7.Cerrar sesion");

            int o = Pantalla.pedirenteros("Elegir opcion");

            switch (o){
                case 1:
                    for (Usuario a: listaUsuarios){
                        Pantalla.imprimir(a.getNombre());
                    }
                    Pantalla.imprimir("Escribe...  cancelar  ... para Salir");
                    String nombre = Pantalla.pedircadena("Usuario a buscar");
                    if (nombre.equals("cancelar")){
                        Pantalla.imprimir("Saliendo...");
                        break;
                    }
                    Usuario u = seleccionarUsuario(listaUsuarios,nombre);

                    if (u.getPersonaje() == null){
                        Pantalla.imprimir("El usuario no tiene personaje");
                        break;
                    }
                    PersonajeController pjController = new PersonajeController();
                    listaUsuarios.remove(u);
                    u.setPersonaje(pjController.modificarPersonaje(u.getPersonaje()));
                    listaUsuarios.add(u);
                    break;
                case 2:
                    DesafiosController dcontroller = new DesafiosController();
                    dcontroller.validarDesafio(listaUsuarios);
                    break;
                case 3:
                    String banear = Pantalla.pedircadena("Nombre del Usuario a banear:");
                    Usuario baneado = seleccionarUsuario(listaUsuarios,banear);
                    if (baneado != null){
                        baneado.setBaneado(true);
                    }
                    break;
                case 4:
                    banear = Pantalla.pedircadena("Nombre del Usuario a desbanear:");
                    baneado = seleccionarUsuario(listaUsuarios,banear);
                    if (baneado != null){
                        baneado.setBaneado(false);
                    }
                    break;
                case 5:
                    break;
                case 6:
                    usu = null;
                    Pantalla.imprimir("Se elimino el usuario");
                    salir = true;
                    break;
                case 7:
                    salir = true;
                    break;
            }
        }
        return listaUsuarios;
    }

    public Usuario seleccionarUsuario(List<Usuario> listaUsuarios, String nombre) {
        int size = listaUsuarios.size();
        if (size == 0){
            Pantalla.imprimir("No hay Usuarios registrados");
            return null;
        }
        for (Usuario u : listaUsuarios){
            if (u.getNombre().equals(nombre)){
                return u;
            }
        }
        Pantalla.imprimir("No existe tal Usuario");
        return null;
    }
    public void verCombate(Usuario user){
        List<Desafio> lista = new ArrayList<Desafio>();
        try {
            File file = new File("listaDesafiosCompletados.dat");
            if (!file.exists()) {
                file.createNewFile();
            }
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("listaDesafiosCompletados.dat"));
            Object aux = ois.readObject();
            while (aux != null) {
                if (aux instanceof Desafio)
                    lista.add((Desafio) aux);
                aux = ois.readObject();
            }
            ois.close();
        } catch (EOFException e1) {
            //Fin del fichero.
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        for(Desafio d : lista){
            if (d.getUserUno().getNombre().equals(user.getNombre()) || d.getUserUno().getNombre().equals(user.getNombre())) {
                Pantalla.imprimir(d.getUserUno().getNombre() + " vs " + d.getUserDos().getNombre() + ". Oro ganado: " + d.getOroGanado() + ". Fecha: " + d.getFecha() + " Rondas: " + d.getRondas() + ".");
            }
        }
    }
    public void desafiar(Usuario user, List<Usuario> listaUsuarios) throws IOException, ClassNotFoundException {
        if (user.getDesafio() != null) {

        }
        boolean encontrado = false;
        String desafiado = Pantalla.pedircadena("Indica al usuario al que quieres desafiar");

        for (Usuario a : listaUsuarios) {
            if (a.getNombre().equals(desafiado)){
                encontrado = true;
                if(a.getPersonaje()!=null){
                    if (a.getDesafio() != null) {
                        Pantalla.imprimir("El usuario ya tiene un desafío");
                    } else {
                        DesafiosController desafioController = new DesafiosController();
                        Desafio desafio = new Desafio(user, a);

                        desafio.setUserUno(user);
                        desafio.setUserDos(a);
                        desafio.setFecha(LocalDate.now());
                        desafioController.agregarDesafio(desafio);
                        desafio.getOroApostado();
                        desafioController.guardarDatos();
                        Pantalla.imprimir("Desafio guardado a espera de la confirmacion");
                    }
                }
                else{
                    Pantalla.imprimir("No se puede desafiar al usuario "+ a.getNickname()+ "porque no tiene personajes");
                    encontrado=false;
                }
            }
        }
        if (!encontrado){
            Pantalla.imprimir("El usuario no existe");
        }

    }
    public void responderDesafio() {

    }
}

