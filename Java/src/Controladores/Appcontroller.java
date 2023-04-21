package Controladores;

import ORIGEN.Operador;
import ORIGEN.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Appcontroller{
    Scanner scanner = new Scanner(System.in);
    private List<Usuario> usuarios;
    public Appcontroller(){
        usuarios = new ArrayList<>();
    }
    public void iniciarSesion(){
        Scanner sc = new Scanner(System.in);
        String usuario = new String();
        String contraseña = new String();
        System.out.println("Usuario");
        usuario = sc.nextLine();
        System.out.println("Contraseña");
        contraseña = sc.nextLine();
        boolean encontrado = false;

        for (Usuario usu: usuarios){
            if (usu == null){
                continue;
            }
            if (usu.getNombre().equals(usuario) && usu.getContrasena().equals(contraseña)){
                encontrado = true;
                UsuarioController usuarioController = new UsuarioController();
                if(usu.isBaneado()){//COmprobar si esta baneado el personaje
                    System.out.println("Jugador baneado");
                    System.out.println("NO puede iniciar Sesión");
                    System.out.println("Contacte Operador");
                }
                //Comprobar si tiene desafios pendientes
                // no se hacer el observer habra que ver que se hace
               usuarioController.menuUsuario(usu);
            }
            if (!encontrado){
                System.out.println("No existe el usuario...");
                System.out.println(("Regresando al menu"));
            }
        }





    }

    public void registrarse(){
        Scanner sc = new Scanner(System.in);
        Usuario usu = new Usuario();
        System.out.println("Introduce el Nombre");
        usu.setNombre(sc.nextLine());
        System.out.println("Introduce el Nick");
        usu.setNickname(sc.nextLine());
        System.out.println("Introduce la Contraseña");
        usu.setContrasena(sc.nextLine());
        usu.setOro(500);
        usu.setBaneado(false);

        if (usu.getNickname().equals("") || usu.getContrasena().equals("") || usu.getNombre().equals("")){
            System.out.println("Rellena todos los campos");
            return;
        }
        boolean UsuarioExistente = false;
        if (usuarios.isEmpty()){
            usuarios.add(usu);
            System.out.println("Usuario Registrado");
            return;
        }
        for (Usuario aux :usuarios){
            if (aux.getNickname().equals(usu.getNickname())){
                UsuarioExistente = true;
                break;
            }
        }
        if (UsuarioExistente){
            System.out.println("El Usuario ya existe");
        }
        else{
            usuarios.add(usu);
            System.out.println("Usuario creado con éxito");
        }
    }

    public void cargarDatos() throws IOException, ClassNotFoundException {  //metemos el operador a pincho
        Operador operador = new Operador();
        operador.setNickname("OPERADOR");
        operador.setNombre("OPERADOR");
        operador.setContrasena("12345678");
        operador.setBaneado(false);
        operador.setOro(5000);
        operador.setPersonaje(null);
        this.usuarios = cargarUsuarios();

    }

    private List<Usuario>cargarUsuarios() throws IOException,ClassNotFoundException {
        List<Usuario> lista = new ArrayList<Usuario>();
        try {
            File file = new File("listaUsuarios.dat");
            if (!file.exists()){
                file.createNewFile();
                return lista;
            }
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("listaUsuarios.dat"));
            Object aux = ois.readObject();
            while (aux!=null) {
                if (aux instanceof Operador)
                    lista.add((Operador) aux);
                else if (aux instanceof Usuario)
                    lista.add((Usuario) aux);
                aux = ois.readObject();
            }
            ois.close();
        }
        catch (EOFException e1)
        {
            //Fin del fichero.
        }
        return lista;
    }
    public void guardarDatos() throws  IOException{
        guardadrUsuarios(this.usuarios);
    }
    public void guardadrUsuarios(List<Usuario>lista) throws IOException{
        File file = new File("listaUsuarios.dat");
        if (file.exists()){
            file.delete();
            file.createNewFile();
        }
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("listaUsuarios.dat"));
        for (int i = 0; i <lista.size(); i++) {
            if (lista.get(i)==null)
                continue;
            Usuario usuario = lista.get(i);
            oos.writeObject(usuario);
        }
        oos.close();
    }

    public void menu(){
        System.out.println("**----Bienvenido Dark Chronicles----**");
        System.out.println("Seleccione que quiere hacer:");
        System.out.println("  1.Iniciar Sesión");
        System.out.println("  2.Registrarse");
        System.out.println("  3.Salir");
        System.out.println();
    }
    public void start() throws IOException, ClassNotFoundException {
        boolean salir = false;
        while(!salir) {
            menu();
            cargarDatos();
            int option = scanner.nextInt();
            while ((option != 1) && (option != 2) && (option != 3)) {
                System.out.println("La opción escogida no es válida.");
                System.out.println();
                menu();
                option = scanner.nextInt();
            }
            switch (option) {
                case 1:
                    this.iniciarSesion();
                    break;
                case 2:
                    this.registrarse();
                    break;
                case 3:
                    salir = true;
                    System.out.println("Cerrando aplicacion");
                    break;
            }
            guardarDatos();
        }
    }
}
